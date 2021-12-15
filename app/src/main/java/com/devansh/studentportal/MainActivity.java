package com.devansh.studentportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.devansh.studentportal.models.EntireStudentData;
import com.devansh.studentportal.models.StudentData;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ArrayList<StudentData> studentDataArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.add_student).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void addStudent() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_update_student);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        ((TextView)dialog.findViewById(R.id.title)).setText("Add Student");
        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentData studentData = new StudentData();
                String name = ((EditText)dialog.findViewById(R.id.name)).getText().toString();
                String branch = ((EditText)dialog.findViewById(R.id.branch)).getText().toString();
                String sap = ((EditText)dialog.findViewById(R.id.sap)).getText().toString();
                String cgpa = ((EditText)dialog.findViewById(R.id.cgpa)).getText().toString();
                int id;
                try{
                    id = studentDataArrayList.get(studentDataArrayList.size()).getId();
                } catch (Exception e) {
                    if(studentDataArrayList==null) return;
                    id = 1;
                }
                studentData.setId(id);
                studentData.setName(name);
                studentData.setBranch(branch);
                studentData.setCgpa(Float.parseFloat(cgpa));
                studentData.setSap(Long.parseLong(sap));
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RestApi restApi = retrofit.create(RestApi.class);
                Call<ResponseBody> call = restApi.addStudent(studentData);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful() && response.body()!=null){
                            dialog.dismiss();
                            onResume();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        int i=0;
                    }
                });
            }
        });
        dialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestApi restApi = retrofit.create(RestApi.class);
        Call<EntireStudentData> entireStudentDataCall = restApi.getAllStudentData();
        entireStudentDataCall.enqueue(new Callback<EntireStudentData>() {
            @Override
            public void onResponse(Call<EntireStudentData> call, Response<EntireStudentData> response) {
                if(response.isSuccessful() && response.body()!=null){
                    studentDataArrayList = response.body().getData();
                    RecyclerView recyclerView = findViewById(R.id.recycler);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerView.setAdapter(new StudentAdapter(studentDataArrayList));
                }
            }

            @Override
            public void onFailure(Call<EntireStudentData> call, Throwable t) {
                int i=0;
            }
        });
    }
}