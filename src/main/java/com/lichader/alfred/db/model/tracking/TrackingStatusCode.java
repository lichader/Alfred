package com.lichader.alfred.db.model.tracking;

public enum  TrackingStatusCode {
    DATABASE_ERROR(-9),
    DATABASE_VERSION_UNSUPPORTED(-5),
    SYSTEM_UNREGISTERED(-4),
    INVALID_TRACKING_NO(-3),
    NO_RESULT(-2),
    NOT_SENT(0),
    SENT(1),
    IN_PROGRESS(2),
    DELIVERED(3),
    TIMEOUT(4),
    IN_CUSTOM(5),
    WRONG_ADDRESS(6),
    PARCEL_LOST(7),
    RETURNED(8),
    OTHER_ERROR(9),
    DESTROYED(10);

    private int code;

    TrackingStatusCode(int code){
        this.code = code;
    }

    public int getCode(){
        return this.code;
    }
}
