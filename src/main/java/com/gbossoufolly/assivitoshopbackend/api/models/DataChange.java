package com.gbossoufolly.assivitoshopbackend.api.models;

public class DataChange <T> {

    private ChangeType changeType;

    private T data;

    public DataChange(ChangeType changeType, T data) {
        this.changeType = changeType;
        this.data = data;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public enum ChangeType {
        INSERT,
        UPDATE,
        DELETE
    }
}
