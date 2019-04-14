package ap.efficient_farming;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    Button RegisterButton;
    URLs URLObject = new URLs();
    String BASE_URL = URLObject.URL_REGISTER;
    ArrayList<User> mUserDataList = new ArrayList<>();
    AutoCompleteTextView UserName, UserPhone, UserP, UserC;
    //int NumberOfRequestsCompleted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        UserName = findViewById(R.id.userName);
        UserPhone = findViewById(R.id.userNumber);
        UserP = findViewById(R.id.userPassword);
        UserC = findViewById(R.id.userCPassword);
        RegisterButton = findViewById(R.id.RegisterButton);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((UserP.getText().toString()).equals(UserC.getText().toString())) AttemptRegisterUser();
                else {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    protected void AttemptRegisterUser() {
        // Reset errors
        UserName.setError(null);
        UserPhone.setError(null);
        UserP.setError(null);

        // Store values at the time of the login attempt.
        String name = UserName.getText().toString();
        String phone = UserPhone.getText().toString();
        String password = UserP.getText().toString();
        Boolean cancel=false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(name)) {
            UserName.setError(getString(R.string.error_empty_field));
            focusView = UserName;
            cancel = true;
        }
        else if(TextUtils.isEmpty(phone)) {
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
            UserP.setError(getString(R.string.error_invalid_password));
            focusView = UserP;
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
            param.put("name", UserName.getText().toString().trim());
            param.put("phone", UserPhone.getText().toString().trim());
            param.put("c_password", UserP.getText().toString().trim());
            param.put("password", UserC.getText().toString().trim());

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
                            Toast.makeText(RegisterActivity.this, "You've been successfully registered!", Toast.LENGTH_LONG).show();
                            mainActivity();
                        } else {
                            Log.v("ERROR", "ERROR");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }.method(AQuery.METHOD_POST));//.header("secret_key ", SharedPreferencesMethod.getString(context, SharedPreferencesMethod.USER_TOKEN)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void mainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
