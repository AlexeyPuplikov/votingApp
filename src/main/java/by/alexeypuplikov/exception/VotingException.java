package by.alexeypuplikov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VotingException extends RuntimeException {
    public VotingException(String error) {
        super(error);
    }
}
