package ap.efficient_farming;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;

public class FirstActivity extends AppCompatActivity {

        Button LButton, RButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FullScreen Code
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );*/
        setContentView(R.layout.activity_first);
        LButton = (Button) findViewById(R.id.LButton);
        RButton = (Button) findViewById(R.id.RButton);
        LButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1= new Intent(FirstActivity.this,LoginActivity.class);
                startActivity(i1);
            }
        });
        RButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(FirstActivity.this,RegisterActivity.class);
                startActivity(i2);
            }
        });
    }

}
