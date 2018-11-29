package exceptions;

public class InvalidProductException extends Exception{

	private static final long serialVersionUID = 1L;
	public InvalidProductException() {super();}
	public InvalidProductException(String msg) {
		super(msg);
	}
	
}
