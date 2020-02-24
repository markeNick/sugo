package com.sugo.common.annotator.Validator;

import com.google.common.collect.Lists;
import com.sugo.common.annotator.Order;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class OrderValidator implements ConstraintValidator<Order, String> {

    private List<String> valueList;

    @Override
    public void initialize(Order order) {
        valueList = Lists.newArrayList();
        for (String o : order.accepts()) {
            valueList.add(o.toUpperCase());
        }
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(valueList.contains(s.toUpperCase())) return true;
        return false;
    }
}