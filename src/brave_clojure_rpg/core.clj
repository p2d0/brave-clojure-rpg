(ns brave-clojure-rpg.core
  (:require [brave-clojure-rpg.dialogs :as di])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
(def ^:dynamic input (fn [] 25))

(defn start-dialog-loop [dialog]
  (di/display dialog)
  (let [next_dialog (di/choose dialog (Integer. (input)))]
    (if next_dialog
      (start-dialog-loop next_dialog))))
