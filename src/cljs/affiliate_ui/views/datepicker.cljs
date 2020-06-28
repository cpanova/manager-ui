(ns affiliate-ui.views.datepicker
  (:require
   [reagent.core]
   ["react-semantic-ui-datepickers" :default SemanticDatepicker]))


(def Datepicker (reagent.core/adapt-react-class SemanticDatepicker))
