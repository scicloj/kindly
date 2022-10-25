;; # Value advice

(ns value-advice
  (:require [scicloj.kindly.v3.api :as kindly]
            [scicloj.kindly.v3.kind :as kind]
            [scicloj.kindly.v3.defaults :as defaults]))

(kindly/set-only-advice!
 (fn [{:as context
       :keys [value]}]
   (if (= value 3)
     (assoc context :kind :kind/abcd)
     context)))

(kindly/advice {:value 3})

(kindly/advice {:value 2})
