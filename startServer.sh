mvn clean
mvn install

rmiregistry -J-Djava.rmi.server.codebase="file:///home/dembanakh/ml-manager/target/classes/" &

java -cp target/classes/ -Djava.security,policy=server.policy main.ServerMain
