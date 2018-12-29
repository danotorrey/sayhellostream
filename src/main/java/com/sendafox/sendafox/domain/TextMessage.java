package com.sayhellostream.sayhellostream.domain;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.Entity;

@Entity
public class TextMessage extends BaseEntity {

    public String phoneNumber;
    public String body;
    public boolean wasSent = false;
    @Type( type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime" )
    public DateTime sendDate;

    public String getPhoneNumber() {

        return phoneNumber;
    }

    public void setPhoneNumber( String phoneNumber ) {

        this.phoneNumber = phoneNumber;
    }

    public String getBody() {

        return body;
    }

    public void setBody( String body ) {

        this.body = body;
    }

    public boolean isWasSent() {

        return wasSent;
    }

    public void setWasSent( boolean wasSent ) {

        this.wasSent = wasSent;
    }

    public DateTime getSendDate() {

        return sendDate;
    }

    public void setSendDate( DateTime sendDate ) {

        this.sendDate = sendDate;
    }

    @Override public String toString() {

        return "TextMessage{" +
               "body='" + body + '\'' +
               ", phoneNumber='" + phoneNumber + '\'' +
               ", sendDate=" + sendDate +
               ", wasSent=" + wasSent +
               '}';
    }
}