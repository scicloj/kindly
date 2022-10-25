;; # Default advice

(ns default-advice
  (:require [scicloj.kindly.v3.api :as kindly]
            [scicloj.kindly.v3.kind :as kind]
            [scicloj.kindly.v3.defaults :as defaults]))

(defaults/setup!)

(-> {:value {:x 9}}
    kindly/advice)


;; ## Specifying kinds explicitly

(-> {:value (-> {:x 9}
                (kindly/consider :kind/abcd))}
    kindly/advice)

(kindly/add-kind! :kind/abcd)

(-> {:value (-> {:x 9}
                kind/abcd)}
    kindly/advice)

(-> {:form (read-string
            "^:kind/abcd (+ 1 2)")
     :value (+ 1 2)}
    kindly/advice)

;; ## Some usage examples

(def my-hiccup
  [:p {:style {:color "purple"}}
   "hello"])

my-hiccup

(-> my-hiccup
    (kindly/consider :kind/hiccup))

(-> my-hiccup
    kind/hiccup)

^:kind/hiccup
my-hiccup

;; ## Inferring some default kinds

(kindly/advice {:value #'clojure.core/reduce})

(import java.awt.image.BufferedImage)

(kindly/advice {:value (BufferedImage. 32 32 BufferedImage/TYPE_INT_RGB)})
