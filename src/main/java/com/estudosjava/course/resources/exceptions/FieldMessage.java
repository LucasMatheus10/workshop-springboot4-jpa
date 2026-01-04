package com.estudosjava.course.resources.exceptions;

import java.io.Serializable;

public record FieldMessage(String fieldName, String message) implements Serializable {
    private static final long serialVersionUID = 1L;
}
