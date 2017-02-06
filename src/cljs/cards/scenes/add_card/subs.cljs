(ns cards.scenes.add-card.subs
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :add-card-front-text
 (fn [db _]
   (get-in db [:scenes :add-card-page :front-text])))

(re-frame/reg-sub
 :add-card-back-text
 (fn [db _]
   (get-in db [:scenes :add-card-page :back-text])))
