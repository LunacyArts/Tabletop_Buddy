package lunacyarts.abgstudios.butany62.accessories.dndapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CommsTester extends AppCompatActivity implements CommsInterface {

    EditText etUsername;
    EditText etPassword;
    EditText etUrl;
    Button bAttemptAuth;
    CommsInterface commsInterface = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comms_tester);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        //set up xml resources
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etUrl = (EditText) findViewById(R.id.etUrl);
        bAttemptAuth = (Button) findViewById(R.id.bAuth);

        //set click function on button
        bAttemptAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //create a new comms client with the URL of the server and .this
                    CommsClient cc = new CommsClient(etUrl.getText().toString(), commsInterface);

                    //run the auth method to get a session ID
                    cc.auth(etUsername.getText().toString(),
                            etPassword.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    //these methods are where you'll handle a network failure or success
    //responses will be either a string or JSON object encoded as a string
    //depending on which task. this will be documented as we build on
    //the network api functionality.
    @Override
    public void handleFail(Exception e, int taskID) {

        Toast.makeText(CommsTester.this, "Oh no it failed!" + e.getMessage(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void handleSuccess(String s, int taskID) {
        //auth task ID is 0
        Toast.makeText(CommsTester.this, "Got success from task " + taskID + ": " + s, Toast.LENGTH_LONG).show();


    }
}
