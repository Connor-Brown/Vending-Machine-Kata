package exceptions;

public class NotAcceptedCoinException extends Exception{
	
	private static final long serialVersionUID = 1L;
	public NotAcceptedCoinException() {super();}
	public NotAcceptedCoinException(String msg) {
		super(msg);
	}
	
}
