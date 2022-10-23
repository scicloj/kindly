(ns custom-default-advice
  (:require [scicloj.kindly.v3.api :as kindly]
            [scicloj.kindly.v3.kind :as kind]
            [scicloj.kindly.v3.kindness :as kindness]
            [scicloj.kindly.v3.defaults :as defaults]))

;; ## Extending the default advice with predicates

(kindly/set-only-advice!
 (defaults/create-advice
  {:predicate-kinds [[(fn [v] (= v 3))
                      :kind/three]]}))

(kindly/advice {:value 3})


;; ## Extending the default advice with types

(deftype MyType1 [])

(extend-protocol kindness/Kindness
  MyType1
  (kind [this]
    :kind/abcd))


(kindly/advice {:value (MyType1.)})
