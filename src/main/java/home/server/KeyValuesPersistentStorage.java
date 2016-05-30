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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class KeyValuesPersistentStorage {
	private static File storage;
	
	public void createStorage() {
		storage = new File("storage.dat");
		try {
			storage.createNewFile();
		} catch (IOException e) {
			System.err.println("couldn't create new storage file. reason: " + e.getMessage());
		}
	}
	
	public void destoryStorage() {
		storage = new File("storage.dat");
		try (PrintWriter pw = new PrintWriter(storage)) {
		} catch (FileNotFoundException e) {
			System.err.println("couldn't find storage file. reason: " + e.getMessage());
		}
	}
	
	
	public Set<KeyValues> readAllFromStorage() {
		Map<String, KeyValues> keyValuesMap = new HashMap<String, KeyValues>();
		try (BufferedReader br = new BufferedReader(new FileReader(storage))) {
			String line;
			ObjectMapper mapper = new ObjectMapper();
		    while ((line = br.readLine()) != null) {
		    	KeyValues keyValues = mapper.readValue(line, KeyValues.class);
		    	keyValuesMap.put(keyValues.getKey(), keyValues);
		    }
		} catch (FileNotFoundException e) {
			System.err.println("couldn't find storage file. reason: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("couldn't read from storage file. reason: " + e.getMessage());
		}
		return new HashSet<KeyValues>(keyValuesMap.values());
	}
	
	public KeyValues readFromStorage(String key) {
		KeyValues result = null;
		try (BufferedReader br = new BufferedReader(new FileReader(storage))){
			String line;
			ObjectMapper mapper = new ObjectMapper();
		    while ((line = br.readLine()) != null) {
		    	KeyValues keyValues = mapper.readValue(line, KeyValues.class);
		    	if(keyValues.getKey().equals(key)) {
		    		result = keyValues;
		    	}
		    }
		} catch (FileNotFoundException e) {
			System.err.println("couldn't find storage file. reason: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("couldn't read from storage file. reason: " + e.getMessage());
		} 
		return result;
	}
	
	public void insertToStorage(KeyValues keyValues) {
		ObjectMapper mapper = new ObjectMapper();
		try (FileWriter fw = new FileWriter(storage, true);
			BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter out = new PrintWriter(bw)){
			
			String json = mapper.writeValueAsString(keyValues);
		    out.println(json);
			
		} catch (JsonProcessingException e) {
			System.err.println("couldn't process json. reason: " + e.getMessage() );
		} catch (IOException e) {
			System.err.println("couldn't write to storage file. reason: " + e.getMessage());
		}
	}
}
