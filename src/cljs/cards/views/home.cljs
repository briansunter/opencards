(ns cards.views.home
  (:require
   [cljsjs.material-ui]
   [cljs-react-material-ui.core :refer [get-mui-theme color]]
   [cljs-react-material-ui.reagent :as ui]
   [cljs-react-material-ui.icons :as ic]
   [re-frame.core :as re-frame]
   [reagent.core :as r]))

(defn deck-list []
  [ui/selectable-list
   (for [i (range 200)]
     [:div {:key i}
      [ui/list-item
       {:primary-text (str "Brian" i)
        :href "/decks/add"
        }]
      [ui/divider]])])

(def floating-add-button-style
  {:margin 0
   :top "auto"
   :right 20
   :bottom 20
   :left "auto"
   :position "fixed"})

(defn home-page [adding]
  [ui/mui-theme-provider
   {:mui-theme (get-mui-theme
                {:palette {:text-color (color :green600)}})}
   [:div
    [ui/app-bar {:title "My Decks"
                 :style {:position "fixed"}}]
    [ui/dialog {:title "Add Deck"
                :actions (r/as-component [:div
                                          [ui/flat-button {:label "Cancel"
                                                           :href "/decks"}]
                                          [ui/flat-button {:label "Add Deck"}]])
                :open adding}
     [ui/text-field {:hint-text "Deck Name"}]
     [ui/text-field {:hint-text "Deck Description (optional)"}]]
    [ui/floating-action-button {:iconClassName "content-add"
                                :style floating-add-button-style
                                :href "/decks/add"}]
    [deck-list]]])

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
    :home [home-page false]
    :decks [home-page false]
    :add-deck [home-page true]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [:active-panel])]
    (fn []
      [show-panel @active-panel])))
