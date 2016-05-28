package home.server;

import home.commons.Action;
import home.commons.KeyValues;
import home.commons.Request;
import home.commons.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class KeyValuesHandler {
	
	private KeyValuesStore keyValuesStore;
	
	public KeyValuesHandler() {
		keyValuesStore = new KeyValuesStore();
	}
	
	public Response process(Request request) {
		Response response = new Response();
		Action action = request.getAction();
		switch (action) {
			case GET_ALL_KEYS: {
				List<KeyValues> filteredKeyValuesList = new ArrayList<KeyValues>();
				String pattern = request.getPattern();
				Set<KeyValues> keyValuesList = keyValuesStore.readAll();
				for (KeyValues keyValues : keyValuesList) {
					if(keyValues.getKey().contains(pattern)) {
						filteredKeyValuesList.add(keyValues);
					}
				}
				response.setKeyValuesList(filteredKeyValuesList);
				break;
			}
			case RIGHT_ADD: {
				String key = request.getKey();
				String value = request.getValue();
				KeyValues keyValues = add(key, value, true);
				if (keyValues == null) {
					response = new Response(true, "key " + key + " not found");
				} else {
					response.addKeyValues(keyValues);
				}
				break;
			}
			case LEFT_ADD: {
				String key = request.getKey();
				String value = request.getValue();
				KeyValues keyValues = add(key, value, false);
				if (keyValues == null) {
					response = new Response(true, "key " + key + " not found");
				} else {
					response.addKeyValues(keyValues);
				}
				break;
			}
			case GET: {
				String key = request.getKey();
				KeyValues keyValues = keyValuesStore.read(key);
				if (keyValues == null) {
					response = new Response(true, "key " + key + " not found");
				} else {
					response.addKeyValues(keyValues);
				}
				break;
			}
			case SET: {
				String key = request.getKey();
				List<String> values = request.getValues();
				KeyValues keyValues = new KeyValues(key, values);
				keyValuesStore.write(keyValues);
				response.addKeyValues(keyValues);
				break;
			}
			default:
				break;
		}
		
		
		return response;
	}

	private KeyValues add(String key, String value, boolean addFromRight) {
		KeyValues keyValues = keyValuesStore.read(key);
		if (keyValues != null) {
			if (addFromRight) {
				keyValues.getValues().add(value);
			} else {
				keyValues.getValues().add(0, value);
			}
			keyValuesStore.write(keyValues);
		}
		return keyValues;
	}
}
