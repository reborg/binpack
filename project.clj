(defproject binpack "0.1.0"
  :description "A Clojure library to solve the bin packing problem."
  :url "https://github.com/reborg/binpack"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :plugins [[lein-midje "3.1.3"]]
  :profiles {:dev {:dependencies [[midje "1.6.0"]
                                  [criterium "0.4.2"]
                                  [xrepl "0.1.1"]]}})
