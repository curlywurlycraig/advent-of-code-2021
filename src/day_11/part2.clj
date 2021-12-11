(ns day-11.part2
  (:require [clojure.string :refer [split split-lines]]))

(def dumbos (->> (slurp "res/day11/input.txt")
                 split-lines
                 (map (fn [l]
                        (map #(Integer/parseInt %)
                             (split l #""))))))

(defn map-grid-indexed
  "Run a function on all elements in a grid, passing the element,
  and its x and y pos."
  [f g]
  (map-indexed
   (fn [y r]
     (map-indexed #(f %2 %1 y) r))
   g))

(defn xth-yth
  "Get the value at x, y in a grid."
  [x y g]
  (-> g
      (nth y nil)
      (nth x nil)))

(defn all-neighbors
  "Get all the neighbors from an x and y, on a grid."
  [x y g]
  (remove
   nil?
   (list
    (xth-yth (dec x) (dec y) g)
    (xth-yth x (dec y) g)
    (xth-yth (inc x) (dec y) g)
    (xth-yth (dec x) y g)
    (xth-yth (inc x) y g)
    (xth-yth (dec x) (inc y) g)
    (xth-yth x (inc y) g)
    (xth-yth (inc x) (inc y) g))))

(defn update-from-flashes
  [g v x y]
  (cond
    (> v 9) 0 
    (zero? v) 0
    :else (->> (all-neighbors x y g)
               (filter #(> % 9))
               count
               (+ v))))

(defn update-until-settled
  [g]
  (let [flash-step (doall (map-grid-indexed (partial update-from-flashes g) g))]
    (if (first (filter #(> % 9) (flatten flash-step)))
      (update-until-settled flash-step)
      flash-step)))

(defn step
  [g]
  (->> g
       (map-grid-indexed (fn [v _ _] (inc v)))
       update-until-settled))

(println (first (first (filter (fn [[_ v]] (apply = (flatten v)))
               (map-indexed (fn [step v] [step v]) (iterate step dumbos))))))
