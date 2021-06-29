package com.bob.common.base.dao;

/**
 * Created by Jimmy on 2017/12/4 0004.
 */
public class DaoException extends Exception {

    private int code;

    public DaoException(int code) {
        this.code = code;
    }

    public DaoException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
