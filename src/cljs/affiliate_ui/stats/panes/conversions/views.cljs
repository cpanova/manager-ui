(ns affiliate-ui.stats.panes.conversions.views
  (:require
   [reagent.core :as r]
   [re-frame.core :as rf]
   [soda-ash.core :as sa]
   [affiliate-ui.stats.panes.conversions.subs :as subs]
   [affiliate-ui.stats.panes.conversions.events :as events]
   [affiliate-ui.views.datepicker :refer [Datepicker]]
   [goog.string :as gstring]
   [goog.string.format]))


(defn conversions-pane []
  [:div
   [:div {:class "ui segments"}
    [sa/Segment {:class "primary"} "Filter"]
    [sa/Segment {:class "primary"}
     [sa/Grid
      [sa/GridRow
       [sa/GridColumn {:width 4}
        [Datepicker {:placeholder "Dates"
                     :size 4
                     :type "range"
                     :on-change #(rf/dispatch [::events/set-date (. %2 -value)])
                     :date-picker-only true
                     :value @(rf/subscribe [::subs/date])
                     :first-day-of-week 1}]]]
      [sa/GridRow
       [sa/GridColumn {:width 4}
        [sa/Button {:class "primary"
                    :on-click #(rf/dispatch [::events/load-conversions])}
         "Filter"]]]]]]

   [sa/Table {:compact true
              :celled true
              :structured true
              :size "small"}
    [sa/TableHeader
     [sa/TableRow
      [sa/TableHeaderCell {:text-align "left"} "Date created"]
      [sa/TableHeaderCell {:text-align "center"} "Offer"]
      [sa/TableHeaderCell {:text-align "center"} "Geo / IP"]
      [sa/TableHeaderCell {:text-align "center"} "Status"]
      [sa/TableHeaderCell {:text-align "right"} "Payout"]
      [sa/TableHeaderCell {:text-align "center"} "Goal"]
      [sa/TableHeaderCell {:text-align "center"} "sub1"]
      [sa/TableHeaderCell {:text-align "center"} "sub2"]
      [sa/TableHeaderCell {:text-align "center"} "sub3"]
      [sa/TableHeaderCell {:text-align "center"} "sub4"]
      [sa/TableHeaderCell {:text-align "center"} "sub5"]
      [sa/TableHeaderCell {:text-align "center"} "User Agent"]
      [sa/TableHeaderCell {:text-align "center"} "ID"]]]
    [sa/TableBody
     (for [conversion @(rf/subscribe [::subs/conversions])]
       [sa/TableRow {:key (:id conversion)}
        [sa/TableCell {:text-align "left"} (:created_at conversion)]
        [sa/TableCell {:text-align "center"}
         (str "(" (get-in conversion [:offer :id]) ")" " ")
         (get-in conversion [:offer :title])]
        [sa/TableCell {:text-align "center"}
         [:div (:country conversion)]
         (:ip conversion)]
        [sa/TableCell {:text-align "center"} (:status conversion)]
        [sa/TableCell {:text-align "right"}
         (gstring/format "%.2f"(:payout conversion))
         " "
         (get-in conversion [:currency :code])]
        [sa/TableCell {:text-align "center"} (:goal conversion)]
        [sa/TableCell {:text-align "center"} (or (:sub1 conversion) "-")]
        [sa/TableCell {:text-align "center"} (or (:sub2 conversion) "-")]
        [sa/TableCell {:text-align "center"} (or (:sub3 conversion) "-")]
        [sa/TableCell {:text-align "center"} (or (:sub4 conversion) "-")]
        [sa/TableCell {:text-align "center"} (or (:sub5 conversion) "-")]
        [sa/TableCell {:text-align "center"}
         [sa/Popup {:content (:ua conversion)
                    :mouse-leave-delay 500
                    :on "click"
                    :position "left center"
                    :trigger (r/as-element
                              [sa/Button {:size "mini" :class "basic" :icon "eye"}])}]]
        [sa/TableCell {:text-align "center"}
         (:id conversion)]])]]])
