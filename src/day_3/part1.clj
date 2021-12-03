(ns day-3.part1
  (:require [clojure.string :refer [split-lines split]]))

;; Find gamma rate and epsilon rate, then multiply them to get the power consumption

(defn bit-pos-flip
  "Flip all binary digits to obtain another positive number."
  [n]
  (bit-xor 2r111111111111 n))

(defn str-to-digit-list
  [s]
  (->> s
       seq
       (map #(Character/digit % 10))))

(defn count-reducer
  [acc curr]
  (map (partial apply +)
       (map vector acc curr)))

(defn main
  []
  (let [nums (->> (slurp "res/day3/input.txt")
                  split-lines
                  (map str-to-digit-list))
        c (count nums)
        counts (reduce count-reducer
                       (take 12 (repeat 0))
                       nums)
        counted (map (comp 
                      {true 1
                       false 0}
                      #(>= % (/ c 2)))
                     counts)
        counted-int (Integer/parseInt (apply str counted) 2)]
    (* counted-int (bit-pos-flip counted-int))))
                  
