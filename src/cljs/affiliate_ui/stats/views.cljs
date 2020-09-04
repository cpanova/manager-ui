(ns affiliate-ui.stats.views
  (:require
   [reagent.core :as r]
   [re-frame.core :as rf]
   [affiliate-ui.views.nav :refer [nav]]
   [soda-ash.core :as sa]
   [affiliate-ui.routes :refer [url-for]]
   [accountant.core :as accountant]
   [affiliate-ui.stats.panes.daily.views :refer [daily-pane]]
   [affiliate-ui.stats.panes.conversions.views :refer [conversions-pane]]
   [affiliate-ui.stats.panes.offers.views :refer [offers-pane]]
   [affiliate-ui.stats.panes.goals.views :refer [goals-pane]]
   [affiliate-ui.stats.subs :as subs]
   [affiliate-ui.stats.events :as events]))


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
            {:menuItem "Conversions"
             :render #(r/as-element
                       [conversions-pane])}
            {:menuItem "Offers"
             :render #(r/as-element
                       [offers-pane])}
            {:menuItem "Goals"
             :render #(r/as-element
                       [goals-pane])}])


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
