package com.redhat.coolstore.altproducts.model;

public class AlternativeProduct {

    private String itemId;

    private String[] altItemIds;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String[] getAltItemIds() {
        return altItemIds;
    }

    public void setAltItemIds(String[] altItemIds) {
        this.altItemIds = altItemIds;
    }
}
