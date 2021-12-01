(ns main
  (:require [clojure.core :refer [partition]]
            [clojure.string :refer [split-lines]]))

(defn parse-int
  "Given a string, returns an integer."
  [s]
  (. Integer parseInt s))
  

(defn -main
  []
  (print (let [vals (->> (slurp "./input.txt")
                         (split-lines)
                         (map parse-int))]
           (count
            (filter (partial apply <)
                    (partition 2 1 vals))))))

(-main)
