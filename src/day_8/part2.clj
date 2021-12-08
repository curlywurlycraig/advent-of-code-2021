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

(defn existing-digit
  "The set of segments for a digit n that has already been found for the given puzzle p."
  [p n]
  (get-in p [:result n]))

(defn has-n-segments
  "Creates a predicate function that returns true when the given digit has n segments."
  [n]
  (fn [[dig puz]]
    (= n (count dig))))

(defn digit-inverse
  "Given a digit n and a puzzle p, returns the set of segments that are _not_ in n."
  [n p]
  (let [eight (existing-digit p 8)]
    (difference eight n)))

(defn update-puzzle
  "Given a puzzle, a digit, and a predicate, finds the remaining digit that satisfies the predicate
  and adds it to the set of results."
  [puzzle dig pred]
  (let [{remaining :remaining result :result} puzzle
        new-res (first (filter #(pred [% puzzle]) remaining))]
    {:remaining (remove #(= new-res %) remaining)
     :result (assoc result dig new-res)}))

(defn build-display-map
  "Given some list of digits, returns a a map from (set of segments) -> (digit)."
  [digits]
  (map-invert
   (:result
    (-> {:remaining digits
         :result {}}
        (update-puzzle 1 (has-n-segments 2))
        (update-puzzle 4 (has-n-segments 4))
        (update-puzzle 7 (has-n-segments 3))
        (update-puzzle 8 (has-n-segments 7))
        (update-puzzle 6 (every-pred
                          (has-n-segments 6)
                          (fn [[d p]] (subset? (digit-inverse d p) (existing-digit p 1)))))
        (update-puzzle 5 (every-pred
                          (has-n-segments 5)
                          (fn [[d p]] (subset? d (existing-digit p 6)))))
        (update-puzzle 9 (every-pred
                          (has-n-segments 6)
                          (fn [[d p]] (subset? (existing-digit p 4) d))))
        (update-puzzle 0 (has-n-segments 6))
        (update-puzzle 3 (fn [[d p]] (subset? (existing-digit p 7) d)))
        (update-puzzle 2 (constantly true))))))

(defn determine-value
  "Given some puzzle line, return the total value displayed."
  [{digits :digits
    display :display}]
  (let [display-map (build-display-map digits)]
    (map #(get display-map (set %)) display)))

(print (apply + (map #(Integer/parseInt (apply str (determine-value %))) all-puzzles)))
