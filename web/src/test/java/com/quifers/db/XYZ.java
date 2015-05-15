package com.quifers.db;

import com.quifers.db.annotations.Column;
import com.quifers.db.annotations.Table;
import com.quifers.domain.QuifersDomainObject;

import java.util.Date;

@Table(name = "xyz")
public class XYZ implements QuifersDomainObject {

    @Column(name = "abc")
    private String abc;

    private Date someDate;

    @Column(name = "other_date")
    private Date otherDate;

    @Column(name = "count")
    private int count;

    public XYZ() {
    }

    public XYZ(String abc, Date someDate, Date otherDate, int count) {
        this.abc = abc;
        this.someDate = someDate;
        this.otherDate = otherDate;
        this.count = count;
    }

    public String getAbc() {
        return abc;
    }

    public Date getOtherDate() {
        return otherDate;
    }

    public int getCount() {
        return count;
    }
}
