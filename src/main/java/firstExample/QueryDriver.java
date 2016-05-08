package firstExample;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class QueryDriver {

	public static void main(String[] args) throws UnknownHostException {


		DB db = (new MongoClient("localhost" , 27017)).getDB("zaneAcademyDatabase");
		DBCollection collection = db.getCollection("channel");
		BasicDBObject basicDBObject = new BasicDBObject();
		basicDBObject.put("name", "zaneAcademy");
		DBCursor cursor = collection.find();
		while(cursor.hasNext()){
			System.out.println(cursor.next());
		}
	}

}
