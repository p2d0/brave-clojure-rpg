(ns brave-clojure-rpg.dialog-controller
  (:gen-class))

(defprotocol Dialog
  "Control dialog reactions"
  (print [x] "Print a dialog"))

(defrecord SimpleDialog [title choices]
  Dialog
  (print [dialog]
    (println title)
    (doseq [choice (keys choices) i (range 1 (+ (count choices) 1))]
      (println (str i ":" choice)))))
