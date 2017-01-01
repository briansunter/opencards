(defproject cards "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.9.0-alpha4"]
                 [org.clojure/clojurescript "1.9.293"]
                 [reagent "0.6.0" :exclusions [org.clojure/tools.reader cljsjs/react cljsjs/react-dom ]]
                 [cljs-react-material-ui "0.2.34"]
                 [re-frame "0.9.0"]
                 [re-frisk "0.3.2"]
                 [venantius/accountant "0.1.7"]
                 [bidi "2.0.14"]
                 [kibu/pushy "0.3.6"]
                 [ring/ring-defaults "0.2.1"]
                 [compojure "1.5.0"]
                 [yogthos/config "0.8"]
                 [ring "1.4.0"]]

  :plugins [[lein-cljsbuild "1.1.4"]]

  :min-lein-version "2.5.3"

  :source-paths ["src/clj"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :figwheel {:css-dirs ["resources/public/css"]
             :ring-handler cards.handler/dev-handler}

  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

  :profiles
  {:dev
   {:dependencies [[binaryage/devtools "0.8.2"]
                   [figwheel-sidecar "0.5.7"]
                   [com.cemerick/piggieback "0.2.1"]]

    :plugins      [[lein-figwheel "0.5.7"]]
    }}

  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs"]
     :figwheel     {:on-jsload "cards.core/mount-root"}
     :compiler     {:main                 cards.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "js/compiled/out"
                    :source-map-timestamp true
                    :preloads             [devtools.preload]
                    :external-config      {:devtools/config {:features-to-install :all}}
                    }}

    {:id           "min"
     :source-paths ["src/cljs"]
     :jar true
     :compiler     {:main            cards.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :advanced
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}]}

  :main cards.server

  :aot [cards.server]

  :uberjar-name "cards.jar"

  :prep-tasks [["cljsbuild" "once" "min"] "compile"])
