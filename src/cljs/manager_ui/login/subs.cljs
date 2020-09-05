(ns manager-ui.login.subs
  (:require
   [re-frame.core :as rf]
   [manager-ui.login.db :refer [state-key]]))


(rf/reg-sub
  ::form
  (fn [db _]
    (get db state-key)))


(rf/reg-sub
  ::show-error?
  (fn [db _]
    (get-in db [state-key :error :show?])))


(rf/reg-sub
  ::error-message
  (fn [db _]
    (get-in db [state-key :error :message])))
