(ns ft-turing.helpers.general
	(:require [clojure.data.json :as json]
            [clojure.string :refer [split]]))

(defn return
	[value]
	((constantly value)))

(defn get-file 
  "captura conteudo do arquivo passado como parametro"
  [path]
  (try (json/read-str (slurp path)) (catch Exception e (return nil))))

(defn get-input-mtu
  "separa o input do restante das informações que uma mtu recebe na fita"
  [tape]
  (let [vector-input (split tape #"\*")]
    (if (= (count vector-input) 2) (get vector-input 1) (return nil))))