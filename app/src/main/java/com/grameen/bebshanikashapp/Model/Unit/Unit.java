package com.grameen.bebshanikashapp.Model.Unit;

public class Unit {
    private String unit;

    public Unit() {
    }


    public Unit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return unit;
    }
}
