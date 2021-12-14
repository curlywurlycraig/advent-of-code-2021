(ns day-14.part2
  (:require [clojure.string :refer [split split-lines join]]
            [clojure.set :refer [map-invert]]))

(def whole-input
  (split (slurp "res/day14/input.txt") #"\n\n"))

(def polymer-string (first whole-input))

(defn inc-default
  [ps p]
  (update ps p #(if (nil? %) 1 (inc %))))

(def polymer (->> polymer-string
                  (partition 2 1)
                  (map (partial apply str))
                  (reduce (fn [counts pair]
                            (inc-default counts pair))
                          {})))

(def insertion-rules
  (->> (second whole-input)
       split-lines
       (map #(split % #" -> "))
       (#(apply hash-map (flatten %)))))

(defn updates-from-rule
  [p [k v]]
  (let [pair-count (get p k 0)
        first-key (str (first k) v)
        second-key (str v (second k))]
    (if (= 0 pair-count)
      {}
      (merge-with +
                  {k (- pair-count)}
                  {first-key pair-count
                   second-key pair-count}))))

(defn step
  "For each pair in the rules, increment the associated pair counts."
  [ir p]
  (reduce #(merge-with + %1 %2)
          p
          (map (partial updates-from-rule p) (vec ir))))

(def result-by-letter
  (let [pair-freq (->> polymer
                       (iterate (partial step insertion-rules))
                       (take 41)
                       last)]
    (reduce (fn [letter-counts [pair pair-count]]
              (merge-with +
                          letter-counts
                          {(first pair) pair-count}
                          {(second pair) pair-count}))
            {}
            (vec pair-freq))))

(def result
  (- (/ (apply max (vals result-by-letter)) 2)
     (/ (apply min (vals result-by-letter)) 2)))

(println (Math/round (double result)))

;; (def result
;;   (let [freq (frequencies last-result)
;;         max-freq (apply max (vals freq))
;;         min-freq (apply min (vals freq))]
;;     (- max-freq min-freq)))
  
;; (println result)

