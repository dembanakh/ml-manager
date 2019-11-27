# ml-manager
Project for Java course.

# Description
Nowadays, many widely-known companies, like Google or Netflix, have a lot of tasks involving machine learning running all the time (like Netflix's recommendation system or Google photos search engine) and having all the neural networks for that being prepared, tuned and properly working is a rather hard managing task. This project is a prototype of a command-line manager for maintaining a bunch of tasks each with a dataset that it is trained on and a neural network architecture that it is trained with. It allows for an easy execution of train or test process at the server as well.

# Usage
To execute client, 
```shell
mvn clean && mvn install && java -Djava.security.policy=client.policy -cp target/classes main.Main
```
in shell, having installed and in the path maven and java.
You will be provided with instructions in the command line while ml-manager is running.

# Implementation
Project uses JavaRMI to communicate with the server, JavaNativeInterface to train/test tasks and MongoDB API to connect with a database, containing task list. Besides Java and C++, project uses Python's Tensorflow to take advantage of Python's ML nature. Remote operations (involving the change of tasks Map) are safe and synchronized.
