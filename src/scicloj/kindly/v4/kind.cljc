(ns scicloj.kindly.v4.kind
  "Kinds for visualization"
  (:require [scicloj.kindly.v4.api :refer [attach-kind-to-value hide-code]])
  (:refer-clojure :exclude [test seq vector set map fn]))

;; ## simple behaviours

(defn pprint
  "display-as: a formatted string
example: {:key1 \"value1\", :key2 \"value2\"}"
  ([] :kind/pprint)
  ([value] (pprint nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/pprint :kindly/options options})))

(defn hidden
  "display-as: do not display
example: [\"SECRET\"]"
  ([] :kind/hidden)
  ([value] (hidden nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/hidden :kindly/options options})))


;; ## web dev

(defn html
  "display-as: HTML
example: <div><h3>Hello ><em>World</em></h3><div>"
  ([] :kind/html)
  ([value] (html nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/html :kindly/options options})))

(defn hiccup
  "display-as: HTML
example: [:div [:h3 \"Hello \" [:em \"World\"]]]"
  ([] :kind/hiccup)
  ([value] (hiccup nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/hiccup :kindly/options options})))

(defn reagent
  "display-as: A reagent component inside HTML
example: [(fn [] [:button {:on-click (fn [ev] (js/alert \"You pressed it\"))} \"Press me\"])]"
  ([] :kind/reagent)
  ([value] (reagent nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/reagent :kindly/options options})))


;; ## data visualization formats

(defn md
  "display-as: a Markdown string
example: ## Hello *World*"
  ([] :kind/md)
  ([value] (md nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/md :kindly/options options})))

(defn tex
  "display-as: a TeX formula
example: x^2"
  ([] :kind/tex)
  ([value] (tex nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/tex :kindly/options options})))

(defn code
  "display-as: a piece syntax highlighted Clojure code
example: (+ 1 2)"
  ([] :kind/code)
  ([value] (code nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/code :kindly/options options})))

(defn edn
  "display-as: a piece syntax highlighted EDN structure
example: {:x [1 2 3]}"
  ([] :kind/edn)
  ([value] (edn nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/edn :kindly/options options})))

(defn vega
  "display-as: a chart
example: {:description \"A basic bar chart example, with value labels shown upon pointer hover.\", :axes [{:orient \"bottom\", :scale \"xscale\"} {:orient \"left\", :scale \"yscale\"}], :width 400, :scales [{:name \"xscale\", :type \"band\", :domain {:data \"table\", :field \"category\"}, :range \"width\", :padding 0.05, :round true} {:name \"yscale\", :domain {:data \"table\", :field \"amount\"}, :nice true, :range \"height\"}], :padding 5, :marks [{:type \"rect\", :from {:data \"table\"}, :encode {:enter {:x {:scale \"xscale\", :field \"category\"}, :width {:scale \"xscale\", :band 1}, :y {:scale \"yscale\", :field \"amount\"}, :y2 {:scale \"yscale\", :value 0}}, :update {:fill {:value \"steelblue\"}}, :hover {:fill {:value \"red\"}}}} {:type \"text\", :encode {:enter {:align {:value \"center\"}, :baseline {:value \"bottom\"}, :fill {:value \"#333\"}}, :update {:x {:scale \"xscale\", :signal \"tooltip.category\", :band 0.5}, :y {:scale \"yscale\", :signal \"tooltip.amount\", :offset -2}, :text {:signal \"tooltip.amount\"}, :fillOpacity [{:test \"datum === tooltip\", :value 0} {:value 1}]}}}], :signals [{:name \"tooltip\", :value {}, :on [{:events \"rect:pointerover\", :update \"datum\"} {:events \"rect:pointerout\", :update \"{}\"}]}], :height 200, :data [{:name \"table\", :values [{:category \"A\", :amount 28} {:category \"B\", :amount 55} {:category \"C\", :amount 43} {:category \"D\", :amount 91} {:category \"E\", :amount 81} {:category \"F\", :amount 53} {:category \"G\", :amount 19} {:category \"H\", :amount 87}]}]}
docs: https://vega.github.io/vega/docs/
json-schema: https://vega.github.io/schema/vega/v5.json"
  ([] :kind/vega)
  ([value] (vega nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/vega :kindly/options options})))

(defn vega-lite
  "display-as: VegaLite chart
example: {:description \"A simple bar chart with embedded data.\", :data {:values [{\"a\" \"A\", \"b\" 28} {\"a\" \"B\", \"b\" 55} {\"a\" \"C\", \"b\" 43} {\"a\" \"D\", \"b\" 91} {\"a\" \"E\", \"b\" 81} {\"a\" \"F\", \"b\" 53} {\"a\" \"G\", \"b\" 19} {\"a\" \"H\", \"b\" 87} {\"a\" \"I\", \"b\" 52}]}, :mark \"bar\", :encoding {\"x\" {\"field\" \"a\", \"type\" \"nominal\", \"axis\" {\"labelAngle\" 0}}, \"y\" {\"field\" \"b\", \"type\" \"quantitative\"}}}
docs: https://vega.github.io/vega-lite/docs/
json-schema: https://vega.github.io/schema/vega-lite/v5.json"
  ([] :kind/vega-lite)
  ([value] (vega-lite nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/vega-lite :kindly/options options})))

(defn echarts
  "display-as: a chart
example: [[\"a\" \"b\" \"c\" \"d\"] [1 2 3 4]]
docs: https://echarts.apache.org/en/option.html"
  ([] :kind/echarts)
  ([value] (echarts nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/echarts :kindly/options options})))

(defn cytoscape
  "display-as: a graph
example: {:nodes #{1 4 3 2 5}, :edges #{[4 3] [4 2] [1 2] [3 5]}}
docs: https://js.cytoscape.org/#notation/elements-json
json-schema: https://raw.githubusercontent.com/AZaitzeff/cytoscape_js_schema/main/cytoscape_schema.json"
  ([] :kind/cytoscape)
  ([value] (cytoscape nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/cytoscape :kindly/options options})))

(defn plotly
  "display-as: a plot
example: [{:x [1 2 3 4 5], :y [1 2 4 8 16]}]
docs: https://plotly.com/javascript/getting-started/
json-schema: https://plotly.com/chart-studio-help/json-chart-schema/"
  ([] :kind/plotly)
  ([value] (plotly nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/plotly :kindly/options options})))

(defn htmlwidgets-plotly
  "display-as: a plot rendered by the JS client side of Plotly R
docs: https://plotly.com/r/"
  ([] :kind/htmlwidgets-plotly)
  ([value] (htmlwidgets-plotly nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/htmlwidgets-plotly :kindly/options options})))

(defn htmlwidgets-ggplotly
  "display-as: a plot rendered by the JS client side of Plotly R, specifically for a ggplotly plot
docs: https://plotly.com/ggplot2/"
  ([] :kind/htmlwidgets-ggplotly)
  ([value] (htmlwidgets-ggplotly nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/htmlwidgets-ggplotly :kindly/options options})))

(defn video
  "display-as: an embedded video
example: {:youtube-id \"MXHI4mgfVk8\"}"
  ([] :kind/video)
  ([value] (video nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/video :kindly/options options})))

(defn observable
  "display-as: Observable visualizations
docs: https://observablehq.com/"
  ([] :kind/observable)
  ([value] (observable nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/observable :kindly/options options})))

(defn highcharts
  "display-as: a chart
example: {:title {:text \"Line chart\"}, :subtitle {:text \"By Job Category\"}, :yAxis {:title {:text \"Number of Employees\"}}, :series [{:name \"Installation & Developers\", :data [43934 48656 65165 81827 112143 142383 171533 165174 155157 161454 154610]} {:name \"Manufacturing\", :data [24916 37941 29742 29851 32490 30282 38121 36885 33726 34243 31050]} {:name \"Sales & Distribution\", :data [11744 30000 16005 19771 20185 24377 32147 30912 29243 29213 25663]} {:name \"Operations & Maintenance\", :data [nil nil nil nil nil nil nil nil 11164 11218 10077]} {:name \"Other\", :data [21908 5548 8105 11248 8989 11816 18274 17300 13053 11906 10073]}], :xAxis {:accessibility {:rangeDescription \"Range: 2010 to 2020\"}}, :legend {:layout \"vertical\", :align \"right\", :verticalAlign \"middle\"}, :plotOptions {:series {:label {:connectorAllowed false}, :pointStart 2010}}, :responsive {:rules [{:condition {:maxWidth 500}, :chartOptions {:legend {:layout \"horizontal\", :align \"center\", :verticalAlign \"bottom\"}}}]}}
docs: https://www.highcharts.com/docs/index
json-schema: "
  ([] :kind/highcharts)
  ([value] (highcharts nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/highcharts :kindly/options options})))


;; ## specific types

(defn image
  "display-as: an image
example: At the moment, java BufferedImage objects are supported."
  ([] :kind/image)
  ([value] (image nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/image :kindly/options options})))

(defn dataset
  "display-as: a table
example: (->> (System/getProperties) (map (fn [[k v]] {:k k, :v (apply str (take 40 (str v)))})) (tech.v3.dataset/->>dataset {:dataset-name \"My Truncated System Properties\"}))
docs: https://github.com/techascent/tech.ml.dataset"
  ([] :kind/dataset)
  ([value] (dataset nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/dataset :kindly/options options})))

(defn smile-model
  "display-as: the `str` value should be displayesd as code
docs: https://haifengl.github.io/"
  ([] :kind/smile-model)
  ([value] (smile-model nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/smile-model :kindly/options options})))


;; ## clojure specific

(defn var
  "display-as: the name of a var
example: (def testvar 100)"
  ([] :kind/var)
  ([value] (var nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/var :kindly/options options})))

(defn test
  "display-as: success or failure
example: (deftest unity-test (is (= 1 1)))"
  ([] :kind/test)
  ([value] (test nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/test :kindly/options options})))


;; ## plain structures

(defn seq
  "display-as: a sequence
example: (range 5)"
  ([] :kind/seq)
  ([value] (seq nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/seq :kindly/options options})))

(defn vector
  "display-as: a sequence
example: (vec (range 5))"
  ([] :kind/vector)
  ([value] (vector nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/vector :kindly/options options})))

(defn set
  "display-as: a bag
example: (set (range 5))"
  ([] :kind/set)
  ([value] (set nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/set :kindly/options options})))

(defn map
  "display-as: associated values
example: {:key1 \"value1\", :key2 \"value2\"}"
  ([] :kind/map)
  ([value] (map nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/map :kindly/options options})))


;; ## other recursive structures

(defn table
  "display-as: a table
example: {:headers [:a], :rows [{:a 1} {:a 2}]}"
  ([] :kind/table)
  ([value] (table nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/table :kindly/options options})))

(defn portal
  "display-as: portal
example: {:key1 \"value1\", :key2 [:div [:h3 \"Hello \" [:em \"World\"]]]}"
  ([] :kind/portal)
  ([value] (portal nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/portal :kindly/options options})))


;; ## meta kinds

(defn fragment
  "display-as: one toplevel context with a sequential value considered as many toplevel contexts of various kinds
example: [[\"**hello**\"] [:p [:b \"hello\"]]]"
  ([] :kind/fragment)
  ([value] (fragment nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/fragment :kindly/options options})))

(defn fn
  "display-as: the evaluation of the given function and arguments
example: [+ 2 3]"
  ([] :kind/fn)
  ([value] (fn nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/fn :kindly/options options})))

(defn test-last
  "display-as: invisible (both code and value), but generates a test
example: [> 9]"
  ([] :kind/test-last)
  ([value] (test-last nil))  ([value options] (attach-meta-to-value value {:kindly/kind :kind/test-last :kindly/options options})))

