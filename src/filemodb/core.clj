(ns filemodb.core
  (:require
    [clojure.core.cache :as cache]
    [clojure.edn        :as edn]))

(defn init
  "FIXME"
  ([] (init {}))
  ([initial-data] (atom (cache/fifo-cache-factory initial-data))))

(defn get-last-modified
  [^java.io.File f]
  (.lastModified f))

(defn register!
  [c ^java.io.File f]
  (swap! c cache/miss
         (.getAbsolutePath f)
         (get-last-modified f)))

(defn cached?
  [c ^java.io.File f]
  (cache/has? @c (.getAbsolutePath f)))

(defn modified?
  [c ^java.io.File f]
  (let [path (.getAbsolutePath f)]
    (if-let [v (cache/lookup @c path)]
      (> (get-last-modified f) v)
      true)))

(defn export-edn
  [c]
  (pr-str @c))

(defn import-edn
  [s]
  (init (edn/read-string s)))
