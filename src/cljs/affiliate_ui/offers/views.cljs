(ns affiliate-ui.offers.views
  (:require
   [re-frame.core :as rf]
   [affiliate-ui.views.nav :refer [nav]]
   [soda-ash.core :as sa]
   [affiliate-ui.routes :refer [url-for]]
   [affiliate-ui.offers.subs :as subs]
   [affiliate-ui.offers.events]))


(defn offers-panel []
  [:div

   [nav]

   [:div {:class "ui main container"
          :style {:margin-top "60px"}}

    [:h3 "Offers"]

    [sa/Table {:celled  true
               :striped true}
     [sa/TableHeader
      [sa/TableRow
       [sa/TableHeaderCell "ID"]
       [sa/TableHeaderCell "Offer"]
       [sa/TableHeaderCell "Payout"]
       [sa/TableHeaderCell "Geo"]
       [sa/TableHeaderCell "Categories"]
       [sa/TableHeaderCell ""]]]
     [sa/TableBody
      (for [offer @(rf/subscribe [::subs/offers])]
        [sa/TableRow {:key (:id offer)}
         [sa/TableCell (:id offer)]
         [sa/TableCell [:a {:href (url-for :offer :id (:id offer))} (:title offer)]]
         [sa/TableCell
          [:div
           (get-in (:payouts offer) [0 :payout]) " "
           (get-in (:payouts offer) [0 :currency :code]) " "]
          (get-in (:payouts offer) [0 :goal :name])]
         [sa/TableCell
          (for [country (take 3 (:countries offer))]
            [:div {:key (:code country)}
             [sa/Flag {:name (clojure.string/lower-case (:iso country))}] (:iso country)])
          (when (> (count (:countries offer)) 3)
            [:div "..."])]
         [sa/TableCell
          (for [category (:categories offer)]
           [:div {:key (:name category)}
            (:name category)])]
         [sa/TableCell {:text-align "center"}
          [:a {:href (:preview_link offer) :target "_blank"}
           [sa/Icon {:name "eye"}]]]])]]]])
