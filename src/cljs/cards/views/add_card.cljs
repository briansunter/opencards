(ns cards.views.add-card
  (:require
   [cljsjs.material-ui]
   [cljs-react-material-ui.core :refer [get-mui-theme color]]
   [cljs-react-material-ui.reagent :as ui]
   [cljs-react-material-ui.icons :as ic]
   [re-frame.core :as re-frame]
   [reagent.core :as r]))

(defn face-view
  [name hint]
  [ui/paper
   {:z-depth 3
    :style {
            :margin 10
            :padding 10
            }}
   [ui/text-field {:name name :floating-label-text hint
                   :full-width true
                   :multi-line true
                   :style {:font-size 28
                           }}]])

(defn add-card-view
  []
  [:div
   [face-view "front" "Front"]
   [face-view "back" "Back"]
   [ui/paper {:z-depth 3
              :style {:margin 10
                      :margin-top 40}}
    [ui/raised-button {:primary true
                       :full-width true
                       }[:a {:style {:color "white"
                                     :margin 40
                                     :font-size 28
                                     }}"Create Card"]]]]
  )
