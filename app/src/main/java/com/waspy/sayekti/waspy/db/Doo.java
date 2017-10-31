package com.waspy.sayekti.waspy.db;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by sayekti on 10/16/17.
 */

public class Doo extends RealmObject {
    @PrimaryKey
    private String id = UUID.randomUUID().toString();
    private Date time = Calendar.getInstance().getTime();

    public String getId() {
        return id;
    }

    public Date getTime() {
        return time;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getTicker() {
        return ticker;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    private String packageName;
    private String ticker;
    private String title;
    private String text;

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }
}
