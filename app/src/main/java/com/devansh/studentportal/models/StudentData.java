package com.devansh.studentportal.models;

import com.google.gson.annotations.SerializedName;

public class StudentData {

    @SerializedName("id") private int id;
    @SerializedName("sap") private long sap;
    @SerializedName("branch") private String branch;
    @SerializedName("name") private String name;
    @SerializedName("cgpa") private float cgpa;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getSap() {
        return sap;
    }

    public void setSap(long sap) {
        this.sap = sap;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCgpa() {
        return cgpa;
    }

    public void setCgpa(float cgpa) {
        this.cgpa = cgpa;
    }
}
