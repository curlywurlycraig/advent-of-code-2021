(ns day-5.part1
  (:require [clojure.string :refer [split split-lines]]))

;; "lines" is a pair of pairs (points). E.g. ((1 2) (1 5))
(def lines (as-> (slurp "res/day5/input.txt") sss
                (split sss #"( -> )|(,)|(\n+)")
                (map #(. Integer parseInt %) sss)
                (partition 4 sss)
                (map (partial partition 2) sss)))

;; Only those lines where x1 = x2 or y1 = y2
(def only-h-v
  (filter
   (fn [[[x1 y1] [x2 y2]]]
     (or (= x1 x2) (= y1 y2)))
   lines))

(defn points-from-ends
  "Given two endpoints, return a list of all points between them."
  [[[x1 y1] [x2 y2]]]
  (if (= x1 x2)
    (map list (repeat x1) (range (min y1 y2) (inc (max y1 y2))))
    (map list (range (min x1 x2) (inc (max x1 x2))) (repeat y1))))

(def all-points
  (apply concat (map points-from-ends only-h-v)))

(def counts (reduce
             (fn [counter p] (update counter p #(inc (or % 0))))
             {}
             all-points))

(print (count (filter (partial < 1) (vals counts))))

