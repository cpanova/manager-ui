(ns affiliate-ui.db
  (:require
   [affiliate-ui.login.db]
   [affiliate-ui.sign-up.db]))


(def default-db
  {:name "CPA Network"
   :tracking-link ""
   affiliate-ui.login.db/state-key   affiliate-ui.login.db/defaults
   affiliate-ui.sign-up.db/state-key affiliate-ui.sign-up.db/defaults})
