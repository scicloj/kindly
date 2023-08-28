(ns scicloj.kindly.v4.api
  (:require [scicloj.kindly.v4.impl :as impl]
            [scicloj.kindly.v4.advisors :as advisors]))

(def *advisors
  (atom []))

(defn advice
  ([context]
   (advice context @*advisors))
  ([{:as context}
    advisors]
   (-> (loop [results []
              remaining-advisors advisors]
         (if-let [advisor1 (first remaining-advisors)]
           (let [advisor-output (advisor1 context)]
             (recur (conj results advisor-output)
                    (rest remaining-advisors)))
           results))
       seq
       (or [context]))))

(defn consider [value kind]
  (cond (keyword? kind) (impl/attach-kind-to-value value kind)
        (fn? kind) (consider value (kind))))

(def *kinds-set (atom #{}))

(defn add-kind! [kind]
  (swap! *kinds-set conj kind)
  (intern 'scicloj.kindly.v3.kind
          (symbol (name kind))
          (impl/kind-as-a-fn kind))
  kind)

(defn known-kinds []
  (->> 'scicloj.kindly.v4.kind
       find-ns
       ns-publics
       vals
       (map #(%))))

(defn add-advisor! [advisor]
  (swap! *advisors conj advisor))

(defn set-advisors! [advisors]
  (reset! *advisors advisors))

(defn set-only-advisor! [advisor]
  (set-advisors! [advisor]))

(set-only-advisor! (advisors/default-advisor))
