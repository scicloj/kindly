(ns scicloj.kindly.v4.api
  "See the kind namespace")

(defn attach-kind-to-value
  "Prefer using the functions in the kind namespace instead"
  [value kind]
  (if (instance? clojure.lang.IObj value)
    (vary-meta value assoc :kindly/kind kind)
    (attach-kind-to-value [value] kind)))

(defn hide-code
  "Annotate whether the code of this value should be hidden"
  ([value]
    (hide-code value true))
  ([value bool]
    (if (instance? clojure.lang.IObj value)
      (vary-meta value assoc :kindly/hide-code true)
      (hide-code [value]))))

(defn consider
  "Prefer using the functions in the kind namespace instead"
  [value kind]
  (cond (keyword? kind) (attach-kind-to-value value kind)
        (fn? kind) (consider value (kind))))

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
