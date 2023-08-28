(ns scicloj.kindly.v4.kind
  (:require [scicloj.kindly.v4.impl :as impl]))

(def hidden
  (impl/kind-as-a-fn :kind/hidden))

(def pprint
  (impl/kind-as-a-fn :kind/pprint))

(def println
  (impl/kind-as-a-fn :kind/println))

(def test
  (impl/kind-as-a-fn :kind/test))

(def var
  (impl/kind-as-a-fn :kind/var))

(def map
  (impl/kind-as-a-fn :kind/map))

(def set
  (impl/kind-as-a-fn :kind/set))

(def vector
  (impl/kind-as-a-fn :kind/vector))

(def seq
  (impl/kind-as-a-fn :kind/seq))

(def table
  (impl/kind-as-a-fn :kind/table))

(def md
  (impl/kind-as-a-fn :kind/md))

(def hiccup
  (impl/kind-as-a-fn :kind/hiccup))

(def reagent
  (impl/kind-as-a-fn :kind/reagent))

(def vega
  (impl/kind-as-a-fn :kind/vega))

(def vega-lite
  (impl/kind-as-a-fn :kind/vega-lite))

(def cytoscape
  (impl/kind-as-a-fn :kind/cytoscape))

(def echarts
  (impl/kind-as-a-fn :kind/echarts))
