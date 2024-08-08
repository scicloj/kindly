(ns scicloj.kindly.v4.kind
  "Kinds for visualization"
  (:require [scicloj.kindly.v4.api :refer [attach-kind-to-value hide-code]])
  (:refer-clojure :exclude [test seq vector set map fn]))

;; ## simple behaviours

(defn pprint
  "display-as: a formatted string
example: {:key1 \"value1\", :key2 \"value2\"}"
  ([] :kind/pprint)
  ([value] (attach-kind-to-value value :kind/pprint)) 
  ([value options] (scicloj.kindly.v4.kind/pprint (vary-meta value assoc :kindly/options options))))

(defn hidden
  "display-as: do not display
example: [\"SECRET\"]"
  ([] :kind/hidden)
  ([value] (attach-kind-to-value value :kind/hidden)) 
  ([value options] (scicloj.kindly.v4.kind/hidden (vary-meta value assoc :kindly/options options))))


;; ## web dev

(defn html
  "display-as: HTML
example: <div><h3>Hello ><em>World</em></h3><div>"
  ([] :kind/html)
  ([value] (attach-kind-to-value value :kind/html)) 
  ([value options] (scicloj.kindly.v4.kind/html (vary-meta value assoc :kindly/options options))))

(defn hiccup
  "display-as: HTML
example: [:div [:h3 \"Hello \" [:em \"World\"]]]"
  ([] :kind/hiccup)
  ([value] (attach-kind-to-value value :kind/hiccup)) 
  ([value options] (scicloj.kindly.v4.kind/hiccup (vary-meta value assoc :kindly/options options))))

(defn reagent
  "display-as: A reagent component inside HTML
example: [(fn [] [:button {:on-click (fn [ev] (js/alert \"You pressed it\"))} \"Press me\"])]"
  ([] :kind/reagent)
  ([value] (attach-kind-to-value value :kind/reagent)) 
  ([value options] (scicloj.kindly.v4.kind/reagent (vary-meta value assoc :kindly/options options))))


;; ## data visualization formats

(defn md
  "display-as: a Markdown string
example: ## Hello *World*"
  ([] :kind/md)
  ([value] (attach-kind-to-value value :kind/md)) 
  ([value options] (scicloj.kindly.v4.kind/md (vary-meta value assoc :kindly/options options))))

(defn tex
  "display-as: a TeX formula
example: x^2"
  ([] :kind/tex)
  ([value] (attach-kind-to-value value :kind/tex)) 
  ([value options] (scicloj.kindly.v4.kind/tex (vary-meta value assoc :kindly/options options))))

(defn code
  "display-as: a piece syntax highlighted Clojure code
example: (+ 1 2)"
  ([] :kind/code)
  ([value] (attach-kind-to-value value :kind/code)) 
  ([value options] (scicloj.kindly.v4.kind/code (vary-meta value assoc :kindly/options options))))

(defn edn
  "display-as: a piece syntax highlighted EDN structure
example: {:x [1 2 3]}"
  ([] :kind/edn)
  ([value] (attach-kind-to-value value :kind/edn)) 
  ([value options] (scicloj.kindly.v4.kind/edn (vary-meta value assoc :kindly/options options))))

(defn vega
  "display-as: a chart
example: {:description \"A basic bar chart example, with value labels shown upon pointer hover.\", :axes [{:orient \"bottom\", :scale \"xscale\"} {:orient \"left\", :scale \"yscale\"}], :width 400, :scales [{:name \"xscale\", :type \"band\", :domain {:data \"table\", :field \"category\"}, :range \"width\", :padding 0.05, :round true} {:name \"yscale\", :domain {:data \"table\", :field \"amount\"}, :nice true, :range \"height\"}], :padding 5, :marks [{:type \"rect\", :from {:data \"table\"}, :encode {:enter {:x {:scale \"xscale\", :field \"category\"}, :width {:scale \"xscale\", :band 1}, :y {:scale \"yscale\", :field \"amount\"}, :y2 {:scale \"yscale\", :value 0}}, :update {:fill {:value \"steelblue\"}}, :hover {:fill {:value \"red\"}}}} {:type \"text\", :encode {:enter {:align {:value \"center\"}, :baseline {:value \"bottom\"}, :fill {:value \"#333\"}}, :update {:x {:scale \"xscale\", :signal \"tooltip.category\", :band 0.5}, :y {:scale \"yscale\", :signal \"tooltip.amount\", :offset -2}, :text {:signal \"tooltip.amount\"}, :fillOpacity [{:test \"datum === tooltip\", :value 0} {:value 1}]}}}], :signals [{:name \"tooltip\", :value {}, :on [{:events \"rect:pointerover\", :update \"datum\"} {:events \"rect:pointerout\", :update \"{}\"}]}], :height 200, :data [{:name \"table\", :values [{:category \"A\", :amount 28} {:category \"B\", :amount 55} {:category \"C\", :amount 43} {:category \"D\", :amount 91} {:category \"E\", :amount 81} {:category \"F\", :amount 53} {:category \"G\", :amount 19} {:category \"H\", :amount 87}]}]}
docs: https://vega.github.io/vega/docs/
json-schema: https://vega.github.io/schema/vega/v5.json"
  ([] :kind/vega)
  ([value] (attach-kind-to-value value :kind/vega)) 
  ([value options] (scicloj.kindly.v4.kind/vega (vary-meta value assoc :kindly/options options))))

(defn vega-lite
  "display-as: VegaLite chart
example: {:description \"A simple bar chart with embedded data.\", :data {:values [{\"a\" \"A\", \"b\" 28} {\"a\" \"B\", \"b\" 55} {\"a\" \"C\", \"b\" 43} {\"a\" \"D\", \"b\" 91} {\"a\" \"E\", \"b\" 81} {\"a\" \"F\", \"b\" 53} {\"a\" \"G\", \"b\" 19} {\"a\" \"H\", \"b\" 87} {\"a\" \"I\", \"b\" 52}]}, :mark \"bar\", :encoding {\"x\" {\"field\" \"a\", \"type\" \"nominal\", \"axis\" {\"labelAngle\" 0}}, \"y\" {\"field\" \"b\", \"type\" \"quantitative\"}}}
docs: https://vega.github.io/vega-lite/docs/
json-schema: https://vega.github.io/schema/vega-lite/v5.json"
  ([] :kind/vega-lite)
  ([value] (attach-kind-to-value value :kind/vega-lite)) 
  ([value options] (scicloj.kindly.v4.kind/vega-lite (vary-meta value assoc :kindly/options options))))

(defn echarts
  "display-as: a chart
example: [[\"a\" \"b\" \"c\" \"d\"] [1 2 3 4]]
docs: https://echarts.apache.org/en/option.html"
  ([] :kind/echarts)
  ([value] (attach-kind-to-value value :kind/echarts)) 
  ([value options] (scicloj.kindly.v4.kind/echarts (vary-meta value assoc :kindly/options options))))

(defn cytoscape
  "display-as: a graph
example: {:nodes #{1 4 3 2 5}, :edges #{[4 3] [4 2] [1 2] [3 5]}}
docs: https://js.cytoscape.org/#notation/elements-json
json-schema: https://raw.githubusercontent.com/AZaitzeff/cytoscape_js_schema/main/cytoscape_schema.json"
  ([] :kind/cytoscape)
  ([value] (attach-kind-to-value value :kind/cytoscape)) 
  ([value options] (scicloj.kindly.v4.kind/cytoscape (vary-meta value assoc :kindly/options options))))

(defn plotly
  "display-as: a plot
example: [{:x [1 2 3 4 5], :y [1 2 4 8 16]}]
docs: https://plotly.com/javascript/getting-started/
json-schema: https://plotly.com/chart-studio-help/json-chart-schema/"
  ([] :kind/plotly)
  ([value] (attach-kind-to-value value :kind/plotly)) 
  ([value options] (scicloj.kindly.v4.kind/plotly (vary-meta value assoc :kindly/options options))))

(defn htmlwidgets-plotly
  "display-as: a plot rendered by the JS client side of Plotly R
docs: https://plotly.com/r/"
  ([] :kind/htmlwidgets-plotly)
  ([value] (attach-kind-to-value value :kind/htmlwidgets-plotly)) 
  ([value options] (scicloj.kindly.v4.kind/htmlwidgets-plotly (vary-meta value assoc :kindly/options options))))

(defn htmlwidgets-ggplotly
  "display-as: a plot rendered by the JS client side of Plotly R, specifically for a ggplotly plot
docs: https://plotly.com/ggplot2/"
  ([] :kind/htmlwidgets-ggplotly)
  ([value] (attach-kind-to-value value :kind/htmlwidgets-ggplotly)) 
  ([value options] (scicloj.kindly.v4.kind/htmlwidgets-ggplotly (vary-meta value assoc :kindly/options options))))

(defn video
  "display-as: an embedded video
example: {:youtube-id \"MXHI4mgfVk8\"}"
  ([] :kind/video)
  ([value] (attach-kind-to-value value :kind/video)) 
  ([value options] (scicloj.kindly.v4.kind/video (vary-meta value assoc :kindly/options options))))

(defn observable
  "display-as: Observable visualizations
docs: https://observablehq.com/"
  ([] :kind/observable)
  ([value] (attach-kind-to-value value :kind/observable)) 
  ([value options] (scicloj.kindly.v4.kind/observable (vary-meta value assoc :kindly/options options))))

(defn highcharts
  "display-as: a chart
example: {:title {:text \"Line chart\"}, :subtitle {:text \"By Job Category\"}, :yAxis {:title {:text \"Number of Employees\"}}, :series [{:name \"Installation & Developers\", :data [43934 48656 65165 81827 112143 142383 171533 165174 155157 161454 154610]} {:name \"Manufacturing\", :data [24916 37941 29742 29851 32490 30282 38121 36885 33726 34243 31050]} {:name \"Sales & Distribution\", :data [11744 30000 16005 19771 20185 24377 32147 30912 29243 29213 25663]} {:name \"Operations & Maintenance\", :data [nil nil nil nil nil nil nil nil 11164 11218 10077]} {:name \"Other\", :data [21908 5548 8105 11248 8989 11816 18274 17300 13053 11906 10073]}], :xAxis {:accessibility {:rangeDescription \"Range: 2010 to 2020\"}}, :legend {:layout \"vertical\", :align \"right\", :verticalAlign \"middle\"}, :plotOptions {:series {:label {:connectorAllowed false}, :pointStart 2010}}, :responsive {:rules [{:condition {:maxWidth 500}, :chartOptions {:legend {:layout \"horizontal\", :align \"center\", :verticalAlign \"bottom\"}}}]}}
docs: https://www.highcharts.com/docs/index
json-schema: "
  ([] :kind/highcharts)
  ([value] (attach-kind-to-value value :kind/highcharts)) 
  ([value options] (scicloj.kindly.v4.kind/highcharts (vary-meta value assoc :kindly/options options))))


;; ## specific types

(defn image
  "display-as: an image
example: At the moment, java BufferedImage objects are supported."
  ([] :kind/image)
  ([value] (attach-kind-to-value value :kind/image)) 
  ([value options] (scicloj.kindly.v4.kind/image (vary-meta value assoc :kindly/options options))))

(defn dataset
  "display-as: a table
example: (->> (System/getProperties) (map (fn [[k v]] {:k k, :v (apply str (take 40 (str v)))})) (tech.v3.dataset/->>dataset {:dataset-name \"My Truncated System Properties\"}))
docs: https://github.com/techascent/tech.ml.dataset"
  ([] :kind/dataset)
  ([value] (attach-kind-to-value value :kind/dataset)) 
  ([value options] (scicloj.kindly.v4.kind/dataset (vary-meta value assoc :kindly/options options))))

(defn smile-model
  "display-as: the `str` value should be displayesd as code
docs: https://haifengl.github.io/"
  ([] :kind/smile-model)
  ([value] (attach-kind-to-value value :kind/smile-model)) 
  ([value options] (scicloj.kindly.v4.kind/smile-model (vary-meta value assoc :kindly/options options))))


;; ## clojure specific

(defn var
  "display-as: the name of a var
example: (def testvar 100)"
  ([] :kind/var)
  ([value] (attach-kind-to-value value :kind/var)) 
  ([value options] (scicloj.kindly.v4.kind/var (vary-meta value assoc :kindly/options options))))

(defn test
  "display-as: success or failure
example: (deftest unity-test (is (= 1 1)))"
  ([] :kind/test)
  ([value] (attach-kind-to-value value :kind/test)) 
  ([value options] (scicloj.kindly.v4.kind/test (vary-meta value assoc :kindly/options options))))


;; ## plain structures

(defn seq
  "display-as: a sequence
example: (range 5)"
  ([] :kind/seq)
  ([value] (attach-kind-to-value value :kind/seq)) 
  ([value options] (scicloj.kindly.v4.kind/seq (vary-meta value assoc :kindly/options options))))

(defn vector
  "display-as: a sequence
example: (vec (range 5))"
  ([] :kind/vector)
  ([value] (attach-kind-to-value value :kind/vector)) 
  ([value options] (scicloj.kindly.v4.kind/vector (vary-meta value assoc :kindly/options options))))

(defn set
  "display-as: a bag
example: (set (range 5))"
  ([] :kind/set)
  ([value] (attach-kind-to-value value :kind/set)) 
  ([value options] (scicloj.kindly.v4.kind/set (vary-meta value assoc :kindly/options options))))

(defn map
  "display-as: associated values
example: {:key1 \"value1\", :key2 \"value2\"}"
  ([] :kind/map)
  ([value] (attach-kind-to-value value :kind/map)) 
  ([value options] (scicloj.kindly.v4.kind/map (vary-meta value assoc :kindly/options options))))


;; ## other recursive structures

(defn table
  "display-as: a table
example: {:headers [:a], :rows [{:a 1} {:a 2}]}"
  ([] :kind/table)
  ([value] (attach-kind-to-value value :kind/table)) 
  ([value options] (scicloj.kindly.v4.kind/table (vary-meta value assoc :kindly/options options))))

(defn portal
  "display-as: portal
example: {:key1 \"value1\", :key2 [:div [:h3 \"Hello \" [:em \"World\"]]]}"
  ([] :kind/portal)
  ([value] (attach-kind-to-value value :kind/portal)) 
  ([value options] (scicloj.kindly.v4.kind/portal (vary-meta value assoc :kindly/options options))))


;; ## meta kinds

(defn fragment
  "display-as: one toplevel context with a sequential value considered as many toplevel contexts of various kinds
example: [[\"**hello**\"] [:p [:b \"hello\"]]]"
  ([] :kind/fragment)
  ([value] (attach-kind-to-value value :kind/fragment)) 
  ([value options] (scicloj.kindly.v4.kind/fragment (vary-meta value assoc :kindly/options options))))

(defn fn
  "display-as: the evaluation of the given function and arguments
example: [+ 2 3]"
  ([] :kind/fn)
  ([value] (attach-kind-to-value value :kind/fn)) 
  ([value options] (scicloj.kindly.v4.kind/fn (vary-meta value assoc :kindly/options options))))

(defn test-last
  "display-as: invisible (both code and value), but generates a test
example: [> 9]"
  ([] :kind/test-last)
  ([value] (attach-kind-to-value value :kind/test-last)) 
  ([value options] (scicloj.kindly.v4.kind/test-last (vary-meta value assoc :kindly/options options))))

