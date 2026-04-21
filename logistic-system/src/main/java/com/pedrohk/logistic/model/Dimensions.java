package com.pedrohk.logistic.model;

public record Dimensions(double length, double width, double height) {
    public double volume() {
        return length * width * height;
    }
}
