package tp;

//la classe MyException2 hérite de RuntimeException
//---> à try/catch facultatif "uncheck excption"

public class MyException2 extends RuntimeException {
//public class MyException extends Exception {

	public MyException2() {
	}

	public MyException2(String message) {
		super(message);
	}

	public MyException2(Throwable cause) {
		super(cause);
	}

	public MyException2(String message, Throwable cause) {
		super(message, cause);
	}

	public MyException2(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
