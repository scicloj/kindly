(ns scicloj.kindly.kindness)

(defprotocol Kindness
  (->behaviour [this]))

(extend-protocol Kindness
  nil
  (->behaviour [this]
    nil)
  Object
  (->behaviour [this]
    nil))
