(ns affiliate-ui.stats.panes.goals.views
  (:require
   [re-frame.core :as rf]
   [soda-ash.core :as sa]
   [affiliate-ui.stats.panes.goals.subs :as subs]
   [affiliate-ui.stats.panes.goals.events :as events]
   [affiliate-ui.views.datepicker :refer [Datepicker]]
   [goog.string :as gstring]
   [goog.string.format]))

(defn goals-pane []
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
                    :on-click #(rf/dispatch [::events/load-goals-stats])}
         "Filter"]]]]]]

   [:div {:class "ui segments"}
    [sa/Segment {:class "primary"} "Statistics"]
    [sa/Segment {:class "primary"}
     [sa/Table {:compact true
                :celled true
                :structured true
                :size "small"}
      [sa/TableHeader
       [sa/TableRow
        [sa/TableHeaderCell {:row-span 2}
         "Goal"]
        [sa/TableHeaderCell  {:col-span 4
                              :text-align "center"}
         "Conversions"]
        [sa/TableHeaderCell  {:col-span 4
                              :text-align "center"}
         "Payout"]]
       [sa/TableRow
        [sa/TableHeaderCell {:text-align "right"} "Total"]
        [sa/TableHeaderCell {:text-align "right"} "Approved"]
        [sa/TableHeaderCell {:text-align "right"} "Hold"]
        [sa/TableHeaderCell {:text-align "right"} "Rejected"]
        [sa/TableHeaderCell {:text-align "right"} "Total"]
        [sa/TableHeaderCell {:text-align "right"} "Approved"]
        [sa/TableHeaderCell {:text-align "right"} "Hold"]
        [sa/TableHeaderCell {:text-align "right"} "Rejected"]]]
      [sa/TableBody
       (for [row @(rf/subscribe [::subs/data])]
         [sa/TableRow {:key (:goal_id row)}
          [sa/TableCell (:goal_name row)]
          [sa/TableCell {:text-align "right"} (:total_qty row)]
          [sa/TableCell {:text-align "right"} (:approved_qty row)]
          [sa/TableCell {:text-align "right"} (:hold_qty row)]
          [sa/TableCell {:text-align "right"} (:rejected_qty row)]
          [sa/TableCell {:text-align "right"} (gstring/format "%.2f" (:total_payout row))]
          [sa/TableCell {:text-align "right"} (gstring/format "%.2f" (:approved_payout row))]
          [sa/TableCell {:text-align "right"} (gstring/format "%.2f" (:hold_payout row))]
          [sa/TableCell {:text-align "right"} (gstring/format "%.2f" (:rejected_payout row))]])]]]]])
