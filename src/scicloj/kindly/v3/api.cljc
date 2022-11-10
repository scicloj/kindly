(ns scicloj.kindly.v3.api
  (:require [scicloj.kindly.v3.impl :as impl]))

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
             (recur
              (cond
                ;; the advisor returned a single context map
                (map? advisor-output)
                (conj results advisor-output)
                ;; the advisor returned multiple context maps
                (sequential? advisor-output)
                (concat results advisor-output))
              (rest remaining-advisors)))
           results))
       seq
       (or [context]))))

(defn consider [value kind]
  (cond (keyword? kind) (impl/attach-kind-to-value value kind)
        (fn? kind) (consider value (kind))))

(defn add-kind! [kind]
  (impl/define-kind! kind)
  kind)

(defn known-kinds []
  @impl/*kinds-set)

(defn add-advisor! [advisor]
  (swap! *advisors conj advisor))

(defn set-advisors! [advisors]
  (reset! *advisors advisors))

(defn set-only-advisor! [advisor]
  (set-advisors! [advisor]))
