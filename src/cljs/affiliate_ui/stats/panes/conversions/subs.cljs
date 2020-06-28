(ns affiliate-ui.stats.panes.conversions.subs
  (:require
   [re-frame.core :as rf]
   [affiliate-ui.stats.panes.conversions.db :refer [state-key]]))


(rf/reg-sub
 ::conversions
 (fn [db _]
   (get-in db [state-key :data])))


(rf/reg-sub
 ::date
 (fn [db _]
   (get-in db [state-key :date])))
