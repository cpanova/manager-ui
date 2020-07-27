(ns affiliate-ui.offer.views
  (:require
   [re-frame.core :as rf]
   [affiliate-ui.views.nav :refer [nav]]
   [soda-ash.core :as sa]
   [affiliate-ui.offer.subs :as subs]
   [affiliate-ui.offer.events]
   [hickory.core :as hickory]))


(defn offer-panel []
  (let [offer @(rf/subscribe [::subs/offer])]
    [:div

     [nav]

     [:div {:class "ui main container"
            :style {:margin-top "60px"}}

      [:h3 (:title offer)]

      [sa/Segment
       [:h4 "Information"]
       [:div (map hickory/as-hiccup (hickory/parse-fragment (:description_html offer)))]

       [:h4 "Tracking Link"]
       [sa/Input {:value @(rf/subscribe [::subs/tracking-link])
                  :style {:width "100%"}}]

       [:h4 "Geo and Payout"]
       [sa/Table {:celled  true
                  :striped true}
        [sa/TableBody
         (for [payout (:payouts offer)]
           [sa/TableRow {:key (:id payout)}
            [sa/TableCell
             (for [country (:countries payout)]
               [:div {:key (:code country)}
                [sa/Flag {:name (clojure.string/lower-case (:iso country))}] (:iso country)])]
            [sa/TableCell
             (:payout payout) " "
             (get-in payout [:currency :code])]
            [sa/TableCell (get-in payout [:goal :name])]])]]

       [:h4 "Traffic Sources"]
       [sa/Table {:celled  false
                  :striped true}
        [sa/TableBody
         (for [traffic_source (:traffic_sources offer)]
           [sa/TableRow {:key (:id traffic_source)}
            [sa/TableCell (:name traffic_source)]
            [sa/TableCell
             [:span {:style {:color (if (:allowed traffic_source)
                                        "limegreen"
                                        "red")}}
              [sa/Icon {:name (if (:allowed traffic_source)
                                  "check circle"
                                  "ban")}]
              (if (:allowed traffic_source)
                  "Allowed"
                  "Restricted")]]])]]]]]))
