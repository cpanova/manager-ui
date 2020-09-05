(ns manager-ui.stats.views
  (:require
   [reagent.core :as r]
   [re-frame.core :as rf]
   [manager-ui.views.nav :refer [nav]]
   [soda-ash.core :as sa]
   [manager-ui.routes :refer [url-for]]
   [accountant.core :as accountant]
   [manager-ui.stats.panes.daily.views :refer [daily-pane]]
   ; [manager-ui.stats.panes.conversions.views :refer [conversions-pane]]
   [manager-ui.stats.panes.offers.views :refer [offers-pane]]
   [manager-ui.stats.panes.affiliates.views :refer [affiliates-pane]]
   ; [manager-ui.stats.panes.goals.views :refer [goals-pane]]
   ; [manager-ui.stats.panes.subid.views :refer [subid-pane]]
   [manager-ui.stats.subs :as subs]
   [manager-ui.stats.events :as events]))


(defn positions
  [pred coll]
  (keep-indexed (fn [idx x]
                  (when (pred x)
                    idx))
                coll))


(defn position-of [x coll]
  (first (positions #{x} coll)))


(def panes [{:menuItem "Daily"
             :render #(r/as-element [daily-pane])}
            ; {:menuItem "Conversions"
            ;  :render #(r/as-element
            ;            [conversions-pane])}
            {:menuItem "Offers"
             :render #(r/as-element
                       [offers-pane])}
            {:menuItem "Affiliates"
             :render #(r/as-element
                       [affiliates-pane])}])
            ; {:menuItem "Goals"
            ;  :render #(r/as-element
            ;            [goals-pane])}
            ; {:menuItem "Sub1"
            ;  :render #(r/as-element
            ;            [subid-pane 1])}
            ; {:menuItem "Sub2"
            ;  :render #(r/as-element
            ;            [subid-pane 2])}
            ; {:menuItem "Sub3"
            ;  :render #(r/as-element
            ;            [subid-pane 3])}
            ; {:menuItem "Sub4"
            ;  :render #(r/as-element
            ;            [subid-pane 4])}
            ; {:menuItem "Sub5"
            ;  :render #(r/as-element
            ;            [subid-pane 5])}])


(defn stats-panel []
  [:div

   [nav]

   [:div {:class "ui main container"
          :style {:margin-top "60px"}}

    [:h3 "Statistics"]

    [sa/Tab {:menu {:secondary true
                    :pointing  true}
             :panes panes
             :active-index (position-of @(rf/subscribe [::subs/active-report])
                                        (map (comp clojure.string/lower-case :menuItem) panes))
             :on-tab-change #(let [idx (aget %2 "activeIndex")
                                   v   (map (comp clojure.string/lower-case :menuItem) panes)]
                               (accountant/navigate! (url-for :stats :report (nth v idx))))}]]])
