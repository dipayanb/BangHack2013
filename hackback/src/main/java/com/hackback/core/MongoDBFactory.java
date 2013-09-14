package com.hackback.core;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoDBFactory {
        
        
        private static Mongo m;
        
        // Make sure no one can instantiate our factory
        private MongoDBFactory() {}
        
        // Return an instance of Mongo
        public static Mongo getMongo() {
                if (m == null) {
                        try {
                                m = new Mongo( "localhost" , 27017 );
                        } catch (UnknownHostException e) {
                                e.printStackTrace();
                        } catch (MongoException e) {
                                e.printStackTrace();
                        }
                }
                
                return m;
        }
        
        // Retrieve a db
        public static DB getDB(String dbname) {
                
                return getMongo().getDB(dbname);
        }
        
        // Retrieve a collection
        public static DBCollection getCollection(String dbname, String collection) {
                
                return getDB(dbname).getCollection(collection);
        }

}