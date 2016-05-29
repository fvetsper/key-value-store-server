# key-value-store-server

### Installation

```sh
mvn clean install
```

### Usage

```sh
usage : java -jar server-jar-with-dependencies.jar <port number>
```

### Implementation

* use of java sockets to accept new connections.
* protocol accept json requests and return json responses
* each connection asssigned to new thread to support multiple users
* persistent data stored using java file API in simple file of json format
* cache implemented by simple java map


