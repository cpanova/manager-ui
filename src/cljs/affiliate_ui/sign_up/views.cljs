(ns affiliate-ui.sign-up.views
  (:require
   [re-frame.core :as rf]
   [soda-ash.core :as sa]
   [ajax.core :as ajax]
   [affiliate-ui.routes :refer [url-for]]
   [affiliate-ui.config :as cfg :refer [URL]]
   [affiliate-ui.sign-up.subs :as subs]
   [affiliate-ui.sign-up.events :as events]))


(defn sign-up-panel []
  (let [form @(rf/subscribe [::subs/form])]
    [:div {:class "ui main container"
           :style {:margin-top "60px"}}
     [:div {:class "ui middle aligned center aligned grid"}

      [:div.column {:style {:max-width "450px"}}

       [sa/Form {:class "large"}
        [:div.ui.stacked.segment
         [:div.field
          [:div.ui.left.icon.input
           [:i.user.icon]
           [sa/Input {:value (:email form)
                      :placeholder "E-mail address"
                      :on-change #(rf/dispatch [::events/set-value :email (-> % .-target .-value)])}]]]
         [:div.field
          [:div.ui.left.icon.input
           [:i.lock.icon]
           [sa/Input {:type "password"
                      :value (:password form)
                      :placeholder "Password"
                      :on-change #(rf/dispatch [::events/set-value :password (-> % .-target .-value)])}]]]
         [:div.field
          [:div.ui.left.icon.input
           [:i.lock.icon]
           [sa/Input {:type "password"
                      :value (:confirm form)
                      :placeholder "Confirm password"
                      :on-change #(rf/dispatch [::events/set-value :confirm (-> % .-target .-value)])}]]]
         [:div.field
          [:div.ui.left.icon.input
           [:i.lock.icon]
           [sa/Input {
                      :value (:messenger form)
                      :placeholder "Skype / Telegram"
                      :on-change #(rf/dispatch [::events/set-value :messenger (-> % .-target .-value)])}]]]

         [sa/Button {:type "submit"
                     :class "fluid large teal"
                     :on-click #(rf/dispatch [::events/sign-up])}
          "Sign up"]]

        [sa/Message {:negative true
                     :hidden (not @(rf/subscribe [::subs/show-error?]))}
         ; [sa/MessageHeader "Sorry"]]]
         [:p @(rf/subscribe [::subs/error-message])]]]

       [:div.ui.message
        [:span "Have an account? "] [:a {:href (url-for :login)} "Login"]]

       [:div
        [:p "Support: "
         [:a.item {:href (str  "https://teleg.run/" cfg/TELEGRAM_ID)
                   :target "_blank"}
          [sa/Icon {:name "send"}]
          cfg/TELEGRAM_ID]]]]]]))
