package pillar;

import java.util.HashMap;
import java.util.Map;

import pillar.item.AbstractItem;
import pillar.item.QuantifiedItem;

public class Checkout {

	private Store store;
	private HashMap<String, Integer> scannedItems;
	
	public Checkout(Store store) {
		this.store = store;
		this.scannedItems = new HashMap<String, Integer>();
	}
	
	public double scanItem(String itemName) {
		if(scannedItems.get(itemName) != null) {
			scannedItems.put(itemName, scannedItems.get(itemName) + 1);
		} else {
			scannedItems.put(itemName, 1);
		}
		return this.getTotal();
	}
	
	private double getTotal() {
		double total = 0.0;
		for(Map.Entry<String, Integer> entry: this.scannedItems.entrySet()) {
			AbstractItem<?> currentItem = store.getItem(entry.getKey());
			if(currentItem instanceof QuantifiedItem) {
				total += ((QuantifiedItem) currentItem).getSubTotal(entry.getValue());
			}
		}
		return total;
	}
}
