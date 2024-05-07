package com.svalero.apiEvent.validator;

import com.svalero.apiEvent.domain.Game;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class GameValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Game.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mapName", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "playersInGame", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "playerWins", "field.required");
    }
}
