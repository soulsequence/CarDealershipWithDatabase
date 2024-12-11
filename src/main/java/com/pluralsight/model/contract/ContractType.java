package com.pluralsight.model.contract;

public enum ContractType {
    SALE("Sale"),
    LEASE("Lease");

    private final String displayName;

    ContractType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}