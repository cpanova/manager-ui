(ns manager-ui.stats.utils)


(defn format-js-date [d]
  (let [y (.getFullYear d)
        m (+ 1 (.getMonth d))
        mm (if (< m 10)
             (str "0" m)
             m)
        d (.getDate d)
        dd (if (< d 10)
             (str "0" d)
             d)]
    (str y "-" mm "-" dd)))
