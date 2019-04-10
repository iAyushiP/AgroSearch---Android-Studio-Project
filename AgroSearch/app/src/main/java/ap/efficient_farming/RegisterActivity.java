package ap.efficient_farming;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
                if((UserP.getText().toString()).equals(UserC.getText().toString())) RegisterUser();
                else {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void RegisterUser() {
        try {
            HashMap<String, String> param = new HashMap<>();
            param.put("name", UserName.getText().toString());
            param.put("phone", UserPhone.getText().toString());
            param.put("c_password", UserP.getText().toString());
            param.put("password", UserC.getText().toString());

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
