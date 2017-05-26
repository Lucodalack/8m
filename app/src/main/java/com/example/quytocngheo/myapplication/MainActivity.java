package com.example.quytocngheo.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.quytocngheo.myapplication.Model.ListQuestion;
import com.example.quytocngheo.myapplication.Model.ResponseLogin;
import com.example.quytocngheo.myapplication.Model.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.Arrays;

import bolts.Continuation;
import bolts.Task;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewById(R.id.login_button)
    LoginButton loginButton;
    protected CallbackManager callbackManager;
    @Override
    void init() {
        super.init();
        loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));
        AppEventsLogger.activateApp(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(MainActivity.this, loginResult.getAccessToken().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "dcm", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(MainActivity.this, "dcm", Toast.LENGTH_SHORT).show();
            }
        });

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        AccessToken accessToken = loginResult.getAccessToken();
                        GraphRequest request = GraphRequest.newMeRequest(accessToken,
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        try {
                                            String userID = (String) object.get("id");
                                            String userName = (String) object.get("name");
                                            String birthday = object.isNull("birthday")?"":object.getString("birthday");
                                            birthday = birthday.replace("\"","");
                                            String avatar = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                            avatar = avatar.replace("\"","");
                                            String gender = object.getString("gender");
                                            String email = object.isNull("email")?"":object.getString("email");
                                            User user_fb = new User(userID, userName, birthday, gender, avatar,email);
                                            pushUser(user_fb);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,link,birthday,picture");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(MainActivity.this, "dcm", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(MainActivity.this, "dcm", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    @Click(R.id.button)
    void getQuestion() {

        HomeActivity_.intent(MainActivity.this).start();
    }

    @Background
    void pushUser(User user) {
        Task<ResponseLogin> task=retrofitService.pushUser(user);
        task.continueWith(new Continuation<ResponseLogin, Object>() {
            @Override
            public Object then(Task<ResponseLogin> task) throws Exception {
                if(task.isFaulted()||task.isCancelled()){

                }else {
                    ResponseLogin tamp=task.getResult();
                    if(tamp.isLogin_status())
                    HomeActivity_.intent(MainActivity.this).start();
                }
                return null;
            }
        },Task.UI_THREAD_EXECUTOR);
    }

}
