(ns affiliate-ui.offers.events
  (:require
   [re-frame.core :as rf]
   [ajax.core :as ajax]
   [affiliate-ui.config :as cfg]))


(def offers [{:id 1 :title "LeonBets" :payouts [{:id 1 :goal "First Deposit" :payout 10 :currency {:code "USD"}}] :countries [{:code "RU"} {:code "UA"} {:code "BY"} {:code "KZ"}] :categories [{:id 1 :name "Betting"} {:id 2 :name "Gambling"}] :preview_url "https://ya.ru"}
             {:id 2 :title "1xBet" :payouts [{:id 1 :goal "First Deposit" :payout 10 :currency {:code "USD"}}] :countries [{:code "RU"} {:code "UA"} {:code "BY"}] :categories [{:id 1 :name "Betting"}]}
             {:id 3 :title "MelBet" :payouts [{:id 1 :goal "First Deposit" :payout 10 :currency {:code "USD"}}] :countries [{:code "RU"} {:code "UA"} {:code "BY"}] :categories [{:id 1 :name "Betting"}]}])


(rf/reg-event-db
  ::init-offers
  (fn [db _]
    (assoc db :offers offers)))


(rf/reg-event-fx
  ::load-offers
  [(rf/inject-cofx :local-store cfg/local-store-auth-key)]
  (fn [cofx _]
      (let [auth (:local-store cofx)]
           {:http-xhrio  {:method          :get
                          :uri             (str cfg/URL "/affiliate/offers/")
                          ; :params          params
                          :headers         {"Authorization" (str "JWT " (:token auth))}
                          :response-format (ajax/json-response-format {:keywords? true})
                          :on-success      [::load-offers-success]
                          :on-failure      [:http-fail]}})))


(rf/reg-event-db
  ::load-offers-success
  (fn [db [_ offers]]
    (assoc db :offers offers)))
