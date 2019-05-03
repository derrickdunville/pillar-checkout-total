package pillar;

import pillar.exception.InvalidSpecialException;
import pillar.exception.ItemNotFoundException;
import pillar.exception.QuantifiedItemException;
import pillar.exception.RangeException;
import pillar.exception.WeightedItemException;
import pillar.item.QuantifiedItem;
import pillar.item.WeightedItem;

public class Main {

	public static void main(String[] args) {
		System.out.println("Checkout API example");
		try {
			
			Store store = new Store();
			
			QuantifiedItem milk = new QuantifiedItem("milk", 3.49);
			
			QuantifiedItem soup = new QuantifiedItem("soup", 1.59);
			soup.setMarkdown(0.29);
			
			QuantifiedItem bread = new QuantifiedItem("bread", 2.49);
			bread.setSpecial(2, 4.00);
			
			QuantifiedItem eggs = new QuantifiedItem("eggs", 2.39);
			eggs.setSpecial(2, 1, 100.00);
			
			WeightedItem bananas = new WeightedItem("bananas", 0.79);
			
			WeightedItem hamburger = new WeightedItem("hamburger", 3.49);
			hamburger.setSpecial(3.0, 1.0, 75.0);
			
			store.addItem(milk);
			store.addItem(soup);
			store.addItem(bread);
			store.addItem(eggs);
			store.addItem(bananas);
			store.addItem(hamburger);
			
			Checkout checkout = new Checkout(store);
			
			System.out.println("Scanning items");
			System.out.println(checkout.scanItem("milk"));
			System.out.println(checkout.scanItem("soup"));
			System.out.println(checkout.scanItem("bread"));
			System.out.println(checkout.scanItem("bread"));
			System.out.println(checkout.scanItem("eggs"));
			System.out.println(checkout.scanItem("eggs"));
			System.out.println(checkout.scanItem("eggs"));
			System.out.println(checkout.scanItem("bananas", 2.50));
			System.out.println(checkout.scanItem("hamburger", 2.00));
			System.out.println(checkout.scanItem("hamburger", 2.00));
			
			
			System.out.println("Removing item in order of scan");
			System.out.println(checkout.removeItem("hamburger", 2.00));
			System.out.println(checkout.removeItem("hamburger", 2.00));
			System.out.println(checkout.removeItem("bananas", 2.50));
			System.out.println(checkout.removeItem("eggs"));
			System.out.println(checkout.removeItem("eggs"));
			System.out.println(checkout.removeItem("eggs"));
			System.out.println(checkout.removeItem("bread"));
			System.out.println(checkout.removeItem("bread"));
			System.out.println(checkout.removeItem("soup"));
			System.out.println(checkout.removeItem("milk"));


		} catch (RangeException | InvalidSpecialException | ItemNotFoundException | WeightedItemException | QuantifiedItemException e) {
			e.printStackTrace();
		}
		
	}
}