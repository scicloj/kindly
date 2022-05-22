(ns scicloj.kindly.v2.kindness)

(defprotocol Kindness
  (kind [this]))

(extend-protocol Kindness
  nil
  (kind [this]
    nil)
  Object
  (kind [this]
    nil))
