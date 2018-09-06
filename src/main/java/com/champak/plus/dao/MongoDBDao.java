package com.champak.plus.dao;

import com.champak.plus.util.MyMongoCollections;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.bson.json.JsonParseException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MongoDBDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public boolean insert(MyMongoCollections myMongoCollection, String jsonString) {
        DBObject dbObject = (DBObject) JSON.parse(jsonString);
        String collectionName = String.valueOf(myMongoCollection);
        mongoTemplate.insert(dbObject, collectionName);
        return true;
    }

    public boolean update(MyMongoCollections myMongoCollection, String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(jsonString);
        String _id = null;
        if(actualObj.has("_id") && actualObj.get("_id").has("$oid"))
            _id = actualObj.get("_id").get("$oid").asText();
        else
            throw new JsonParseException("_id is missing or invalid _id is present");

        ObjectNode objectNode = (ObjectNode) actualObj;

        if(actualObj.has("_class"))
            objectNode.remove("_class");

        objectNode.remove("_id");

        DBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(_id));

        DBCollection collection = mongoTemplate.getDb().getCollection(String.valueOf(myMongoCollection));
        DBObject dbObject = (DBObject) JSON.parse(objectNode.toString());
        collection.update(query,dbObject);
        return true;
    }

    public JsonNode findAll(MyMongoCollections myMongoCollection) throws IOException {
        DBCollection collection = mongoTemplate.getDb().getCollection(String.valueOf(myMongoCollection));
        JSON json = new JSON();
        String data = json.serialize(collection.find());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(data);
    }

    public JsonNode findOne(MyMongoCollections myMongoCollection, DBObject query) throws IOException {
        DBCollection collection = mongoTemplate.getDb().getCollection(String.valueOf(myMongoCollection));
        JSON json = new JSON();
        String data = json.serialize(collection.findOne(query));
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(data);
    }

    public JsonNode find(MyMongoCollections myMongoCollection, DBObject query) throws IOException {
        DBCollection collection = mongoTemplate.getDb().getCollection(String.valueOf(myMongoCollection));
        JSON json = new JSON();
        String data = json.serialize(collection.find(query));
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(data);
    }

    public JsonNode delete(MyMongoCollections myMongoCollection, DBObject query) throws IOException {
        DBCollection collection = mongoTemplate.getDb().getCollection(String.valueOf(myMongoCollection));
        JSON json = new JSON();
        String data = json.serialize(collection.findAndRemove(query));
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(data);
    }

}
