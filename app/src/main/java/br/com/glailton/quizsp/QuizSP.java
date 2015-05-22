package br.com.glailton.quizsp;

import android.accounts.Account;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.example.games.basegameutils.BaseGameActivity;
import com.google.example.games.basegameutils.BaseGameUtils;
import com.google.android.gms.games.Games;
import com.google.android.gms.plus.Plus;

public class QuizSP extends Activity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener{

    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "Quiz";
    private static final String KEEP_CONECTED = "keepConnected";

    private GoogleApiClient mGoogleApiClient;

    private CheckBox keepConected;

  //  private SharedPreferences preferences;
    private Account account;

    private ProgressDialog mConnectionProgressDialog;

    private boolean mIntentInProgress;

    private boolean mSignInClicked;

    // Set to true to automatically start the sign in flow when the Activity starts.
    // Set to false to require the user to click the button in order to sign in.
    private boolean mAutoStartSignInFlow = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
                  .addApi(Plus.API)
             //   .addApi(Games.API).addScope(Games.SCOPE_GAMES)
            //    .addScope(new Scope("profile"))
                .build();

        findViewById(R.id.button_sign_in).setOnClickListener(this);

    /*    keepConected = (CheckBox) findViewById(R.id.keep_conected);

        SharedPreferences preferences = getSharedPreferences(Constant.PREFERENCES, MODE_PRIVATE);
        boolean conected = preferences.getBoolean(KEEP_CONECTED, false);

        Log.v(TAG, "" + conected);

        if(conected){
            startDashboard();
        }*/

    }

    // Shows the "sign in" bar (explanation and button).
    private void showSignInBar() {
        Log.d(TAG, "Showing sign in bar");
        findViewById(R.id.sign_in_bar).setVisibility(View.VISIBLE);
        findViewById(R.id.sign_out_bar).setVisibility(View.GONE);
    }

    // Shows the "sign out" bar (explanation and button).
    private void showSignOutBar() {
        Log.d(TAG, "Showing sign out bar");
        findViewById(R.id.sign_in_bar).setVisibility(View.GONE);
        findViewById(R.id.sign_out_bar).setVisibility(View.VISIBLE);
    }

    private void startDashboard() {
        startActivity(new Intent(this, DashboardActivity.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
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

  /*  public void loginAppGoogle(View view){
        Log.v(TAG, "ta entrando");
        if (view.getId() == R.id.button_google && !mGoogleApiClient.isConnected()){
            mSignInClicked = true;
            mGoogleApiClient.connect();
        }

    }*/

    public void loginAppFacebook(View view){
        startActivity(new Intent(this, DashboardActivity.class));
        Log.v("Glailton", "entrou");
    }

    @Override
    protected void onStart() {
        super.onStart();
     //   mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
     //   this.finish();

     /*   if(mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }*/
    }

    @Override
    public void onConnected(Bundle bundle) {
        mSignInClicked = false;

       /* SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEEP_CONECTED, keepConected.isChecked());
        editor.commit();*/

      //  Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
        showSignOutBar();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (!mIntentInProgress) {
            if (!mIntentInProgress && connectionResult.hasResolution()) {
                try {
                    mIntentInProgress = true;
                    connectionResult.startResolutionForResult(this, RC_SIGN_IN);
                } catch (IntentSender.SendIntentException e) {
                    mIntentInProgress = false;
                    mGoogleApiClient.reconnect();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnected()) {
                mGoogleApiClient.reconnect();
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnected()) {
                mGoogleApiClient.reconnect();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_sign_in && !mGoogleApiClient.isConnected()){
            mSignInClicked = true;
            mGoogleApiClient.connect();

            Log.v(TAG, "" + keepConected);

            startDashboard();
            Log.v(TAG,"conectou");
        }
    }

}

