package dk.iot.remember;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String DEBUG_TAG = MainActivity.class.getSimpleName();

    private TextView txtInfo;
    private Button btnScan, btnSignIn;
    private EditText edtEmail;
    private EditText edtPassword;
    private NetworkManager networkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        networkManager = new NetworkManager(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnSignIn = (Button) findViewById(R.id.btn_sign_in);
        btnSignIn.setOnClickListener(this);
        txtInfo = (TextView) findViewById(R.id.txt_info);
        btnScan = (Button) findViewById(R.id.btn_scan);
        btnScan.setOnClickListener(this);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtPassword = (EditText) findViewById(R.id.edt_password);


        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                testApi();

            }
        });
    }

    private void signUserIn() {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        if(email.length() > 0 && password.length() > 0) {
            User user = new User(email, password);
            networkManager.signIn(user);
            Log.d(DEBUG_TAG, "User: " + user.toString());
        } else {
            Toast.makeText(this, "You need to provide an email and password before using the app!", Toast.LENGTH_SHORT).show();
        }
    }

    private void scan() {

    }

    private void testApi() {
        Board board = new Board("keys", UUID.randomUUID().toString());
        networkManager.addBoard(board);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(DEBUG_TAG, "Intent: " + intent);
        if (intent != null && NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if(rawMessages != null) {
                NdefMessage[] messages = new NdefMessage[rawMessages.length];
                for (int i = 0; i < rawMessages.length; i++) {
                    messages[i] = (NdefMessage) rawMessages[i];
                }
                Log.d(DEBUG_TAG, "MEssages from NFC: " + messages.toString());
                txtInfo.setText(messages.toString());
                // Process the message array
            }
        }
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == btnSignIn.getId()) {
            signUserIn();
        }

        if(v.getId() == btnScan.getId()) {
            scan();
        }
    }
}
