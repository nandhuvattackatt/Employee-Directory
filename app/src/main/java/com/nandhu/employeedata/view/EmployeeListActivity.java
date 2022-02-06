package com.nandhu.employeedata.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.nandhu.employeedata.R;
import com.nandhu.employeedata.adapter.EmployeeListAdapter;
import com.nandhu.employeedata.api.APIRequest;
import com.nandhu.employeedata.databinding.ActivityEmployeeListBinding;
import com.nandhu.employeedata.model.Employee;
import com.nandhu.employeedata.model.EmployeeDB;
import com.nandhu.employeedata.utils.BaseActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class EmployeeListActivity extends BaseActivity {
    ActivityEmployeeListBinding binding;
    private ArrayList<Employee> employeeArrayList=new ArrayList<>();
    private EmployeeListAdapter employeeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEmployeeListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.baseToolbar.toolbar.setTitle("Employees");
        setSupportActionBar(binding.baseToolbar.toolbar);

        employeeListAdapter=new EmployeeListAdapter(this,employeeArrayList);
        binding.employeeList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.employeeList.setAdapter(employeeListAdapter);

        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==0){
                    employeeListAdapter.setData(employeeArrayList);
                }else {
                    searchEmployee(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (new EmployeeDB().getAll().size()==0){
            getEmployeeList();
        }else {
            employeeArrayList.addAll(new EmployeeDB().getAll());
            employeeListAdapter.setData(employeeArrayList);

        }
    }

    private void searchEmployee(String text){
        ArrayList<Employee> temp = new ArrayList();
        for(Employee employee: employeeArrayList){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(employee.getName().toLowerCase().contains(text.toLowerCase())|employee.getEmail().toLowerCase().contains(text.toLowerCase())){
                temp.add(employee);
            }
        }

        employeeListAdapter.setData(temp);

    }

    private void getEmployeeList() {
        showProgressWheel();
        APIRequest apiRequest = new APIRequest(EmployeeListActivity.this);
        apiRequest.setPath("5d565297300000680030a986");
        apiRequest.executeRequest(new APIRequest.VolleyCallback() {
            @Override
            public void getSuccessResponse(JSONArray response) {
                try {
                    for (int i=0;i<response.length();i++){
                        JSONObject jsonObject=response.optJSONObject(i);
                        Employee employee=new Employee(jsonObject);
                        employeeArrayList.add(employee);

                        new EmployeeDB().saveToDB(employee);
                    }
                    employeeListAdapter.setData(employeeArrayList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                hideProgressWheel(false);
            }

            @Override
            public void getErrorResponse(String error) {

                hideProgressWheel(false);
                showAlert(error, "", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getEmployeeList();
                    }
                });
            }
        });
    }

}