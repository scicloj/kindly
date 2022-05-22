(ns scicloj.kindly.v2.generate
  (:require
   [scicloj.kindly.v2.kind] ; needs to be here for intern to work
   ))

(defn generate-kind! [kind definition]
  (intern 'scicloj.kindly.v2.kind
          (symbol (name kind))
          definition))
