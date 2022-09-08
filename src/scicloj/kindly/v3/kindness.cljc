(ns scicloj.kindly.v3.kindness)

(defprotocol Kindness
  (kind [this]))

(extend-protocol Kindness
  nil
  (kind [this]
    :kind/pprint)
  Object
  (kind [this]
    :kind/pprint))
