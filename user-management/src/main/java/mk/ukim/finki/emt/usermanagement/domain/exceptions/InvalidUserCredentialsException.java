package mk.ukim.finki.emt.usermanagement.domain.exceptions;

public class InvalidUserCredentialsException extends RuntimeException{

    public InvalidUserCredentialsException(){
        super("Invalid user credentials exception!");
    }
}