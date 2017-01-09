(ns cards.views.home
  (:require
   [cljsjs.material-ui]
   [cljs-react-material-ui.core :refer [get-mui-theme color]]
   [cljs-react-material-ui.reagent :as ui]
   [cljs-react-material-ui.icons :as ic]
   [cards.views.add-card :refer [add-card-view]]
   [re-frame.core :as re-frame]
   [reagent.core :as r]))

(defn deck-list []
  (let [cards (re-frame/subscribe [:all-cards])]
    (fn []
      [ui/selectable-list
       (for [c @cards]
         [:div {:key (hash c)}
          [ui/list-item
           {:primary-text (get-in c [:front :value])
            :href "/cards/add"
            }]
          [ui/divider]])])))

(def floating-add-button-style
  {:margin 0
   :top "auto"
   :bottom 0
   :left "auto"
   :position "fixed"})

(defn add-deck-dialog
  ""
  [adding]
  [ui/dialog {:title "Add Deck"
              :actions (r/as-component [:div
                                        [ui/flat-button {:label "Cancel"
                                                         :href "/decks"}]
                                        [ui/flat-button {:label "Add Deck"}]])
              :open adding}
   [ui/text-field {:hint-text "Deck Name"}]
   [ui/text-field {:hint-text "Deck Description (optional)"}]])

(defn navigation [content]
  (let [tab-bar-index (re-frame/subscribe [:tab-bar-index])]
    (fn [content]
      [ui/mui-theme-provider
       {:mui-theme (get-mui-theme
                    {:palette {:text-color (color :green600)}})}
       [:div
        [content]
        [ui/paper {:z-depth 1}
         [ui/bottom-navigation {:selected-index @tab-bar-index
                                :style floating-add-button-style}
          [ui/bottom-navigation-item {:label "Feed"
                                      :icon (ic/av-featured-play-list)
                                      :href "/cards"}]
          [ui/bottom-navigation-item {:label "Add"
                                      :href "/cards/add"
                                      :icon (ic/content-add)}]

          [ui/bottom-navigation-item {:label "Home"
                                      :href "/"
                                      :icon (ic/action-home)}]]]]])))

(defn home-panel []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [:div (str "Hello from " @name ". This is the Home Page.")
       [:div [:a {:href "/about"} "go to About Page"]]])))

(defn about-panel []
  (fn []
    [:div "This is the About Page."
     [:div [:a {:href "/"} "go to Home Page"]]]))

(defn- panels [panel-name]
  (case panel-name
    :home home-panel
    :cards deck-list
    :add-card add-card-view
    [:div]))

(defn show-panel [panel-name]
  [navigation (panels panel-name)])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [:active-panel])]
    (fn []
      [show-panel @active-panel])))
