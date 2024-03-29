(ns brave-clojure-rpg.person-test
  (:require [clojure.test :refer [testing deftest is]]
            [brave-clojure-rpg.person :as bt]
            [brave-clojure-rpg.test-helpers :refer [hero enemy]]))

(deftest attack-test
  (is (= (:hp (bt/damage enemy 3))
         7/2)))

(deftest attack-without-armor
  (let [enemy (assoc enemy :inventory {})]
    (with-out-str (is (= (:hp (bt/damage enemy 3)) 2)))))

(deftest attack-critical-hit-test
  (let [enemy (assoc-in enemy [:inventory :one-true-ring :critical-hit] 99)]
    (is
     (some #{2} (map  (fn [x] (:hp (bt/damage enemy 3)))
                      (range 1))))))

(deftest equip-weapon-test ; TODO inventory slots
  (let [hero (bt/->Person "Hero" 10 {} {:lulz {:type "weapon" :damage 3} :wtf {:armor 5}} 15) ; TODO to helpers
        expected-hero (bt/->Person "Hero" 10 {:lulz {:type "weapon" :damage 3}} {:wtf {:armor 5}} 15)]
    (is (= (bt/equip hero :lulz) expected-hero))
    (is (thrown? Exception (bt/equip hero :wtf)))
    (testing "Equipping should move equipped item to inventory")))
(deftest add-to-inventory-test
  (is (= (bt/add-to-inventory hero {:lul 25}) (assoc-in hero [:inventory :lul] 25))))
(deftest has?-test
  (is (= (bt/has? hero :spear) true))
  (is (= (bt/has? hero :lul) false)))
