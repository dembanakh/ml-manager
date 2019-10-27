mvn clean
mvn install

mkdir target/cpp
./compileNatives.sh

rmiregistry -J-Djava.rmi.server.codebase="file:///home/dembanakh/ml-manager/target/classes/" &

java -cp target/classes/ -Djava.library.path=/$HOME/ml-manager/target/cpp -Djava.rmi.useLocalHostname=false -Djava.rmi.server.hostname=40.87.143.114  -Djava.security,policy=server.policy main.ServerMain
