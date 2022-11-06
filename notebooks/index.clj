;; # Kindly

;; Kindly is a proposed common ground for Clojure literate programming.
;;
;; It is a small library for specifying in what kind of way things should be displayed.
;;
;; It can offer its advice to various tools for data visualization and literate programming, with sensible defaults which are user customizable.
;;
;; It grew out of the [visual-tools group](https://scicloj.github.io/docs/community/groups/visual-tools/) and has been inspired by converstions with Carsten Behring, Lukas Domalga, Kira McLean, Christopher Small, Martin Kavalar, Tomasz Sulej, Ethan Miller, and many other friends.

;; ## Status

;; (v3, 2022-11-04)
;;
;; - still alpha
;; - will soon be used  in community projects such as [ds4clj](https://scicloj.github.io/docs/community/groups/ds4clj/)
;; - currently supported by the [Clay](https://scicloj.github.io/clay/) tool
;; - will hopefully have adapters for Oz, Calva Notebooks, Portal, Clerk, etc.
;; - will hopefully be composable with [Casegamas](https://github.com/behrica/casegamas)
;;

;; ## Rationale

;; ### The problem

;; - Clojure tools for data visualization and literate programming have an [amazing diversity](https://scicloj.github.io/docs/resources/libs/#visual-tools-literate-programming-and-data-visualization).
;;
;; - Different tools have different ways of writing notes. For example:
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
;; - Thus, to use such tutorials in various tools, the code needs adaptation to the tool of choice.
;;
;; ### Goal
;; - Allow writing docs & tutorials which are copy-paste-friendly (a term phrased by Carsten Behring).
;; - A tutorial written today should just work with the tools of the future.
;;

;; ### Conceptual solution

;; - For a given context (code, form, value), a *kind* can be inferred (e.g., `:kind/hiccup`). The kind says how to display the value.
;; - Kindly provides such kinds as advice for tools. Each tool may ask for Kindly's advice and apply it when displaying values.
;; - Thus, we decouple kind inference from kind application.
;; - Kind inference has sensible defaults, which are user-customizable.

;; ## The bigger landscape

;; Kindly is part of projects:

;; - [Kindly](https://scicloj.github.io/kindly/) - a common ground for Clojure literate programming
;; - [kindly-default](https://github.com/scicloj/kindly-default) - sensible defaults for Kindly
;; - [kindly-noted](https://github.com/scicloj/kindly-noted) - a common space for sharing Kindly-compatible notes
;; - [Clay](https://scicloj.github.io/clay/) - a minimalistic Kindly-compatible fool for data visualization and literate programming
;; - [Viz.clj](https://scicloj.github.io/viz.clj/) - a (WIP) Kindly-compatible library for literate programming on top of [Hanami](https://github.com/jsa-aerial/hanami)

;; ## Setup for this document

;; To demonstrate how to use Kindly with the [Clay](https://scicloj.github.io/clay/) to create the current document, let us first run the relevant initializations to render this document.
;; Typically, this part can be done in a `user.clj` file, once for a Clojure project.
;; Here, we do it explicitly at the beginning of the namespace.

(ns index
  (:require [scicloj.kindly.v3.api :as kindly]
            [scicloj.kindly.v3.kind :as kind]
            [scicloj.kindly.v3.kindness :as kindness]
            [scicloj.kindly-default.v1.api :as kindly-default]
            [scicloj.clay.v2.api :as clay])
  (:import java.awt.image.BufferedImage))

;; We declare this page to be displayed by the Kindly default advice.
(kindly-default/setup!)

;; We initialize Clay for rendering this page.
(clay/start!)

;; ## What Kindly does

;; Kindly's main entry point is the `kindly/advice` function. That is what tools use to ask for advice.
;; The input to that function is the context of evaluation of a given form.
;;
;; For example, if the user writes the code `(+ 1 2)` in their namespace, the relevant context is the form `(+ 1 2)` and the resulting value `3`. In the current case, since this namespace uses the default kind inference, there is no special kind inferred.

(kindly/advice {:form '(+ 1 2)
                :value 3})

;; Behind the scenes, Kindly's advice is based on a global state holding a sequence of functions called advisors. In the current case, it holds the default function, which was set up by the call to `(kindly-default/setup!)` above.

scicloj.kindly.v3.api/*advisors

;; Kindly simply runs those functions on the given context.

;; To explore Kindly's behaviour, it is also possible to use it in a purely functional way, with an explicitly chosen sequence of advisors.  For example, let us use a simple advisor that assigns the kind `:kind/abcd` to all contexts:

(defn abcd-advisor [context]
  (assoc context
         :kind :kind/abcd))

(kindly/advice {:form '(+ 1 2)
                :value 3}
               [abcd-advisor])

;; While this purely functional way is useful for debugging and testing, the recommended way to use Kindly is through the global `*advisors` atom. This way, the user can adjust the advisors to fit their needs (typically some version of the default), and the various tools would simply use the advice based on that user choice.

;; ## Using Kindly

;; We will describe Kindly's usage in three different cases:
;; - creating a tool (or a tool adapter) which supports Kindly's advice
;; - using Kindly for writing simple notes (tutorial/documentation/blog-post/etc.)
;; - using Kindly for notes in a custom way (with user-defined kind inference)

;; ### Kindly for tool makers
;; Various tools for data visualization and literate programming can ask for Kindly's advice.
;;
;; The single entry point for doing that is the `kindly/advice` function.
;;
;; #### Getting advice
;; For example, if the user evaluates the code `(+ 1 2)`, the relevant context is the form `(+ 1 2)` and the evaluation value `3`. A tool can ask for advice for this context:

(kindly/advice {:form '(+ 1 2)
                :value 3})

;; Since the advice did not assign any kind in this case, the tool will keep its usual behaviour (probably just displaying the text "`3`").
;;
;; #### Partial information
;; If any of the form or value parts is not availabile for some reason, the advice would rely on the partial information given. For some tools, which lack the form information, this can be useful and allow them to follow sensible advice in most cases.
;; #### Another example
;;
;; For another example, assume the user creates an image.
;;


(kindly/advice {:value (java.awt.image.BufferedImage.
                        32 32 BufferedImage/TYPE_INT_RGB)})

;; In this case, the default advice recognizes the `BufferedImage` object and proposes the `:kind/buffered-image` kind.
;;
;; #### Kindly as a dependency
;; Tools can include Kindly as a dependency, but should **avoid** including [kindly-default](github.com/scicloj/kindly-default). This way, notes written with old versions of kindly-default will keep working correctly with tools using new versions of Kindly.

;; ### Kindly for common users

;; Users will typically write their notes using Kindly's default advice, which is defined in the [kindly-default](github.com/scicloj/kindly-default) library.

;; #### Setup
;; To set it up, one only needs to call `(kindly-default/setup!)` as we did above. Typically, this can be done once in a project, e.g., in a `user.clj` file.

;; Let us see how the default behaviour of Kindly infers kinds.

;; #### The default advice

;; Most of the time, users will not need to care about Kindly's presence, as kindly-default simply tries to act sensibly. Anyway, here are the main details of its behaviour and options to affet it.

;; ##### Values with no special kind information

;; For many values, no kind is inferred.

(-> {:value {:x 9}}
    kindly/advice)

;; Thus, any tool would display such values the usual way it does.

{:x 9}

;; ##### Values with default kind

;; Kindly's default advice does attach some sensible default kind to certain types of values.

;; For example, `BufferedImage` object are assigned `:kind/buffered-image`, and thus can be displayes appropriately.

(BufferedImage.
 32 32 BufferedImage/TYPE_INT_RGB)

;; ##### Specifying kinds explicitly

;; Values can be assigned a kind in a few ways. Let us see, for example, how to assign the `:kind/hiccup` kind to some hiccup form.

(def big-big-orange-three
  [:p {:style {:color "orange"}}
   [:big [:big 3]]])

;; Without kind information, some tools will not interpret this value as hiccup, and just treat it as a plain Clojure data structure.

big-big-orange-three

;; ##### Assigning metadata

;; The kind can be specified by varying the value's metadata:

(-> big-big-orange-three
    (vary-meta assoc :kindly/kind :kind/hiccup))

;; ##### Using `kindly/consider`

;; There is a `kindly/consider` convenience function for varying the metadata as above.

(-> big-big-orange-three
    (kindly/consider :kind/hiccup))

;; ##### Using a kind function


;; For kinds which have been added to the system using the `kindly/add-kind!` function, there is a dedicated convenience function at the `kind` namespace to specify them as metadata.
;;
;; For example, since `(kindly/add-kind! :kind/hiccup)` has been called in the kindly-default library, we can do the following:

(-> big-big-orange-three
    kind/hiccup)

;; ##### Using code metadata

;; It is also possible to attach metadata to the form to be evaluated (rather than the resulting value):

^:kind/hiccup
big-big-orange-three

^{:kind/hiccup true}
big-big-orange-three


;; ### Kindly for sophisticated users

;; Users looking for different kind inference can define it in various ways.

;; #### Advisors from scratch

;; It is possible to set the list of advisors used for advice using `kindly/set-advisors!`
;;
;; Let us look into a few basic examples defining advisors. For the convenience of this tutorial, we will use these advisors through the purely functional version of `kindly/advice`, rather than changing Kindly's global state.
;;
;; The following advisor assigns `:kind/hiccup` to values of the `[:div ...]` format.

(defn div-value-is-hiccup-advisor
  [{:as context
    :keys [value]}]
  (if (and (vector? value)
           (-> value first (= :div)))
    (assoc context :kind :kind/hiccup)
    context))

;; The following advisor assigns `:kind/hiccup` to all forms of the `[:span ...]` format.

(defn span-form-is-hiccup-advisor
  [{:as context
    :keys [form]}]
  (if (and (vector? form)
           (-> form first (= :span)))
    (assoc context :kind :kind/hiccup)
    context))

;; Let us use these two advices in some contexts.
;;
;; The value is relevant:

(kindly/advice {:value [:div "hello"]}
               [div-value-is-hiccup-advisor
                span-form-is-hiccup-advisor])

;; The form is relevant:

(kindly/advice {:form [:span "hello"]
                :value [:span "hello"]}
               [div-value-is-hiccup-advisor
                span-form-is-hiccup-advisor])

;; Neither the form nor the value is relevant:

(kindly/advice {:form '(into [:span] ["hello"])
                :value [:span "hello"]}
               [div-value-is-hiccup-advisor
                span-form-is-hiccup-advisor])

;; #### Extending the default advice

;; Here are a few ways for users to extend the behaviour of Kindly's default advice.

;; ##### Extending with types

;; The default advice looks into the `Kindness` protocol as a source of kind information. Extending that protocol would extend the advice.

(deftype MyType1 [])

(extend-protocol kindness/Kindness
  MyType1
  (kind [this]
    :kind/abcd))

(kindly/advice {:value (MyType1.)})

;; ##### Adding an advisor to a list including the default advisor

;; The default advice is defined by an advisor generated by `(kindly-default/create-advisor)`. We can use that default advisor together with others. For example:

(kindly/advice
 {:value [:div "hello"]}
 [div-value-is-hiccup-advisor
  (kindly-default/create-advisor)])

;; ##### Generating a variation of the default advisor

;; When creating the default advisor through `kindly-default/create-advisor`, we can pass an additional argument: a vector of predicate-kind pairs. The advisor then applies the predicates to the values and assigns the corresponding kinds according to their response.

(def a-variation-of-the-default-advisor
  (kindly-default/create-advisor
   {:predicate-kinds [[(fn [v]
                         (and (vector? v)
                              (-> v first (= :div))))
                       :kind/hiccup]]}))

(kindly/advice
 {:value [:div "hello"]}
 [a-variation-of-the-default-advisor])
