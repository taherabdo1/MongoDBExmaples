package secondExample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

public class Driver {

	public static void main(String[] args) throws UnknownHostException {

		DB db = (new MongoClient("localhost", 27017)).getDB("zadb");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		boolean flag = false;
		while (!flag) {
			authenticate(db, bufferedReader);
		}

	}

	private static boolean authenticate(DB db, BufferedReader bufferedReader) {
		boolean flag = true;
		String userName = "";
		String password = "";

		try {
			System.out.println("user:");
			userName = bufferedReader.readLine();
			System.out.println("password:");
			password = bufferedReader.readLine();
			if (db.authenticate(userName, password.toCharArray())) {
				DBCollection collection = db.getCollection("channel");
				String command = "";
				while (true) {
					System.out.println("What do you want to do: ");
					command = bufferedReader.readLine();
					if (command.equals("exit")) {
						break;
					} else if (command.equalsIgnoreCase("findAll")) {
						findAll(collection);
					} else if (command.equalsIgnoreCase("insertJson")) {
						insertJson(bufferedReader, collection);
					} else if (command.equalsIgnoreCase("insert")) {
						insert(bufferedReader, collection);
					} else if (command.equalsIgnoreCase("update")) {
						update(bufferedReader, collection);
					} else if (command.equalsIgnoreCase("delete")) {
						delete(bufferedReader, collection);
					}

				}
			} else {

				System.out.println("invalid userName or password..");
				flag = false;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return flag;
	}

	private static void delete(BufferedReader bufferedReader, DBCollection collection) {
		try {
			System.out.println("Delete What: ");
			BasicDBObject basicDBObject = new BasicDBObject();
			basicDBObject.put("name", bufferedReader.readLine());
			collection.remove(basicDBObject);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void update(BufferedReader bufferedReader, DBCollection collection) {
		try {
			System.out.println("update from name: ");
			BasicDBObject FromBasicDBObject = new BasicDBObject();
			FromBasicDBObject.put("name", bufferedReader.readLine());

			System.out.println("new name: ");
			BasicDBObject toBasicDBObject = new BasicDBObject();
			toBasicDBObject.put("name", bufferedReader.readLine());

			DBObject updateDBObject = new BasicDBObject();
			updateDBObject.put("$set", toBasicDBObject);
			collection.update(FromBasicDBObject, updateDBObject);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void insert(BufferedReader bufferedReader, DBCollection collection) {
		String name = "";
		int subscriptions = 0;
		try {
			System.out.println("Name: ");
			name = bufferedReader.readLine();
			BasicDBObject basicDBObject = new BasicDBObject();
			basicDBObject.put("name", name);
			System.out.println("number of subscriptions: ");
			subscriptions = Integer.parseInt(bufferedReader.readLine());
			basicDBObject.put("subscriptions", subscriptions);
			collection.insert(basicDBObject);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void findAll(DBCollection collection) {
		DBCursor cursor = collection.find();
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
	}

	private static void insertJson(BufferedReader bufferedReader, DBCollection collection) {
		try {
			System.out.println("JSON: ");
			collection.insert((DBObject) JSON.parse(bufferedReader.readLine()));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
