package com.devansh.studentportal;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BranchAdapter extends RecyclerView.Adapter<BranchAdapter.ViewHolder> {

    private ArrayList<String> branchArrayList;
    private ArrayList<String> selectedBranches;

    public BranchAdapter(ArrayList<String> branchArrayList) {
        this.branchArrayList = branchArrayList;
        selectedBranches = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.branch_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(selectedBranches.contains(branchArrayList.get(position))){
            ((CardView)holder.itemView.findViewById(R.id.card)).setCardBackgroundColor(Color.BLUE);
            ((TextView)holder.itemView.findViewById(R.id.text)).setTextColor(Color.WHITE);
        }
        else{
            ((CardView)holder.itemView.findViewById(R.id.card)).setCardBackgroundColor(Color.parseColor("#DDDDDD"));
            ((TextView)holder.itemView.findViewById(R.id.text)).setTextColor(Color.BLACK);
        }
        ((TextView)holder.itemView.findViewById(R.id.text)).setText(branchArrayList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                if(selectedBranches.contains(branchArrayList.get(position))) selectedBranches.remove(branchArrayList.get(position));
                else selectedBranches.add(branchArrayList.get(position));
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return branchArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public ArrayList<String> getSelectedBranches() {
        return selectedBranches;
    }


}
