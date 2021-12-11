(ns day-11.drawutil
  (:require [clojure.java.io :refer [file]])
  (:import [java.awt.image BufferedImage]
           [java.awt Color]
           [javax.imageio ImageIO IIOImage]))

(defrecord ImageContainer [bi g])

(defn make-image
  [w h]
  (let [bi (BufferedImage. w h BufferedImage/TYPE_INT_RGB)
        g (.createGraphics bi)]
    (ImageContainer. bi g)))

(defn save-image
  [ic filename]
  (let [out (file filename)]
    (ImageIO/write (:bi ic) "gif" out)))

(defn- jelly-color
  [j]
  (case j
    0 (Color. 1.0 1.0 1.0)
    (Color. 1.0 1.0 1.0 (float (/ j 30)))))

(defn draw-jellies
  [ic j]
  (let [g (:g ic)]
    (.clearRect g 0 0 10 10)
    (doseq [y (range (count j))
            x (range (count (nth j 0)))]
      (let [v (nth (nth j y) x)]
        (doto (:g ic)
          (.setColor (jelly-color v))
          (.fillRect x y 1 1))))))
