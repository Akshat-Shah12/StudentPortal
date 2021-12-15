package com.devansh.studentportal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devansh.studentportal.models.StudentData;

import java.util.ArrayList;

public class StudentAdapter  extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private ArrayList<StudentData> studentDataArrayList;
    private Context context;

    public StudentAdapter(ArrayList<StudentData> studentDataArrayList) {
        this.studentDataArrayList = studentDataArrayList;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setStudentDataArrayList(ArrayList<StudentData> studentDataArrayList) {
        this.studentDataArrayList = studentDataArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StudentData studentData = studentDataArrayList.get(position);
        holder.name.setText(studentData.getName());
        holder.branch.setText(studentData.getBranch());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ViewStudentActivity.class);
                intent.putExtra("id",studentData.getId()+"");
                intent.putExtra("name",studentData.getName());
                intent.putExtra("branch",studentData.getBranch());
                intent.putExtra("sap",studentData.getSap()+"");
                intent.putExtra("cgpa",studentData.getCgpa()+" / 10");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentDataArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,branch;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            branch = itemView.findViewById(R.id.branch);
        }
    }
}
