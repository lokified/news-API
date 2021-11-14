package Exceptions;

public class APIException extends RuntimeException{
    private final int statusCode;

    public APIException(int statusCode ,String msg){
        super(msg);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}