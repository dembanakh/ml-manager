mvn clean
mvn install

rmiregistry -J-Djava.rmi.server.codebase="file:///home/dembanakh/ml-manager/target/classes/" &

java -cp target/classes/ -Djava.rmi.useLocalHostname=false -Djava.rmi.server.hostname=40.87.143.114  -Djava.security,policy=server.policy main.ServerMain
