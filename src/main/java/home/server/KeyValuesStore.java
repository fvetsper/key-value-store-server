package home.server;

import home.commons.KeyValues;

import java.util.HashMap;
import java.util.List;
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
	
	public List<KeyValues> readAll() {
		List<KeyValues> keyValuesList = keyValuesStorage.readAllFromStorage();
		for (KeyValues keyValues : keyValuesList) {
			keyValuesCache.put(keyValues.getKey(), keyValues);
		}
		return keyValuesList;
		
	}
	
	public void write(KeyValues keyValues) {
		keyValuesStorage.insertToStorage(keyValues);
		keyValuesCache.put(keyValues.getKey(), keyValues);
	}


	public void destroy() {
		keyValuesCache.clear();
		keyValuesStorage.destoryStorage();
	}
}
