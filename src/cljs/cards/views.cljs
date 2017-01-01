(ns cards.views
  (:require
   [cljsjs.material-ui]
   [cljs-react-material-ui.core :refer [get-mui-theme color]]
   [cljs-react-material-ui.reagent :as ui]
   [cljs-react-material-ui.icons :as ic]
   [re-frame.core :as re-frame]
   [reagent.core :as r]))

(defn deck-list []
  (fn []
    [ui/selectable-list
     (for [i (range 1000)]
       (r/as-element
        [:div
         [ui/list-item
          {:value i
           :key i
           :href "/decks"
           :primary-text (str "Foo " i)}]
         [ui/divider]]))]))

(defn home-page [adding]
  (let [open (r/atom adding)]
    (fn []
      [ui/mui-theme-provider
       {:mui-theme (get-mui-theme
                    {:palette {:text-color (color :green600)}})}
       [:div
        [ui/app-bar {:title "My Decks"
                     :style {:position "fixed"}
                     :icon-element-right
                     (r/as-element [ui/icon-button
                                    (ic/content-add)])
                     :on-right-icon-button-touch-tap #(swap! open complement)}]
        [ui/dialog {:title "Add Deck"
                    :actions [[ui/flat-button]]
                    :open @open}
         [ui/text-field {:hint-text "Deck Name"}]
         [ui/text-field {:hint-text "Deck Description (optional)"}]]
        [deck-list]]])))

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
    :home [home-page]
    :decks [home-page]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [:active-panel])]
    (fn []
      [show-panel @active-panel])))
