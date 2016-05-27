package home.server;

import home.commons.KeyValues;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class KeyValuesStorageTest {
	private KeyValuesPersistentStorage keyValuesStorage = new KeyValuesPersistentStorage();
	
	
	@Before
	public void setup() {
		keyValuesStorage.createStorage();
	}
	
	@Test
	public void readFromStorage() {
		KeyValues keyValues1 = new KeyValues("key1", Arrays.asList(new String[]{"12","2"}));
		KeyValues keyValues2 = new KeyValues("key1", Arrays.asList(new String[]{"12","2","3"}));
		keyValuesStorage.insertToStorage(keyValues1);
		keyValuesStorage.insertToStorage(keyValues2);
		KeyValues read = keyValuesStorage.readFromStorage("key1");
		KeyValues read2 = keyValuesStorage.readFromStorage("key2");
		assertNotNull(read);
		assertTrue(read.equals(keyValues1));
		assertTrue(read.getValues().size() == 3);
		assertNull(read2);
	}
	
	@Test
	public void insertToStorage() {
		KeyValues keyValues = new KeyValues("hello23", Arrays.asList(new String[]{"12","2"}));
		keyValuesStorage.insertToStorage(keyValues);
	}
}
