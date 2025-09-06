;; # Your code, Kindly expressed
^:kindly/hide-code
(ns index
  "Why kindly is the best way to request visualizations"
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [scicloj.kindly.v4.kind :as kind]
            [scicloj.kindly.v4.api :as kindly]))

^:kindly/hide-code
(kind/hiccup [:img {:src "../Kindly.svg"
                    :alt "Kindly logo"
                    :align "right"}])

;; Kindly is a standard way to annotate visualizations.
;; A visualization is a form of code will produce a result that we want to send to a tool to display in HTML.
;; Annotations enable literate programming, publishing, and interactive coding.

;; > Kindly show me the numbers
;; >
;; > -- Stephen Few, author of "Designing Tables and Graphs to Enlighten"

;; The name Kindly was chosen as a play on words related to identifying the `kind` of visualization requested,
;; and the way in which one might make the request.

;; ## Setup

;; Add Kindly as a dependency of your project

;; [![Clojars Project](https://img.shields.io/clojars/v/org.scicloj/kindly.svg)](https://clojars.org/org.scicloj/kindly)

;; ## Usage: Requesting visualizations with Kindly

;; Kindly supports several ways to request a visualization.
;; For most use cases "functional style" will work best.

;; ### Functional style (recommended)

;; The `kind` namespace contains functions that annotate forms and values with a known kind.

(require '[scicloj.kindly.v4.kind :as kind])

(kind/vega-lite
  {:description "A simple bar chart with embedded data."
   :data        {:values [{"a" "A", "b" 28},
                          {"a" "B", "b" 55},
                          {"a" "C", "b" 43},
                          {"a" "D", "b" 91},
                          {"a" "E", "b" 81},
                          {"a" "F", "b" 53},
                          {"a" "G", "b" 19},
                          {"a" "H", "b" 87},
                          {"a" "I", "b" 52}]},
   :mark        "bar",
   :encoding    {"x" {"field" "a", "type" "nominal", "axis" {"labelAngle" 0}},
                 "y" {"field" "b", "type" "quantitative"}}})

;; ### Attaching metadata to forms (alternative)

;; While the functional style `(kind/vega-lite ...)` should suit most use cases,
;; it is also possible to directly annotate metadata instead.
;; This may be useful in situations where you would like to avoid depending on Kindly as a library,
;; however the downside is that it requires more attention to detail.

;; Instead of using the functional api:

(kind/md "hello *world*")

;; We can directly attach a kind with metadata:

;; TODO: metadata is not shown in the rendered output

^{:kindly/kind :kind/md}
["hello *world*"]

;; Or with the more compact shorthand:

^:kind/md
["hello *world*"]

;; :::{.callout-warning}
;; Not all values support metadata!
;; Primitives like strings, numbers, nil and keywords can be wrapped in a vector as seen above to work around this.
;; :::

;; ## Catalogue of available Kinds (Visualisations)

(def all-kinds
  (-> (io/resource "kinds.edn")
      (slurp)
      (edn/read-string)))

(kind/hiccup [:h1 "TEST"])

(require '[clojure.test :refer :all])
(deftest t
  (is (= 1 2))
  (is (= 2 2))
  (is (= 0 (/ 1 0))))

(kind/hiccup
    (into (kind/hiccup [:div])
          (apply concat
                 (for [[category kinds] all-kinds]
                   [(into [:h3 category]
                          (apply concat
                                 (for [[kind {:keys [display-as example docs json-schema]}] kinds]
                                   [[:h4 kind [:em display-as]]
                                    (when example
                                      (kindly/attach-kind-to-value example (keyword "kind" (name kind))))
                                    (kind/md docs)
                                    [:a {:href docs} "docs"]
                                    [:a {:href json-schema} "json-schema"]])))]))))
;; ## The Kindly Specification

;; Kindly is a specification and a library.
;; Kindly crystallizes conventions for how to request visualizations, and what they should mean.
;; The primary role of Kindly is to define known kinds of visualizations that can be requested.

kindly/known-kinds

;; The specification generates helper code so that users can more conveniently make use of those conventions.
;; The specification itself is contained in the [kinds.edn](resources/kinds.edn)

;; ## Related tools and libraries

;; To benefit from Kindly annotations, users will need to make use of tools and libraries that recognize them.

;; * [Clay](https://github.com/scicloj/clay/) for rendering notebooks and individual visualizations
;; * [kind-clerk](https://github.com/scicloj/kind-clerk/) for rendering notebooks
;; * [kind-portal](https://github.com/scicloj/kind-portal/) for viewing individual visualizations at the REPL

;; Kind inference

;; * [kindly-advice](https://github.com/scicloj/kindly-advice/)
;;   identifies kinds in a standard way, and provides the mechanisms for kind inference where desired.
;;   This library simplifies the task of toolmakers in supporting kinds.

;; ## Example projects using Kindly

;; * [Clay documentation](https://scicloj.github.io/clay/) is written using Kindly annotations,
;;   and contains examples of what Kindly visualizations look like.
;; * The [Clojure Data Cookbook](https://github.com/scicloj/clojure-data-cookbook)
;;   is an example of a book that can be visualized and published with several different tools.
;; * Kindly itself is documented with Kindly annotations, see [notebooks/index.clj](notebooks/index.clj).

;; ## Form and value options

;; Options take the following form:

{:deps         {:reagent "https://reagent.dev/reagent"}
 :hide-code    true
 :side-by-side :horizontal}

;; All the kind annotation functions accept an optional second argument:

(kind/table [] {:side-by-side :horizontal})

;; ## Kind specific options

;; kind specific options
{:kinds {:table {:class "responsive"}
         :var   {:hide-code true}}}

;; current way
^{:kindly/options {:keys-that-hide-code #{:kind/var :kind/hiccup}}}

;; new way
^{:kindly/options {:kinds {:var    {:hide-code true}
                           :hiccup {:hide-code true
                                    :class     "responsive"}}}}

;; kind specific options
{:kinds {:table {:class "responsive"}
         :var   {:hide-code true}}}



;; when on a ns form, it should MUTATE
;; otherwise it only applies to one form

;; another way now is
(kindly/set-options! {:code-value :horizontal})

(ns index
  {:kindly/options {:hide-code true}}
  (:require [clojure.edn :as edn]))

;; for one form
(kind/table {:rows [[]]}
            {:class          "reactive"
             :style          {}
             :code-and-value :horizontal})

;; add prn in clay
(comment
  (require '[scicloj.clay.v2.api :as clay])
  (clay/make! {:title       "Your code, Kindly expressed"
               :source-path ["index.clj"]}))
