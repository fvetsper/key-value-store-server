package home.server;

import home.commons.KeyValues;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class KeyValuesPersistentStorage {
	private static File storage;
	
	public void createStorage() {
		storage = new File("storage.dat");
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(storage);
			writer.close();
		} catch (FileNotFoundException e) {
		}
	}
	
	
	public KeyValues readFromStorage(String key) {
		BufferedReader br = null;
		KeyValues result = null;
		try {
			br = new BufferedReader(new FileReader(storage));
			String line;
			ObjectMapper mapper = new ObjectMapper();
		    while ((line = br.readLine()) != null) {
		    	KeyValues keyValues = mapper.readValue(line, KeyValues.class);
		    	if(keyValues.getKey().equals(key)) {
		    		result = keyValues;
		    	}
		    }
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {
			
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		return result;
	}
	
	public void insertToStorage(KeyValues keyValues) {
		ObjectMapper mapper = new ObjectMapper();
		PrintWriter out = null;
		try {
			
			String json = mapper.writeValueAsString(keyValues);
			
			FileWriter fw = new FileWriter(storage, true);
			BufferedWriter bw = new BufferedWriter(fw);
		    out = new PrintWriter(bw);
		    
		    out.println(json);
			
		} catch (JsonGenerationException e) {
		} catch (JsonMappingException e) {
		} catch (IOException e) {
		}
		finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	public void updateStorage(KeyValues keyValues) {
		
	}
	
	public void upsertDb(KeyValues keyValues) {
		
	}
	
}
