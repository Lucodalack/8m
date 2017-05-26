package com.example.quytocngheo.myapplication.Service;

import com.example.quytocngheo.myapplication.Model.ListQuestion;
import com.example.quytocngheo.myapplication.Model.ResponseLogin;
import com.example.quytocngheo.myapplication.Model.Store;
import com.example.quytocngheo.myapplication.Model.User;

import java.io.IOException;
import java.util.List;

import bolts.Task;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by quytocngheo on 5/21/2017.
 */

public class ServiceHelper {
    private API retrofitInterface;

    public ServiceHelper (API retrofitServiceInterface) {
        this.retrofitInterface = retrofitServiceInterface;
        this.retrofitInterface=ServiceGenerator.createService(API.class);
    }

    public Task<ListQuestion> getCurrentUser() {
        Call<ListQuestion> call=null;
        try{
            call=retrofitInterface.loadQuestions("android");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        Task<ListQuestion> task=null;
        try{
            Response<ListQuestion> response=call.execute();
            if(response.code()==401) return null;
            else {
                task= Task.forResult(response.body());
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return task;
    }

    public Task<List<Store>> loadStore() {
        Call<List<Store>> call=null;
        try{
            call=retrofitInterface.loadStore();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        Task<List<Store>> task=null;
        try{
            Response<List<Store>> response=call.execute();
            if(response.code()==401) return null;
            else {
                task= Task.forResult(response.body());
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return task;
    }

    public Task<ResponseLogin> pushUser(User user) {
        Call<ResponseLogin> call=null;
        try{
            call=retrofitInterface.pushUser(user);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        Task<ResponseLogin> task=null;
        try{
            Response<ResponseLogin> response=call.execute();
            if(response.code()==401) return null;
            else {
                task= Task.forResult(response.body());
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return task;
    }
}
