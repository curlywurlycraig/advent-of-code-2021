(ns day-6.part2
  (:require [clojure.string :refer [split trim]]))

(def fish-days (as-> (slurp "res/day6/input.txt") inp
                 (trim inp)
                 (split inp #",")
                 (mapv #(. Integer parseInt %) inp)))

;; fish-days-counts is a map from days until reproduction
;; to counts (e.g. { 5 10 } would represent 10 fish with 5 days left until
;; reproduction)
(def fish-days-counts
  {0 0
   1 (count (filter (partial = 1) fish-days))
   2 (count (filter (partial = 2) fish-days))
   3 (count (filter (partial = 3) fish-days))
   4 (count (filter (partial = 4) fish-days))
   5 (count (filter (partial = 5) fish-days))
   6 0
   7 0
   8 0})

(defn update-fish-days-counts
  [fdc]
  {0 (get fdc 1)
   1 (get fdc 2)
   2 (get fdc 3)
   3 (get fdc 4)
   4 (get fdc 5)
   5 (get fdc 6)
   6 (+ (get fdc 7) (get fdc 0))
   7 (get fdc 8)
   8 (get fdc 0)})

(print (apply + (vals (nth (iterate update-fish-days-counts fish-days-counts) 256))))
