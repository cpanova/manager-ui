(ns manager-ui.stats.events
  (:require
   [re-frame.core :as rf]))


(rf/reg-event-fx
  ::set-active-report
    (fn [cofx [_ report]]
      (let [db (:db cofx)
            set-report (assoc db :active-report report)]
        (case report
              "daily" {:db set-report
                       :dispatch-n [
                                    ; [:manager-ui.stats.panes.daily.events/init]
                                    [:manager-ui.stats.panes.daily.events/load-daily-stats]]}
              ; "conversions" {:db set-report
              ;                :dispatch-n [
              ;                             ; [:manager-ui.stats.panes.conversions.events/init]
              ;                             [:manager-ui.stats.panes.conversions.events/load-conversions]]}
              "offers" {:db set-report
                        :dispatch-n [
                                     ; [:manager-ui.stats.panes.offers.events/init]
                                     [:manager-ui.stats.panes.offers.events/load-offers-stats]]}
              "affiliates" {:db set-report
                            :dispatch [:manager-ui.stats.panes.affiliates.events/load-affiliates-stats]}
              ; "goals" {:db set-report
              ;          :dispatch-n [
              ;                        ; [:manager-ui.stats.panes.offers.events/init]
              ;                       [:manager-ui.stats.panes.goals.events/load-goals-stats]]}
              ; "sub1" {:db set-report
              ;         :dispatch [:manager-ui.stats.panes.subid.events/load-subid-stats 1]}
              ; "sub2" {:db set-report
              ;         :dispatch [:manager-ui.stats.panes.subid.events/load-subid-stats 2]}
              ; "sub3" {:db set-report
              ;         :dispatch [:manager-ui.stats.panes.subid.events/load-subid-stats 3]}
              ; "sub4" {:db set-report
              ;         :dispatch [:manager-ui.stats.panes.subid.events/load-subid-stats 4]}
              ; "sub5" {:db set-report
              ;         :dispatch [:manager-ui.stats.panes.subid.events/load-subid-stats 5]}
              {:db set-report}))))
