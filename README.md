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
