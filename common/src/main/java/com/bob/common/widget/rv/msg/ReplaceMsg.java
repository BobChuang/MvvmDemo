package com.bob.common.widget.rv.msg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jimmy on 2017/9/30 0030.
 */
public class ReplaceMsg {

    private Object data;

    public static ReplaceMsg create(Object data) {
        ReplaceMsg msg = new ReplaceMsg();
        msg.setData(data);
        return msg;
    }

    public List<Object> getData() {
        if (data instanceof List)
            return (List<Object>) data;
        else
            return new ArrayList<>();
    }

    public void setData(Object data) {
        if (data instanceof Iterable) {
            this.data = data;
        } else {
            this.data = new ArrayList<>();
        }
    }
}
