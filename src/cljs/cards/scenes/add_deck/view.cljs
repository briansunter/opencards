(ns cards.scenes.add-deck.view
  (:require [re-frame.core :as re-frame]
            [cljsjs.material-ui]
            [cljsjs.material-ui-chip-input]
            [cljs-react-material-ui.reagent :as ui]
            [cljs-react-material-ui.icons :as ic]
            [reagent.core :as r]))

(def chip-input (r/adapt-react-class js/MaterialUIChipInput))

(defn target-value
  [tv]
  (-> tv .-target .-value))

(def paper-props {:z-depth 2
                  :style {:margin 10
                          :padding 10}})

(defn deck-tags-input
  [props]
  (let [{:keys [tags matching-tags on-input-update on-add-chip on-delete-chip]} props]
    [chip-input {:value (clj->js tags)
                 :dataSource (clj->js matching-tags)
                 :hintText "Enter tags to describe the deck here"
                 :onUpdateInput on-input-update
                 :onRequestAdd on-add-chip
                 :onRequestDelete on-delete-chip
                 :openOnFocus true
                 :fullWidth true
                 :style {:margin 10}}]))

(defn add-deck-panel
  []
  (let [title (re-frame/subscribe [:add-deck-title])
        description (re-frame/subscribe [:add-deck-description])
        matching-tags (re-frame/subscribe [:matching-tags])
        tags (re-frame/subscribe [:add-deck-tags])]
    [ui/paper {:z-depth 2
               :style {:padding 10
                       :overflow "hidden"}}
     [ui/text-field {:floating-label-text "Deck Name"
                     :value @title
                     :on-change #(re-frame/dispatch [:add-deck-update-title (target-value %)])
                     :style {:padding 10
                             :font-size 26
                             :multi-line true
                             :width "100%"}}]
     [ui/text-field {:floating-label-text "Deck Description"
                     :value @description
                     :on-change  #(re-frame/dispatch [:add-deck-update-description (target-value %)])
                     :multi-line true
                     :rows 3
                     :style {:padding 10
                             :width "100%"}}]
     [deck-tags-input {:tags @tags
                       :matching-tags @matching-tags
                       :on-input-update #(re-frame/dispatch [:search-for-tag %])
                       :on-add-chip #(re-frame/dispatch [:add-deck/add-chip %])
                       :on-delete-chip #(re-frame/dispatch [:add-deck/delete-chip %])}]
     [ui/raised-button {:primary true
                        :label "upload cover image"}]]))
