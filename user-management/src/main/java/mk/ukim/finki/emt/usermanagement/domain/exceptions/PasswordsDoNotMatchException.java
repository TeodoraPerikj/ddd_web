package mk.ukim.finki.emt.usermanagement.domain.exceptions;

public class PasswordsDoNotMatchException extends RuntimeException {

    public PasswordsDoNotMatchException(){
        super("Passwords do not match!");
    }
}
