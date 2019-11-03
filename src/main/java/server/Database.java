package server;

import com.mongodb.*;

import java.net.UnknownHostException;

class Database {

    private MongoClient client;
    private DB database;

    Database() {
        try {
            client = new MongoClient();
            database = client.getDB("ml-manager");
            init();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        DBCollection datasets = database.getCollection("datasets");
        DBObject mnist = new BasicDBObject("_id", "mnist")
                .append("name", "MNIST");
        DBObject coco = new BasicDBObject("_id", "coco")
                .append("name", "COCO");
        datasets.insert(mnist, coco);
        //Testing
        DBCollection tasks = database.getCollection("tasks");
        DBObject test = Database.toDBObject(new Task("test", "IMAGENET", "MobileNet"));
        tasks.insert(test);
    }

    DBCursor getCursor(String coll) {
        return database.getCollection(coll).find();
    }

    void addTask(Task task) {
        DBCollection tasks = database.getCollection("tasks");
        tasks.insert(Database.toDBObject(task));
    }

    void deleteTask(String id) {
        DBCollection tasks = database.getCollection("tasks");
        tasks.remove(new BasicDBObject("title", id));
    }

    void changeTask_dataset(String id, String dataset) {
        DBCollection tasks = database.getCollection("tasks");
        tasks.findAndModify(new BasicDBObject("title", id), new BasicDBObject("dataset", dataset));
    }

    void changeTask_neuralNet(String id, String net) {
        DBCollection tasks = database.getCollection("tasks");
        tasks.findAndModify(new BasicDBObject("title", id), new BasicDBObject("net", net));
    }

    private static DBObject toDBObject(Task task) {
        return new BasicDBObject("_id", task.getTitle())
                .append("title", task.getTitle())
                .append("dataset", task.getDataset().getName())
                .append("net", task.getNeuralNet().toString());
    }

}