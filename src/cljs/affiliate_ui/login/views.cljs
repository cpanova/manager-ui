(ns affiliate-ui.login.views
  (:require
   [re-frame.core :as rf]
   [soda-ash.core :as sa]
   [ajax.core :as ajax]
   [affiliate-ui.routes :refer [url-for]]
   [affiliate-ui.config :as cfg :refer [local-store-auth-key URL]]
   [affiliate-ui.login.subs :as subs]
   [affiliate-ui.login.events :as events]))


(defn login-panel []
  (let [form @(rf/subscribe [::subs/form])]
    [:div {:class "ui main container"
           :style {:margin-top "60px"}}

     [sa/Grid {:class "middle aligned center aligned"}

      [sa/GridColumn {:style {:max-width "450px"}}

       ; [:h2.ui.teal.image.header
       ;  [:img.image]
       ;  [:div.content "Log-in"]]

       [sa/Form {:class "large"}

        [sa/Segment {:class "stacked"}

         [:div.field
          [:div.ui.left.icon.input
           [:i.user.icon]
           [sa/Input {:value (:email form)
                      :placeholder "E-mail"
                      :on-change #(rf/dispatch [::events/set-value :email (-> % .-target .-value)])}]]]

         [:div.field
          [:div.ui.left.icon.input
           [:i.lock.icon]
           [sa/Input {:value (:password form)
                      :type "password"
                      :placeholder "Password"
                      :on-change #(rf/dispatch [::events/set-value :password (-> % .-target .-value)])}]]]

         [sa/Button {:type "submit"
                     :class "fluid large teal submit"
                     :on-click #(rf/dispatch [::events/login])}
          "Login"]]

        [sa/Message {:negative true
                     :hidden (not @(rf/subscribe [::subs/show-error?]))}
         ; [sa/MessageHeader "Sorry"]]]
         [:p "Wrong email/password"]]]

       [sa/Message
        [:span "New to us? "] [:a {:href (url-for :sign-up)} "Sign Up"]]

       [:div
        [:p "Support: "
         [:a.item {:href (str  "https://teleg.run/" cfg/TELEGRAM_ID)
                   :target "_blank"}
          [sa/Icon {:name "send"}]
          cfg/TELEGRAM_ID]]]]]]))
         ; " "
         ; [:a.item {:href (str  "mailto:" cfg/EMAIL)
         ;           :target "_blank"}
         ;  [sa/Icon {:name "mail"}]
         ;  cfg/EMAIL]]]]]]))
