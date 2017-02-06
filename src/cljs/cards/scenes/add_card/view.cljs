(ns cards.scenes.add-card.view
  (:require
   [cljsjs.material-ui]
   [cljs-react-material-ui.core :refer [get-mui-theme color]]
   [cljs-react-material-ui.reagent :as ui]
   [cljs-react-material-ui.icons :as ic]
   [cljs.spec :as s]
   [re-frame.core :as re-frame]
   [reagent.core :as r]))


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

(defn add-card-panel
  []
  (let  [front-text (re-frame/subscribe [:add-card-front-text])
         back-text (re-frame/subscribe [:add-card-back-text])]
    [:div
     [face-view "front" "Front" @front-text #(re-frame/dispatch [:add-card/update-front-text (-> % .-target .-value)])]
     [face-view "back" "Back" @back-text #(re-frame/dispatch [:add-card/update-back-text (-> % .-target .-value)])]
     ]))
