(ns day-3.part2
  (:require [clojure.string :refer [split-lines split]]))

;; Find gamma rate and epsilon rate, then multiply them to get the power consumption
(def bit-count 12)

(def bit-invert {0 1 1 0})

(defn bit-test-big-endian
  [n idx]
  (bit-test n (- bit-count idx 1)))

(defn pp-bin
  [n]
  (Integer/toString n 2))

(defn most-common
  [ns idx]
  (let [sum-col (->> ns
                     (map #(bit-test-big-endian % idx))
                     (map {false 0 true 1})
                     (apply +))
        half-count (/ (count ns) 2)]
    (cond
      (< sum-col half-count) 0
      (> sum-col half-count) 1
      :else nil)))

(defn only-with-bit
  "Given some numbers, keep only the ones that have the bit set at that index."
  [ns idx v]
  (filter #(= ({false 0 true 1} (bit-test-big-endian % idx))
              v)
          ns))

(defn follow-most-common
  "Given a list of numbers, find the number in the list that follows the most common."
  [ns default]
  (reduce (fn [acc curr]
            (if (= 1 (count acc))
              acc
              (only-with-bit acc
                             curr
                             (or (if (= 1 default)
                                   (most-common acc curr)
                                   (bit-invert (most-common acc curr)))
                                 default))))
          ns
          (range bit-count)))

(defn main
  []
  (let [nums (->> (slurp "res/day3/input.txt")
                  split-lines
                  (map #(Integer/parseInt % 2)))
        oxy (first (follow-most-common nums 1))
        scrubber (first (follow-most-common nums 0))]
    (* oxy scrubber)))
                  
