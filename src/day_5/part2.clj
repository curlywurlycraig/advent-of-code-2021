(ns day-5.part2
  (:require [clojure.string :refer [split split-lines]]))

;; "lines" is a pair of pairs (points). E.g. ((1 2) (1 5))
(def lines (as-> (slurp "res/day5/input.txt") sss
             (split sss #"( -> )|(,)|(\n+)")
             (map #(. Integer parseInt %) sss)
             (partition 4 sss)
             (map (partial partition 2) sss)))

(defn points-from-ends
  "Given two endpoints, return a list of all points between them."
  [[[x1 y1] [x2 y2]]]
  (map list
       (if (= x1 x2)
         (repeat x1)
         (if (pos? (- x2 x1))
           (range x1 (inc x2))
           (range x1 (dec x2) -1)))
       (if (= y1 y2)
         (repeat y1)
         (if (pos? (- y2 y1))
           (range y1 (inc y2))
           (range y1 (dec y2) -1)))))

(def all-points
  (apply concat (map points-from-ends lines)))

(def counts (reduce
             (fn [counter p] (update counter p #(inc (or % 0))))
             {}
             all-points))

(print (count (filter (partial < 1) (vals counts))))

