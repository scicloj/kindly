(ns scicloj.kindly.v4.api
  "See the kind namespace")

(defn attach-meta-to-value
  [value m]
  (if (instance? clojure.lang.IObj value)
    (vary-meta value merge m)
    (attach-meta-to-value [value] m)))

(defn attach-kind-to-value
  [value kind]
  (attach-meta-to-value value {:kindly/kind kind}))

(defn deep-merge
  "Recursively merges maps only."
  [& xs]
  (->> xs
       (remove nil?)
       (reduce (fn m [a b]
                 (if (and (map? a) (map? b))
                   (merge-with m a b)
                   b))
               {})))

(defn get-options
  []
  (-> (meta *ns*) :kindly/options))

(defn hide-code
  "Annotate whether the code of this value should be hidden"
  ([value]
    (hide-code value true))
  ([value bool]
   ;; Will change when Clay is updated
   (attach-meta-to-value value {:kindly/hide-code bool})))

(defn set-options!
  "Replaces *options* with options"
  [options]
  (hide-code
    (attach-kind-to-value
      (alter-meta! *ns* merge {:kindly/options options})
      :kind/hidden)))

(defn merge-options!
  "Mutates *options* with the deep merge of options"
  [options]
  (hide-code
    (attach-kind-to-value
      (alter-meta! *ns* deep-merge {:kindly/options options})
      :kind/hidden)))

(defn consider
  "Add metadata to a given value.
A values which cannot have metadata
(i.e., is not an instance of `IObj`)
is wrapped in a vector first"
  [value m]
  (cond (keyword? m) (attach-kind-to-value value m)
        (fn? m) (consider value (m))
        (map? m) (attach-meta-to-value value m)))

(defn check
  "Add a generated test using `:kind/test-last`"
  [& args]
  (consider args :kind/test-last))

(def known-kinds
  "A set of common visualization requests"
  #{
;; simple behaviours
    :kind/println
    :kind/pprint
    :kind/hidden
;; web dev
    :kind/html
    :kind/hiccup
    :kind/reagent
    :kind/scittle
    :kind/emmy-viewers
;; data visualization formats
    :kind/md
    :kind/tex
    :kind/code
    :kind/edn
    :kind/vega
    :kind/vega-lite
    :kind/echarts
    :kind/cytoscape
    :kind/plotly
    :kind/htmlwidgets-plotly
    :kind/htmlwidgets-ggplotly
    :kind/video
    :kind/observable
    :kind/highcharts
;; specific types
    :kind/image
    :kind/dataset
    :kind/smile-model
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
    :kind/portal
;; meta kinds
    :kind/fragment
    :kind/fn
    :kind/test-last})
