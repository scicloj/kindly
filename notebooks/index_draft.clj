(ns index-draft
  (:require [scicloj.kindly.v4.api :as kindly]
            [scicloj.kindly.v4.kind :as kind]
            [scicloj.kindly.v4.advisors :as advisors])
  (:import java.awt.image.BufferedImage))

(kindly/advise {:form '(+ 1 2)
                :value 3})

scicloj.kindly.v4.api/*advisors

(defn abcd-advisor [context]
  [[:kind/abcd {:reason :abcd}]])

(kindly/advise {:form '(+ 1 2)
                :value 3}
               [abcd-advisor])

(kindly/advise {:value (java.awt.image.BufferedImage.
                        32 32 BufferedImage/TYPE_INT_RGB)})

(kindly/advise
 {:value (-> ["a"]
             kind/md)})

;; just a helper fn for this notebook
(defn value-advise [v]
  (kindly/advise {:value v}))

(value-advise {:x 9})

(def big-big-orange-three
  [:p {:style {:color "orange"}}
   [:big [:big 3]]])

(-> big-big-orange-three
    (vary-meta assoc :kindly/kind :kind/hiccup)
    value-advise)

(-> big-big-orange-three
    (kindly/consider :kind/hiccup)
    value-advise)

(-> big-big-orange-three
    kind/hiccup
    value-advise)

(-> "hello"
    kind/md
    value-advise)

(kindly/advise
 {:form (read-string "^:kind/hiccup big-big-orange-three")})

(kindly/advise
 {:form (read-string "^{:kind/hiccup true} big-big-orange-three")})
