(ns manager-ui.stats.panes.affiliates.events
  (:require
   [re-frame.core :as rf]
   [ajax.core :as ajax]
   [manager-ui.config :as cfg]
   [manager-ui.stats.panes.affiliates.db :refer [state-key]]
   [manager-ui.stats.utils :refer [format-js-date]]
   [manager-ui.stats.db :as stats-db]))


(rf/reg-event-db
  ::init
  (fn [db _]
    (assoc db state-key manager-ui.stats.panes.affiliates.db/defaults)))


(rf/reg-event-fx
  ::load-affiliates-stats
  [(rf/inject-cofx :local-store cfg/local-store-auth-key)]
  (fn [cofx _]
      (let [auth (:local-store cofx)
            db (:db cofx)
            params {:start_date (format-js-date (first  (get-in db [stats-db/state-key :date])))
                    :end_date   (format-js-date (second (get-in db [stats-db/state-key :date])))}]
           {:http-xhrio  {:method          :get
                          :uri             (str cfg/URL "/network/stats/affiliates/")
                          :params          params
                          :headers         {"Authorization" (str "JWT " (:token auth))}
                          :response-format (ajax/json-response-format {:keywords? true})
                          :on-success      [::load-affiliates-stats-success]
                          :on-failure      [:http-fail]}})))


(rf/reg-event-db
  ::load-affiliates-stats-success
  (fn [db [_ data]]
    (assoc-in db [state-key :data] data)))


(rf/reg-event-db
  ::set-date
  (fn [db [_ date]]
    (assoc-in db [stats-db/state-key :date] date)))
