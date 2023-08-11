(ns ft-turing.validations.core
  (:require [clojure.set :refer [subset?]]
    [ft-turing.helpers.validations :refer :all]
    [ft-turing.helpers.general :refer [return]]))

(defn is-name-field-valid?
  "realiza validação do campo name"
  [map-machine]
  (print "NAME FIELD: ")
  (and 
    (is-error? (not (nil? (get-field-name map-machine))) "Name field is empty")
    (success-validation)))

(defn are-all-elements-string?
  "verifica se todos os elementos da lista são string"
  [elements-list]
  (= 0 (count (filter #(not (string? %)) elements-list))))

(defn all-elements-size-one?
  "verifica se as stirng tem tamanho 1, ou seja, apenas 1 caracter"
  [elements-list]
  (= 0 (count (filter #(not (= 1 %)) (map #(count %) elements-list)))))

(defn is-alphabet-field-valid?
  "realiza a validação do campo alphabet"
  [map-machine]
  (let [alphabet-list (get-field-alphabet map-machine)]
    (print "ALPHABET FIELD: ")
    (and
      (is-error? (vector? alphabet-list) "The alphabet must be a vector and contain at least 1 element")
      (is-error? (are-all-elements-string? alphabet-list) "All elements must be a string") 
      (is-error? (all-elements-size-one? alphabet-list) "All elements must be size 1")
      (success-validation))))

(defn- is-blank-field-subset-alphabet?
  "verifica se o campo blank é um subconjunto de alphabet"
  [map-machine]
  (subset? #{ (get-field-blank map-machine) } (set (get-field-alphabet map-machine))))

(defn is-blank-field-valid? 
  "realiza validação do campo blank"
  [map-machine]
  (let [blank (get-field-blank map-machine)]
    (print "BLANK FIELD: ")
    (and 
      (is-error? (not (nil? blank)) "Blank field cannot be empty")
      (is-error? (string? blank) "Must be a string")
      (is-error? (= 1 (count blank)) "Must be size 1")
      (is-error? (is-blank-field-subset-alphabet? map-machine) "The blank field must be part of the alphabet")
      (success-validation))))

(defn is-states-field-valid?
  "realiza a validação do campo states"
  [map-machine]
  (let [states-list (get-field-states map-machine)]
    (print "STATES FIELD: ")
    (and 
      (is-error? (not (nil? states-list)) "States field is empty")
      (is-error? (vector? states-list) "The states must be a vector") 
      (is-error? (> (count states-list) 0) "The states field must contain at least 1 element")
      (is-error? (are-all-elements-string? states-list) "All elements must be a string")
      (success-validation))))


(defn- is-initial-field-subset-states?
  "verifica se o campo initial é um subconjunto de states"
  [map-machine]
  (subset? #{ (get-field-initial map-machine) } (set (get-field-states map-machine))))

(defn is-initial-field-valid?
  "realiza a validação do campo initial"
  [map-machine]
  (let [initial (get-field-initial map-machine)]
    (print "INITIAL FIELD: ")
    (and 
      (is-error? (not (nil? initial)) "Initial field is empty")
      (is-error? (string? initial) "Must be a string")
      (is-error? (is-initial-field-subset-states? map-machine) "The initial field must be part of the states")
      (success-validation))))

(defn- is-finals-field-subset-states?
  "verifica se o campo finals é um subconjunto de states"
  [map-machine]
  (subset?  (set (get-field-finals map-machine)) (set (get-field-states map-machine))))

(defn is-finals-field-valid?
  "realiza a validação do campo final"
  [map-machine]
  (let [finals-list (get-field-finals map-machine)]
    (print "FINAL FIELD: ")
    (and 
      (is-error? (not (nil? finals-list)) "Finals field is empty")
      (is-error? (vector? finals-list) "Must be a vector")
      (is-error? (are-all-elements-string? finals-list) "All elements must be a string")
      (is-error? (is-finals-field-subset-states? map-machine) "The finals field must be part of the states")
      (success-validation))))

(defn- is-set-transitions-keys-subset-states?
  "verifica se o conjunto de chaves do campo transitions é um subconjunto do campo estates"
  [map-machine]
  (subset? (set (keys (get-field-transitions map-machine))) (set (get-field-states map-machine))))

(defn- are-all-transition-unit-map? 
  "verifica se todos os elementos do array de transição são maps"
  [transition-vector]
  (= 0 (count (filter #(not (map? %)) transition-vector))))

(defn- are-all-transitions-vectors?
  "verifica se todas as transições são arrays"
  [transitions-map]
  (let [transitions-keys (vec (keys transitions-map))]
    (= 0 (count (filter #(or (not (vector? (get transitions-map %))) ( = 0 (count (get transitions-map %))) (not (are-all-transition-unit-map? (get transitions-map %)))) 
      transitions-keys)))))

(defn is-transitions-field-valid?
  "realiza a validação do campo final"
  [map-machine]
  (let [transitions-map (get-field-transitions map-machine)]
    (print "TRANSITIONS FIELD: ")
    (and 
      (is-error? (not (nil? transitions-map)) "Transitions field is empty")
      (is-error? (map? transitions-map) "Must be a map")
      (is-error? (is-set-transitions-keys-subset-states? map-machine) 
        "All keys that are in the transition field must be part of the states field")
      (is-error? (are-all-transitions-vectors? transitions-map) "All elements must be a vector and each vector element a map")
      (success-validation))))

(defn json-is-valid?
  [map-machine]
  (and 
    (is-name-field-valid? map-machine) 
    (is-alphabet-field-valid? map-machine)
    (is-blank-field-valid? map-machine)
    (is-states-field-valid? map-machine)
    (is-initial-field-valid? map-machine)
    (is-finals-field-valid? map-machine)
    (is-transitions-field-valid? map-machine)))

(defn tape-is-valid?
  [tape blank-char alphabet]
  (print "TAPE: ")
    (if (and  (is-error? (not (nil? tape)) "Tape with null value")
              (is-error? (not (clojure.string/includes? tape blank-char)) "Blank character is present on the tape")
              (is-error? (subset? (set (map #(return (str %)) tape)) (set alphabet)) "The tape is not a subset of the alphabet")
              (success-validation))
                (return true) 
                (return false)))