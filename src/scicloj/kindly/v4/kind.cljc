(ns scicloj.kindly.v4.kind
  "Kinds for visualization"
  (:require [scicloj.kindly.v4.api :refer [attach-kind-to-value hide-code]])
  (:refer-clojure :exclude [test seq vector set map]))

;; ## simple behaviours

(defn pprint
  "display-as: a formatted string
example: {:key1 \"value1\", :key2 \"value2\"}"
  ([] :kind/pprint)
  ([value] (attach-kind-to-value value :kind/pprint)) 
  ([value options] (pprint [options value])))

(defn hidden
  "display-as: do not display
example: [\"SECRET\"]"
  ([] :kind/hidden)
  ([value] (attach-kind-to-value value :kind/hidden)) 
  ([value options] (hidden [options value])))


;; ## web dev

(defn html
  "display-as: HTML
example: <div><h3>Hello ><em>World</em></h3><div>"
  ([] :kind/html)
  ([value] (attach-kind-to-value value :kind/html)) 
  ([value options] (html [options value])))

(defn hiccup
  "display-as: HTML
example: [:div [:h3 \"Hello \" [:em \"World\"]]]"
  ([] :kind/hiccup)
  ([value] (attach-kind-to-value value :kind/hiccup)) 
  ([value options] (hiccup [options value])))

(defn reagent
  "display-as: A reagent component inside HTML
example: [(fn [] [:button {:on-click (fn [ev] (js/alert \"You pressed it\"))} \"Press me\"])]"
  ([] :kind/reagent)
  ([value] (attach-kind-to-value value :kind/reagent)) 
  ([value options] (reagent [options value])))


;; ## data visualization formats

(defn md
  "display-as: a Markdown string
example: ## Hello *World*
hide-code: true"
  ([] :kind/md)
  ([value] (hide-code (attach-kind-to-value value :kind/md))) 
  ([value options] (md [options value])))

(defn code
  "display-as: the code only, not the result
example: (+ 1 2)
hide-code: true"
  ([] :kind/code)
  ([value] (hide-code (attach-kind-to-value value :kind/code))) 
  ([value options] (code [options value])))

(defn vega
  "display-as: a chart
example: {:description \"A basic bar chart example, with value labels shown upon pointer hover.\", :axes [{:orient \"bottom\", :scale \"xscale\"} {:orient \"left\", :scale \"yscale\"}], :width 400, :scales [{:name \"xscale\", :type \"band\", :domain {:data \"table\", :field \"category\"}, :range \"width\", :padding 0.05, :round true} {:name \"yscale\", :domain {:data \"table\", :field \"amount\"}, :nice true, :range \"height\"}], :padding 5, :marks [{:type \"rect\", :from {:data \"table\"}, :encode {:enter {:x {:scale \"xscale\", :field \"category\"}, :width {:scale \"xscale\", :band 1}, :y {:scale \"yscale\", :field \"amount\"}, :y2 {:scale \"yscale\", :value 0}}, :update {:fill {:value \"steelblue\"}}, :hover {:fill {:value \"red\"}}}} {:type \"text\", :encode {:enter {:align {:value \"center\"}, :baseline {:value \"bottom\"}, :fill {:value \"#333\"}}, :update {:x {:scale \"xscale\", :signal \"tooltip.category\", :band 0.5}, :y {:scale \"yscale\", :signal \"tooltip.amount\", :offset -2}, :text {:signal \"tooltip.amount\"}, :fillOpacity [{:test \"datum === tooltip\", :value 0} {:value 1}]}}}], :signals [{:name \"tooltip\", :value {}, :on [{:events \"rect:pointerover\", :update \"datum\"} {:events \"rect:pointerout\", :update \"{}\"}]}], :height 200, :data [{:name \"table\", :values [{:category \"A\", :amount 28} {:category \"B\", :amount 55} {:category \"C\", :amount 43} {:category \"D\", :amount 91} {:category \"E\", :amount 81} {:category \"F\", :amount 53} {:category \"G\", :amount 19} {:category \"H\", :amount 87}]}]}
docs: https://vega.github.io/vega/docs/
json-schema: https://vega.github.io/schema/vega/v5.json"
  ([] :kind/vega)
  ([value] (attach-kind-to-value value :kind/vega)) 
  ([value options] (vega [options value])))

(defn vega-lite
  "display-as: VegaLite chart
example: {:description \"A simple bar chart with embedded data.\", :data {:values [{\"a\" \"A\", \"b\" 28} {\"a\" \"B\", \"b\" 55} {\"a\" \"C\", \"b\" 43} {\"a\" \"D\", \"b\" 91} {\"a\" \"E\", \"b\" 81} {\"a\" \"F\", \"b\" 53} {\"a\" \"G\", \"b\" 19} {\"a\" \"H\", \"b\" 87} {\"a\" \"I\", \"b\" 52}]}, :mark \"bar\", :encoding {\"x\" {\"field\" \"a\", \"type\" \"nominal\", \"axis\" {\"labelAngle\" 0}}, \"y\" {\"field\" \"b\", \"type\" \"quantitative\"}}}
docs: https://vega.github.io/vega-lite/docs/
json-schema: https://vega.github.io/schema/vega-lite/v5.json"
  ([] :kind/vega-lite)
  ([value] (attach-kind-to-value value :kind/vega-lite)) 
  ([value options] (vega-lite [options value])))

(defn echarts
  "display-as: a chart
example: [[\"a\" \"b\" \"c\" \"d\"] [1 2 3 4]]
docs: https://echarts.apache.org/en/option.html"
  ([] :kind/echarts)
  ([value] (attach-kind-to-value value :kind/echarts)) 
  ([value options] (echarts [options value])))

(defn cytoscape
  "display-as: a graph
example: {:nodes #{1 4 3 2 5}, :edges #{[4 3] [4 2] [1 2] [3 5]}}
docs: https://js.cytoscape.org/#notation/elements-json
json-schema: https://raw.githubusercontent.com/AZaitzeff/cytoscape_js_schema/main/cytoscape_schema.json"
  ([] :kind/cytoscape)
  ([value] (attach-kind-to-value value :kind/cytoscape)) 
  ([value options] (cytoscape [options value])))

(defn plotly
  "display-as: a plot
example: [{:x [1 2 3 4 5], :y [1 2 4 8 16]}]
docs: https://plotly.com/javascript/getting-started/
json-schema: https://plotly.com/chart-studio-help/json-chart-schema/"
  ([] :kind/plotly)
  ([value] (attach-kind-to-value value :kind/plotly)) 
  ([value options] (plotly [options value])))


;; ## specific types

(defn image
  "display-as: an image
example: https://raw.githubusercontent.com/scicloj/graphic-design/live/icons/Kindly.svg"
  ([] :kind/image)
  ([value] (attach-kind-to-value value :kind/image)) 
  ([value options] (image [options value])))

(defn dataset
  "display-as: a table
example: (->> (System/getProperties) (map (fn [[k v]] {:k k, :v (apply str (take 40 (str v)))})) (tech.v3.dataset/->>dataset {:dataset-name \"My Truncated System Properties\"}))
docs: https://github.com/techascent/tech.ml.dataset"
  ([] :kind/dataset)
  ([value] (attach-kind-to-value value :kind/dataset)) 
  ([value options] (dataset [options value])))


;; ## clojure specific

(defn var
  "display-as: the name of a var
example: (def testvar 100)"
  ([] :kind/var)
  ([value] (attach-kind-to-value value :kind/var)) 
  ([value options] (var [options value])))

(defn test
  "display-as: success or failure
example: (deftest unity-test (is (= 1 1)))"
  ([] :kind/test)
  ([value] (attach-kind-to-value value :kind/test)) 
  ([value options] (test [options value])))


;; ## plain structures

(defn seq
  "display-as: a sequence
example: (range 5)"
  ([] :kind/seq)
  ([value] (attach-kind-to-value value :kind/seq)) 
  ([value options] (seq [options value])))

(defn vector
  "display-as: a sequence
example: (vec (range 5))"
  ([] :kind/vector)
  ([value] (attach-kind-to-value value :kind/vector)) 
  ([value options] (vector [options value])))

(defn set
  "display-as: a bag
example: (set (range 5))"
  ([] :kind/set)
  ([value] (attach-kind-to-value value :kind/set)) 
  ([value options] (set [options value])))

(defn map
  "display-as: associated values
example: {:key1 \"value1\", :key2 \"value2\"}"
  ([] :kind/map)
  ([value] (attach-kind-to-value value :kind/map)) 
  ([value options] (map [options value])))


;; ## other recursive structures

(defn table
  "display-as: a table
example: {:headers [:a], :rows [{:a 1} {:a 2}]}"
  ([] :kind/table)
  ([value] (attach-kind-to-value value :kind/table)) 
  ([value options] (table [options value])))

(defn portal
  "display-as: portal
example: {:key1 \"value1\", :key2 [:div [:h3 \"Hello \" [:em \"World\"]]]}"
  ([] :kind/portal)
  ([value] (attach-kind-to-value value :kind/portal)) 
  ([value options] (portal [options value])))

