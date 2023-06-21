package tp;

//la classe MyException hérite directement de Exception
//et n'hérite pas de RuntimeException
//---> à try/catch obligatoire "check excption"

public class MyException extends Exception {
//public class MyException extends RuntimeException {

	public MyException() {
	}

	public MyException(String message) {
		super(message);
	}

	public MyException(Throwable cause) {
		super(cause);
	}

	public MyException(String message, Throwable cause) {
		super(message, cause);
	}

	public MyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
