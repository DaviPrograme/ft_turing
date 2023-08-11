(ns ft-turing.turing_machine.core
  (:require [ft-turing.helpers.general :refer [return]]
            [ft-turing.helpers.validations :refer [get-field-transitions]]
            [clojure.set :refer [subset?]]))

(defn highlight-head-position
  [tape-with-head head-position]
  (let [tape-size (count tape-with-head)
        highlight-color "\033[1;32m"
        return-color "\033[0m"]
    (str
      (subs tape-with-head 0 head-position)
      highlight-color
      (subs tape-with-head head-position (+ head-position 3))
      return-color
      (subs tape-with-head (+ head-position 3) tape-size))))

(defn insert-head-in-tape
  "apresenta na string em qual posição o cabeçote da maquina de turing esta"
  [head-position tape blank]
  (let [tape-size (count tape) position (if (< head-position 0) (return 0) (if (>= head-position tape-size) (return tape-size) (return head-position)))]
    (str 
      (if (< position 0) ("") (subs tape 0 position))
      (if (and (>= position 0) (< position tape-size)) (str "[" (subs tape position (+ position 1)) "]"))
      (if (>= position tape-size) (str "[" blank "]") (subs tape (+ position 1) tape-size)))))

(defn remove-head-in-tape
  [head-position tape]
  (let [tape-size (count tape)]
    (str 
      (subs tape 0 head-position)
      (subs tape (+ head-position 1) (+ head-position 2))
      (if (< (+ head-position 3) tape-size) (subs tape (+ head-position 3) tape-size)))))

(defn get-transition-list-with-key
  [map-machine key-trasition]
  (get (get-field-transitions map-machine) key-trasition nil))

(defn get-char-focus-head
  [head-position, tape]
  (subs tape head-position (+ head-position 1)))

(defn get-element-list-through-char
  [char transition-list]
  (if (nil? transition-list) (return []) (vec (filter #(= char (get % "read" nil)) transition-list))))

(defn insert-new-char-in-tape
  [tape head-position new-char new-char-valid?]
  (if (or (nil? new-char) (not new-char-valid?)) (return tape) 
    (str (subs tape 0 head-position) new-char (subs tape (+ head-position 1) (count tape)))))

(defn get-char-to-insert-tape
  [char-read-focus map-machine state]
  (get (get (get-element-list-through-char char-read-focus (get-transition-list-with-key map-machine state)) 0) "write" nil))

(defn get-next-state
  [char-read-focus map-machine state]
  (get (get (get-element-list-through-char char-read-focus (get-transition-list-with-key map-machine state)) 0) "to_state" nil))

(defn get-next-move-head
  [char-read-focus map-machine state]
  (let [ move (get (get (get-element-list-through-char char-read-focus (get-transition-list-with-key map-machine state)) 0) "action" nil)]
    (cond 
      (= move "RIGHT") (return 1)
      (= move "LEFT") (return -1)
      (and (not (nil? move)) (not (= move "LEFT")) (not (= move "RIGHT"))) (return 0)
      :else (return nil))))

(defn next-head-position
  [head-position next-move]
  (if (and (zero? head-position) (= next-move -1)) (return 0) (return (+ head-position next-move))))

(defn- exit-turing-machine
  [next-move next-state-is-present-in-finals-list? new-char-valid? is-loop?]
  (cond
    (return next-state-is-present-in-finals-list?) (println "\nWE HAVE REACHER AN FINAL STATE, BYE BYE! :)")
    (nil? next-move) (println "\nERROR: next move not found")
    (= next-move 0) (println "\nERROR: next move with a different value of \"RIGHT\" and \"LEFT\"")
    (= false new-char-valid?) (println "\nERROR: tried to write a character on the tape that is not present in the alphabet.")
    (= true is-loop?) (println "\nERROR: Exceeded movement limit")
    :else (println "\nUNMAPPED ERROR. :(")))

(defn insert-char-present-alphabet?
  [insert-char alphabet]
  (subset? (set  (set [insert-char])) (set alphabet)))

(defn turing-machine
  [step map-machine alphabet tape tape-original blank-char head-position state finals]
  (let [tape-with-head (insert-head-in-tape head-position tape blank-char)
      tape-without-head (remove-head-in-tape head-position tape-with-head)
      insert-char (get-char-to-insert-tape (get-char-focus-head head-position tape-without-head) map-machine state)
      new-char-valid? (insert-char-present-alphabet? insert-char alphabet)
      new-tape-without-head (insert-new-char-in-tape tape-without-head head-position insert-char new-char-valid?)
      next-move  (get-next-move-head (get-char-focus-head head-position tape-without-head) map-machine state)
      next-state (get-next-state (get-char-focus-head head-position tape-without-head) map-machine state)
      next-state-is-present-in-finals-list? (some #(= % next-state) finals)
      is-loop? (< (* (count tape-original) 100) step)]
        (println "\n\n")
        (println "STEP: " step)
        (println "STATE: " state)
        (println "TAPE-ORIGINAL: " tape-original)
        (println "TAPE-OLD: " (highlight-head-position tape-with-head head-position))
        (println "INSERT-CHAR-IN-HEAD: " insert-char)
        (println "TAPE-NOW: " (highlight-head-position (insert-head-in-tape head-position new-tape-without-head blank-char) head-position))
        (println "NEXT-STATE: " next-state)
        (if (and (not next-state-is-present-in-finals-list?) (not (nil? next-move)) (not (= next-move 0)) new-char-valid? (not is-loop?))
          (recur (inc step) map-machine alphabet new-tape-without-head tape-original blank-char (next-head-position head-position next-move) next-state finals)
          (exit-turing-machine next-move next-state-is-present-in-finals-list? new-char-valid? is-loop?))))
