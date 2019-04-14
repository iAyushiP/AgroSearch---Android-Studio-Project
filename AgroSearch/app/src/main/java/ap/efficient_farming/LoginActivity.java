package ap.efficient_farming;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    Button LoginButton;
    AutoCompleteTextView UserPhone, UserPassword;
    String BASE_URL = new URLs().URL_LOGIN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginButton = findViewById(R.id.LoginButton);
        UserPhone = findViewById(R.id.userNumber);
        UserPassword = findViewById(R.id.loginPassword);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AttemptLogin();
            }
        });

    }
    protected void AttemptLogin() {
        // Reset errors
        UserPhone.setError(null);
        UserPassword.setError(null);

        // Store values at the time of the login attempt.
        String phone = UserPhone.getText().toString();
        String password = UserPassword.getText().toString();
        Boolean cancel=false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if(TextUtils.isEmpty(phone)) {
            UserPhone.setError(getString(R.string.error_empty_field));
            focusView = UserPhone;
            cancel = true;
        }
        else if(phone_invalid(phone)) {
            UserPhone.setError(getString(R.string.error_invalid_phone));
            focusView = UserPhone;
            cancel = true;
        }
        else if (!TextUtils.isEmpty(password)&&password_invalid(password)){
            UserPassword.setError(getString(R.string.error_invalid_password));
            focusView = UserPassword;
            cancel = true;
        }
        if (!cancel) RegisterUser();
    }
    private boolean phone_invalid(String phone) {
        if(phone.length()!=10) return true;
        return false;
    }
    private boolean password_invalid(String password) {
        if(password.length()<6) return true;
        return false;
    }
    private void RegisterUser() {
        try {
            HashMap<String, String> param = new HashMap<>();
            param.put("phone", UserPhone.getText().toString().trim());
            param.put("password", UserPassword.getText().toString().trim());

            AQuery aq = new AQuery(this);

            aq.ajax(BASE_URL, param,  JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject json, AjaxStatus status) {
                    super.callback(url, json, status);

                    Log.v("CALLBACK RECEIVED" , String.valueOf(json) + status);

                    try {
                        if (json != null) {
                            JSONObject h = json.getJSONObject("success");

                            Log.v("SUCCESS" ,h.getString("token") );
                            Toast.makeText(LoginActivity.this, "You've been successfully logged in!", Toast.LENGTH_LONG).show();
                            mainActivity();
                        } else {
                            Log.v("ERROR", "ERROR");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }.method(AQuery.METHOD_POST));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void mainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
