package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import pillar.Item;

public class ItemTest {

	@Test
	public void itemCreatedWithNameAndPrice() {
		Item testItem = new Item("Test", 2.59);
		assertEquals(testItem.getName(), "Test");
		assertEquals(testItem.getPrice(), 2.59, 0);	
	}
}
