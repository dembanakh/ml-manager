javac -h src/main/cpp/ src/main/java/utility/Dataset.java src/main/java/utility/NeuralNet.java src/main/java/utility/Utility.java src/main/java/server/MLManager.java
g++ -c -fPIC -I/usr/lib/jvm/java-8-openjdk-amd64/include -I/usr/lib/jvm/java-8-openjdk-amd64/include/linux src/main/cpp/server_MLManager.cpp -o target/cpp/server_MLManager.o
g++ -shared -fPIC -o target/cpp/libnative.so target/cpp/server_MLManager.o -lc
