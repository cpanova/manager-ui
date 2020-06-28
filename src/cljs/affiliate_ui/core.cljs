(ns affiliate-ui.core
  (:require
   [reagent.core :as reagent]
   [reagent.dom :as rdom]
   [re-frame.core :as re-frame]
   [affiliate-ui.events :as events]
   [affiliate-ui.views.main :as main]
   [affiliate-ui.config :as config]
   [affiliate-ui.routes :as routes]
   [affiliate-ui.cofx]
   [affiliate-ui.fx]))


(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [main/main-panel] root-el)))

(defn init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (routes/app-routes)
  (routes/configure-navigation!)
  (mount-root))
