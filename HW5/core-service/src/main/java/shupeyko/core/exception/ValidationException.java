package shupeyko.core.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ValidationException extends RuntimeException {
    private List<String> errorFieldsMessages;
    public ValidationException(List<String> errorFieldsMessages) {
        super(String.join(", ", errorFieldsMessages));
        this.errorFieldsMessages = errorFieldsMessages;
    }
}
