(defproject filemodc "0.0.1"
  :description "A Clojure library designed to cache file modification status"
  :url         "https://github.com/liquidz/filemodc"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure    "1.6.0"]
                 [org.clojure/core.cache "0.6.4"]]

  :profiles {:dev {:global-vars {*warn-on-reflection* true}
                   :dependencies [[midje "1.6.3" :exclusions [org.clojure/clojure]]
                                  [org.clojars.runa/conjure "2.1.3"]]}}
  :plugins [[lein-midje "3.1.3"]])
