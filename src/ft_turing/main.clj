(ns ft-turing.main
  (:require 
    [ft-turing.helpers.general :refer [return get-file get-input-mtu]]
    [ft-turing.helpers.validations :refer [get-field-blank get-field-alphabet get-field-initial get-field-finals is-error? success-validation is-mtu?]]
    [ft-turing.validations.core :refer [json-is-valid? tape-is-valid?]]
    [ft-turing.turing_machine.core :refer [turing-machine]]))

(defn- json-was-found?
  [map-machine]
  (print "JSON: ")
  (if (and (is-error? (not (nil? map-machine)) "JSON not found") (success-validation)) (return true) (return false)))
  
(defn- start-turing-machine
  [map-machine tape blank-char alphabet]
  (println "\n\n\n-=-=-=-=-=-=-=-=-START TURING MACHINE-=-=-=-=-=-=-=-=-")
  (let
    [step 0 
    head-position 0
    initial-state (get-field-initial map-machine)
    finals-list (get-field-finals map-machine)
    tape-original tape]
      (turing-machine step map-machine alphabet tape tape-original blank-char head-position initial-state finals-list)))

(defn main
  [path-json tape]
  (println "\n-=-=-=-=-=-=-=-=-TURING MACHINE-=-=-=-=-=-=-=-=-\n")
  (let [map-machine (get-file path-json) 
        blank-char (get-field-blank map-machine)
        alphabet (get-field-alphabet map-machine)]
    (if (and 
          (json-was-found? map-machine) 
          (json-is-valid? map-machine) 
          (tape-is-valid? (if (is-mtu? map-machine) (get-input-mtu tape) (return tape)) blank-char alphabet))
            (start-turing-machine map-machine tape blank-char alphabet)
            (println "Finishing the Turing Machine! :("))))
