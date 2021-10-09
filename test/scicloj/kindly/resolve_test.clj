(ns scicloj.kindly.resolve-test
  (:require [clojure.test :refer [deftest is]]
            [scicloj.kindly.kind :as kind]
            [scicloj.kindly.kindness :as kindness]
            [scicloj.kindly.resolve]))

(def div-with-meta-tag-1
  (read-string
   "^scicloj.kindly.kind/hiccup [:div]"))

(def div-with-meta-tag-2
  ^:scicloj.kindly.kind/hiccup [:div])

(deftest metadata->kind-test
  (-> div-with-meta-tag-1
      meta
      scicloj.kindly.resolve/metadata->kind
      (= kind/hiccup)
      is)
  (-> div-with-meta-tag-2
      meta
      scicloj.kindly.resolve/metadata->kind
      (= kind/hiccup)
      is))

(deftest value->kind-test
  (-> [:div]
      (with-meta
        {:kindly/kind :scicloj.kindly.kind/hiccup})
      scicloj.kindly.resolve/value->kind
      (= :scicloj.kindly.kind/hiccup)
      is)
  (-> [:div]
      scicloj.kindly.resolve/value->kind
      (= :scicloj.kindly.kind/naive)
      is))

(def dummy-behaviour
  {:render-src?   true
   :value->hiccup identity})

(deftype DummyType []
  kindness/Kindness
  (->behaviour [this]
    dummy-behaviour))

(defmethod kind/kind->behaviour ::dummy
  [_]
  dummy-behaviour)

(kind/intern-kinds!)

(deftest value->behaviour-test
  (-> (DummyType.)
      scicloj.kindly.resolve/value->behaviour
      (= dummy-behaviour)
      is)
  (-> [3]
      (with-meta {:kindly/kind ::dummy})
      scicloj.kindly.resolve/value->behaviour
      (= dummy-behaviour)
      is)
  (-> [3]
      scicloj.kindly.resolve/value->behaviour
      (= (kind/kind->behaviour :scicloj.kindly.kind/naive))
      is))



