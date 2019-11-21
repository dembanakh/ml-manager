#/bin/bash
mvn clean
mvn install

mkdir target/cpp
./compileNatives.sh

rmiregistry -J-Djava.rmi.server.codebase="file:///home/dembanakh/ml-manager/target/classes/" 2>/dev/null &

export LD_PRELOAD=/usr/lib/python2.7/config-x86_64-linux-gnu/libpython2.7.so
mvn exec:exec
# mvn exec:java -Dexec.mainClass="main.ServerMain" -Dexec.args="-Djava.library.path=/home/dembanakh/ml-manager/target/cpp -Djava.rmi.useLocalHostname=false -Djava.rmi.server.hostname=40.87.143.114  -Djava.security,policy=server.policy"
