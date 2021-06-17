package com.babai.assignment;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("v1/employees")
    Call<EmployeesModel> getEmployeeData();
}
