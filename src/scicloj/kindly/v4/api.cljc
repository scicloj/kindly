(ns scicloj.kindly.v4.api
  "See the kind namespace")

(defn attach-meta-to-value
  [value m]
  (if (instance? clojure.lang.IObj value)
    (vary-meta value merge m)
    (attach-meta-to-value [value] m)))

(defn attach-kind-to-value
  [value kind]
  (attach-meta-to-value [value {:kindly/kind kind}]))

(defn hide-code
  "Annotate whether the code of this value should be hidden"
  ([value]
    (hide-code value true))
  ([value bool]
    (if (instance? clojure.lang.IObj value)
      (vary-meta value assoc :kindly/hide-code true)
      (hide-code [value]))))

(defn consider
  "Add metadata to a given value.
A values which cannot have metadata
(i.e., is not an instance of `IObj`)
is wrapped in a vector first"
  [value m]
  (cond (keyword? m) (attach-kind-to-value value m)
        (fn? m) (consider value (m))
        (map? m) (attach-meta-to-value value m)))

(def known-kinds
  "A set of common visualization requests"
  #{
;; simple behaviours
    :kind/pprint
    :kind/hidden
;; web dev
    :kind/html
    :kind/hiccup
    :kind/reagent
;; data visualization formats
    :kind/md
    :kind/code
    :kind/vega
    :kind/vega-lite
    :kind/echarts
    :kind/cytoscape
    :kind/plotly
;; specific types
    :kind/image
    :kind/dataset
;; clojure specific
    :kind/var
    :kind/test
;; plain structures
    :kind/seq
    :kind/vector
    :kind/set
    :kind/map
;; other recursive structures
    :kind/table
    :kind/portal})
