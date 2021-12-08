(ns day-8.part2
  (:require [clojure.string :refer [split split-lines trim]]))

;; Compare 1 and 7. The additional segment on 7 is segment "a".
;; The digit with 6 segments turned on, and one in the off position that is in digit 1, is 6.
;;    Also the segment that is off is "c".

(defn is-unique
  "Unique digits are 1, 4, 7, and 8. Because they are the only
  digits with 2, 4, 3, and 7 segments respectively."
  [d]
  (case (count d)
    2 true
    4 true
    3 true
    7 true
    false))

(def all-puzzles (->> (slurp "res/day8/input.txt")
                      split-lines
                      (map (fn [l]
                             (let [[digit-str display-str] (split l #" \| ")]
                               {:digits (split digit-str #" ")
                                :display (split display-str #" ")})))))

(print (count (filter is-unique (apply concat (map :display all-puzzles)))))
