(ns cards.views.navigation
  (:require
   [cljs.spec :as s]
   [clojure.test.check.generators :as gen]
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
      #_[ui/menu-item {:primary-text "Cards"
                     :href (path-for-page :cards)
                     :left-icon (ic/hardware-sim-card)}]
      [ui/divider]
      [ui/menu-item {:primary-text "Decks"
                     :href (path-for-page :decks)
                     :left-icon (ic/hardware-dock)}]
      [ui/divider]]]))

(defn toggle-app-drawer-button
  []
  [ui/icon-button {:on-click #(re-frame/dispatch [:toggle-nav-drawer])}
   [ic/navigation-menu {:style {:color "white"}}]])



(defn add-card-button
  []
  (let [enabled (re-frame/subscribe [:add-card-button-enabled])
        add-card-front-text (re-frame/subscribe [:add-card-front-text])
        add-card-back-text (re-frame/subscribe [:add-card-back-text])]
    [ui/flat-button {:label "save"
                     :on-click #(re-frame/dispatch [:add-card/create-card @add-card-front-text @add-card-back-text])
                     :disabled (not @enabled)
                     :style {:color "white"
                             :margin-top 5}}]))

(defn app-bar-close-button
  [props]
  [ui/icon-button props [ic/navigation-close {:style {:fill "white"}}]])

(defn app-bar-menu-button
  []
  [ui/icon-button {:on-click #(re-frame/dispatch [:toggle-nav-drawer])}
   [ic/navigation-menu {:style {:fill "white"}}]])

(defn left-app-bar-button-for-page
  [page]
  (case page
    :add-deck
    :add-card [app-bar-close-button {:href (path-for-page :cards)}]
    [app-bar-menu-button]))

(s/def ::title string?)
(s/def ::elem-type #{:div :a :href })
(s/def ::props-elem (s/cat :type ::elem-type :props map? :elem string?))

(s/def ::color #{"blue" "green" "red" "magenta" "lavender"})
(s/def ::font-size (s/and pos-int? #(< 0 20)))
(s/def ::draggable true?)
(s/def ::rotate pos-int?)
(s/def ::x pos-int?)

(s/def ::style (s/keys :req-un [::color ::font-size]))


(s/def ::elem (s/with-gen (s/cat :type #{:a}
                               :props (s/? (s/keys :req-un [::style]))
                               :elem vector?)
                #(gen/return [:a "test"])))

(s/def ::hiccup ::elem)

(s/def ::left-element ::hiccup)
(s/def ::right-element ::hiccup)

(s/def ::app-bar-props (s/keys :req [::title ::left-element ::right-element]))

(s/fdef main-app-bar :args ::app-bar-props)

(defn main-app-bar
  [props]
  [:div
  [main-app-drawer]
  [ui/app-bar {:title (::title props)
               :z-depth 2
               :icon-element-left (r/as-element (or (::left-element props) [toggle-app-drawer-button]))
               :icon-element-right (r/as-element (::right-element props))
               :style {:position "fixed"
                       :top 0
                       :left 0}}]])

(defn theme
  [content]
    [ui/mui-theme-provider
     {:mui-theme (get-mui-theme
                  {:palette {:text-color (color :green600)}})}
     [:div {:style {:margin-top 100}}
      [content]]])
