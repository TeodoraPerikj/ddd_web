package mk.ukim.finki.emt.usermanagement.domain.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(){
        super("User not found!");
    }
}

