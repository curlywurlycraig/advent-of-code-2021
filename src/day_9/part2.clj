(ns day-9.part2
  (:require [clojure.string :refer [split-lines split]]))

(def heightmap (->> (slurp "res/day9/input.txt")
                    split-lines
                    (map (fn [line]
                           (map #(Integer/parseInt %) (split line #""))))))

(defn get-in-hm
  [hm x y]
  {:x x
   :y y
   :v (nth (nth hm y nil) x nil)})

(defn all-adj
  [hm x y]
  (remove #(nil? (:v %))
          (list
           (get-in-hm hm x y)
           (get-in-hm hm x (dec y))
           (get-in-hm hm x (inc y))
           (get-in-hm hm (dec x) y)
           (get-in-hm hm (inc x) y))))

(defn basin-from
  [hm x y]
  (let [min-neighbor (apply min-key :v (all-adj hm x y))
        nx (:x min-neighbor)
        ny (:y min-neighbor)]
    (if (and (= x nx) (= y ny))
      min-neighbor
      (basin-from hm nx ny))))

(defn find-basins
  [hm]
  (let [all-points (for [y (range (count hm))
                         x (range (count (nth hm 0)))] [x y])]
    (reduce (fn [basins [x y]]
              (if (= 9 (:v (get-in-hm hm x y)))
                basins
                (update basins
                        (basin-from hm x y)
                        (fn [v] (if (nil? v) 1 (inc v))))))
            {}
            all-points)))

(defn score-basins
  [basins]
  (apply * (->> basins
               vals
               sort
               reverse
               (take 3))))

(print (score-basins (find-basins heightmap)))
