;; # Kindly

;; Kindly is a proposed common ground for Clojure literate programming.

;; ## Status

;; (2022-10-23, v3)
;;
;; - still alpha
;; - will soon be used  in community projects such as [ds4clj](https://scicloj.github.io/docs/community/groups/ds4clj/)
;; - currently supported by the [Clay](https://scicloj.github.io/viz.clj/) tool
;; - will hopefully have adapters for Oz, Calva Notebooks, Portal, Clerk, etc.
;; - will hopefully be composable with [Casegamas](https://github.com/behrica/casegamas)
;; - maintained by Daniel Slutsky
;; - inspired by converstions with Carsten Behring, Lukas Domalga, Kira McLean, Christopher Small, Martin Kavalar, Tomasz Sulej, and many other friends
;;
;; ## The problem

;; - [amazing diversity](https://scicloj.github.io/docs/resources/libs/#visual-tools-literate-programming-and-data-visualization) of Clojure tools for data visualization and literate programming
;;
;; - different tools have different ways of writing notes -- for example:
;;   - [Anglican tutorials](https://probprog.github.io/anglican/examples/index.html) ([source](https://bitbucket.org/probprog/anglican-examples/src/master/worksheets/)) - written in [Gorilla REPL](https://github.com/JonyEpsilon/gorilla-repl)
;;   - [thi-ng/geom viz examples](https://github.com/thi-ng/geom/blob/feature/no-org/org/examples/viz/demos.org)  ([source](https://raw.githubusercontent.com/thi-ng/geom/feature/no-org/org/examples/viz/demos.org)) - written in [Org-babel-clojure](https://orgmode.org/worg/org-contrib/babel/languages/ob-doc-clojure.html)
;;   - [Clojure2d docs](https://github.com/Clojure2D/clojure2d#Usage) ([source1](https://github.com/Clojure2D/clojure2d/blob/master/src/clojure2d), [source2](https://github.com/Clojure2D/clojure2d/blob/master/metadoc/clojure2d/)) - written in [Codox](https://github.com/weavejester/codox) and [Metadoc](https://github.com/generateme/metadoc)
;;   - [Tablecloth API docs](https://scicloj.github.io/tablecloth/index.html) ([source](https://github.com/scicloj/tablecloth/blob/master/docs/index.Rmd)) - written in [rmarkdown-clojure](https://github.com/genmeblog/rmarkdown-clojure)
;;   - [R interop ClojisR examples](https://github.com/scicloj/clojisr-examples) ([source](https://github.com/scicloj/clojisr-examples/tree/master/src/clojisr_examples)) - written in [Notespace v2](https://github.com/scicloj/notespace/blob/master/doc/v2.md)
;;   - [Bayesian optimization tutorial](https://nextjournal.com/generateme/bayesian-optimization) ([source](https://nextjournal.com/generateme/bayesian-optimization)) - written in [Nextjournal](https://nextjournal.com/)
;;   - [scicloj.ml tutorials](https://github.com/scicloj/scicloj.ml-tutorials#tutorials-for-sciclojml) ([source](https://github.com/scicloj/scicloj.ml-tutorials/tree/main/src/scicloj/ml)) - written in [Notespace v3](https://github.com/scicloj/notespace/blob/master/doc/v3.md)
;;   - [Clojure2d color tutorial](https://clojure2d.github.io/clojure2d/docs/notebooks/index.html#/notebooks/color.clj) ([source](https://github.com/Clojure2D/clojure2d/blob/master/notebooks/color.clj)) - written in [Clerk](https://github.com/nextjournal/)
;;   - [Viz.clj](https://scicloj.github.io/viz.clj/) ([source](https://github.com/scicloj/viz.clj/blob/master/notebooks/intro.clj)) - written in Kindly using [Clay](https://scicloj.github.io/viz.clj/)
;;   - ...
;;
;; - To use tutorials, the code needs adaptation to the tool of choice.
;;
;; ## Goal
;; - Allow writing copy-paste-friendly docs & tutorials (as Carsten Behring phrased it).
;; - A tutorial written today should just work with the tools of the future.
;;

;; ## Conceptual solution

;; - For a given context (code, form, value), a *kind* can be inferred (e.g., `:kind/hiccup`).
;; - The kind says how to display the value.
;; - Each tool look into Kindly's advice, and implement it when displaying notes.
;; - Kind-inference is user-customizable, with sensible defaults.
;; - Thus, we decouple kind inference from kind implementation.

;; ## Using Kindly

(ns index
  (:require [scicloj.kindly.v3.api :as kindly]
            [scicloj.kindly.v3.kind :as kind]
            [scicloj.kindly.v3.defaults :as defaults]
            [scicloj.clay.v2.api :as clay]))

(clay/start!)

(defaults/setup!)

;; Let us define two advices.
;;
;; One says that all numbers are of kind `:kind/abcd`:

(defn advice-number->abcd
  [{:as context
    :keys [value]}]
  (if (number? value)
    (assoc context :kind :kind/abcd)
    context))

;; One says that all strings are of kind `:kind/efgh`:

(defn advice-string->efgh
  [{:as context
    :keys [value]}]
  (if (string? value)
    (assoc context :kind :kind/efgh)
    context))

;; Let us use these two advices in some contexts:

(kindly/advice {:value 3}
               [advice-number->abcd
                advice-string->efgh])

(kindly/advice {:value "three"}
               [advice-number->abcd
                advice-string->efgh])

(kindly/advice {:value :three}
               [advice-number->abcd
                advice-string->efgh])

;; We can make these advices the current state of Kindly,
;; to avoid the need of passing them all the time
;; (commented out here, to make the rendering of this notebook consistent).

(comment

  (kindly/set-advices!
   [advice-number->abcd
    advice-string->efgh])

  (kindly/advice {:value 3}))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(require 'default-advice)

#'default-advice/my-hiccup
