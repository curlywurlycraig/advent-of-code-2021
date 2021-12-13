(ns day-13.part2
  (:require [clojure.pprint :refer [pprint]]
            [clojure.string :refer [split-lines split join]]))

(def input-parts
  (-> (slurp "res/day13/input.txt")
      (split #"\n\n")))

(def points (->> (first input-parts)
                 split-lines
                 (map #(split % #","))
                 (map (fn [r] (map #(Integer/parseInt %) r)))
                 (map vec)))

(def instructions (->> (second input-parts)
                       split-lines
                       (map #(second (split % #"fold along ")))
                       (map #(split % #"="))
                       (map (fn [[a v]] [a (Integer/parseInt v)]))))

(defn point-from-fold
  [p inst]
  (let [fold-axis (first inst)
        fold-val (second inst)
        axis-idx ({"x" 0 "y" 1} fold-axis)
        relevant-val (if (= fold-axis "x")
                       (first p)
                       (second p))]
    (if (< relevant-val fold-val)
      p
      (assoc p
             axis-idx
             (- fold-val (- relevant-val fold-val))))))
  
(def after-first-fold
  (map #(point-from-fold % (first instructions)) points))

(def after-all-folds
  (reduce (fn [result inst]
            (map #(point-from-fold % inst) result))
          points
          instructions))

(def max-x (apply max (map first after-all-folds)))
(def max-y (apply max (map second after-all-folds)))

(def printable-result
  (vec (repeat (inc max-y) (vec (repeat (inc max-x) ".")))))

(def updated-result
  (reduce (fn [result [x y]]
            (assoc-in result [y x] "#"))
          printable-result
          after-all-folds))
         
(def string-solution (join "\n" (map (partial join "") updated-result)))

(println string-solution)
