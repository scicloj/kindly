(ns scicloj.kindly.v4.kind
  (:require [scicloj.kindly.v4.fn :as fn]))

(fn/defkinds

  ;; simple behaviours
  pprint
  hidden

  ;; web dev
  hiccup
  reagent

  ;; data visualization formats
  md
  code
  vega
  vega-lite
  echarts
  cytoscape

  ;; specific types
  image
  dataset

  ;; clojure specific
  var
  test

  ;; plain structures
  seq
  vector
  set
  map

  ;; other recursive structures
  table)
