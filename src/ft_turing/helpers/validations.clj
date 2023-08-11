(ns ft-turing.helpers.validations
	(:require [ft-turing.helpers.general :refer [return]]))

(defn get-field-name
  "captura o campo name do mapa ou retorna nil caso não encontrar"
  [map-machine]
  (get map-machine "name" nil))

(defn get-field-alphabet
  "captura o campo alphabet do mapa ou retorna nil caso não encontrar"
  [map-machine]
  (get map-machine "alphabet" nil))

(defn get-field-blank
  "captura o campo alphabet do mapa ou retorna nil caso não encontrar"
  [map-machine]
  (get map-machine "blank" nil))

(defn get-field-states
  "captura o campo states do mapa ou retorna nil caso não encontrar"
  [map-machine]
  (get map-machine "states" nil))

(defn get-field-initial
  "captura o campo initial do mapa ou retorna nil caso não encontrar"
  [map-machine]
  (get map-machine "initial" nil))

(defn get-field-finals
  "captura o campo finals do mapa ou retorna nil caso não encontrar"
  [map-machine]
  (get map-machine "finals" nil))

(defn get-field-transitions
  "captura o campo transitions do mapa ou retorna nil caso não encontrar"
  [map-machine]
  (get map-machine "transitions" nil))

(defn is-mtu?
  "captura o campo type_machine (este campo é opcional) e verifica se é do tipo mtu (machine turing universal)"
  [map-machine]
  (= (get map-machine "type_machine" "default") "mtu"))

(defn is-error?
	[boolean message-error]
	(if (not boolean) (println (str "ERROR " "(" message-error ")" ) ))
	(return boolean))

(defn success-validation
	[]
  (println "OK")
  (return true))