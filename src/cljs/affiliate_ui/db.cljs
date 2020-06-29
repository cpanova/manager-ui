(ns affiliate-ui.db
  (:require
   [affiliate-ui.login.db]
   [affiliate-ui.sign-up.db]
   [affiliate-ui.stats.db]))


(def default-db
  {:name "CPA Nova"
   :tracking-link ""
   affiliate-ui.login.db/state-key   affiliate-ui.login.db/defaults
   affiliate-ui.sign-up.db/state-key affiliate-ui.sign-up.db/defaults
   affiliate-ui.stats.db/state-key   affiliate-ui.stats.db/defaults})
