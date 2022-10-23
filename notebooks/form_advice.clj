(ns form-advice
  (:require [scicloj.kindly.v3.api :as kindly]
            [scicloj.kindly.v3.kind :as kind]
            [scicloj.kindly.v3.defaults :as defaults]))

(kindly/set-only-advice!
 (fn [{:as context
       :keys [form]}]
   (if (= form '(+ 1 2))
     (assoc context :kind :kind/abcd)
     context)))

(kindly/advice {:form '(+ 1 2) :value 3})

(kindly/advice {:form '(* 1 3) :value 3})
