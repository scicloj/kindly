(ns scicloj.kindly.v3.defaults-test
  (:require [scicloj.kindly.v3.api :as kindly]
            [scicloj.kindly.v3.kindness :as kindness]
            [scicloj.kindly.v3.defaults :as defaults]
            [scicloj.kindly.v3.kind :as kind]
            [clojure.test :refer [deftest is]]))

(def default-advice
  (defaults/create-advice))

(deftest default-test
  (is (-> {:value {:x 9}}
          (kindly/advice [default-advice])
          :kind
          nil?)))

(deftype MyType1 [])
(extend-protocol kindness/Kindness
  MyType1
  (kind [this]
    :kind/mytestkind1))

(deftest type-with-user-defined-kindness-test

  (is (-> {:value (MyType1.)}
          (kindly/advice [default-advice])
          :kind
          (= :kind/mytestkind1))))

(deftype MyType2 [])

(deftest type-without-user-defined-kindness-test
  (is (-> {:value (MyType2.)}
          (kindly/advice [default-advice])
          :kind
          nil?)))

(deftest nil-test
  (is (-> {:value nil}
          (kindly/advice [default-advice])
          :kind
          nil?)))

(deftest value-with-kind-metadata-test
  (is (-> {:value (-> {:some :data}
                      (with-meta {:kindly/kind :kind/mytestkind2}))}
          (kindly/advice [default-advice])
          :kind
          (= :kind/mytestkind2))))

(deftest consider-test
  (is (-> {:some :data}
          (kindly/consider :kind/mytestkind3)
          meta
          :kindly/kind
          (= :kind/mytestkind3)))
  (is (-> {:value (-> {:some :data}
                      (kindly/consider :kind/mytestkind4))}
          (kindly/advice [default-advice])
          :kind
          (= :kind/mytestkind4)
          is)))

(kindly/add-kind! :kind/mytestkind5)

(deftest add-kind-test
  (is (-> {:value (-> {:some :data}
                      kind/mytestkind5)}
          (kindly/advice [default-advice])
          :kind
          (= :kind/mytestkind5)))
  (is (-> {:value (-> {:some :data}
                      (kindly/consider kind/mytestkind5))}
          (kindly/advice [default-advice])
          :kind
          (= :kind/mytestkind5))))

(deftest form-metadata-test
  (is (-> {:form (read-string "^:kind/mytestkind6 (+ 1 2)")
           :value 3}
          (kindly/advice [default-advice])
          :kind
          (= :kind/mytestkind6)))
  (is (-> {:form (read-string "^{:kind/mytestkind7 true} (+ 1 2)")
           :value 3}
          (kindly/advice [default-advice])
          :kind
          (= :kind/mytestkind7))))

(deftest default-test
  (is (-> {:value {:x 9}}
          (kindly/advice [default-advice])
          :kind
          nil?)))

;; (deftest hiccup-test
;;   (is (-> {:value [:h4 "hi"]}
;;           (kindly/advice [default-advice])
;;           :kind
;;           (= :kind/hiccup))))

(deftest var-test
  (is (-> {:value #'clojure.core/reduce}
          (kindly/advice [default-advice])
          :kind
          (= :kind/var))))

(import java.awt.image.BufferedImage)

(deftest image-test
  (is (-> {:value (BufferedImage. 4 4 BufferedImage/TYPE_INT_RGB)}
          (kindly/advice [default-advice])
          :kind
          (= :kind/buffered-image))))

(deftest predicate-test
  (let [advice (defaults/create-advice
                {:predicate-kinds [[(fn [v] (= v 3))
                                    :kind/three]
                                   [(fn [v] (-> v type pr-str (= "java.lang.String")))
                                    :kind/string]]})]
    (is (-> {:value 3}
            (kindly/advice [advice])
            :kind
            (= :kind/three)))
    (is (-> {:value "abcd"}
            (kindly/advice [advice])
            :kind
            (= :kind/string)))))
