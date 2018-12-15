package dz_spring7.execption;

public class BadRequestException extends Exception {
    public BadRequestException(String message){
        super(message);
    }
}
