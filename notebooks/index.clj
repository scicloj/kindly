(ns index
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [scicloj.kindly.v4.kind :as kind]
            [scicloj.kindly.v4.api :as api]))

;; # Kindly

;; Kindly is a way to request visualizations of data in a common syntax that works across many tools.
;; This enables literate coding for richer interactive code and publication.

;; > Kindly show me the numbers
;; >
;; > -- Stephen Few, author of "Designing Tables and Graphs to Enlighten"

;; The name Kindly was chosen as a play on words related to identifying the `kind` of visualization requested,
;; and the way in which one might make the request.

;; See the README for setup and getting started information.

;; ## Examples

^:kind/hidden
(def all-kinds
  (-> (io/resource "kinds.edn")
      (slurp)
      (edn/read-string)))

(kind/hiccup
  (for [[category kinds] all-kinds]
    [:<>
     [:h2 category]
     (for [[kind {:keys [display-as example docs json-schema]}] kinds]
       [:<>
        [:h3 kind [:em display-as]]
        (api/attach-kind-to-value example (keyword "kind" (name kind)))
        (kind/md docs)
        [:a {:href docs} "docs"]
        [:a {:href json-schema} "json-schema"]])]))
