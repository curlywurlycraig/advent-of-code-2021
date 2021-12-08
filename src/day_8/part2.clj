(ns day-8.part2
  (:require [clojure.pprint :refer [pprint]]
            [clojure.set :refer [subset? difference map-invert]]
            [clojure.string :refer [split split-lines trim]]))

(def all-puzzles (->> (slurp "res/day8/input.txt")
                      split-lines
                      (map (fn [l]
                             (let [[digit-str display-str] (split l #" \| ")]
                               {:digits (map set (split digit-str #" "))
                                :display (split display-str #" ")})))))

(defn count-segments-on
  [n]
  (fn [[dig puz]]
    (= n (count dig))))

(defn digit-inverse
  [n p]
  (let [eight (get (:result p) 8)]
    (difference eight n)))

(defn update-puzzle
  [puzzle dig pred]
  (let [{remaining :remaining result :result} puzzle
        new-res (first (filter #(pred [% puzzle]) remaining))]
    {:remaining (remove #(= new-res %) remaining)
     :result (assoc result dig new-res)}))

(defn build-display-map
  [digits]
  (map-invert
   (:result
    (-> {:remaining digits
         :result {}}
        (update-puzzle 1 (count-segments-on 2))
        (update-puzzle 4 (count-segments-on 4))
        (update-puzzle 7 (count-segments-on 3))
        (update-puzzle 8 (count-segments-on 7))
        (update-puzzle 6 (every-pred
                          (count-segments-on 6)
                          (fn [[d p]] (subset? (digit-inverse d p) (get (:result p) 1)))))
        (update-puzzle 5 (every-pred
                          (count-segments-on 5)
                          (fn [[d p]] (subset? d (get (:result p) 6)))))
        (update-puzzle 9 (every-pred
                          (count-segments-on 6)
                          (fn [[d p]] (subset? (get (:result p) 4) d))))
        (update-puzzle 0 (count-segments-on 6))
        (update-puzzle 3 (fn [[d p]] (subset? (get (:result p) 7) d)))
        (update-puzzle 2 (constantly true))))))

(defn determine-value
  [{digits :digits
    display :display}]
  (let [display-map (build-display-map digits)]
    (map #(get display-map (set %)) display)))

(print (apply + (map #(Integer/parseInt (apply str (determine-value %))) all-puzzles)))
