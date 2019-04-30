package pillar.exception;

public class QuantifiedItemException extends Exception{

	private static final long serialVersionUID = 1L;
	private String message;
	
	public QuantifiedItemException(String message) {
		this.message = message; 
	}
	
	public String getMessage() {
		return this.message;
	}
}
