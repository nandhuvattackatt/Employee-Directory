package com.nandhu.employeedata.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.nandhu.employeedata.R;
import com.nandhu.employeedata.model.Employee;
import com.nandhu.employeedata.view.EmployeeDetailActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.ViewHolder> {
    private static final String TAG = "AddImageListAdapter";

    public ArrayList<Employee> rows = new ArrayList<>();
    private Context context;




    public EmployeeListAdapter(Context context, ArrayList<Employee> rows) {
        this.context = context;
        this.rows = rows;
    }

    public Employee getItem(int position) {
        try {
            return rows.get(position);

        } catch (Exception e) {
            return null;
        }
    }

    public void setData(ArrayList<Employee> rows) {
        this.rows = rows;

        notifyDataSetChanged();

    }

    public void appProduct(Employee employee) {
        notifyItemInserted(rows.size());
    }

    public ArrayList<Employee> getData() {
        return this.rows;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public EmployeeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Employee row = getItem(position);
        bindData(row, holder);
    }

    public void bindData(final Employee row, final ViewHolder holder) {

        try {
            CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
            circularProgressDrawable.setStrokeWidth(5f);
            circularProgressDrawable.setCenterRadius(30f);
            circularProgressDrawable.start();
            Glide.with(context).load(row.getProfileImage()).placeholder(circularProgressDrawable).into(holder.profileImage);

            holder.name.setText(row.getName());
            holder.company.setText(row.getCompanyName());

            holder.employeeCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, EmployeeDetailActivity.class);
                    intent.putExtra("id",row.getId());
                    context.startActivity(intent);
                }
            });

        } catch (NullPointerException e) {
            Log.e(TAG, "bindVehicle: ", e);
        }
    }


    @Override
    public int getItemCount() {
        try {
            return rows.size();
        } catch (NullPointerException e) {
            return 0;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView profileImage;
        private TextView name,company;
        private CardView employeeCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            
            profileImage=itemView.findViewById(R.id.profile_image);
            name=itemView.findViewById(R.id.name);
            company=itemView.findViewById(R.id.company);
            employeeCard=itemView.findViewById(R.id.employee_card);

        }
    }
}


