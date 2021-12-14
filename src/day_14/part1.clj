(ns day-14.part1
  (:require [clojure.string :refer [split split-lines join]]
            [clojure.set :refer [map-invert]]))

(def whole-input
  (split (slurp "res/day14/input.txt") #"\n\n"))

(def polymer (first whole-input))

(def insertion-rules
  (->> (second whole-input)
       split-lines
       (map #(split % #" -> "))
       (#(apply hash-map (flatten %)))))

(defn step
  [ir p]
  (apply
   str
   (concat [(first p)]
           (map (fn [pair]
                  (let [insert (ir (join pair))]
                    (if insert
                      (str insert
                           (second pair))
                      pair)))
                (partition 2 1 p)))))

(def last-result
  (->> polymer
       (iterate (partial step insertion-rules))
       (take 11)
       last))

(def result
  (let [freq (frequencies last-result)
        max-freq (apply max (vals freq))
        min-freq (apply min (vals freq))]
    (- max-freq min-freq)))
  
(println result)

