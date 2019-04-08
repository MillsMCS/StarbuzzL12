package com.hfad.starbuzz;

class ModelException extends Exception {
    ModelException(String errorMessage) {
        super(errorMessage);
    }

    ModelException(String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
    }
}