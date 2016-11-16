package com.rest.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GoogleMapService {
	public static String getAddressForLatLog(Double lat, Double log) {
		String latlog=lat.toString()+","+log.toString();
		String address="";
		List<Object> providers = new ArrayList<Object>(1);
		providers.add(new JacksonJsonProvider());
		WebClient webclient = WebClient.create("http://maps.googleapis.com/maps/api/geocode/json?latlng="+latlog+"&sensor=true", providers);
		Response response = webclient.accept(MediaType.APPLICATION_JSON).get();
		if(response.getStatus() == 200) {
			String s = "";				
				try {
					InputStream is = (InputStream)response.getEntity();
					s = IOUtils.toString(is);
	                JSONObject json = (JSONObject)new JSONParser().parse(s);
	                JSONArray a=(JSONArray)json.get("results");
	                JSONObject o=(JSONObject)a.get(0);
	                address=(String)o.get("formatted_address");
                } catch(ParseException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
                } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			
			
			finally{
				
				webclient.close();
			}

		}
		return address;
	}

}
