(ns brave-clojure-rpg.parsing
  (:require [brave-clojure-rpg.dialogs :as Dialogs])
  (:require [clojure.data.json :as json])
  (:require [brave-clojure-rpg.person :as person]))

 ; TODO different condition instead of counting array (dictionary)
 ; TODO add SideEffectDialog
(defn parse-dialog [hero arr]
  (let [real-arr (drop 1 arr)]
    (case (clojure.string/lower-case (nth arr 0 ""))
      "battle" (Dialogs/->BattleDialog (first real-arr) (nth real-arr 1) hero (person/map->Person (nth real-arr 2)) (parse-dialog hero (last real-arr)))
      "simple" (Dialogs/->SimpleDialog (first real-arr)
                                       (nth real-arr 1)
                                       hero
                                       (into [] (map
                                                 (fn [item] (parse-dialog hero item)) (last real-arr))))
      "sideeffect" (Dialogs/->SideEffectDialog (first real-arr) (nth real-arr 1) hero (nth real-arr 2) (parse-dialog hero (last real-arr)))
      "" [])))

(defmulti parse-dialog-from-file (fn [filename] (last (clojure.string/split filename #"\."))))
(defmethod parse-dialog-from-file "json"
  [filename]
  (let [json (json/read-str (slurp filename) :key-fn keyword)
        hero (person/map->Person (:hero json {}))
        dialogs (get json :dialogs)]
    (parse-dialog hero dialogs)))

; (defn parse-dialog-from-file [file]
;   (let [json (json/read-str (slurp file) :key-fn keyword)
;         hero (person/map->Person (:hero json))]
;     (parse-dialog-json (if hero (person/map->Person (:hero json {})) {}) (get  json :dialogs))))
