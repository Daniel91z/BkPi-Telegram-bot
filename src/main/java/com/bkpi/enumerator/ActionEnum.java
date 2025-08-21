package com.bkpi.enumerator;


public enum ActionEnum {

    IP,
    PING,
    SAVE_PHOTO,
    SAVE_VIDEO,
    SAVE_DOCUMENT,
    UNKNOWN;

    public static ActionEnum getAction(String action) {
        try {
            return ActionEnum.valueOf(action.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }

}