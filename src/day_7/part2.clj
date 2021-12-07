(ns day-7.part2
  (:require [clojure.string :refer [split trim]]))

(def in-nums (->> (slurp "res/day7/input.txt")
                  trim
                  (#(split % #","))
                  (map #(Integer/parseInt %))))

(defn abs
  [n]
  (if (neg? n)
    (- n)
    n))

(defn tri-number
  [n]
  (apply + (range (inc n))))

(defn calc-cost
  "Calculate the cost to position n for all values nums."
  [n nums]
  (apply + (map
            #(tri-number (abs (- n %)))
            nums)))

(def result
  (apply min (map
              #(calc-cost % in-nums)
              (range (apply max in-nums)))))

(print result)
