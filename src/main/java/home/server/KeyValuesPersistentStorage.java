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
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
		PrintWriter pw;
		try {
			pw = new PrintWriter(storage);
			pw.close();
		} catch (FileNotFoundException e) {
			System.err.println("couldn't find storage file. reason: " + e.getMessage());
		}
	}
	
	
	public List<KeyValues> readAllFromStorage() {
		List<KeyValues> keyValuesList = new ArrayList<KeyValues>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(storage));
			String line;
			ObjectMapper mapper = new ObjectMapper();
		    while ((line = br.readLine()) != null) {
		    	KeyValues keyValues = mapper.readValue(line, KeyValues.class);
		    	keyValuesList.add(keyValues);
		    }
		    if (br != null) {
		    	br.close();
		    }
		} catch (FileNotFoundException e) {
			System.err.println("couldn't find storage file. reason: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("couldn't read from storage file. reason: " + e.getMessage());
		}
		return keyValuesList;
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
		    if (br != null) {
		    	br.close();
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
		PrintWriter out = null;
		try {
			String json = mapper.writeValueAsString(keyValues);
			
			FileWriter fw = new FileWriter(storage, true);
			BufferedWriter bw = new BufferedWriter(fw);
		    out = new PrintWriter(bw);
		    
		    out.println(json);
			
		} catch (JsonProcessingException e) {
			System.err.println("couldn't process json. reason: " + e.getMessage() );
		} catch (IOException e) {
			System.err.println("couldn't write to storage file. reason: " + e.getMessage());
		}
		finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
