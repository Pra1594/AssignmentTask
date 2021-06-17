package com.babai.assignment;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EmployeesAdapter extends RecyclerView.Adapter<EmployeesAdapter.EmployeesHolder> {

    Context context;
    ArrayList<EmployeesModel.Datum> empList;
    boolean isSelected = false;
    ArrayList<String> idList = new ArrayList<>();

    public EmployeesAdapter(Context context, ArrayList<EmployeesModel.Datum> empList) {
        this.context = context;
        this.empList = empList;
    }

    @NonNull
    @Override
    public EmployeesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EmployeesHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeesHolder holder, int position) {

        holder.empName.setText(empList.get(position).getEmployeeName());

        holder.empSalary.setText(String.valueOf(empList.get(position).getEmployeeSalary()));

        holder.rLayout.setBackgroundColor(empList.get(position).isSelected ? Color.GREEN : Color.WHITE);

        holder.rLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                empList.get(position).setSelected(!empList.get(position).isSelected());

                holder.rLayout.setBackgroundColor(empList.get(position).isSelected() ? Color.GREEN : Color.WHITE);

                if(idList.contains(String.valueOf(empList.get(position).getId()))){

                    Log.e("REMOVE",String.valueOf(empList.get(position).getId()));

                    String value = String.valueOf(empList.get(position).getId());

                    removeVal(value);

                }else{

                    idList.add(String.valueOf(empList.get(position).getId()));

                }
                print();

            }
        });

    }

    private void print() {

        StringBuilder builder = new StringBuilder();

        for(String ids : idList){

            builder.append("\t");

            builder.append(ids);

        }
        if(builder.toString().equals(null) || builder.toString().isEmpty()){

            Toast.makeText(context,"No Selected ID/s",Toast.LENGTH_SHORT).show();

        }else{

            Toast.makeText(context,"Selected ID/s:- "+builder,Toast.LENGTH_SHORT).show();
        }

    }

    private void removeVal(String value) {
        for(String ids : idList){

            if(ids.equals(value)){

                Toast.makeText(context,"Removed ID:- "+value,Toast.LENGTH_SHORT).show();

                idList.remove(value);

                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return empList.size();
    }

    public class EmployeesHolder extends RecyclerView.ViewHolder{

        TextView empName,empSalary;
        RelativeLayout rLayout;

        public EmployeesHolder(@NonNull View itemView) {
            super(itemView);

            empName = itemView.findViewById(R.id.empName);
            empSalary = itemView.findViewById(R.id.empSalary);
            rLayout = itemView.findViewById(R.id.rLayout);

        }
    }
}
