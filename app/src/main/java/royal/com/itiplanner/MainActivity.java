package royal.com.itiplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText edtEmail, edtPass;
    Button btnSn, btnSu;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;
    private String TAG = MainActivity.class.getName();
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtEmail = findViewById(R.id.edt_email);
        edtPass = findViewById(R.id.edt_password);
        btnSn = findViewById(R.id.btn_sn);
        btnSu = findViewById(R.id.btn_su);

        signInButton = (SignInButton) findViewById(R.id.google_sn);

        mAuth = FirebaseAuth.getInstance();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getResources().getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        /* mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).build();*/


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleSignInClient);
                // Intent signInIntent  = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 10);

            }
        });


        btnSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btnSn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                while (true) {
                    if (TextUtils.isEmpty(edtEmail.getText().toString()) || !Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
                        edtEmail.setError("Please enter a valid Email ID!");
                        break;
                    }
                    if (TextUtils.isEmpty(edtPass.getText().toString()) || edtPass.getText().toString().length() < 6) {
                        edtPass.setError(" Password too short. Minimum 6 alphanumeric characters.");
                        Toast.makeText(MainActivity.this, "pass<6", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if (Pattern.matches("[a-zA-Z]+$", edtPass.getText().toString())) {
                        edtPass.setError("Please at least use a character from 0-9.");
                        break;
                    }
                    if (Pattern.matches("[0-9]+$", edtPass.getText().toString())) {
                        edtPass.setError("Please at least use a character from A-Z.");
                        break;
                    }
                    if (!(TextUtils.isEmpty(edtEmail.getText().toString()) &&
                            Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) &&
                            !Pattern.matches("[0-9]+$", edtPass.getText().toString()) &&
                            !Pattern.matches("[a-zA-Z]+$", edtPass.getText().toString())
                    ) {
                        mAuth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtPass.getText().toString()).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String strUId = mAuth.getUid();

                                    myRef.child(strUId).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                            UserModel userModel = dataSnapshot.getValue(UserModel.class);

                                            String strName = userModel.getName();
                                            String strId = mAuth.getUid();


                                            SharedPreferences sharedPreferences = getSharedPreferences("ITIPlanner", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("NAME_KEY", strName);
                                            editor.putString("UID_KEY", strId);

                                            editor.commit();


                                            //Toast.makeText(MainActivity.this, "Login successfully.", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                            finish();


                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });


                                } else {
                                    Toast.makeText(MainActivity.this, "Login failed. Incorrect details.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        break;
                    }
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 10) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {

                GoogleSignInAccount googleSignInAccount = task.getResult(ApiException.class);
                firebasebaseLogin(googleSignInAccount);

            } catch (ApiException e) {

            }
        }
    }

    private void firebasebaseLogin(GoogleSignInAccount googleSignInAccount) {

        AuthCredential credential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String key = mAuth.getUid();

                            UserModel userModel = new UserModel();
                            userModel.setName(user.getDisplayName());
                            userModel.setEmail(user.getEmail());
                            userModel.setMobNo(user.getPhoneNumber());
                            //userModel.setPassWord(edtPass.getText().toString());
                            myRef.child(key).setValue(userModel);
                            //Toast.makeText(SignUpActivity.this, myRef.child(key).getParent().toString(), Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();


                            //FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);

                            Intent i = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }

}
