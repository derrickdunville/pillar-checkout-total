package pillar.exception;

public class InvalidSpecialException extends Exception{

	private static final long serialVersionUID = 1L;
	private String message;
	
	public InvalidSpecialException(String message) {
		this.message = message; 
	}
	
	public String getMessage() {
		return this.message;
	}
}
