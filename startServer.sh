mvn clean
mvn install

mkdir target/cpp
./compileNatives.sh

rmiregistry -J-Djava.rmi.server.codebase="file:///home/dembanakh/ml-manager/target/classes/" &

export LD_PRELOAD=/usr/lib/python2.7/config-x86_64-linux-gnu/libpython2.7.so
java -cp target/classes/ -Djava.library.path=/$HOME/ml-manager/target/cpp -Djava.rmi.useLocalHostname=false -Djava.rmi.server.hostname=40.87.143.114  -Djava.security,policy=server.policy main.ServerMain
