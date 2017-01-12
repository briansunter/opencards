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

(defn face-view
  [name hint]
  [ui/paper
   {:z-depth 2
    :style {
            :margin 10
            :padding 10
            }}
   [ui/text-field {:name name :floating-label-text hint
                   :full-width true
                   :multi-line true
                   :style {:font-size 28}}]])

(def stub-languages
  ["spanish" "english" "chinese" "japanese" "hindi" "italian" "german" "russian" "arabic" "integerated chinese 1"])

(defn add-card-view
  []
  [:div
   [face-view "front" "Front"]
   [face-view "back" "Back"]
   [ui/paper {:z-depth 3
              :style {:margin 10
                      :margin-top 40}}
    [chip-input {:dataSource (clj->js stub-languages)
                 :fullWidth true
                 :style {:margin 10}
                 }]]
   [ui/paper {:z-depth 3
              :style {:margin 10
                      :margin-top 40}}
    [ui/raised-button {:primary true
                       :full-width true
                       }[:a {:style {:color "white"
                                     :padding 30
                                     :margin 40
                                     :font-size 28
                                     }}"Create Card"]]]])
