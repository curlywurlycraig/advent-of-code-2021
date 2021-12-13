(ns day-13.part1
  (:require [clojure.string :refer [split-lines split]]))

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

(println (count (distinct after-first-fold)))
