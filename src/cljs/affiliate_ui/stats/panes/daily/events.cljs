(ns affiliate-ui.stats.panes.daily.events
  (:require
   [re-frame.core :as rf]
   [ajax.core :as ajax]
   [affiliate-ui.config :as cfg]
   [affiliate-ui.stats.panes.daily.db :refer [state-key]]
   [affiliate-ui.stats.utils :refer [format-js-date]]))


(rf/reg-event-db
  ::init
  (fn [db _]
    (assoc db state-key affiliate-ui.stats.panes.daily.db/defaults)))


(rf/reg-event-fx
  ::load-daily-stats
  [(rf/inject-cofx :local-store cfg/local-store-auth-key)]
  (fn [cofx _]
      (let [auth (:local-store cofx)
            db (:db cofx)
            params {:start_date (format-js-date (first  (get-in db [state-key :date])))
                    :end_date   (format-js-date (second (get-in db [state-key :date])))}]
           {:http-xhrio  {:method          :get
                          :uri             (str cfg/URL "/affiliate/stats/daily/")
                          :params          params
                          :headers         {"Authorization" (str "JWT " (:token auth))}
                          :response-format (ajax/json-response-format {:keywords? true})
                          :on-success      [::load-daily-stats-success]
                          :on-failure      [:http-fail]}})))


(rf/reg-event-db
  ::load-daily-stats-success
  (fn [db [_ data]]
    (assoc-in db [state-key :data] data)))


(rf/reg-event-db
  ::set-date
  (fn [db [_ date]]
    (assoc-in db [state-key :date] date)))
