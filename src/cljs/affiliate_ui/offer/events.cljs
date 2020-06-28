(ns affiliate-ui.offer.events
  (:require
   [re-frame.core :as rf]
   [ajax.core :as ajax]
   [affiliate-ui.config :as cfg]))


(def offer {:id 1
            :title "LeonBets"
            :description "
       Оплачивается депозит 50 руб

       Важно! Лид засчитывается если депозит выполняет условие сразу или в течении 7-ми суток с даты первого депозита.

       Гео: Россия

       Таргет: мужская аудитория 23-35+"
            :payouts [{:id 1
                       :countries [{:code "RU"} {:code "UA"} {:code "BY"} {:code "KZ"}]
                       :goal "First Deposit"
                       :payout 10
                       :currency {:code "USD"}}
                      {:id 2
                       :countries [{:code "AZ"} {:code "AM"}]
                       :goal "First Deposit"
                       :payout 5
                       :currency {:code "USD"}}]
            :countries [{:code "RU"}
                        {:code "UA"}
                        {:code "BY"}
                        {:code "KZ"}]
            :categories [{:id 1 :name "Betting"}
                         {:id 2 :name "Gambling"}]
            :preview_url "https://ya.ru"
            :traffic_sources [{:id 1 :traffic_source "Веб-сайты" :allowed true}
                              {:id 2 :traffic_source "Дорвеи" :allowed true}
                              {:id 3 :traffic_source "Мотивированный трафик" :allowed false}
                              {:id 4 :traffic_source "Мобильный трафик" :allowed false}]})


(def tracking-link "https://gocpa.g2afse.com/click?pid=1&offer_id=88")


(rf/reg-event-db
  ::init
  (fn [db _]
    (-> db
        (assoc :offer offer)
        (assoc :tracking-link tracking-link))))


(rf/reg-event-fx
  ::load-offer
  [(rf/inject-cofx :local-store cfg/local-store-auth-key)]
  (fn [cofx [_ offer-id]]
      (let [auth (:local-store cofx)]
           {:http-xhrio  {:method          :get
                          :uri             (str cfg/URL "/affiliate/offers/" offer-id "/")
                          ; :params          params
                          :headers         {"Authorization" (str "JWT " (:token auth))}
                          :response-format (ajax/json-response-format {:keywords? true})
                          :on-success      [::load-offer-success]
                          :on-failure      [:http-fail]}})))


(rf/reg-event-db
  ::load-offer-success
  (fn [db [_ offer]]
    (assoc db :offer offer)))


(rf/reg-event-fx
  ::load-tracking-link
  [(rf/inject-cofx :local-store cfg/local-store-auth-key)]
  (fn [cofx [_ offer-id]]
      (let [auth (:local-store cofx)]
           {:http-xhrio  {:method          :get
                          :uri             (str cfg/URL "/affiliate/offers/" offer-id "/tracking-link/")
                          ; :params          params
                          :headers         {"Authorization" (str "JWT " (:token auth))}
                          :response-format (ajax/json-response-format {:keywords? true})
                          :on-success      [::load-tracking-link-success]
                          :on-failure      [:http-fail]}})))


(rf/reg-event-db
  ::load-tracking-link-success
  (fn [db [_ resp]]
    (assoc db :tracking-link (:url resp))))
