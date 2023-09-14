(ns scicloj.kindly.v4.kind
  "Kinds for visualization"
  (:require [scicloj.kindly.v4.api :refer [attach-kind-to-value]]))

;; ## simple behaviours

(defn pprint
  "display-as: a formatted string"
  ([] :kind/pprint)
  ([value] (attach-kind-to-value value :kind/pprint)))

(defn hidden
  "display-as: do not display"
  ([] :kind/hidden)
  ([value] (attach-kind-to-value value :kind/hidden)))


;; ## web dev

(defn hiccup
  "display-as: HTML"
  ([] :kind/hiccup)
  ([value] (attach-kind-to-value value :kind/hiccup)))

(defn reagent
  "display-as: A reagent component inside HTML"
  ([] :kind/reagent)
  ([value] (attach-kind-to-value value :kind/reagent)))


;; ## data visualization formats

(defn md
  "display-as: a Markdown string"
  ([] :kind/md)
  ([value] (attach-kind-to-value value :kind/md)))

(defn code
  "display-as: ???"
  ([] :kind/code)
  ([value] (attach-kind-to-value value :kind/code)))

(defn vega
  "display-as: A Vega chart
docs: https://vega.github.io/vega/docs/
json-schema: https://vega.github.io/schema/vega/v5.json"
  ([] :kind/vega)
  ([value] (attach-kind-to-value value :kind/vega)))

(defn vega-lite
  "display-as: VegaLite chart
docs: https://vega.github.io/vega-lite/docs/
json-schema: https://vega.github.io/schema/vega-lite/v5.json"
  ([] :kind/vega-lite)
  ([value] (attach-kind-to-value value :kind/vega-lite)))

(defn echarts
  ""
  ([] :kind/echarts)
  ([value] (attach-kind-to-value value :kind/echarts)))

(defn cytoscape
  ""
  ([] :kind/cytoscape)
  ([value] (attach-kind-to-value value :kind/cytoscape)))


;; ## specific types

(defn image
  ""
  ([] :kind/image)
  ([value] (attach-kind-to-value value :kind/image)))

(defn dataset
  ""
  ([] :kind/dataset)
  ([value] (attach-kind-to-value value :kind/dataset)))


;; ## clojure specific

(defn var
  ""
  ([] :kind/var)
  ([value] (attach-kind-to-value value :kind/var)))

(defn test
  ""
  ([] :kind/test)
  ([value] (attach-kind-to-value value :kind/test)))


;; ## plain structures

(defn seq
  ""
  ([] :kind/seq)
  ([value] (attach-kind-to-value value :kind/seq)))

(defn vector
  ""
  ([] :kind/vector)
  ([value] (attach-kind-to-value value :kind/vector)))

(defn set
  ""
  ([] :kind/set)
  ([value] (attach-kind-to-value value :kind/set)))

(defn map
  ""
  ([] :kind/map)
  ([value] (attach-kind-to-value value :kind/map)))


;; ## other recursive structures

(defn table
  ""
  ([] :kind/table)
  ([value] (attach-kind-to-value value :kind/table)))

