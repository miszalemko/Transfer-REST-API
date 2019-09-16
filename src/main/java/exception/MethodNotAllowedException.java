package exception;

class MethodNotAllowedException extends ApplicationException {

    MethodNotAllowedException(int code, String message) {
        super(code, message);
    }
}