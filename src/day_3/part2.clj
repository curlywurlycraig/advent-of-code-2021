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

(defn most-common-bit-func
  [ns idx]
  (or (most-common ns idx) 1))

(defn least-common-bit-func
  [ns idx]
  (or (bit-invert (most-common ns idx)) 0))

(defn only-with-bit
  "Given some numbers, keep only the ones that have the bit set at that index."
  [ns idx v]
  (filter #(= ({false 0 true 1} (bit-test-big-endian % idx))
              v)
          ns))

(defn reduce-bit-find
  "Given a list of numbers, and a bit finder, retain only those numbers that continually pass the bit function."
  [ns bit-func]
  (reduce (fn [acc curr]
            (if (= 1 (count acc))
              acc
              (only-with-bit acc
                             curr
                             (bit-func acc curr))))
          ns
          (range bit-count)))

(defn main
  []
  (let [nums (->> (slurp "res/day3/input.txt")
                  split-lines
                  (map #(Integer/parseInt % 2)))
        oxy (first (reduce-bit-find nums most-common-bit-func))
        scrubber (first (reduce-bit-find nums least-common-bit-func))]
    (* oxy scrubber)))
                  
