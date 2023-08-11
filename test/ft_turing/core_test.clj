(ns ft-turing.core-test
  (:require [clojure.test :refer :all]
            [ft-turing.helpers.general :refer :all]
            [ft-turing.helpers.validations :refer :all]
            [ft-turing.validations.core :refer :all]
            [ft-turing.turing_machine.core :refer :all]))

(deftest get-file-test
  (testing "verificando a captura de arquivos .json existentes" 
    (is(= {} (get-file "./test/ft_turing/jsons-to-testes/teste1.json"))))
  (testing "verificando a captura de arquivos .json inexistentes" 
    (is(= nil (get-file "./test/ft_turing/teste2.json")))))

(deftest get-field-name-test
  (testing "verificando a captura do campo name do mapa" 
    (is (= 0 (compare (get-field-name (get-file "./test/ft_turing/jsons-to-testes/turing.json")) "unary_sub"))))
  (testing "verificando a captura do campo name do mapa onde ele nao existe" 
    (is (= nil (get-field-name (get-file "./test/ft_turing/jsons-to-testes/teste1.json"))))))

(deftest get-field-blank-test
  (testing "verificando a captura do campo blank do mapa" 
    (is (= 0 (compare (get-field-blank (get-file "./test/ft_turing/jsons-to-testes/turing.json")) "."))))
  (testing "verificando a captura do campo blank em um mapa onde ele nao existe" 
    (is (= nil (get-field-blank (get-file "./test/ft_turing/jsons-to-testes/teste1.json")))))
  (testing "verificando a captura do campo blank em um vector" 
    (is (= nil (get-field-blank []))))
  (testing "verificando a captura do campo blank em uma string" 
    (is (= nil (get-field-blank "42sp"))))
  (testing "verificando a captura do campo blank em um nil" 
    (is (= nil (get-field-blank nil)))))

(deftest get-field-states-test
  (testing "verificando a captura do campo states do mapa" 
    (is (= 0 (compare (get-field-states (get-file "./test/ft_turing/jsons-to-testes/turing.json")) [ "scanright", "eraseone", "subone", "skip", "HALT" ]))))
  (testing "verificando a captura do campo states em um mapa onde ele nao existe" 
    (is (= nil (get-field-states (get-file "./test/ft_turing/jsons-to-testes/teste1.json"))))))

(deftest get-field-initial-test
  (testing "verificando a captura do campo initial do mapa" 
    (is (= 0 (compare (get-field-initial (get-file "./test/ft_turing/jsons-to-testes/turing.json")) "scanright"))))
  (testing "verificando a captura do campo initial em um mapa onde ele nao existe" 
    (is (= nil (get-field-initial (get-file "./test/ft_turing/jsons-to-testes/teste1.json"))))))

(deftest get-field-finals-test
  (testing "verificando a captura do campo finals do mapa" 
    (is (= 0 (compare (get-field-finals (get-file "./test/ft_turing/jsons-to-testes/turing.json")) [ "HALT" ]))))
  (testing "verificando a captura do campo finals em um mapa onde ele nao existe" 
    (is (= nil (get-field-finals (get-file "./test/ft_turing/jsons-to-testes/teste1.json"))))))

(deftest get-field-transitions-test
  (testing "verificando a captura do campo transitions do mapa" 
    (is (= {} (get-field-transitions (get-file "./test/ft_turing/jsons-to-testes/turing.json"))))))
  (testing "verificando a captura do campo transitions em um mapa onde ele nao existe" 
    (is (= nil (get-field-transitions (get-file "./test/ft_turing/jsons-to-testes/teste1.json")))))

(deftest is-name-field-valid?-test
  (testing "checando a validação do campo name da maquina para quando ele existe"
    (is (= true (is-name-field-valid? (get-file "./test/ft_turing/jsons-to-testes/turing.json")))))
  (testing "checando a validação do campo name da maquina para quando ele nao existe"
    (is (= false (is-name-field-valid? (get-file "./test/ft_turing/jsons-to-testes/teste1.json"))))) )

(deftest are-all-elements-string?-test
  (testing "checando com todos os elementos sendo string"
    (is (= true (are-all-elements-string? ["42", "sp", "Sp", "SP"]))))
  (testing "checando com um elemento sendo numero"
    (is (= false (are-all-elements-string? ["42", "sp", 42, "Sp", "SP"]))))
  (testing "checando com uma lista vazia"
    (is (= true (are-all-elements-string? [])))))

(deftest all-elements-size-one?-test
  (testing "checando com todas as strings tendo o tamanho 1"
    (is (= true (all-elements-size-one? ["4", "2", "S", "P"]))))
  (testing "checando com uma lista que contem uma string com tamanho maior que 1"
    (is (= false (all-elements-size-one? ["4", "2", "Sp", "S", "P"]))))
  (testing "checando com uma lista vazia"
    (is (= true (all-elements-size-one? [])))))

(deftest is-alphabet-field-valid?-test
  (testing "checando com todos os elementos sendo uma string tendo o tamanho 1"
    (is (= true (is-alphabet-field-valid? {"alphabet" ["4", "2", "S", "P"]}))))
  (testing "checando com apenas 1 elementos com tamanho 1"
    (is (= true (is-alphabet-field-valid? {"alphabet" ["4"]}))))
  (testing "checando com o campo alphabet nao sendo uma lista"
    (is (= false (is-alphabet-field-valid? {"alphabet" "42sp"}))))
  (testing "checando com um elemento sendo numero"
    (is (= false (is-alphabet-field-valid? {"alphabet" ["4", "s", 42, "p", "P"]}))))
  (testing "checando com uma lista que contem uma string com tamanho maior que 1"
    (is (= false (is-alphabet-field-valid? {"alphabet" ["4", "2", "Sp", "S", "P"]}))))
  (testing "checando com uma lista vazia"
    (is (= false (is-alphabet-field-valid? [])))))

(deftest  is-blank-field-valid?-test
  (testing "checando com o blank sendo subconjunto de alphabet"
    (is (= true (is-blank-field-valid? {"blank" "S" "alphabet" ["4", "2", "S", "P"]}))))
  (testing "checando com o blank sendo subconjunto de alphabet mas contendo mais de 1 caracter"
    (is (= false (is-blank-field-valid? {"blank" "SP" "alphabet" ["4", "2", "S", "P", "SP"]}))))
  (testing "checando com o blank NÃO sendo subconjunto de alphabet"
    (is (= false (is-blank-field-valid? {"blank" "s" "alphabet" ["4", "2", "S", "P"]}))))
  (testing "checando com o blank sendo vector"
    (is (= false (is-blank-field-valid? {"blank" ["s"] "alphabet" ["4", "2", "S", "P"]}))))
  (testing "checando com o blank sendo numero"
    (is (= false (is-blank-field-valid? {"blank" 4 "alphabet" ["4", "2", "S", "P"]}))))
  (testing "checando com o blank não existindo"
    (is (= false (is-blank-field-valid? { "alphabet" ["4", "2", "S", "P"]})))))

(deftest is-states-field-valid?-test 
  (testing "checando com o campo states existindo e sendo valido"
    (is (= true (is-states-field-valid? {"states" ["state1" "state2" "state3"]}))))
  (testing "checando com o campo states NÃO existindo e sendo valido"
    (is (= false (is-states-field-valid? {"blank" "."}))))
  (testing "checando com o campo states NÃO sendo vector"
    (is (= false (is-states-field-valid? {"states" '("42", "43", "44")}))))
  (testing "checando com o campo states sendo um array vazio"
    (is (= false (is-states-field-valid? {"states" []}))))
  (testing "checando com o campo states tendo um alemento no array que não é string"
    (is (= false (is-states-field-valid? {"states" ["state1" "state2" "state3" 42]})))))

(deftest is-initial-field-valid?-test 
  (testing "checando se a função detecta que o campo initial é valido"
    (is (= true (is-initial-field-valid? {"initial" "42sp" "states" ["initial", "middle", "42sp", "end"]})))) 
  (testing "checando com o campo initial não estando presente nos states"
    (is (= false (is-initial-field-valid? {"initial" "42sp" "states" ["initial", "middle", "end"]}))))
  (testing "checando com o campo initial não sendo uma string"
    (is (= false (is-initial-field-valid? {"initial" 42 "states" ["initial", "middle", "42", "end"]})))))

(deftest is-finals-field-valid?-test 
  (testing "checando se a função detecta que o campo finals é valido com apenas um elemento em seu array"
    (is (= true (is-finals-field-valid? {"finals" ["42sp"] "states" ["initial", "middle", "42sp", "end"]}))))
  (testing "checando se a função detecta que o campo finals é valido com mais de um elemento em seu array"
    (is (= true (is-finals-field-valid? {"finals" ["42sp" "end"] "states" ["initial", "middle", "42sp", "end"]}))))
  (testing "checando se a função detecta que o campo finals é valido contendo todos os elementos de states"
    (is (= true (is-finals-field-valid? {"finals" ["initial", "middle", "42sp", "end"] "states" ["initial", "middle", "42sp", "end"]}))))
  (testing "checando com o campo finals contendo um array vazio"
    (is (= true (is-finals-field-valid? {"finals" [] "states" ["initial", "middle", "42sp", "end"]}))))
  (testing "checando sem o campo finals"
    (is (= false (is-finals-field-valid? { "states" ["initial", "middle", "42sp", "end"]}))))
  (testing "checando com o campo finals não sendo um array"
    (is (= false (is-finals-field-valid? {"finals" "42sp" "states" ["initial", "middle", "42sp", "end"]}))))
  (testing "checando com o campo final contendo dentro do array um elemento que não é string"
    (is (= false (is-finals-field-valid? {"finals" ["42sp" "end", 42] "states" ["initial", "middle", "42sp", "end", 42]})))))

(deftest is-transitions-field-valid?-test
  (testing "caso onde todas os elementos de states estão presente no campo transitions"
    (is (= true (is-transitions-field-valid? {"transitions" {"initial", [{"4" "2"}], "middle", [{"4" "2"}], "42sp", [{"4" "2"}], "end", [{"4" "2"}]} 
      "states" ["initial", "middle", "42sp", "end"]}))))
  (testing "caso onde existe um elemento no states que não esta presente no campo transitions"
    (is (= true (is-transitions-field-valid? {"transitions" {"initial", [{"4" "2"}], "middle", [{"4" "2"}], "42sp", [{"4" "2"}]} 
      "states" ["initial", "middle", "42sp", "end"]}))))
  (testing "caso onde existe um elemento no transitions que não esta presente no campo state"
    (is (= false (is-transitions-field-valid? {"transitions" {"initial", [{"4" "2"}], "middle", [{"4" "2"}], "42sp", [{"4" "2"}], "end", [{"4" "2"}]} 
      "states" ["initial", "middle", "42sp"]}))))
  (testing "caso onde um dos elementos do transitions não é um vector"
    (is (= false (is-transitions-field-valid? {"transitions" {"initial", [{"4" "2"}], "middle", [{"4" "2"}], "42sp", {"escola" 42}, "end", [{"4" "2"}]} 
      "states" ["initial", "middle", "42sp", "end"]}))))
  (testing "caso onde um dos array de trasição estja vazio"
    (is (= false (is-transitions-field-valid? {"transitions" {"initial", [{"4" "2"}], "middle", [{"4" "2"}], "42sp", [], "end", [{"4" "2"}]} 
      "states" ["initial", "middle", "42sp", "end"]}))))
  (testing "caso onde um dos array de trasição esteja com um elemento que não seja um objeto"
    (is (= false (is-transitions-field-valid? {"transitions" {"initial", [{"4" "2"}], "middle", [{"4" "2"}], "42sp", [{"4" "2"} 42], "end", [{"4" "2"}]} 
      "states" ["initial", "middle", "42sp", "end"]}))))
  (testing "caso onde o campo transitions nao existe"
    (is (= false (is-transitions-field-valid? {"states" ["initial", "middle", "42sp"]})))))

;COMEÇANDO OS TESTES DA MAQUINA DE TURING

(deftest insert-head-in-tape-teste
  (testing "Com o cabeçote da maquina de turing na posição negativa"
    (is (= "[4]2São-Paulo" (insert-head-in-tape -5 "42São-Paulo" "."))))
  (testing "Com o cabeçote da maquina de turing na posição zero"
    (is (= "[4]2São-Paulo" (insert-head-in-tape 0 "42São-Paulo" "."))))
  (testing "Com o cabeçote da maquina de turing na posição um"
    (is (= "4[2]São-Paulo" (insert-head-in-tape 1 "42São-Paulo" "."))))
  (testing "Com o cabeçote da maquina de turing na posição do meio da string"
    (is (= "42São[-]Paulo" (insert-head-in-tape 5 "42São-Paulo" "."))))
  (testing "Com o cabeçote da maquina de turing na penultima posição da string"
    (is (= "42São-Pau[l]o" (insert-head-in-tape 9 "42São-Paulo" "."))))
  (testing "Com o cabeçote da maquina de turing na ultima posição da string"
    (is (= "42São-Paul[o]" (insert-head-in-tape 10 "42São-Paulo" "."))))
  (testing "Com o cabeçote da maquina de turing logo após a ultima posição da string"
    (is (= "42São-Paulo[.]" (insert-head-in-tape 11 "42São-Paulo" "."))))
  (testing "Com o cabeçote da maquina de turing sendo muito maior que o tamanho da string"
    (is (= "42São-Paulo[.]" (insert-head-in-tape 50 "42São-Paulo" "."))))
  (testing "Recebendo uma string vazia e e o cabeçote na posição zero"
    (is (= "[.]" (insert-head-in-tape 0 "" ".")))))

(deftest get-transition-list-with-key-test
  (let [map-machine (get-file "./test/ft_turing/jsons-to-testes/turing2.json")]
    (testing "capturando uma chave que existe no campo de transitions"
      (is (not (nil? (get-transition-list-with-key map-machine "subone")))))
    (testing "tentando capturar uma chave que NÃO existe no campo de transitions"
      (is (nil? (get-transition-list-with-key map-machine "42SP"))))))

(deftest remove-head-in-tape-test
  (testing "Com o cabeçote da maquina de turing na posição zero"
    (is (= "42São-Paulo" (remove-head-in-tape 0 "[4]2São-Paulo"))))
  (testing "Com o cabeçote da maquina de turing na posição um"
    (is (= "42São-Paulo" (remove-head-in-tape 1 "4[2]São-Paulo"))))
  (testing "Com o cabeçote da maquina de turing na posição do meio da string"
    (is (= "42São-Paulo" (remove-head-in-tape 5 "42São[-]Paulo"))))
  (testing "Com o cabeçote da maquina de turing na penultima posição da string"
    (is (= "42São-Paulo" (remove-head-in-tape 9 "42São-Pau[l]o"))))
  (testing "Com o cabeçote da maquina de turing na ultima posição da string"
    (is (= "42São-Paulo" (remove-head-in-tape 10 "42São-Paul[o]")))))


(deftest get-char-focus-head-test
  (testing "Com o cabeçote da maquina de turing na posição zero"
    (is (= "4" (get-char-focus-head 0 "42São-Paulo"))))
  (testing "Com o cabeçote da maquina de turing na posição um"
    (is (= "2" (get-char-focus-head 1 "42São-Paulo"))))
  (testing "Com o cabeçote da maquina de turing na posição do meio da string"
    (is (= "-" (get-char-focus-head 5 "42São-Paulo"))))
  (testing "Com o cabeçote da maquina de turing na penultima posição da string"
    (is (= "l" (get-char-focus-head 9 "42São-Paulo"))))
  (testing "Com o cabeçote da maquina de turing na ultima posição da string"
    (is (= "o" (get-char-focus-head 10 "42São-Paulo")))))


(deftest get-element-list-through-char-test
  (testing "buscando por um caracter que existe na lista do estado"
    (is (= [{"read" "P"}] (get-element-list-through-char "P" [{"read" "4"} {"read" "2"} {"read" "S"} {"read" "P"}]))))
  (testing "buscando por um caracter que NÃO existe na lista do estado"
    (is (= [] (get-element-list-through-char "A" [{"read" "4"} {"read" "2"} {"read" "S"} {"read" "P"}]))))
  (testing "buscando por um caracter em uma lista vazia"
    (is (= [] (get-element-list-through-char "A" []))))
  (testing "buscando por um caracter em uma lista onde existe duas ocorrencias do mesmo"
    (is (= [{"read" "P"} {"read" "P"}] (get-element-list-through-char "P" [{"read" "P"} {"read" "4"} {"read" "2"} {"read" "S"} {"read" "P"}]))))
  (testing "buscando por um caracter em uma lista NULA"
    (is (= [] (get-element-list-through-char "A" nil)))))

(deftest insert-new-char-in-tape-test
  (testing "trocando o caracter do inicio da string"
    (is (= "52São-Paulo" (insert-new-char-in-tape "42São-Paulo" 0 "5" true))))
  (testing "trocando o caracter da posição um da string"
    (is (= "45São-Paulo" (insert-new-char-in-tape "42São-Paulo" 1 "5" true))))
  (testing "trocando o caracter de posição no meio da string"
    (is (= "42São5Paulo" (insert-new-char-in-tape "42São-Paulo" 5 "5" true))))
  (testing "tentando inserir um nil como caracter"
    (is (= "42São-Paulo" (insert-new-char-in-tape "42São-Paulo" 5 nil true))))
  (testing "trocando o caracter da penultima posição da string"
    (is (= "42São-Pau5o" (insert-new-char-in-tape "42São-Paulo" 9 "5" true))))
  (testing "trocando o caracter da ultima posição da string"
    (is (= "42São-Paul5" (insert-new-char-in-tape "42São-Paulo" 10 "5" true))))
  (testing "tentando inserir um caracter que não faz parte do alfabeto"
    (is (= "42São-Paulo" (insert-new-char-in-tape "42São-Paulo" 10 "5" false)))))

(deftest get-char-to-insert-tape-test
  (let [map-machine (get-file "./test/ft_turing/jsons-to-testes/turing2.json")]
    (testing "pegando o caracter que deve ser inserido na fita"
      (is (= "." (get-char-to-insert-tape "=" map-machine "scanright"))))
    (testing "passando como parametro um caracter que não existe na fita"
      (is (nil? (get-char-to-insert-tape "?" map-machine "scanright"))))
    (testing "passando um estado que nao existe"
      (is (nil? (get-char-to-insert-tape "=" map-machine "teste"))))))

(deftest get-next-move-head-test
  (let [map-machine (get-file "./test/ft_turing/jsons-to-testes/turing3.json")]
    (testing "capturando movimento para a DIREITA do cabeçote da fita"
      (is (= 1 (get-next-move-head "." map-machine "scanright"))))
    (testing "capturando movimento para a ESQUERDA do cabeçote da fita"
      (is (= -1 (get-next-move-head "=" map-machine "scanright"))))
    (testing "passando como parametro um caracter cujo action não é nem para direita nem para a esquerda"
      (is (zero? (get-next-move-head "4" map-machine "scanright"))))
    (testing "passando como parametro um state que não existe nas transações"
      (is (nil? (get-next-move-head "." map-machine "teste"))))))


(deftest get-next-state-test
  (let [map-machine (get-file "./test/ft_turing/jsons-to-testes/turing2.json")]
    (testing "pegando o proximo  estado da maquina"
      (is (= "eraseone" (get-next-state "=" map-machine "scanright"))))
    (testing "passando como parametro um caracter que não existe na fita"
      (is (nil? (get-next-state "?" map-machine "scanright"))))
    (testing "passando um estado que nao existe"
      (is (nil? (get-next-state "=" map-machine "teste"))))))

(deftest tape-is-valid?-test
  (let [alphabet [ "1", ".", "-", "=" ]]
    (testing "passando uma fita com string grande valida"
      (is (= true (tape-is-valid? "=1=1-=1-" "." alphabet))))
    (testing "passando uma fita vazia"
      (is (= true (tape-is-valid? "" "." alphabet))))
    (testing "passando uma fita que contem o caracter branco"
      (is (= false (tape-is-valid? "=1=1-=1-." "." alphabet))))
    (testing "passando uma fita que contem um caracter que não faz parte do alfabeto"
      (is (= false (tape-is-valid? "=1=1-=1-4" "." alphabet))))
    (testing "passando uma fita sendo nil"
      (is (= false (tape-is-valid? nil "." alphabet))))))

(deftest next-head-position-test
  (testing "passando um head positivo com movimento -1"
    (is (= 4 (next-head-position 5 -1))))
  (testing "passando um head positivo com movimento +1"
    (is (= 6 (next-head-position 5 1))))
  (testing "passando um head zerado com movimento -1"
    (is (= 0 (next-head-position 0 -1))))
  (testing "passando um head zerado com movimento +1"
    (is (= 1 (next-head-position 0 1)))))

(deftest insert-char-present-alphabet?-test
  (testing "passando um char que existe no alfabeto"
    (is (= true (insert-char-present-alphabet? "z" ["a" "b" "c" "z"]))))
  (testing "passando um char que NÃO existe no alfabeto"
    (is (= false (insert-char-present-alphabet? "y" ["a" "b" "c" "z"]))))
  (testing "passando um nil no lugar do char"
    (is (= false (insert-char-present-alphabet? nil ["a" "b" "c" "z"])))))

(deftest is-mtu?-test
  (testing "passando uma maquina SEM o campo que defini o mtu"
    (is (= false (is-mtu? {}))))
  (testing "passando uma maquina COM o campo que defini o mtu"
    (is (= true (is-mtu? {"type_machine" "mtu"}))))
  (testing "passando uma maquina COM o campo que defini o mtu mas com outro valor"
    (is (= false (is-mtu? {"type_machine" "42sp"})))))

(deftest get-input-mtu-test
  (testing "passando uma entrada para a mtu que CONTÉM um input"
    (is (= "111+11=" (get-input-mtu "a&a{[+b>1][1a>1]}b{[1b>1][=c>=]}c{[.d<.]}d{[1s>.][=d<.]}*111+11="))))
  (testing "passando uma entrada para a mtu que contém DOIS MARCADORES de input"
    (is (= nil (get-input-mtu "a&a{[+b>1][1a>1]}b{[1b>1][=c>=]}c{[.d<.]}d{[1s>.][=d<.]}*11111*111+11="))))
  (testing "passando uma entrada para a mtu que NÃO CONTÉM um input"
    (is (= nil (get-input-mtu "a&a{[+b>1][1a>1]}b{[1b>1][=c>=]}c{[.d<.]}d{[1s>.][=d<.]}")))
  (testing "passando uma entrada para a mtu que contém o marcador de input, mas não tem nada após o mesmo"
    (is (= nil (get-input-mtu "a&a{[+b>1][1a>1]}b{[1b>1][=c>=]}c{[.d<.]}d{[1s>.][=d<.]}*"))))))