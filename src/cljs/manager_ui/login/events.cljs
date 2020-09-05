(ns manager-ui.login.events
  (:require
   [re-frame.core :as rf]
   [ajax.core :as ajax]
   [manager-ui.routes :refer [url-for]]
   [manager-ui.config :as cfg :refer [local-store-auth-key URL]]
   [manager-ui.login.db :refer [state-key]]))


(rf/reg-event-fx
  ::login
  (fn [cofx _]
      (let [db     (:db cofx)
            params {:username (get-in db [state-key :email])
                    :password (get-in db [state-key :password])}]
           {:http-xhrio {:method          :post
                         :uri             (str URL "/auth/")
                         :params          params
                         :format          (ajax/json-request-format)
                         :response-format (ajax/json-response-format {:keywords? true})
                         :on-success      [::login-success]
                         :on-failure      [::login-fail]}})))


(rf/reg-event-fx
  ::login-success
  (fn [{:keys [db]} [_ {:keys [token]}]]
      {:set-local-store [local-store-auth-key {:token token}]
       :redirect-to (url-for :stats :report "daily")}))


(rf/reg-event-fx
  ::login-fail
  (fn [cofx _]
      (let [db (:db cofx)
            set-error (-> db
                          (assoc-in [state-key :error :show?] true)
                          (assoc-in [state-key :error :message] "Failed"))]
           {:db set-error})))


(rf/reg-event-db
  ::reset
  (fn [db _]
    (assoc db state-key manager-ui.login.db/defaults)))


(rf/reg-event-db
  ::set-value
  (fn [db [_ field value]]
      (assoc-in db [state-key field] value)))
