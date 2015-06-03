package com.quifers.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

public class Price implements Serializable {

    private long orderId;

    private int waitingMinutes;

    private int transitKilometres;

    private int labours;

    private int nonWorkingLifts;

    public Price() {

    }

    public Price(long orderId, int waitingMinutes, int transitKilometres, int labours, int nonWorkingLifts) {
        this.orderId = orderId;
        this.waitingMinutes = waitingMinutes;
        this.transitKilometres = transitKilometres;
        this.labours = labours;
        this.nonWorkingLifts = nonWorkingLifts;
    }

    public int getWaitingCost() {
        if (waitingMinutes <= 60) {
            return 0;
        }
        int remainingMinutes = waitingMinutes - 60;
        int roundOff = remainingMinutes % 15;
        int minutesBlock = remainingMinutes / 15 + (roundOff <= 10 ? 0 : 1);
        return minutesBlock * 50;
    }

    public int getTransitCost() {
        if (transitKilometres <= 2) {
            return 300;
        }
        return 300 + (transitKilometres - 2) * 30;
    }

    public int getLabourCost() {
        if (nonWorkingLifts <= 2) {
            return 300 * labours;
        }
        return 350 * (nonWorkingLifts - 2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Price price = (Price) o;

        if (labours != price.labours) return false;
        if (nonWorkingLifts != price.nonWorkingLifts) return false;
        if (orderId != price.orderId) return false;
        if (transitKilometres != price.transitKilometres) return false;
        if (waitingMinutes != price.waitingMinutes) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (orderId ^ (orderId >>> 32));
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
