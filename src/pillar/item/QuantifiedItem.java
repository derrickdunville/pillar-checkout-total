package pillar.item;

public class QuantifiedItem extends AbstractItem<Integer> {

	public QuantifiedItem(String itemName, double itemPrice) {
		super(itemName, itemPrice);
	}

	public double getSubTotal(Integer quantity) {
		return this.getPrice() * quantity;
	}
}
