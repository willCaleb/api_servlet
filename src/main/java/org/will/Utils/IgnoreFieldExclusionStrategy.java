package org.will.Utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import org.will.annotation.IgnoreField;

public class IgnoreFieldExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return fieldAttributes.getAnnotation(IgnoreField.class) != null;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}