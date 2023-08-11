FROM clojure

RUN mkdir /ft-turing
COPY . /ft-turing
WORKDIR /ft-turing