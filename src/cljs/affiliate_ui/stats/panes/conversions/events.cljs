(ns affiliate-ui.stats.panes.conversions.events
  (:require
   [re-frame.core :as rf]
   [ajax.core :as ajax]
   [affiliate-ui.config :as cfg]
   [affiliate-ui.stats.panes.conversions.db :refer [state-key]]
   [affiliate-ui.stats.utils :refer [format-js-date]]))


; (def conversions [{:id "5ebea4476a10750001cd6998"
;                    :created_at "2020-05-15 17:20:21"
;                    :offer {:id 1 :title "Gratorama [SK] CPL"}
;                    :country {:code "BG"}
;                    :ip "195.91.16.0"
;                    :status "Approved"
;                    :payout 3.5
;                    :currency {:code "EUR"}
;                    :goal "Registration"
;                    :sub1 nil
;                    :sub2 nil
;                    :sub3 nil
;                    :sub4 nil
;                    :sub5 nil
;                    :user_agent "Mozilla/5.0 (Linux; Android 9; SM-A705FN Build/PPR1.180610.011; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/81.0.4044.138 Mobile Safari/537.36"}])


(rf/reg-event-db
  ::init
  (fn [db _]
    (assoc db state-key affiliate-ui.stats.panes.conversions.db/defaults)))


(rf/reg-event-fx
  ::load-conversions
  [(rf/inject-cofx :local-store cfg/local-store-auth-key)]
  (fn [cofx _]
      (let [auth (:local-store cofx)
            db (:db cofx)
            params {:start_date (format-js-date (first (get-in db [state-key :date])))
                    :end_date  (format-js-date (second (get-in db [state-key :date])))}]
           {:http-xhrio  {:method          :get
                          :uri             (str cfg/URL "/affiliate/conversions/")
                          :params          params
                          :headers         {"Authorization" (str "JWT " (:token auth))}
                          :response-format (ajax/json-response-format {:keywords? true})
                          :on-success      [::load-conversions-success]
                          :on-failure      [:http-fail]}})))


(rf/reg-event-db
  ::load-conversions-success
  (fn [db [_ data]]
    (assoc-in db [state-key :data] data)))


(rf/reg-event-db
  ::set-date
  (fn [db [_ date]]
    (assoc-in db [state-key :date] date)))
