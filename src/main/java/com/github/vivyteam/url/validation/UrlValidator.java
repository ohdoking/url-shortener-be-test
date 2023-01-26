package com.github.vivyteam.url.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UrlValidator implements ConstraintValidator<UrlConstraint, String> {

    @Override
    public void initialize(UrlConstraint contactNumber) {
    }

    @Override
    public boolean isValid(String urlField,
      ConstraintValidatorContext cxt) {
        String regex = "((http|https)://)(www.)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)";
        return urlField != null && urlField.matches(regex);
    }

}