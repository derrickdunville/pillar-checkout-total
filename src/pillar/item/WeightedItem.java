package pillar.item;

public class WeightedItem extends AbstractItem<Double> {

	public WeightedItem(String itemName, double itemPrice) {
		super(itemName, itemPrice);
	}

	@Override
	public double getSubTotal(Double weight) {
		return this.getPrice() * weight;
	}

}
