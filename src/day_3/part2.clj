(ns day-3.part2
  (:require [clojure.string :refer [split-lines split]]))

;; Find gamma rate and epsilon rate, then multiply them to get the power consumption
(def bit-count 12)

(def bit-invert {0 1 1 0})

(defn bit-test-big-endian
  [n idx]
  (bit-test n (- bit-count idx 1)))

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

(defn oxygen-criteria
  [ns idx]
  (or (most-common ns idx) 1))

(defn scrubber-criteria
  [ns idx]
  (or (bit-invert (most-common ns idx)) 0))

(defn only-with-bit
  "Given some numbers, keep only the ones that have the bit set at that index."
  [ns idx v]
  (filter #(= ({false 0 true 1} (bit-test-big-endian % idx))
              v)
          ns))

(defn reduce-bit-find
  "Given a list of numbers, and a criteria, retain only those numbers that continually pass the criteria."
  [ns crit]
  (reduce (fn [acc curr]
            (if (= 1 (count acc))
              acc
              (only-with-bit acc
                             curr
                             (crit acc curr))))
          ns
          (range bit-count)))

(defn main
  []
  (let [nums (->> (slurp "res/day3/input.txt")
                  split-lines
                  (map #(Integer/parseInt % 2)))
        oxy (first (reduce-bit-find nums oxygen-criteria))
        scrubber (first (reduce-bit-find nums scrubber-criteria))]
    (* oxy scrubber)))
                  
