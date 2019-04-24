package pillar;

import java.util.HashMap;
import java.util.Map;

import pillar.item.AbstractItem;
import pillar.item.QuantifiedItem;
import pillar.item.WeightedItem;

public class Checkout {

	private Store store;
	private HashMap<String, Object> scannedItems;
	
	public Checkout(Store store) {
		this.store = store;
		this.scannedItems = new HashMap<String, Object>();
	}
	
	public double scanItem(String itemName) {
		if(scannedItems.get(itemName) != null) {
			scannedItems.put(itemName, (Integer) scannedItems.get(itemName) + 1);
		} else {
			scannedItems.put(itemName, 1);
		}
		return this.getTotal();
	}
	
	public double scanItem(String itemName, double weight) {
		if(scannedItems.get(itemName) != null) {
			scannedItems.put(itemName, (Double) scannedItems.get(itemName) + weight);
		} else {
			scannedItems.put(itemName, weight);
		}
		return this.getTotal();
	}
	
	private double getTotal() {
		double total = 0.0;
		for(Map.Entry<String, Object> entry: this.scannedItems.entrySet()) {
			AbstractItem<?> currentItem = store.getItem(entry.getKey());
			if(currentItem instanceof QuantifiedItem) {
				total += ((QuantifiedItem) currentItem).getSubTotal((Integer) entry.getValue());
			} else if (currentItem instanceof WeightedItem) {
				total += ((WeightedItem) currentItem).getSubTotal((Double) entry.getValue());
			}
		}
		return total;
	}
}
