package com.devansh.studentportal.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EntireStudentData {

    @SerializedName("status") private int status;
    @SerializedName("all_data") private ArrayList<StudentData> data;

    public int getStatus() {
        return status;
    }

    public ArrayList<StudentData> getData() {
        return data;
    }
}
