package com.medicapital.common.entities;

import java.util.Date;

/**
 * The persistent class for the PatientQuery database table.
 * 
 */
public class PatientQuery implements SerializableEntity {

    private int queryId;

    private String response;

    private boolean isPrivate;

    private String query;

    private Doctor doctor;

    private User user;

    private String title;

    private Date queryDate;

    private Date responseDate;

    public PatientQuery() {
    }
    
    @Override
    public void setId(int entityId) {
        queryId=entityId;
    }

    @Override
    public int getId() {
        return queryId;
    }

    public int getQueryId() {
        return this.queryId;
    }

    public void setQueryId(int queryId) {
        this.queryId = queryId;
    }

    public String getResponse() {
        return this.response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean getIsPrivate() {
        return this.isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getQuery() {
        return this.query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Doctor getDoctor() {
        return this.doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
    }

    public Date getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Date responseDate) {
        this.responseDate = responseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;
        } else if (!(obj instanceof PatientQuery)) {
            return false;
        }
        PatientQuery other = (PatientQuery) obj;
        return queryId == other.queryId;
    }

}