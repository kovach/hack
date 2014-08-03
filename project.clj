(defproject hack "0.1.0-SNAPSHOT"
  :description "???"
  :url "cutfree.net/hack/index.html"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2280"]
                 [figwheel "0.1.3-SNAPSHOT"]]

  ;:jvm-opts ["-Xmx1G"]

  :plugins [[lein-cljsbuild "1.0.4-SNAPSHOT"]
            [lein-figwheel "0.1.3-SNAPSHOT"]
            [com.cemerick/austin "0.1.4"]
            ]

  :source-paths ["src"]

  :figwheel {
    :http-server-root "public"
    :port 4444
    :css-dirs ["css"]}

  :cljsbuild {
              :builds [{:id "dev"
                        :source-paths ["src/hack"
                                       "src/figwheel"
                                       "src/brepl"]
                        :compiler {
                                   :output-to "resources/public/hack.js"
                                   :output-dir "resources/public/out"
                                   :optimizations :none
                                   :source-map true
                                   }
                        }]
              }
  

  )
