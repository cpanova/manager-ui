(ns affiliate-ui.offers.subs
  (:require
   [re-frame.core :as rf]))


(rf/reg-sub
 ::offers
 (fn [db _]
   (:offers db)))
