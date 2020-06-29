(ns affiliate-ui.stats.panes.offers.subs
  (:require
   [re-frame.core :as rf]
   [affiliate-ui.stats.panes.offers.db :refer [state-key]]
   [affiliate-ui.stats.db :as stats-db]))


(rf/reg-sub
 ::data
 (fn [db _]
   (get-in db [state-key :data])))


(rf/reg-sub
 ::date
 (fn [db _]
   (get-in db [stats-db/state-key :date])))
