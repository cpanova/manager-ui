(ns affiliate-ui.offer.subs
  (:require
   [re-frame.core :as rf]))


(rf/reg-sub
 ::offer
 (fn [db _]
   (:offer db)))


(rf/reg-sub
 ::tracking-link
 (fn [db _]
   (:tracking-link db)))
