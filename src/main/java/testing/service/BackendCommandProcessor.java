package testing.service;

import testing.model.ADBackendCommand;
import testing.model.BackendCommand;
import testing.validator.Validator;

import java.util.List;

public class BackendCommandProcessor {

    private final List<Validator> validators;

    public BackendCommandProcessor(List<Validator> validators) {
        this.validators = validators;
    }

    public void validateCommand(BackendCommand backendCommand) {
        validators.forEach(validator -> validator.validate(backendCommand.getValue()));
    }

}
