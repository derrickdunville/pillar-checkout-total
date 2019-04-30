package pillar.exception;

public class RangeException extends Exception{

	private static final long serialVersionUID = 1L;
	private String message;
	
	public RangeException(String message) {
		this.message = message; 
	}
	
	public String getMessage() {
		return this.message;
	}
}

