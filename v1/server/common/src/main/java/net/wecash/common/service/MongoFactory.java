package net.wecash.common.service;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

/**
 * @author franklin.li
 */
public class MongoFactory {
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(MongoFactory.class);
    private String username;
    private String password;
    private String db;
    private Mongo mongo;
    private UserCredentials credentials;
    @SuppressWarnings("unused")
	private String adminDbName = "admin";
    private ConcurrentMap<String, MongoTemplate> templates = new ConcurrentHashMap<String, MongoTemplate>();

    public MongoFactory(Mongo mongo, String db, String username, String password) throws JsonParseException, JsonMappingException,
            JsonProcessingException, IOException {
        this.mongo = mongo;
        this.db = db;
        this.username = username;
        this.password = password;
        this.credentials = new UserCredentials(username, password);
    }

    public DBCollection getCollectionByDbName(String dbName, String collection) {
        return this.getDBByName(dbName).getCollection(collection);
    }

    public DB getDBByName(String dbName) {
        DB db = this.mongo.getDB(dbName);
        db.authenticate(username, password.toCharArray());
        return db;
    }

    public Mongo getMongo() {
        return this.mongo;
    }
    
    public MongoTemplate getDefaultMongoTemplate() {
    	return getMongoTemplateByDBName(db);
    }

    public MongoTemplate getMongoTemplateByDBName(String dbName) {
        MongoTemplate template = templates.get(dbName);
        if (template != null) {
            return template;
        } else {
            SimpleMongoDbFactory dbFactory = new SimpleMongoDbFactory(mongo, dbName, credentials);
            MappingMongoConverter converter = new MappingMongoConverter(dbFactory, new MongoMappingContext());
            converter.setTypeMapper(new DefaultMongoTypeMapper(null));
            template = new MongoTemplate(dbFactory, converter);
            MongoTemplate temp = templates.putIfAbsent(dbName, template);
            if (temp == null) {
                return template;
            } else {
                return temp;
            }
        }
    }
}
