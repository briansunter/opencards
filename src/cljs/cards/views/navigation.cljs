(ns cards.views.navigation
  (:require
   [cards.routes :refer [path-for-page]]
   [cljsjs.material-ui]
   [cljs-react-material-ui.core :refer [get-mui-theme color]]
   [cljs-react-material-ui.reagent :as ui]
   [cljs-react-material-ui.icons :as ic]
   [re-frame.core :as re-frame]
   [reagent.core :as r]))

(defn main-app-drawer
  []
  (let [app-drawer-open (re-frame/subscribe [:app-drawer-open])]
    [ui/drawer {:open @app-drawer-open
                :docked false
                :z-depth 2
                :on-request-change #(re-frame/dispatch [:set-nav-drawer-open %])}
     [ui/menu
      [ui/divider]
      [ui/menu-item {:primary-text "Feed"
                     :href (path-for-page :feed)
                     :left-icon (ic/communication-rss-feed)}]
      [ui/divider]
      [ui/menu-item {:primary-text "Cards"
                     :href (path-for-page :add-card)
                     :left-icon (ic/hardware-sim-card)}]
      [ui/divider]
      [ui/menu-item {:primary-text "Decks"
                     :href (path-for-page :decks)
                     :left-icon (ic/hardware-dock)}]
      [ui/divider]]]))

(defn right-app-bar-button-for-page
  [page]
  (case page
    :add-deck [ui/flat-button {:label "save"
                               :style {:color "white"
                                       :margin-top 5}}]
    [ui/icon-button]))

(defn app-bar-close-button
  []
  [ui/icon-button {:href (path-for-page :decks)} [ic/navigation-close {:style {:fill "white"}}]])

(defn app-bar-menu-button
  []
  [ui/icon-button {:on-click #(re-frame/dispatch [:toggle-nav-drawer])}
   [ic/navigation-menu {:style {:fill "white"}}]])

(defn left-app-bar-button-for-page
  [page]
  (case page
    :add-deck [app-bar-close-button]
    [app-bar-menu-button]))

(defn main-app-bar
  [page]
  [ui/app-bar {:title "Open Cards"
               :z-depth 2
               :icon-element-left (r/as-element [left-app-bar-button-for-page page])
               :icon-element-right (r/as-element [right-app-bar-button-for-page page])
               :style {:position "fixed"
                       :top 0
                       :left 0}}
   [main-app-drawer]])

(defn navigation [content]
  (let [page (re-frame/subscribe [:active-panel])]
    (fn [content]
      [ui/mui-theme-provider
       {:mui-theme (get-mui-theme
                    {:palette {:text-color (color :green600)}})}
       [:div {:style {:margin-top 100}}
        [main-app-bar @page]
        [content]]])))

(def tab-bar-style
  {:margin 0
   :top "auto"
   :bottom 0
   :left "auto"
   :text-align "center"
   :position "fixed"})

(defn bottom-navigation
  [tab-bar-index]
  [ui/paper
   [ui/bottom-navigation {:selected-index tab-bar-index
                          :style tab-bar-style}
    [ui/bottom-navigation-item {:label "Feed"
                                :icon (ic/av-featured-play-list)
                                :href (path-for-page :feed)}]
    [ui/bottom-navigation-item {:label "Add"
                                :href (path-for-page :add-card)
                                :icon (ic/content-add)}]
    [ui/bottom-navigation-item {:label "Home"
                                :href (path-for-page :home)
                                :icon (ic/action-home)}]]])
