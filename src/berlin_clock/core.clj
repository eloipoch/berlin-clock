(ns berlin-clock.core
  (:require [clojure.string :refer [join split]]))


(defn- parse-int [s] (Integer. (re-find #"\d+" s)))

(defn- show-lights [lights-on pattern length]
  (let [lights-off (- length lights-on)]
    (join
      (concat (take lights-on (cycle pattern))
              (take lights-off (cycle "O"))))))

(defn- hours-first-line-lights [hours]
  (let [lights-on (quot hours 5)]
    (show-lights lights-on "R" 4)))

(defn- hours-second-line-lights [hours]
  (let [lights-on (mod hours 5)]
    (show-lights lights-on "R" 4)))

(defn- minutes-first-line-lights [minutes]
  (let [lights-on (quot minutes 5)]
    (show-lights lights-on "YYR" 11)))

(defn- minutes-second-line-lights [minutes]
  (let [lights-on (mod minutes 5)]
    (show-lights lights-on "Y" 4)))

(defn- seconds-ligths [seconds]
  (let [lights-on (mod (+ seconds 1) 2)]
    (show-lights lights-on "Y" 1)))


(defn show [time]
  (let [[hours minutes seconds] (map parse-int (split time #":"))]
    (join
      "\n"
      [(seconds-ligths seconds)
       (hours-first-line-lights hours)
       (hours-second-line-lights hours)
       (minutes-first-line-lights minutes)
       (minutes-second-line-lights minutes)])))
