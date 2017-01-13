(ns cards.views.add-card
  (:require
   [cljsjs.material-ui-chip-input]
   [cljsjs.material-ui]
   [cljs-react-material-ui.core :refer [get-mui-theme color]]
   [cljs-react-material-ui.reagent :as ui]
   [cljs-react-material-ui.icons :as ic]
   [re-frame.core :as re-frame]
   [reagent.core :as r]))

(def chip-input (r/adapt-react-class js/MaterialUIChipInput))

(def paper-props {:z-depth 3
                  :style {:margin 10
                          :padding 10}})

(defn face-view
  [name hint]
  [ui/paper
   paper-props
   [ui/text-field {:name name
                   :floating-label-text hint
                   :full-width true
                   :multi-line true
                   :style {:font-size 26}}]])


(defn create-card-button
  [label on-click]
  [ui/paper {:z-depth 2
             :style {:margin 10
                     :margin-top 20}}
   [ui/raised-button {:primary true
                      :fullWidth true}
    [:a {:style {:color "white"
                 :padding 30
                 :margin 40
                 :font-size 26}}
     label]]])

(defn card-tags-input
  [tags input-update]
  [ui/paper paper-props
   [chip-input {:dataSource (clj->js tags)
                :hintText "Enter tags to describe the card here"
                :onUpdateInput input-update
                :openOnFocus true
                :fullWidth true
                :style {:margin 10}}]])

(defn add-card-view
  []
  (let [tags (re-frame/subscribe [:matching-tags])]
    (fn []
      [:div
       [face-view "front" "Front"]
       [face-view "back" "Back"]
       [card-tags-input @tags #(re-frame/dispatch [:search-for-tag %])]
       [create-card-button "Create Card" #(re-frame/dispatch [:create-card])]])))
