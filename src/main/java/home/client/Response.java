package home.client;

import home.commons.KeyValues;

public class Response {
	private KeyValues keyValues;

	public KeyValues getKeyValues() {
		return keyValues;
	}

	public void setKeyValues(KeyValues keyValues) {
		this.keyValues = keyValues;
	}

	public void print() {
		System.out.println("key = "  + keyValues.getValues() + 
				"values = " + keyValues.getValues());
	}
}
