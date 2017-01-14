(ns cards.views.home
  (:require
   [cards.routes :refer [path-for-page]]
   [cljsjs.material-ui]
   [cljs-react-material-ui.core :refer [get-mui-theme color]]
   [cljs-react-material-ui.reagent :as ui]
   [cljs-react-material-ui.icons :as ic]
   [cards.views.add-card :refer [add-card-view]]
   [re-frame.core :as re-frame]
   [reagent.core :as r]))

(defn card-list []
  (let [cards (re-frame/subscribe [:all-cards])]
    (fn []
      [ui/selectable-list
       (for [c @cards]
         [:div {:key (:id c)}
          [ui/list-item
           {:primary-text (:front c)
            :href (path-for-page :add-card)}]
          [ui/divider]])])))

(def floating-add-button-style
  {:margin 0
   :top "auto"
   :bottom 0
   :left "auto"
   :text-align "center"
   :position "fixed"})

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
                                      :href (path-for-page :cards)}]
          [ui/bottom-navigation-item {:label "Add"
                                      :href (path-for-page :add-card)
                                      :icon (ic/content-add)}]

          [ui/bottom-navigation-item {:label "Home"
                                      :href (path-for-page :home)
                                      :icon (ic/action-home)}]]]]])))

(defn home-panel []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [:div (str "Hello from " @name ". This is the Home Page.")
       [:div [:a {:href (path-for-page :cards)} "go to Cards Page"]]])))

(defn about-panel []
  (fn []
    [:div "This is the About Page."
     [:div [:a {:href "/"} "go to Home Page"]]]))

(defn- panels [panel-name]
  (case panel-name
    :home home-panel
    :cards card-list
    :add-card add-card-view
    [:div]))

(defn show-panel [panel-name]
  [navigation (panels panel-name)])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [:active-panel])]
    (fn []
      [show-panel @active-panel])))
