(ns affiliate-ui.stats.events
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
                                    ; [:affiliate-ui.stats.panes.daily.events/init]
                                    [:affiliate-ui.stats.panes.daily.events/load-daily-stats]]}
              "conversions" {:db set-report
                             :dispatch-n [
                                          ; [:affiliate-ui.stats.panes.conversions.events/init]
                                          [:affiliate-ui.stats.panes.conversions.events/load-conversions]]}
              "offers" {:db set-report
                        :dispatch-n [
                                     ; [:affiliate-ui.stats.panes.offers.events/init]
                                     [:affiliate-ui.stats.panes.offers.events/load-offers-stats]]}
              {:db set-report}))))
