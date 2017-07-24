package vayu.tech.equilibrium;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);


        Intent intent = new Intent(FirstActivity.this, LoginActivity.class);
        startActivity(intent);

        // TODO: Based on who's logged in, determine where to go
        // For now, we're just going to the login screen
    }


}
