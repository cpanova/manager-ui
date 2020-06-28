FROM theasp/clojurescript-nodejs:latest as build
WORKDIR /usr/src/app
ARG http_proxy
COPY project.clj /usr/src/app/project.clj
RUN lein deps
COPY ./ /usr/src/app/
RUN npm install
RUN lein prod


FROM nginx:stable
COPY --from=build /usr/src/app/resources/* /var/www/
COPY ./deploy/nginx.conf /etc/nginx/conf.d/default.conf.template
COPY ./deploy/entrypoint.sh /entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]
EXPOSE 80
CMD ["nginx","-g","daemon off;"]
