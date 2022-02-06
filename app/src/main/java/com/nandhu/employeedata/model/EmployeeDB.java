package com.nandhu.employeedata.model;


import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Nandhu
 */
public class EmployeeDB {

    public ArrayList<Employee> getAll() {

        ArrayList<Employee> list = new ArrayList<>();

                Realm realm = Realm.getDefaultInstance();

                RealmQuery<Employee> query = realm.where(Employee.class);
                RealmResults<Employee> result1 = query.findAll();

                for (int i = 0; i < result1.size(); i++) {
                    list.add(result1.get(i));

                }
                //Collections.reverse(list);

        return list;

    }
    public ArrayList<Employee> getAllSearched(String text) {

        ArrayList<Employee> list = new ArrayList<>();

        Realm realm = Realm.getDefaultInstance();

        RealmQuery<Employee> query = realm.where(Employee.class).equalTo("name",text).or().equalTo("email",text);
        RealmResults<Employee> result1 = query.findAll();

        for (int i = 0; i < result1.size(); i++) {
            list.add(result1.get(i));

        }
        //Collections.reverse(list);

        return list;

    }


    public Employee getData(int employeeId){
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Employee> query = realm.where(Employee.class)
                .equalTo("id", employeeId);

        return query.findFirst();
    }

    public void saveToDB(Employee employee){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Employee realmObject=realm.createObject(Employee.class);
        realmObject.setId(employee.getId());
        realmObject.setName(employee.getName());
        realmObject.setUserName(employee.getUserName());
        realmObject.setEmail(employee.getEmail());
        realmObject.setProfileImage(employee.getProfileImage());
        realmObject.setPhone(employee.getPhone());
        realmObject.setWebsite(employee.getWebsite());
        realmObject.setStreet(employee.getStreet());
        realmObject.setSuite(employee.getSuite());
        realmObject.setCity(employee.getCity());
        realmObject.setZipCode(employee.getZipCode());
        realmObject.setLatitude(employee.getLatitude());
        realmObject.setLongitude(employee.getLongitude());
        realmObject.setCompanyName(employee.getCompanyName());
        realmObject.setCatchPhrase(employee.getCatchPhrase());
        realmObject.setBs(employee.getBs());
        realm.commitTransaction();
        realm.close();
    }




}
