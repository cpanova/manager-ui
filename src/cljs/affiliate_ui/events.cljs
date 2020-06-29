(ns affiliate-ui.events
  (:require
   [re-frame.core :as re-frame]
   [day8.re-frame.http-fx]
   [ajax.core :as ajax]
   [affiliate-ui.db :as db]
   [affiliate-ui.routes :refer [url-for]]
   [affiliate-ui.config :as cfg]))


(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))


(re-frame/reg-event-fx
  ::set-active-panel
  (fn [cofx [_ active-panel params query-params]]
      (let [db (:db cofx)
            set-panel (assoc db :active-panel active-panel)]
           (case active-panel
                 :root-panel {:redirect-to (url-for :stats :report "daily")}
                 :offers-panel {:db set-panel
                                ; :dispatch [:affiliate-ui.offers.events/init-offers]
                                :dispatch [:affiliate-ui.offers.events/load-offers]}
                 :stats-panel {:db set-panel
                               :dispatch [:affiliate-ui.stats.events/set-active-report (:report params)]}
                 :offer-panel {:db set-panel
                               :dispatch-n [
                                            ; [:affiliate-ui.offer.events/init (:id params)]]}
                                            [:affiliate-ui.offer.events/load-offer (:id params)]
                                            [:affiliate-ui.offer.events/load-tracking-link (:id params)]]}
                 :login-panel {:db set-panel
                               :dispatch [:affiliate-ui.login.events/reset]}
                 :sign-up-panel {:db set-panel
                                 :dispatch [:affiliate-ui.sign-up.events/reset]}
                 {:db set-panel}))))


(re-frame/reg-event-fx
  ::redirect
  (fn [cofx [_ url]]
      {:redirect-to url}))


(re-frame/reg-event-fx
  ::logout
  (fn [cofx _]
      {:remove-local-store cfg/local-store-auth-key
       :redirect-to (url-for :login)}))


(re-frame/reg-event-fx
  :http-fail
  (fn [cofx [_ {:keys [status]}]]
    (when (= status 401)
      {:dispatch [::logout]})))


; (re-frame/reg-event-fx
;   ::load-countries
;   (fn [cofx _]
;       {:http-xhrio  {:method          :get
;                      :uri             (str cfg/URL "/api/countries/")
;                      ; :params          params
;                      ; :headers         {"Authorization" (str "JWT " (:token auth))}
;                      :response-format (ajax/json-response-format {:keywords? true})
;                      :on-success      [::load-countries-success]
;                      :on-failure      [:http-fail]}}))
;
;
; (re-frame/reg-event-db
;   ::load-countries-success
;   (fn [db [_ countries]]
;     (assoc db :countries countries)))
