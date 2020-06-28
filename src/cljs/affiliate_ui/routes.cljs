(ns affiliate-ui.routes
  (:import [goog History]
           [goog.history EventType])
  (:require
   [re-frame.core :as re-frame]
   [bidi.bidi :as bidi]
   [pushy.core :as pushy]
   [accountant.core :as accountant]
   [cemerick.url]))


(def routes ["/" {["stats/" :report] :stats
                  ; ["campaign/" :id "/edit"] :campaign-edit
                  ["offer/" :id] :offer
                  ; ["campaign/" :id "/js-code"] :js-code
                  ; ["campaign/" :id "/js-iframe"] :js-iframe
                  ; "campaign/add"   :campaign-create
                  "offers" :offers
                  ; "domain/add" :domain-add
                  ; ["domain/" :zone-name] :domain
                  "login" :login
                  ;           ["/" :id] {"" :offer-edit
                  ;                      "/payments" :offer-edit-payments}}
                  ; "clicks" :clicks
                  "sign-up" :sign-up}])


(defn match-route-with-query-params
  [route path & {:as options}]
  (let [query-params (->> (:query (cemerick.url/url path))
                          (map (fn [[k v]] [(keyword k) v]))
                          (into {}))]
    (-> (bidi/match-route* route path options)
        (assoc :query-params query-params))))

; (defn- parse-url [url]
  ; (bidi/match-route routes url))
(defn- parse-url [url]
  (match-route-with-query-params routes url))

(defn- dispatch-route [matched-route]
  (let [panel-name (keyword (str (name (:handler matched-route)) "-panel"))]
    (re-frame/dispatch [:affiliate-ui.events/set-active-panel panel-name (:route-params matched-route) (:query-params matched-route)])))

(defn app-routes []
  (pushy/start! (pushy/pushy dispatch-route parse-url)))

(def url-for (partial bidi/path-for routes))


(defn configure-navigation! []
  (accountant/configure-navigation!
    {:nav-handler   (fn [path] (-> path
                                   (parse-url)
                                   (dispatch-route)))
     :path-exists?  (fn [path]
                     (boolean (bidi/match-route app-routes path)))}))
