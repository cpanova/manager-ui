(ns manager-ui.stats.panes.affiliates.views
  (:require
   [re-frame.core :as rf]
   [soda-ash.core :as sa]
   [manager-ui.stats.panes.affiliates.subs :as subs]
   [manager-ui.stats.panes.affiliates.events :as events]
   [manager-ui.views.datepicker :refer [Datepicker]]
   [goog.string :as gstring]
   [goog.string.format]))

(defn affiliates-pane []
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
                    :on-click #(rf/dispatch [::events/load-affiliates-stats])}
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
         "Affiliate"]
        [sa/TableHeaderCell  {:row-span 2
                              :text-align "right"}
         "Clicks"]
        [sa/TableHeaderCell  {:col-span 2
                              :text-align "center"}
         "Approved"]
        [sa/TableHeaderCell  {:col-span 2
                              :text-align "center"}
         "Hold"]
        [sa/TableHeaderCell  {:col-span 2
                              :text-align "center"}
         "Rejected"]
        [sa/TableHeaderCell  {:row-span 2
                              :text-align "right"}
         "CR"]
        [sa/TableHeaderCell  {:col-span 4
                              :text-align "center"}
         "Total"]]
       [sa/TableRow
        [sa/TableHeaderCell {:text-align "right"} "QTY"]
        [sa/TableHeaderCell {:text-align "right"} "Revenue"]
        [sa/TableHeaderCell {:text-align "right"} "QTY"]
        [sa/TableHeaderCell {:text-align "right"} "Revenue"]
        [sa/TableHeaderCell {:text-align "right"} "QTY"]
        [sa/TableHeaderCell {:text-align "right"} "Revenue"]
        [sa/TableHeaderCell {:text-align "right"} "QTY"]
        [sa/TableHeaderCell {:text-align "right"} "Revenue"]
        [sa/TableHeaderCell {:text-align "right"} "Payouts"]
        [sa/TableHeaderCell {:text-align "right"} "Profit"]]]
      [sa/TableBody
       (for [row @(rf/subscribe [::subs/data])]
         [sa/TableRow {:key (:affiliate_id row)}
          [sa/TableCell (str "(" (:affiliate_id row) ") " (:affiliate_name row))]
          [sa/TableCell {:text-align "right"} (:clicks row)]
          [sa/TableCell {:text-align "right"} (:approved_qty row)]
          [sa/TableCell {:text-align "right"} (gstring/format "%.2f" (:approved_revenue row))]
          [sa/TableCell {:text-align "right"} (:hold_qty row)]
          [sa/TableCell {:text-align "right"} (gstring/format "%.2f" (:hold_revenue row))]
          [sa/TableCell {:text-align "right"} (:rejected_qty row)]
          [sa/TableCell {:text-align "right"} (gstring/format "%.2f" (:rejected_revenue row))]
          [sa/TableCell {:text-align "right"} (str (:cr row) "%")]
          [sa/TableCell {:text-align "right"} (:total_qty row)]
          [sa/TableCell {:text-align "right"} (gstring/format "%.2f" (:total_revenue row))]
          [sa/TableCell {:text-align "right"} (gstring/format "%.2f" (:total_payout row))]
          [sa/TableCell {:text-align "right"} (gstring/format "%.2f" (:total_profit row))]])]]]]])
