(ns brave-clojure-rpg.battle-test
  (:require [clojure.test :refer [testing deftest is]]
            [brave-clojure-rpg.battle :as bt]))
(def hero
  (bt/->Person "Hero" 10 {:spear 3} {:headgear 3}))

(def enemy
  (bt/->Person "Gremlin" 1 {:hands 1} {:one-true-ring 50}))

(deftest initalizing-battle-test
  (is (=
       (with-out-str (bt/print-battle-status hero enemy))
       "Started battle with Gremlin\nYour hp is:10\nHis hp is:1\nYour move\n")))

(deftest attack-test
  (is (= (bt/calculate-damage hero enemy :spear)
         297/100)))
; Random generated result test
(deftest attack-critical-hit-test
  (is
   (some #{297/50} (map  (fn [x] (bt/calculate-damage hero enemy :spear))
                         (range 50)))))
