(ns cards.views.navigation
  (:require
   [cards.routes :refer [path-for-page]]
   [cljsjs.material-ui]
   [cljs-react-material-ui.core :refer [get-mui-theme color]]
   [cljs-react-material-ui.reagent :as ui]
   [cljs-react-material-ui.icons :as ic]
   [re-frame.core :as re-frame]
   [reagent.core :as r]))

(def tab-bar-style
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
       [:div {:style {:margin-bottom 100}}
        [content]
        [ui/paper
         [ui/bottom-navigation {:selected-index @tab-bar-index
                                :style tab-bar-style}
          [ui/bottom-navigation-item {:label "Feed"
                                      :icon (ic/av-featured-play-list)
                                      :href (path-for-page :feed)}]
          [ui/bottom-navigation-item {:label "Add"
                                      :href (path-for-page :add-card)
                                      :icon (ic/content-add)}]

          [ui/bottom-navigation-item {:label "Home"
                                      :href (path-for-page :home)
                                      :icon (ic/action-home)}]]]]])))

