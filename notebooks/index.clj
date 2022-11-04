;; # Kindly

;; Kindly is a proposed common ground for Clojure literate programming.

;; It is a small library for specifying in what kind of way things should be displayed.

;; It can offer its advice to various tools for data visualization and literate programming, with sensible defaults which are user customizable.

;; It grew out of the [#visual-tools group](https://scicloj.github.io/docs/community/groups/visual-tools/) and has been inspired by converstions with Carsten Behring, Lukas Domalga, Kira McLean, Christopher Small, Martin Kavalar, Tomasz Sulej, and many other friends.

;; ## Status

;; (v3, 2022-11-04)
;;
;; - still alpha
;; - will soon be used  in community projects such as [ds4clj](https://scicloj.github.io/docs/community/groups/ds4clj/)
;; - currently supported by the [Clay](https://scicloj.github.io/viz.clj/) tool
;; - will hopefully have adapters for Oz, Calva Notebooks, Portal, Clerk, etc.
;; - will hopefully be composable with [Casegamas](https://github.com/behrica/casegamas)
;;
;; ## The problem

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
;; - To use tutorials, the code needs adaptation to the tool of choice.
;;
;; ## Goal
;; - Allow writing docs & tutorials which are copy-paste-friendly (a term phrased by Carsten Behring).
;; - A tutorial written today should just work with the tools of the future.
;;

;; ## Conceptual solution

;; - For a given context (code, form, value), a *kind* can be inferred (e.g., `:kind/hiccup`). The kind says how to display the value.
;; - Kindly provides such kinds as advice for tools. Each tool may look into Kindly's advice and apply it when displaying values.
;; - Thus, we decouple kind inference from kind application.
;; - Kind inference has sensible defaults, which are user-customizable.

;; ## Setup for this document

;; First, let us run the relevant initializations to render this document.
;; Typically, this part can be done in a `user.clj` file, once for a Clojure project.
;; Here, we do it explicitly at the beginning of the namespace.

(ns index
  (:require [scicloj.kindly.v3.api :as kindly]
            [scicloj.kindly.v3.kind :as kind]
            [scicloj.kindly-default.v1.api :as kindly-default]
            [scicloj.clay.v2.api :as clay]))

;; We declare this page to be displayed by the Kindly default advice.
(kindly-default/setup!)

;; We initialize the [Clay](https://scicloj.github.io/clay/) tool for rendering this page.
(clay/start!)

;; ## What Kindly does

;; Kindly's main entry point is the `kindly/advice` function. That is what tools use to ask for advice.
;; The input to that function is the context of encountering a given value to be evaluated.
;;
;; For example, if the user writes the code `(+ 1 2)` in their namespace, the relevant context is the form `(+ 1 2)` and the evaluation value `3`.
;;
;; In the current case, since this namespace uses the default kind inference, there is no special kind inferred.

(kindly/advice {:form '(+ 1 2)
                :value 3})

;; If only the value is passed, and not the form, Kindly's inference will be based on this partial information. As we will see below, both can be valuable for kind inference.
;;
;; Behind the scenes, Kindly's advice is based on a global state holding a sequence of functions. In the current case, it holds the default function, which was set up by the call to `(kindly-default/setup!)` above.

scicloj.kindly.v3.api/*advices

;; Kindly simply runs those functions on the given context, one after the other.

;; To explore Kindly's behaviour, it is also possible to use it in a purely functional way, with an explicitly chosen sequence of advice functions. For example, let us use a simple advice function that assigns the kind `:kind/abcd` to all contexts:

(defn abcd-advice [context]
  (assoc context
         :kind :kind/abcd))

(kindly/advice {:form '(+ 1 2)
                :value 3}
               [abcd-advice])

;; While this purely functional way is useful for debugging and testing, the recommended way to use Kindly is through the global `*advices` atom. The main idea of is that the user can adjust the global `*advices` atom to fit their desired kind inference (typically some version of the default), and the various tools would not change that, but rather respect the advice derinved from the user's definitions.

;; ## Using Kindly

;; We will describe Kindly's usage in three cases:
;; - Creating a tool (or a tool adapter) which supports Kindly's advice.
;; - Using Kindly for writing simple notes (tutorial/documentation/blog-post/etc.).
;; - Using Kindly in a custom way (with user-defined kind inference).

;; ## Kindly for tool makers
;; Various tools for data visualization and literate programming (Clay, Clava Notebooks, Oz,Portal, Clerk, Portal, etc.) can potentially support Kindly, and thus display values by its advice.
;;
;; The single entry point for doing that is the `kindly/advice` function.
;;
;; For example, if the user writes the code `(+ 1 2)` in their namespace, the relevant context is the form `(+ 1 2)` and the evaluation value `3`, as explained above:

(kindly/advice {:form '(+ 1 2)
                :value 3})

;; Tools can include Kindly as a dependency, but should **avoid** including [kindly-default](github.com/scicloj/kindly-default). This way, notes written with old versions of kindly-default will keep working correctly with new versions of Kindly.

;; ### Kindly for common users

;; Users will typically write their notes using the default kind inference, which is defined in the [kindly-default](github.com/scicloj/kindly-default) library.

;; To set it up, they only need to call `(kindly-default/setup!)` as we did above. Typically, this can be done once in a project, e.g., in a `user.clj` ile.

;; Let us see how the default behaviour of kindly infers kinds.

;; #### Values with no special kind information

;; Many values would not get any inferred kind.

(-> {:value {:x 9}}
    kindly/advice)

;; Thus, any tool would display such values the usual way it does.

{:x 9}

;; #### Specifying kinds explicitly

;; Values can be assigned a kind in a few ways. Let us see, for example, how to assign the `:kind/hiccup` kind to some hiccup form.

(def big-big-orange-three
  [:p {:style {:color "orange"}}
   [:big [:big 3]]])

;; ##### Assigning metadata

(-> {:value (-> big-big-orange-three
                (vary-meta assoc :kindly/kind :kind/hiccup))}
    kindly/advice)

(-> big-big-orange-three
    (vary-meta assoc :kindly/kind :kind/hiccup))

;; ##### Using `kindly/consider`

;; This is just a convenience function to perform the above.

(-> big-big-orange-three
    (kindly/consider :kind/hiccup)
    meta)

(-> {:value (-> big-big-orange-three
                (kindly/consider :kind/hiccup))}
    kindly/advice)

(-> big-big-orange-three
    (kindly/consider :kind/hiccup))

;; ##### Using a kind function

;; For kinds which have been added to the system using the `kindly/add-kind!` function, there is a dedicated convenience function at the `kind` namespace to apply them as metadata. For example, since `(kindly/add-kind! :kind/hiccup)` has been called in the kindly-default library, we can do the following:

(-> {:value (-> big-big-orange-three
                kind/hiccup)}
    kindly/advice)

(-> big-big-orange-three
    kind/hiccup)

;; ##### Using code metadata

;; It is also possible to attach metadata to the evaluated form (rather than the value):

(-> {:form (read-string
            "^:kind/hiccup big-big-orange-three")
     :value big-big-orange-three}
    kindly/advice)

^:kind/hiccup big-big-orange-three
big-big-orange-three

;; ## Some usage examples

big-big-orange-three

(-> big-big-orange-three
    (kindly/consider :kind/hiccup))

(-> big-big-orange-three
    kind/hiccup)

^:kind/hiccup
big-big-orange-three

;; ## Inferring some default kinds

(kindly/advice {:value #'clojure.core/reduce})

(import java.awt.image.BufferedImage)

(kindly/advice {:value (BufferedImage. 32 32 BufferedImage/TYPE_INT_RGB)})


;; ### Kindly for sophisticated users



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
