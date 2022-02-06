package com.nandhu.employeedata.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nandhu.employeedata.R;
import com.nandhu.employeedata.databinding.ActivityEmployeeDetailBinding;
import com.nandhu.employeedata.model.Employee;
import com.nandhu.employeedata.model.EmployeeDB;

public class EmployeeDetailActivity extends AppCompatActivity {
    private ActivityEmployeeDetailBinding binding;
    private Employee employee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEmployeeDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        employee=new EmployeeDB().getData(getIntent().getIntExtra("id",0));

        binding.baseToolbar.toolbar.setTitle(employee.getName());
        setSupportActionBar(binding.baseToolbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

    private void viewDetails(){

        Glide.with(this).load(employee.getProfileImage()).into(binding.profileImage);

        updateData(R.id.name, "Name", employee.getName());
        updateData(R.id.user_name, "Username", employee.getUserName());
        updateData(R.id.email, "Email", employee.getEmail());
        updateData(R.id.phone, "Phone", employee.getPhone());
        updateData(R.id.street, "Street", employee.getStreet());
        updateData(R.id.suite, "Suite", employee.getSuite());
        updateData(R.id.city, "City", employee.getCity());
        updateData(R.id.zipcode, "Zipcode", employee.getZipCode());
        updateData(R.id.company, "Company", employee.getCompanyName());
        updateData(R.id.catch_phrase, "Catch Phrase", employee.getCatchPhrase());
        updateData(R.id.bs, "BS", employee.getBs());

        if (!TextUtils.isEmpty(employee.getWebsite())&& !employee.getWebsite().equalsIgnoreCase("null")){
            binding.website.setText(employee.getWebsite());
        }else {
            binding.websiteLbl.setVisibility(View.GONE);
            binding.website.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(employee.getLatitude())){
            binding.location.setText("https://maps.google.com/?q="+employee.getLatitude()+","+employee.getLongitude());
        }else {
            binding.locationLbl.setVisibility(View.GONE);
            binding.location.setVisibility(View.GONE);
        }
    }

    private void updateData(int id, String title, String value) {
        ViewGroup viewGroup = (ViewGroup) findViewById(id);
        TextView titleView = (TextView) viewGroup.findViewById(R.id.title);
        TextView valueView = (TextView) viewGroup.findViewById(R.id.value);
        titleView.setText(title);
        if (value != null) {
            valueView.setText(value);
        } else {
            valueView.setText("-");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onResume() {
        super.onResume();
        viewDetails();
    }
}