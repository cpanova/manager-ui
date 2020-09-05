(ns manager-ui.fx
  (:require
   [re-frame.core :as rf]
   [accountant.core :as accountant]
   [goog.dom :as dom]))


(rf/reg-fx
  :redirect-to
  (fn [url]
      (accountant/navigate! url)))


(rf/reg-fx
  :set-local-store
  (fn [[key value]]
      (.setItem js/localStorage key (str value))))  ;; sorted-map written as an EDN map


(rf/reg-fx
  :remove-local-store
  (fn [key]
      (.removeItem js/localStorage key)))


(rf/reg-fx
  :console-log
  (fn [args]
      (apply (.-log js/console) args)))


(defn- send-file-to-browser [binary file-name]
  (let [blob (js/Blob. #js [binary]
                       #js {"type" "application/octet-binary"})
        url (.createObjectURL (.-URL js/window) blob)
        link (dom/createDom "a" #js {"href" url})]
    (dom/appendChild (.-body js/document) link)
    (set! (.-download link) file-name)
    (.click link)
    (dom/removeChildren link)))


(rf/reg-fx
  :download-file
  (fn [{:keys [binary filename]}]
      (send-file-to-browser binary filename)))
