(ns scicloj.kindly.v3.kindness)

(defprotocol Kindness
  (kind [this]))

(extend-protocol Kindness
  nil
  (kind [this]
    nil
    #_:kind/pprint)
  Object
  (kind [this]
    nil
    #_:kind/pprint))
