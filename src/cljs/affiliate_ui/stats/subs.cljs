(ns affiliate-ui.stats.subs
  (:require
   [re-frame.core :as rf]))


(rf/reg-sub
  ::active-report
  (fn [db _]
    (:active-report db)))
