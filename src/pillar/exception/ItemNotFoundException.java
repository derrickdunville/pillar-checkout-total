package pillar.exception;

@SuppressWarnings("serial")
public class ItemNotFoundException extends Exception{
	public String getMessage() {
		return "Item not found";
	}
}
