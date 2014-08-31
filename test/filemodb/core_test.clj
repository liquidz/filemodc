(ns filemodb.core-test
  (:require
    [filemodb.core      :refer :all]
    [midje.sweet        :refer :all]
    [conjure.core       :refer :all]
    [clojure.java.io    :as io]
    [clojure.core.cache :as cache]
    ))

(def ^:const TEST_FILE "test/files/testfile")

(fact "init should work fine."
  (class (init)) => clojure.lang.Atom
  (instance? clojure.core.cache.CacheProtocol @(init)) => true)

(fact "cached? should work fine."
  (let [f (io/file "DUMMY")
        d {(.getAbsolutePath f) 0}]
    (cached? (init) f)   => false
    (cached? (init d) f) => true))

(fact "register! should work fine."
  (let [c (init)
        f (io/file "DUMMY")]
    (cached? c f) => false
    (register! c f)
    (cached? c f) => true))

(facts "modified? should work fine."
  (fact "no modification"
    (let [c (init)
          f (io/file "DUMMY")]
      (modified? c f) => true
      (register! c f)
      (modified? c f) => false))

  (fact "with modification"
    (let [c (init)
          f (io/file TEST_FILE)]
      (register! c f)
      (modified? c f) => false
      (spit f (slurp f))
      (modified? c f) => true)))

(facts "export/import shoudl work fine."
  (let [c  (init)
        ls (map #(io/file %) ["a" "b"])]
    (doseq [f ls] (register! c f))

    (fact "export"
      (string? (export-edn c)) => true)
    (fact "import"
      (let [c* (import-edn (export-edn c))]
        (doseq [f ls]
          (cached? c* f) => true)))))
