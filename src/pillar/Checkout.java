package pillar;

import java.util.HashMap;
import java.util.Map;

import pillar.item.AbstractItem;
import pillar.item.QuantifiedItem;
import pillar.item.WeightedItem;
import pillar.item.exception.ItemNotFoundException;
import pillar.item.exception.QuantifiedItemException;
import pillar.item.exception.WeightedItemException;

public class Checkout {

	private Store store;
	private HashMap<String, Object> scannedItems;
	
	public Checkout(Store store) {
		if(store == null) {
			throw new IllegalArgumentException("Store can not be null.");
		}
		this.store = store;
		scannedItems = new HashMap<String, Object>();
	}
	
	public double scanItem(String itemName) throws ItemNotFoundException, WeightedItemException {
		AbstractItem<?> targetItem = store.getItem(itemName);
		if(targetItem == null) {
			throw new ItemNotFoundException();
		}
		
		if(targetItem instanceof WeightedItem) {
			throw new WeightedItemException("Must provide item weight");
		}

		if(scannedItems.get(itemName) != null) {
			scannedItems.put(itemName, (Integer) scannedItems.get(itemName) + 1);
		} else {
			scannedItems.put(itemName, 1);
		}
		return getTotal();
	}

	public double scanItem(String itemName, double weight) throws ItemNotFoundException, QuantifiedItemException {
		AbstractItem<?> targetItem = store.getItem(itemName);
		if(targetItem == null) {
			throw new ItemNotFoundException();
		}
		
		if(targetItem instanceof QuantifiedItem) {
			throw new QuantifiedItemException("Item weight should not be provided");
		}
		
		if(store.getItem(itemName) != null) {
			if(scannedItems.get(itemName) != null) {
				scannedItems.put(itemName, (Double) scannedItems.get(itemName) + weight);
			} else {
				scannedItems.put(itemName, weight);
			}
		} 
		return getTotal();
	}
	
	public double removeItem(String itemName) {
		if(scannedItems.get(itemName) != null) {
			if((Integer) scannedItems.get(itemName) > 1) {
				scannedItems.put(itemName, (Integer) scannedItems.get(itemName) - 1);
			} else {
				scannedItems.remove(itemName);
			}
		}
		return getTotal();
	}
	
	public double removeItem(String itemName, double weight) {
		if(scannedItems.get(itemName) != null) {
			if((Double) scannedItems.get(itemName) > weight) {
				scannedItems.put(itemName, (Double) scannedItems.get(itemName) - weight);
			} else {
				scannedItems.remove(itemName);
			}
		}
		return getTotal();
	}
	
	private double getTotal() {
		double total = 0.0;
		for(Map.Entry<String, Object> entry: scannedItems.entrySet()) {
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
