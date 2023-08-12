# ft_turing

Este é o repositório do projeto ft_turing, uma implementação funcional de uma única Máquina de Turing de fita infinita. A Máquina de Turing é um modelo matemático importante na ciência da computação, permitindo simular cálculos e processos algorítmicos.

## Sobre Alan Turing

Alan Mathison Turing foi um cientista da computação pioneiro britânico, matemático, lógico, criptoanalista, filósofo, biólogo matemático e maratonista e corredor de ultradistância. Ele teve um papel fundamental no desenvolvimento da ciência da computação, sendo amplamente considerado o pai da ciência da computação teórica e da inteligência artificial. Após a Segunda Guerra Mundial, Turing trabalhou no Laboratório Nacional de Física, onde projetou o ACE, um dos primeiros projetos para um computador com programa armazenado. Mais tarde, na Universidade de Manchester, ele contribuiu para o desenvolvimento dos computadores de Manchester e se interessou pela biologia matemática.

## Introdução

Este projeto tem como objetivo a implementação funcional de uma Máquina de Turing de fita única e cabeça única a partir de uma descrição fornecida em formato JSON. A Máquina de Turing é um modelo matemático que consiste em estados, transições e uma fita infinita onde a cabeça da máquina lê e escreve símbolos. O projeto incentiva a exploração de linguagens de programação funcionais, como OCaml, embora outras linguagens também sejam aceitas, desde que sigam o paradigma funcional.

## 🛠️ Tecnologias

* [Clojure](https://clojure.org/) - Linguagem de programação
* [Make](https://www.gnu.org/software/make) - Ferramenta de Gerenciamento de projetos
* [Docker](https://www.docker.com/) - Plataforma de virtualização

## Objetivos

O projeto tem os seguintes objetivos:

* Implementar uma Máquina de Turing funcional capaz de simular cálculos algorítmicos.
* Aceitar uma descrição de máquina em formato JSON como entrada.
* Executar as transições da máquina de acordo com as regras definidas.
* Fornecer uma representação visual do processo de execução da Máquina de Turing.

## Uso
### 1º passo - Construir o ambiente

para construirmos o ambiente onde o Clojure possa ser executado  basta executarmos o seguinte comando na raiz do projeto:
```bash
make
```
Após a execução do comando supramencionado, caso não ocorra falhas teremos um ambiente pronto para execução do projeto.

### 2º passo - Execução do projeto

O programa ft_turing aceita como argumentos um arquivo JSON que descreve a Máquina de Turing e uma entrada para a simulação. A descrição da máquina JSON deve incluir informações sobre os estados, alfabeto, transições e comportamento da máquina. O programa simula a execução da Máquina de Turing de acordo com as regras definidas na descrição.

```bash
lein run [arquivo_json] [entrada]
```
Os arquivos json com algumas descrições de maquinas estão dentro da pasta [machines](https://github.com/DaviPrograme/ft_turing/tree/main/machines), mas sinta-se a vontade para brincar com o projeto e crias suas proprias maquinas (achei a criação de maquinas um exercicio divertido :) )

## Arquivo de configuração da maquina de turing

A maquina de turing para executar alguma ação tem que receber como primeiro parâmetro o caminho de um arquivo de configuração json que contém as seguintes informações:


| Campo          | Descrição                                                             |
|----------------|-----------------------------------------------------------------------|
| name           | nome da maquina                                                       |
| alphabet       | caracteres permitidos na fita e na operação da maquina                |
| blank          | caracter que representa um espaço não preenchido                      |
| states         | lista de estados que a maquina utilizara                              |
| initial        | estado inicial da maquina                                             |
| finals         | lista de estados que representa o fim do processo                     |
| transitions    | um mapa que apresenta a lista detalhada de transições de cada estado  |


## Maquinas fornecidas em formato JSON

### 1) unary_add

O objetivo da maquina [unary_add](https://github.com/DaviPrograme/ft_turing/blob/main/machines/unary_add.json) é realizar operações de adição unária, ou seja, ao receber como input a string "11+111=" ela geraria a string "11111", isso porque dois "ums" mais três "ums" é igual a cinco "ums".

Exemplo de execução:
```bash
lein run ./machines/unary_add.json "11+111="
```

![mapa de estados da unary_add](https://github.com/DaviPrograme/ft_turing/blob/main/unary_add.png)


### 2) palidrome_simple

O objetivo da maquina [palidrome_simple](https://github.com/DaviPrograme/ft_turing/blob/main/machines/palidrome_simple.json) é determinar se uma string binária é um palidromo inserindo um "y" após o caracter mais a direita da string em caso afirmativo ou um "n" em caso negativo. Por exemplo, enquanto a input "1001" seria classificada como sendo um palíndromo gerando assim a string "1001y", o input "1011" seria classificada como sendo NÃO palíndromo e por esse motivo geraria a string "1011n". O arquivo [palidrome.json](https://github.com/DaviPrograme/ft_turing/blob/main/machines/palidrome.json) realiza o mesmo procedimento, só que recebendo como input palavras compostas por caracteres de "a" a "z" (em minúsculo apenas).

Exemplo de execução:
```bash
lein run ./machines/palidrome_simple.json "1001"
```

ou 

```bash
lein run ./machines/palidrome.json "level"
```

![mapa de estados do palidrome_simple](https://github.com/DaviPrograme/ft_turing/blob/main/palidrome_simple.png)


### 3) 0n1n

O objetivo da maquina [0n1n](https://github.com/DaviPrograme/ft_turing/blob/main/machines/0n1n.json) é determinar se um input faz parte da familia 0n1n e inserindo o caracter "y" após o caracter mais a direita do input em caso afirmativo ou inserindo um "n" em caso negativo. Por exemplo, o input "000111" seria classificado como fazendo parte da familia e por isso geraria a string "000111y", já o input "00011" NÃO seria classificado como sendo da familia e por este motivo geraria a string "00011n".

Exemplo de execução:
```bash
lein run ./machines/0n1n.json "000111"
```

![mapa de estados do 0n1n](https://github.com/DaviPrograme/ft_turing/blob/main/0n1n.png)


### 4) languageZero2n

O objetivo da maquina [languageZero2n](https://github.com/DaviPrograme/ft_turing/blob/main/machines/languageZero2n.json) é determinar se um input tem a quantidade de zeros par e inserindo o caracter "y" após o caracter mais a direita do input em caso afirmativo ou inserindo um "n" em caso negativo. Por exemplo, os inputs "00" e "0000" seriam classificados como fazendo parte da familia e por isso gerariam as strings "00y" e "0000y" respectivamente, já os inputs "000" e "00000" NÃO seriam classificados como sendo da familia e por este motivo gerariam as strings "000n" e "00000n" respectivamente.

Exemplo de execução:
```bash
lein run ./machines/languageZero2n.json "0000"
```

![mapa de estados do 0n1n](https://github.com/DaviPrograme/ft_turing/blob/main/0n1n.png)



