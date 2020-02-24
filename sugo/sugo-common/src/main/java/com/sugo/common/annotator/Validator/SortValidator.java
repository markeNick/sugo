package com.sugo.common.annotator.Validator;

import com.google.common.collect.Lists;

import com.sugo.common.annotator.Sort;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class SortValidator implements ConstraintValidator<Sort, String> {

    private List<String> valueList;

    @Override
    public void initialize(Sort sort) {
        valueList = Lists.newArrayList();
        for (String s : sort.accepts()) {
            valueList.add(s.toUpperCase());
        }
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(valueList.contains(s.toUpperCase())) return true;
        return false;
    }
}