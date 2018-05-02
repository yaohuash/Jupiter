package external;

import java.io.BufferedReader;
import entity.Item;
import entity.Item.ItemBuilder;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class TicketMasterAPI {
	private static final String URL = "https://app.ticketmaster.com/discovery/v2/events.json";
	private static final String DEFAULT_KEYWORD = ""; // no restriction
	private static final String API_KEY = "GAoCmeJdGhH7C1ZBPMMFcqtYVAygGhyK";
	
    public JSONArray search(double lat, double lon, String keyword) {
    	if (keyword == null) {
    		keyword = DEFAULT_KEYWORD;
    	}
    	
    	try {
    		keyword = java.net.URLEncoder.encode(keyword, "UTF-8"); // Rick Sun => Rick20%Sun
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	String geoHash = GeoHash.encodeGeohash(lat, lon, 9);
    	
    	String query = String.format("apikey=%s&geoPoint=%s&keyword=%s&radius=%s", API_KEY, geoHash, keyword, "50");
    	
    	try {
    		HttpURLConnection connection = (HttpURLConnection)new URL(URL + "?" + query).openConnection();
    		connection.setRequestMethod("GET");
    		
    		int responseCode = connection.getResponseCode();
    		System.out.println(responseCode);
    		
    		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    		String inputLine;
    		StringBuilder response = new StringBuilder();
    		
    		while ((inputLine = in.readLine()) != null) {
    			response.append(inputLine);
    		}
    		in.close();
    		
    		JSONObject obj = new JSONObject(response.toString());
    		if (obj.isNull("_embedded")) {
    			return new JSONArray();
    		}
    		JSONObject embedded = obj.getJSONObject("_embedded");
    		if (embedded.isNull("events")) {
    			return new JSONArray();
    		} else {
        		JSONArray array = embedded.getJSONArray("events");
        		return array;    			
    		}
    		
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    	return new JSONArray();
    }
    
	private void queryAPI(double lat, double lon) {
		JSONArray events = search(lat, lon, null);
		try {
		    for (int i = 0; i < events.length(); i++) {
		        JSONObject event = events.getJSONObject(i);
		        System.out.println(event);
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Main entry for sample TicketMaster API requests.
	 */
	public static void main(String[] args) {
		TicketMasterAPI tmApi = new TicketMasterAPI();
		// Mountain View, CA
		// tmApi.queryAPI(37.38, -122.08);
		// London, UK
		// tmApi.queryAPI(51.503364, -0.12);
		// Houston, TX
		tmApi.queryAPI(29.682684, -95.295410);
		
	}
	/**
	 * Helper methods
	 */

	//  {
	//    "name": "laioffer",
              //    "id": "12345",
              //    "url": "www.laioffer.com",
	//    ...
	//    "_embedded": {
	//	    "venues": [
	//	        {
	//		        "address": {
	//		           "line1": "101 First St,",
	//		           "line2": "Suite 101",
	//		           "line3": "...",
	//		        },
	//		        "city": {
	//		        	"name": "San Francisco"
	//		        }
	//		        ...
	//	        },
	//	        ...
	//	    ]
	//    }
	//    ...
	//  }
	private String getAddress(JSONObject event) throws JSONException {
		return "";
	}


	// {"images": [{"url": "www.example.com/my_image.jpg"}, ...]}
	private String getImageUrl(JSONObject event) throws JSONException {
		return "";
	}

	// {"classifications" : [{"segment": {"name": "music"}}, ...]}
	private Set<String> getCategories(JSONObject event) throws JSONException {
		Set<String> categories = new HashSet<>();

		return categories;
	}

	// Convert JSONArray to a list of item objects.
	private List<Item> getItemList(JSONArray events) throws JSONException {
		List<Item> itemList = new ArrayList<>();
		
		for (int i = 0; i < events.length(); i++) {
			JSONObject event = events.getJSONObject(index)
		}
		return itemList;
	}
	
	private void queryAPI(double lat, double lon) {
		List<Item> itemList = search(lat, lon, null);
		try {
			for (Item item : itemList) {
				JSONObject jsonObject = item.toJSONObject();
				System.out.println(jsonObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}

