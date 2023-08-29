(ns scicloj.kindly.v4.kind
  (:require [scicloj.kindly.v4.fn :as fn]))

(def hidden
  (fn/kind-as-a-fn :kind/hidden))

(def pprint
  (fn/kind-as-a-fn :kind/pprint))

(def println
  (fn/kind-as-a-fn :kind/println))

(def test
  (fn/kind-as-a-fn :kind/test))

(def var
  (fn/kind-as-a-fn :kind/var))

(def map
  (fn/kind-as-a-fn :kind/map))

(def set
  (fn/kind-as-a-fn :kind/set))

(def vector
  (fn/kind-as-a-fn :kind/vector))

(def seq
  (fn/kind-as-a-fn :kind/seq))

(def table
  (fn/kind-as-a-fn :kind/table))

(def md
  (fn/kind-as-a-fn :kind/md))

(def hiccup
  (fn/kind-as-a-fn :kind/hiccup))

(def reagent
  (fn/kind-as-a-fn :kind/reagent))

(def vega
  (fn/kind-as-a-fn :kind/vega))

(def vega-lite
  (fn/kind-as-a-fn :kind/vega-lite))

(def cytoscape
  (fn/kind-as-a-fn :kind/cytoscape))

(def echarts
  (fn/kind-as-a-fn :kind/echarts))
