package pillar.item.exception;

public class WeightedItemException extends Exception{
	
	private static final long serialVersionUID = 1L;
	private String message;
	
	public WeightedItemException(String message) {
		this.message = message; 
	}
	
	public String getMessage() {
		return this.message;
	}
}
