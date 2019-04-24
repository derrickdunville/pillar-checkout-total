package pillar;

import java.util.HashMap;

import pillar.item.AbstractItem;

public class Store {

	private HashMap<String, AbstractItem> storeItems;
	
	public Store() {
		this.storeItems = new HashMap<String, AbstractItem>();
	}
	
	public HashMap<String, AbstractItem> getStoreItems(){
		return this.storeItems;
	}
	
	public void addItem(AbstractItem item) {
		this.storeItems.put(item.getName(), item);
	}
	
	public AbstractItem getItem(String itemName) {
		return this.storeItems.get(itemName);
	}
	
}
