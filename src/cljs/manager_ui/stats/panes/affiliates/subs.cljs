(ns manager-ui.stats.panes.affiliates.subs
  (:require
   [re-frame.core :as rf]
   [manager-ui.stats.panes.affiliates.db :refer [state-key]]
   [manager-ui.stats.db :as stats-db]))


(rf/reg-sub
 ::data
 (fn [db _]
   (get-in db [state-key :data])))


(rf/reg-sub
 ::date
 (fn [db _]
   (get-in db [stats-db/state-key :date])))
