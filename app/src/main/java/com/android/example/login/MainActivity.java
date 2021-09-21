package com.android.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.example.login.databinding.ActivityMainBinding;
import com.android.example.login.model.User;
import com.android.example.login.util.RestApi;
import com.android.example.login.util.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private final View.OnClickListener loginClickListener = v -> loginButtonCLicked();

    private void loginButtonCLicked() {
        Log.i("Login Button","CLicked");
        String email;
        email = binding.emailEditText.getEditText().getText().toString().trim();
        String password;
        password = binding.pwEditText.getEditText().getText().toString().trim();
        if(checkEmpty(email,password)) {
            System.out.println("Email :"+email+" ,Password :"+password);
            createPost(email,password);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setUpButton();




//        User user = new User("interntest@cinquex.com","Iamtesting@987");
//
//        RestApi apiService = RetrofitUtil.getRetrofitClient().create(RestApi.class);
//
//        Call<User> call = apiService.createPost(user);
//
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                Log.i("response", String.valueOf(response.code()));
//                User Muser = response.body();
//                String message = Muser.getMessage();
//                Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Log.i("response", t.getMessage());
//            }
//        });



    }

    private void createPost(String email, String password){

        User user = new User(email,password);

        RestApi apiService = RetrofitUtil.getRetrofitClient().create(RestApi.class);

        Call<User> call = apiService.createPost(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.i("response", String.valueOf(response.code()));
                if(response.body() != null) {
                    User Muser = response.body();

                        String message = Muser.getMessage();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        showDialog(message);

                }else
                {
                    if(response.code() == 400){
                        showDialog("Email Format is Wrong");
                    }else{
                        showDialog(" Wrong Credentials");

                    }
                    //Toast.makeText(getApplicationContext(), "Wrong Username Or Password", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("response", t.getMessage());
                Toast.makeText(getApplicationContext(), "Connection Failed: Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialog(String message){

        DialogFragment dialogFragment = new ResponseDialog();
        Bundle mBundle = new Bundle();
        mBundle.putString("Response",message);
        dialogFragment.setArguments(mBundle);
        dialogFragment.show(getSupportFragmentManager(), "Response");

    }

    private boolean checkEmpty(String email, String password){

        if(email.equals("")){
            Log.i("email empty","Email cannot be empty");
            Toast.makeText(getApplicationContext(),"Email cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(password.equals("")){
            Log.i("Password empty","Password cannot be empty");
            Toast.makeText(getApplicationContext(),"Password cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void setUpButton(){
        binding.loginButton.setOnClickListener(loginClickListener);
    }

}