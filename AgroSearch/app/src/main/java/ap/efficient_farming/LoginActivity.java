package ap.efficient_farming;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button LoginButton;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginButton = (Button) findViewById(R.id.LoginButton);
        /*LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

    }
}
