package home.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import home.commons.KeyValues;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class KeyValuesManagerTest {
	
	KeyValuesStore keyValuesStore;
	
	@Before
	public void setup() {
		keyValuesStore = new KeyValuesStore();
	}
	
	@Test
	public void read() {
		String key = "key1";
		KeyValues keyValues = keyValuesStore.read(key);
		assertNull(keyValues);
		KeyValues keyValues2 = new KeyValues (key, Arrays.asList(new String[]{"1","2"}));
		keyValuesStore.write(keyValues2);
		keyValues = keyValuesStore.read(key);
		assertNotNull(keyValues);
		assertEquals(key, keyValues.getKey());
	}
	
	@Test
	public void write() {
		keyValuesStore.write(new KeyValues ("key2", Arrays.asList(new String[]{"1","2","3"})));
	}
	
	@After
	public void unsetup() {
		keyValuesStore.destroy();
	}
}
