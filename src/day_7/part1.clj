(ns day-7.part1
  (:require [clojure.string :refer [split trim]]))

(defn mean
  [& xs]
  (/ (apply + xs) (count xs)))

(defn median
  [xs]
  (let [sorted (sort xs)
        mid (quot (count sorted) 2)]
    (if (odd? (count sorted))
      (nth sorted mid)
      (mean (nth sorted (dec mid)) (nth sorted mid)))))

(defn abs
  [n]
  (if (neg? n)
    (- n)
    n))

(def in-nums (->> (slurp "res/day7/input.txt")
                  trim
                  (#(split % #","))
                  (map #(Integer/parseInt %))))

(def ideal-x (median in-nums))

(def total-cost (apply + (map #(abs (- ideal-x %)) in-nums)))
