package com.devansh.studentportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.devansh.studentportal.models.StudentData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewStudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);
        ((TextView)findViewById(R.id.name)).setText(getIntent().getStringExtra("name"));
        ((TextView)findViewById(R.id.sap)).setText(getIntent().getStringExtra("sap"));
        ((TextView)findViewById(R.id.branch)).setText(getIntent().getStringExtra("branch"));
        ((TextView)findViewById(R.id.cgpa)).setText(getIntent().getStringExtra("cgpa"));
        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editInformation();
            }
        });
        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteStudent();
            }
        });
    }

    private void editInformation(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_update_student);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        ((EditText)dialog.findViewById(R.id.name)).setText(getIntent().getStringExtra("name"));
        ((EditText)dialog.findViewById(R.id.branch)).setText(getIntent().getStringExtra("branch"));
        ((EditText)dialog.findViewById(R.id.sap)).setText(getIntent().getStringExtra("sap"));
        ((EditText)dialog.findViewById(R.id.cgpa)).setText(getIntent().getStringExtra("cgpa").substring(0,getIntent().getStringExtra("cgpa").indexOf('/')-1));
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
                studentData.setId(Integer.parseInt(getIntent().getStringExtra("id")));
                studentData.setName(name);
                studentData.setBranch(branch);
                studentData.setCgpa(Float.parseFloat(cgpa));
                studentData.setSap(Long.parseLong(sap));
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RestApi restApi = retrofit.create(RestApi.class);
                Call<ResponseBody> call = restApi.updateStudent(studentData.getId(), studentData);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful() && response.body()!=null){
                            dialog.dismiss();
                            Intent intent = new Intent(ViewStudentActivity.this,ViewStudentActivity.class);
                            intent.putExtra("id",studentData.getId()+"");
                            intent.putExtra("name",studentData.getName());
                            intent.putExtra("cgpa",studentData.getCgpa()+" / 10");
                            intent.putExtra("sap",studentData.getSap()+"");
                            intent.putExtra("branch",studentData.getBranch()+"");
                            finish();
                            startActivity(intent);
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
    private void deleteStudent() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestApi restApi = retrofit.create(RestApi.class);
        Call<ResponseBody> call = restApi.deleteStudent(Integer.parseInt(getIntent().getStringExtra("id")));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful() && response.body()!=null){
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                int i=0;
            }
        });
    }
}