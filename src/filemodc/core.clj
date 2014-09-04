(ns filemodc.core
  "File Modification Cache"
  (:require
    [clojure.core.cache :as cache]
    [clojure.edn        :as edn]))

(defn init
  "Initialize cache object."
  ([] (init {}))
  ([initial-data] (atom (cache/fifo-cache-factory initial-data))))

(defn get-last-modified
  "Get last modified time from java.io.File."
  [^java.io.File f]
  (.lastModified f))

(defn register!
  "Register a java.io.File to cache object."
  [c ^java.io.File f]
  (swap! c cache/miss
         (.getAbsolutePath f)
         (get-last-modified f)))

(defn cached?
  "Check whether a specified java.io.File is cached or not."
  [c ^java.io.File f]
  (cache/has? @c (.getAbsolutePath f)))

(defn modified?
  "Check whether a specified java.io.File is modified or not."
  [c ^java.io.File f]
  (let [path (.getAbsolutePath f)]
    (if-let [v (cache/lookup @c path)]
      (> (get-last-modified f) v)
      true)))

(defn export-edn
  "Export cache object as edn string."
  [c]
  (pr-str @c))

(defn import-edn
  "Import cache object from edn string."
  [s]
  (init (edn/read-string s)))
