(ns manager-ui.db
  (:require
   [manager-ui.login.db]
   ; [manager-ui.sign-up.db]
   [manager-ui.stats.db]))


(def default-db
  {:name "CPA Nova"
   :tracking-link ""
   manager-ui.login.db/state-key   manager-ui.login.db/defaults
   ; manager-ui.sign-up.db/state-key manager-ui.sign-up.db/defaults
   manager-ui.stats.db/state-key   manager-ui.stats.db/defaults})
