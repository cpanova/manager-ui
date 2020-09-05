(ns manager-ui.views.main
  (:require
    [re-frame.core :refer [subscribe]]
    [manager-ui.subs :as subs]
    [manager-ui.login.views :refer [login-panel]]
    ; [manager-ui.sign-up.views :refer [sign-up-panel]]
    ; [manager-ui.offers.views :refer [offers-panel]]
    ; [manager-ui.offer.views :refer [offer-panel]]
    [manager-ui.stats.views :refer [stats-panel]]))


(defn- panels [panel-name]
  (case panel-name
    :login-panel [login-panel]
    ; :sign-up-panel [sign-up-panel]
    ; :offers-panel [offers-panel]
    ; :offer-panel [offer-panel]
    :stats-panel [stats-panel]
    [:div]))


(defn show-panel [panel-name]
  [panels panel-name])


(defn main-panel []
  (let [active-panel (subscribe [::subs/active-panel])]
    ; [:div {:class "ui main container"
    ;        :style {:margin-top "60px"}}
    [panels @active-panel]))
