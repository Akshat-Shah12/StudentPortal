package com.devansh.studentportal;

import com.devansh.studentportal.models.EntireStudentData;
import com.devansh.studentportal.models.StudentData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface RestApi {
    @GET("/")
    Call<EntireStudentData> getAllStudentData();

    @POST("add-student/")
    Call<ResponseBody> addStudent(@Body StudentData data);

    @PATCH("update-student/{id}/")
    Call<ResponseBody> updateStudent(@Path("id") int id, @Body StudentData data);

    @DELETE("delete-student/{id}/")
    Call<ResponseBody> deleteStudent(@Path("id") int id);
}
