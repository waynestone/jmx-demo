package com.wayne.jmx.demo.mbean.mx;

import java.beans.ConstructorProperties;
import java.util.Date;

public class PaxiQueue {
    private final Date date;
    private final int size;
    private final String head;

    @ConstructorProperties({"date","size","head"})
    public PaxiQueue(Date date, int size, String head) {
        this.date = date;
        this.size = size;
        this.head = head;
    }

    public Date getDate() {
        return date;
    }

    public String getHead() {
        return head;
    }

    public int getSize() {
        return size;
    }
}
