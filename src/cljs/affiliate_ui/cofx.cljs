(ns affiliate-ui.cofx
  (:require
   [re-frame.core :as rf]
   [cljs.reader]))

(rf/reg-cofx
  :local-store
  (fn [cofx key]
      (assoc cofx :local-store  ;; put the local-store key into the coeffect under :local-store
             (into (sorted-map)      ;; read in key from localstore, and process into a sorted map
                   (some->> (.getItem js/localStorage key)
                            (cljs.reader/read-string))))))  ;; EDN map -> map
