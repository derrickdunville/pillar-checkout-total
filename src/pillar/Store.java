package pillar;

import java.util.HashMap;

import pillar.item.AbstractItem;

public class Store {

	private HashMap<String, AbstractItem<?>> storeItems;
	
	public Store() {
		storeItems = new HashMap<String, AbstractItem<?>>();
	}
	
	public HashMap<String, AbstractItem<?>> getStoreItems(){
		return storeItems;
	}
	
	public void addItem(AbstractItem<?> item) {
		storeItems.put(item.getName(), item);
	}
	
	public AbstractItem<?> getItem(String itemName) {
//		TODO: Item may not be in storeItems. Should throw ItemNotFoundException
		return storeItems.get(itemName);
	}
	
}
