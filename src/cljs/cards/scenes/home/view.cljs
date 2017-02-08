(ns cards.scenes.home.view
  (:require
   [cards.routes :refer [path-for-page]]
   [cljsjs.material-ui]
   [cljs-react-material-ui.reagent :as ui]
   [cljs-react-material-ui.icons :as ic]
   [cards.views.navigation :refer [theme test-bar]]
   [re-frame.core :as re-frame]))

(defn home-panel-app-bar
  []
  [test-bar])

(defn home-panel []
  (let [name (re-frame/subscribe [:name])]
      [:div (str "Hello from " @name ". This is the Home Page.")
       [:div [:a {:href (path-for-page :cards)} "go to Cards Page"]]]))
