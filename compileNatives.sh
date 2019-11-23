javac -h src/main/cpp/ src/main/java/utility/Dataset.java src/main/java/utility/NeuralNet.java src/main/java/utility/Utility.java src/main/java/utility/Batch.java src/main/java/utility/ImageBatch.java src/main/java/server/MLManager.java
g++ -c -fPIC -I/usr/lib/jvm/java-8-openjdk-amd64/include -I/usr/lib/jvm/java-8-openjdk-amd64/include/linux -I/usr/include/python2.7 -I/home/dembanakh/.local/lib/python2.7/site-packages/numpy/core/include src/main/cpp/server_MLManager.cpp -o target/cpp/server_MLManager.o -lpython2.7
#g++ -shared -fPIC -o target/cpp/libnative.so target/cpp/server_MLManager.o -lc
g++ -shared -o target/cpp/libnative.so -fPIC -I/usr/include/python2.7 target/cpp/server_MLManager.o -lc -lpython2.7

cp src/main/python/py_train.py target/cpp
cp src/main/python/py_test_local.py target/cpp
cp src/main/python/py_test_remote.py target/cpp
