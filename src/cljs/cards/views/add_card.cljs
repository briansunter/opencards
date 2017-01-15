(ns cards.views.add-card
  (:require
   [cljsjs.material-ui-chip-input]
   [cljsjs.material-ui]
   [cljs-react-material-ui.core :refer [get-mui-theme color]]
   [cljs-react-material-ui.reagent :as ui]
   [cljs-react-material-ui.icons :as ic]
   [cljs.spec :as s]
   [re-frame.core :as re-frame]
   [reagent.core :as r]))

(def chip-input (r/adapt-react-class js/MaterialUIChipInput))

(def paper-props {:z-depth 3
                  :style {:margin 10
                          :padding 10}})

(defn face-view
  [name hint value on-change]
  [ui/paper
   paper-props
   [ui/text-field {:name name
                   :value value
                   :floating-label-text hint
                   :on-change on-change
                   :full-width true
                   :multi-line true
                   :style {:font-size 26}}]])

(defn create-card-button
  [label enabled? on-click]
  [ui/paper {:z-depth 2
             :style {:margin 10
                     :margin-top 20}}
   [ui/raised-button {:primary true
                      :disabled (not enabled?)
                      :on-click on-click
                      :fullWidth true}
    [:a {:style {:color "white"
                 :padding 30
                 :margin 40
                 :font-size 26}}
     label]]])

(defn card-tags-input
  [tags matching-tags input-update add-chip delete-chip]
  [ui/paper paper-props
   [chip-input {:value (clj->js tags)
                :dataSource (clj->js matching-tags)
                :hintText "Enter tags to describe the card here"
                :onUpdateInput input-update
                :onRequestAdd add-chip
                :onRequestDelete delete-chip
                :openOnFocus true
                :fullWidth true
                :style {:margin 10}}]])

(defn add-card-panel
  []
  (let  [front-text (re-frame/subscribe [:add-card-front-text])
         back-text (re-frame/subscribe [:add-card-back-text])
         matching-tags (re-frame/subscribe [:matching-tags])
         tags (re-frame/subscribe [:add-card-tags])
         create-button-enabled (re-frame/subscribe [:add-card-create-button-enabled])]
    (fn []
      [:div
       [face-view "front" "Front" @front-text #(re-frame/dispatch [:add-card/update-front-text (-> % .-target .-value)])]
       [face-view "back" "Back" @back-text #(re-frame/dispatch [:add-card/update-back-text (-> % .-target .-value)])]
       [card-tags-input @tags @matching-tags #(re-frame/dispatch [:search-for-tag %]) #(re-frame/dispatch [:add-card/add-chip  %]) #(re-frame/dispatch [:add-card/delete-chip %])]
       [create-card-button "Create Card" @create-button-enabled #(re-frame/dispatch [:add-card/create-card @front-text @back-text @matching-tags])]])))
