(defproject hack "0.1.0-SNAPSHOT"
  :description "???"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2197"]]

  :jvm-opts ["-Xmx1G"]

  :plugins [[lein-cljsbuild "1.0.3"]]
  :cljsbuild {
              :builds [{:id "dev"
                        :source-paths ["src/hack/cljs"]
                        :compiler {
                                   :output-to "resources/public/hack.js"
                                   :output-dir "resources/public/out"
                                   :optimizations :none
                                   :source-map true
                                   }
                        }]
              }
  

  )