(ns scicloj.kindly.v2.value
  (:require [scicloj.kindly.v2.impl :as impl]
            [scicloj.kindly.v2.kindness :as kindness]
            [scicloj.kindly.v2.api :as kindly]))

(defn kinds-set []
  (set (keys @impl/!kind->behaviour)))

;; (defn code->kind [code]
;;   (when-let [m (some-> code
;;                        read-string
;;                        meta)]
;;     (or (some->> m
;;                  :tag
;;                  resolve
;;                  deref
;;                  ((kinds-set)))
;;         (some->> m
;;                  keys
;;                  (filter (kinds-set))
;;                  first))))

(defn kind [value]
  (or (kindly/value->kind value)
      (kindness/kind value)))

(defn wrap-value
  "Wrap is used by the visualization tool (portal/clerk/scittle/goldly).
  Expects value to have a kindly kind."
  [tool value]
  (when-let [{:keys [wrap]} (-> value
                                kind
                                impl/kind->behaviour
                                tool)]
    (wrap value)))
