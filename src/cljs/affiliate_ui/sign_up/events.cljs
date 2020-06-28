(ns affiliate-ui.sign-up.events
  (:require
   [re-frame.core :as rf]
   [ajax.core :as ajax]
   [affiliate-ui.routes :refer [url-for]]
   [affiliate-ui.config :as cfg :refer [URL]]
   [affiliate-ui.sign-up.db :refer [state-key]]))


(rf/reg-event-fx
  ::sign-up
  (fn [cofx _]
    (let [db     (:db cofx)
          params {:email (get-in db [state-key :email])
                  :password (get-in db [state-key :password])
                  "confirm_password"  (get-in db [state-key :confirm])
                  :last_name (get-in db [state-key :messenger])}]
      {:http-xhrio {:method          :post
                    :uri             (str URL "/affiliate/sign-up/")
                    :params          params
                    :format          (ajax/json-request-format)
                    :response-format (ajax/json-response-format {:keywords? true})
                    :on-success      [::sign-up-success]
                    :on-failure      [::sign-up-fail]}})))


(rf/reg-event-fx
  ::sign-up-success
  (fn [cofx _]
    {:redirect-to (url-for :login)}))


(rf/reg-event-fx
  ::sign-up-fail
  (fn [cofx [_ data]]
    (let [db (:db cofx)
          error-msg (-> data
                        :response
                        vals
                        first
                        first)
          set-error (-> db
                        (assoc-in [state-key :error :show?] true)
                        (assoc-in [state-key :error :message] (str error-msg)))]
      {:db set-error})))


(rf/reg-event-db
  ::reset
  (fn [db _]
    (assoc db state-key affiliate-ui.sign-up.db/defaults)))


(rf/reg-event-db
  ::set-value
  (fn [db [_ field value]]
    (assoc-in db [state-key field] value)))
