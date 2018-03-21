package com.common.utils.mongo;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class MongoDBJDBC {

//     public static void main(String args[]) {
//         try {
//             mongoConnect();

//         } catch (Exception e) {
//             System.err.println(e.getClass().getName() + ": " + e.getMessage());
//         }
//     }

    private static void mongoConnect() {
        // 连接到 mongodb 服务
        MongoClient mongoClient = new MongoClient("192.168.1.180", 28001);

        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("tianhui");
        System.err.println("Connect to database successfully");

        //创建集合
//            mongoDatabase.createCollection("haitao");

        //获取集合
        MongoCollection<Document> collection = mongoDatabase.getCollection("cen");
         System.err.println( collection.count());

         //插入文档
         insertDocument(collection);

         //检索所有文档
         getAllDocument(collection);

         //更新文档   将文档中likes=100的文档修改为likes=200
         collection.updateMany(Filters.eq("likes", 100), new Document("$set",new Document("likes",200)));

         //删除符合条件的第一个文档
         collection.deleteOne(Filters.eq("likes", 200));

         //删除所有符合条件的文档
         collection.deleteMany (Filters.eq("likes", 200));
    }

    private static void getAllDocument(MongoCollection<Document> collection) {
        /**
         * 1. 获取迭代器FindIterable<Document>
         * 2. 获取游标MongoCursor<Document>
         * 3. 通过游标遍历检索出的文档集合
         * */
         FindIterable<Document> findIterable = collection.find();
         MongoCursor<Document> mongoCursor = findIterable.iterator();
         while(mongoCursor.hasNext()){
            System.err.println(mongoCursor.next());
         }

    }

    private static void insertDocument(MongoCollection<Document> collection) {
        /**
         * 1. 创建文档 org.bson.Document 参数为key-value的格式
         * 2. 创建文档集合List<Document>
         * 3. 将文档集合插入数据库集合中 mongoCollection.insertMany(List<Document>) 插入单个文档可以用 mongoCollection.insertOne(Document)
         * */
         Document document = new Document("title", "MongoDB").
         append("description", "database").
         append("likes", 100).
         append("by", "Fly");
         List<Document> documents = new ArrayList<Document>();
         documents.add(document);
         collection.insertMany(documents);

    }
}
