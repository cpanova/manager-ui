(ns affiliate-ui.views.nav
  (:require
    [re-frame.core :as rf]
    [affiliate-ui.events :as events]
    ; [klo.subs :as subs]))
    [soda-ash.core :as sa]
    [affiliate-ui.routes :refer [url-for]]
    [affiliate-ui.config :as cfg]))


; (def menu [
;            {:label "Campaigns" :route :campaigns}
;            {:label "Login" :route :login}
;            {:label "Registration" :route :registration}])


(defn nav []
  [:div.ui.fixed.menu
   [:div.ui.container
    [:span.item "CPA Nova"]
    [:a.item {:href (url-for :offers)} "Offers"]
    [:a.item {:href (url-for :stats :report "daily")} "Statistics"]
    [:div.right.menu
     [:a.item {:href (str  "https://teleg.run/" cfg/TELEGRAM_ID)
               :target "_blank"}
      [sa/Icon {:name "send"}] "Support"]
     [:a.item {:on-click #(rf/dispatch [::events/logout])} "Logout"]]]])


; (defn nav []
;   [sa/Menu {:class "fixed"}
;    [sa/Container
;     [sa/MenuItem {:name "Campaigns"
;                   :on-click #(rf/dispatch [::events/redirect (url-for :campaigns)])}]
;     [sa/MenuItem {:class "right"
;                   :name "Logout"
;                   :on-click #(rf/dispatch [::events/logout])}]]])
