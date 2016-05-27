package home.server;

import home.commons.KeyValues;

import java.util.HashMap;
import java.util.Map;

public class KeyValuesStore {
	
	private Map<String, KeyValues> keyValuesCache;
	private KeyValuesPersistentStorage keyValuesStorage;
	
	public KeyValuesStore() {
		keyValuesCache = new HashMap<>();
		keyValuesStorage = new KeyValuesPersistentStorage();
		keyValuesStorage.createStorage();
	}
	
	public KeyValues read(String key) {
		
		if (keyValuesCache.containsKey(key)) {
			return keyValuesCache.get(key);
		} 
		KeyValues keyValues = keyValuesStorage.readFromStorage(key);
		if (keyValues != null) {
			keyValuesCache.put(key, keyValues);
		}
		return keyValues;
		
	}
	
	public void write(KeyValues keyValues) {
		keyValuesStorage.insertToStorage(keyValues);
		keyValuesCache.put(keyValues.getKey(), keyValues);
	}
}
