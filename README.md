# filemodc

[![Build Status](https://travis-ci.org/liquidz/filemodc.svg)](https://travis-ci.org/liquidz/filemodc)
[![Dependency Status](https://www.versioneye.com/user/projects/54087e73ccc023e0190001a1/badge.svg?style=flat)](https://www.versioneye.com/user/projects/54087e73ccc023e0190001a1)

file-mod-cache: A Clojure library designed to cache file modification status.

## Usage

```clojure
(ns foo
  (:require
    [filemodc.core   :as fm]
    [clojure.java.io :as io]))

(def c (fm/init))
(def f (io/file "foo/bar"))

(fm/cached? c f)          ;; => false
(fm/register! c f)
(fm/cached? c f)          ;; => true

(fm/modified? c f)        ;; => false
(spit f "modified data")
(fm/modified? c f)        ;; => true
(fm/register! c f)
(fm/modified? c f)        ;; => false

(def cache-file (io/file "bar/baz"))
(spit cache-file (fm/export-edn c))
(def c* (fm/import-edn (slurp cache-file)))
(fm/cached? c* f)         ;; => true
```

## License

Copyright (C) 2014 [@uochan](http://twitter.com/uochan)

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
