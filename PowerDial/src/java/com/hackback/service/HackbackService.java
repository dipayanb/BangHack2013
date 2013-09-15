package com.hackback.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.bson.types.ObjectId;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hackback.core.CrawlResult;
import com.hackback.core.MongoDBFactory;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class HackbackService {


//	query_id, query, city, [5 geo-locations]
	public String getCrawlList() {

		List<Map> list = new ArrayList<Map>();
		DBCollection coll = MongoDBFactory.getCollection("banghack","search_history");
		DBCollection coll2 = MongoDBFactory.getCollection("banghack","just_dial");
		DBObject doc = new BasicDBObject();
		doc.put("crawled", false);
		DBObject keys = new BasicDBObject();
		keys.put("_id", 1);
		keys.put("city", 1);
		keys.put("area", 1);
		keys.put("search_str", 1);
		keys.put("location", 1);
		DBCursor cursor = coll.find(doc, keys);
		while( cursor.hasNext() ) {
			DBObject object = cursor.next();
			if(object.get("city") != null) {
				ObjectId id = (ObjectId)object.get("_id");
				String query_id = id.toString();
				String city = object.get("city").toString();
				String area = object.get("area").toString();
				String search_str = object.get("search_str").toString();
				DBObject location = (DBObject)object.get("location");
				double lat = Double.parseDouble(location.get("lat").toString());
				double lng = Double.parseDouble(location.get("lng").toString());
				Map locMap = new LinkedHashMap();
				locMap.put("lng", lng);
				locMap.put("lat", lat);
				BasicDBObject locQuery = new BasicDBObject();
				locQuery.put("location", new BasicDBObject("$near", new Double[]{lng, lat}));

				keys = new BasicDBObject();
				keys.put("location", 1);
				// run the query
				DBCursor locCursor = coll2.find( locQuery, keys ).limit(5);
				// use cursor to view results
				List<Map> nearLocs = new ArrayList<Map>();
				while( locCursor.hasNext() ) {
					Map nearLocMap = new LinkedHashMap();
					DBObject loc = locCursor.next();
					loc = (DBObject)loc.get("location");
					double nearLat = Double.parseDouble(loc.get("lat").toString());
					double nearLng = Double.parseDouble(loc.get("lng").toString());
					nearLocMap.put("lng", nearLng);
					nearLocMap.put("lat", nearLat);
					nearLocs.add(nearLocMap);
				}

				Map map = new LinkedHashMap();
				map.put("query_id", query_id);
				map.put("search_str", search_str);
				map.put("city", city);
				map.put("area", area);
				map.put("location", locMap);
				map.put("nearby", nearLocs);

				list.add(map);
			}
		}

		Gson gson = new Gson();
		String json = gson.toJson(list);		
		return json;			
	}


    public void dumpCrawlData(CrawlResult cr){
    	DBCollection coll =  null;
    	if( "en".equalsIgnoreCase(cr.languageCode)) {
    		coll = MongoDBFactory.getCollection("banghack","just_dial");    		
    	} else {
    		coll = MongoDBFactory.getCollection("banghack","just_dial_hindi");    		
    	}
    	
    	String address_str = cr.address;
    	String avg_rating_str = cr.avgRating;
    	String total_ratings_str = cr.totalRatings;
    	String city_str = cr.city;
    	String companyname_str = cr.companyName;
    	String website_str = cr.website;
    	String email_str = cr.email;
    	String id_str = cr.id;
    	String just_dial_str = cr.justDialId;
    	String landline_str = cr.landLine;
    	String mobile_str = cr.mobile;
    	String language_code_str = cr.languageCode;
    	String lat_str = cr.lat;
    	String lng_str = cr.lng;
    	String query_id_str = cr.queryId;
    	String pincode_str = cr.pinCode;
    	String to_index = cr.toIndex;
    	
		// Create a new object
		DBObject findDoc = new BasicDBObject();
		// Put id to search
		findDoc.put("justdial_id", just_dial_str);
		DBObject keys = new BasicDBObject();
		keys.put("_id", 1);		
		
		DBCursor cursor = coll.find(findDoc, keys).limit(1);
		if( cursor.size() == 0 ) {
    	
			DBObject doc = new BasicDBObject();
	    	if( !"en".equalsIgnoreCase(cr.languageCode)) {
	    		doc.put("to_index", to_index);
	    	}
			doc.put("justdial_id", just_dial_str);
			doc.put("companyname", companyname_str);
			doc.put("address", address_str);
			doc.put("city", city_str);
			doc.put("pincode", pincode_str);
			doc.put("landline", landline_str);
			doc.put("mobile", mobile_str);
			if( avg_rating_str != null)
				doc.put("avg_rating", Float.parseFloat(avg_rating_str));
			if( total_ratings_str != null)
				doc.put("total_ratings", Float.parseFloat(total_ratings_str));
			doc.put("website", website_str);
			doc.put("email", email_str);
			String concatText = companyname_str + " - " +address_str;			
			doc.put("search_text", concatText);
			DBObject loc = new BasicDBObject();
			loc.put("lng", Double.parseDouble(lng_str));
			loc.put("lat", Double.parseDouble(lat_str));
			doc.put("location", loc);
			
			coll.save(doc);
		}
		
		coll = MongoDBFactory.getCollection("banghack","search_history");   
		findDoc = new BasicDBObject();
		findDoc.put("_id", new ObjectId(query_id_str));
		findDoc.put("results", just_dial_str);
		
		keys = new BasicDBObject();
		keys.put("_id", 1);		
		cursor = coll.find(findDoc, keys);
		if( cursor.size() == 0 ) {
			DBObject doc = new BasicDBObject();
			doc.put("results", cr.justDialId);			
			DBObject obj = new BasicDBObject("$push", doc);
			findDoc = new BasicDBObject();
			findDoc.put("_id", new ObjectId(query_id_str));
			coll.update( findDoc, obj);
		}
		
		DBObject doc =new BasicDBObject("$set", new BasicDBObject("crawled", true));		
		findDoc = new BasicDBObject();
		findDoc.put("_id", new ObjectId(query_id_str));
		coll.update( findDoc, doc);
    }


	public String substring_search( String searchKey, String city, String area, String latlng, String lang ) throws Exception {

		List<Map> list = new ArrayList();
		Map resultMap = new LinkedHashMap();
        String collection = "just_dial";
        String search_field = "search_text";
        if("hi".equals(lang)){
            collection += "_hindi";
            search_field = "to_index";
        }
		DBCollection coll = MongoDBFactory.getCollection("banghack",collection);
		if(searchKey.contains(" ")) {
			DBObject textSearchCommand = new BasicDBObject();
		    textSearchCommand.put("text", collection);
		    textSearchCommand.put("search", searchKey);
		    final CommandResult commandResult = coll.getDB().command(textSearchCommand);
		    BasicDBList objectList = (BasicDBList)commandResult.get("results");
		    Iterator<Object> it = objectList.iterator();
		    for( int i=0; i<10; i++) {
		    	if(!it.hasNext()) {
		    		break;
		    	}
		    	BasicDBObject res = (BasicDBObject) it.next();
		    	res = (BasicDBObject)res.get("obj");
				Map map = new LinkedHashMap();
				map.put("justdial_id", res.get("justdial_id"));
				map.put("companyname", res.get("companyname"));
				map.put("address", res.get("address"));
				map.put("city", res.get("city"));
				map.put("pincode", res.get("pincode"));
				map.put("landline", res.get("landline"));
				map.put("mobile", res.get("mobile"));
				map.put("email", res.get("email"));
				map.put("website", res.get("website"));
				map.put("avg_rating", res.get("avg_rating"));
				map.put("total_ratings", res.get("total_ratings"));
				Map location = new LinkedHashMap();
				DBObject loc = (BasicDBObject)res.get("location");
				location.put("lng", loc.get("lng"));
				location.put("lat", loc.get("lat"));
				map.put("location", location);
				list.add(map);
		    }
		} else {
			
			
			DBObject doc = new BasicDBObject();
			doc.put(search_field, java.util.regex.Pattern.compile(searchKey));
	
	        /* if( latlng != null ) {
	            String[] sArr = latlng.split(",");
	            doc.put("location", new BasicDBObject("$near", new Double[]{Double.parseDouble(sArr[0]), Double.parseDouble(sArr[1])}));
	        }   */
	
			DBObject orderBy = new BasicDBObject();
			orderBy.put("avg_rating", -1);
			orderBy.put("total_ratings", -1);
			DBCursor cursor = coll.find(doc).sort(orderBy).limit(10);
			while( cursor.hasNext() ) {
				DBObject res = cursor.next();
				Map map = new LinkedHashMap();
				map.put("justdial_id", res.get("justdial_id"));
				map.put("companyname", res.get("companyname"));
				map.put("address", res.get("address"));
				map.put("city", res.get("city"));
				map.put("pincode", res.get("pincode"));
				map.put("landline", res.get("landline"));
				map.put("mobile", res.get("mobile"));
				map.put("email", res.get("email"));
				map.put("website", res.get("website"));
				map.put("avg_rating", res.get("avg_rating"));
				map.put("total_ratings", res.get("total_ratings"));
				Map location = new LinkedHashMap();
				DBObject loc = (BasicDBObject)res.get("location");
				location.put("lng", loc.get("lng"));
				location.put("lat", loc.get("lat"));
				map.put("location", location);
				list.add(map);
			}
		}

		resultMap.put("results", list);
		Gson gson = new Gson();
		String json = gson.toJson(resultMap);
		return json;
	}

	public String invokeAPI( String mobile_num, String search_str, String city, String area, String location )
		throws Exception {

		List<Map> list = callAPI(mobile_num, search_str, city, area, location);
		Gson gson = new Gson();
		String json = gson.toJson(list);
		return json;
	}

	public List<Map> callAPI( String mobile_num, String search_str, String city, String area, String location )
		throws Exception {

		// Retrieve collection
		DBCollection coll = MongoDBFactory.getCollection("banghack","just_dial");
		String replaced_search_str = (search_str == null? null :search_str.replaceAll(" ", "+"));
		String replaced_city = (city == null? null : city.replaceAll(" ", "+"));
		String replaced_area = (area == null? null : area.replaceAll(" ", "+"));

		String url = "http://hack2013.justdial.com/index.php?event_token=R1nev3n7t0k3nd0m&token=vLKptNycB90Y2Lc&q="+replaced_search_str;

		if(city != null) {
			url = url + "&city="+replaced_city;
		}
		if(area !=null) {
			url = url + "&area=" +replaced_area;
		}
		if( location != null) {
			url = url + "&geocodes=" +location;
		}

		url = url +"&num_res=1000";
		System.out.println(url);


		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		HttpResponse response = client.execute(request);

		// Get the response
		BufferedReader rd = new BufferedReader
		  (new InputStreamReader(response.getEntity().getContent()));

		List<String> justdial_ids = new ArrayList<String>();
		List<Map> list = new ArrayList<Map>();
		String line = "";
		while ((line = rd.readLine()) != null) {
			Map<String, Map> map = new Gson().fromJson(line, Map.class);
			for( String key: map.keySet()) {
				Map linkedMap = map.get(key);
				justdial_ids.add(key);
				String companyname = linkedMap.get("companyname").toString().toLowerCase();
				String address = linkedMap.get("address").toString().toLowerCase();
				String cityName = linkedMap.get("city").toString().toLowerCase();
				String pincode = linkedMap.get("pincode").toString();
				String landline = linkedMap.get("landline").toString();
				String mobile = linkedMap.get("mobile").toString();
				String email = linkedMap.get("email").toString();
				String website = linkedMap.get("website").toString();
				String concatText = companyname + " - " +address;
				double latitude = Double.parseDouble(linkedMap.get("latitude").toString());
				double longitude = Double.parseDouble(linkedMap.get("longitude").toString());
				float avg_rating = (linkedMap.get("avg_rating") != null ? Float.parseFloat(linkedMap.get("avg_rating").toString()) : 3);
				float total_ratings = Float.parseFloat(linkedMap.get("total_ratings").toString());

				// Create a new object
				DBObject findDoc = new BasicDBObject();
				// Put id to search
				findDoc.put("companyname", companyname);

				// Find and return the person with the given id
				DBCursor cursor = coll.find(findDoc).limit(1);
				if( cursor.size() == 0 ) {

					// Create a new object
					DBObject doc = new BasicDBObject();
					// Put id to search
					doc.put("justdial_id", key);
					doc.put("companyname", companyname);
					doc.put("address", address);
					doc.put("city", cityName);
					doc.put("pincode", pincode);
					doc.put("landline", landline);
					doc.put("mobile", mobile);
					doc.put("avg_rating", avg_rating);
					doc.put("total_ratings", total_ratings);
					doc.put("website", website);
					doc.put("email", email);
					doc.put("search_text", concatText);
					DBObject loc = new BasicDBObject();
					loc.put("lng", longitude);
					loc.put("lat", latitude);
					doc.put("location", loc);

					coll.save(doc);
				}

				Map resultMap = new LinkedHashMap();
				resultMap.put("justdial_id", key);
				resultMap.put("companyname", companyname);
				resultMap.put("address", address);
				resultMap.put("city", city);
				resultMap.put("pincode", pincode);
				resultMap.put("landline", landline);
				resultMap.put("mobile", mobile);
				resultMap.put("email", email);
				resultMap.put("website", website);
				resultMap.put("avg_rating", avg_rating);
				resultMap.put("total_ratings", total_ratings);
				Map locationMap = new LinkedHashMap();
				locationMap.put("lng", longitude);
				locationMap.put("lat", latitude);
				resultMap.put("location", locationMap);
				list.add(resultMap);
			}
		}

		return list;
	}

	/**
	 * Retrieves a single Movie
	 */
	public String search( String mobile_num, String search_str, String city, String area, String location)
		throws Exception {

		Map resultMap = new LinkedHashMap();

		// Retrieve collection
		List<Map> list = callAPI(mobile_num, search_str, city, area, location);
		List<String> justdial_ids = new ArrayList<String>();

		for(Map m: list) {
			justdial_ids.add(m.get("justdial_id").toString());
		}

		String replaced_city = (city == null? null : city.replaceAll(" ", "+"));
		String replaced_area = (area == null? null : area.replaceAll(" ", "+"));

		String url = null;

		HttpClient client = new DefaultHttpClient();
		client = new DefaultHttpClient();

		DBCollection coll = MongoDBFactory.getCollection("banghack","search_history");
		DBObject doc = new BasicDBObject();
		doc.put("mobile_num", mobile_num);
		doc.put("search_str", search_str);
		doc.put("city", city);
		doc.put("area", area);
		doc.put("results", justdial_ids);
        url = "http://maps.googleapis.com/maps/api/geocode/json?address="+replaced_area+","+replaced_city+"&sensor=true";
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);

        // Get the response
        BufferedReader rd = new BufferedReader
          (new InputStreamReader(response.getEntity().getContent()));

        String line = "";
        StringBuffer sb = new StringBuffer();
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        JsonElement jelement = new JsonParser().parse(sb.toString());
        JsonObject  jobject = jelement.getAsJsonObject();
        jobject = jobject.getAsJsonArray("results").get(0).getAsJsonObject();
        JsonObject locObject = jobject.get("geometry").getAsJsonObject().get("location").getAsJsonObject();
        double lat = Double.parseDouble(locObject.get("lat").getAsString());
        double lng = Double.parseDouble(locObject.get("lng").getAsString());
        DBObject loc = new BasicDBObject();
        loc.put("lng", lng);
        loc.put("lat", lat);
        doc.put("location", loc);
        resultMap.put("lat", lat);
        resultMap.put("lng", lng);
		doc.put("crawled", false);
		doc.put("search_time", new Date());

		coll.save(doc);

		resultMap.put("results", list);
		Gson gson = new Gson();
		String json = gson.toJson(resultMap);
		return json;

	}

}
