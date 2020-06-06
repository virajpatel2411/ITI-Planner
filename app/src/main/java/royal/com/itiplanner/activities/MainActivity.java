package royal.com.itiplanner.activities;

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
import royal.com.itiplanner.R;
import royal.com.itiplanner.models.UserModel;

public class MainActivity extends AppCompatActivity {

  EditText edtEmail, edtPass;
  Button btnSn, btnSu;
  SignInButton signInButton;
  GoogleSignInClient mGoogleSignInClient;
  private FirebaseAuth mAuth;
  private DatabaseReference myRef;
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

    GoogleSignInOptions googleSignInOptions =
        new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
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
          if (TextUtils.isEmpty(edtEmail.getText().toString()) || !Patterns.EMAIL_ADDRESS.matcher(
              edtEmail.getText().toString()).matches()) {
            edtEmail.setError("Please enter a valid Email ID!");
            break;
          }
          if (TextUtils.isEmpty(edtPass.getText().toString())) {
            edtPass.setError("Please enter Passsword!");
            break;
          }
          if (!(TextUtils.isEmpty(edtEmail.getText().toString()) &&
              Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) &&
              !Pattern.matches("[0-9]+$", edtPass.getText().toString()) &&
              !Pattern.matches("[a-zA-Z]+$", edtPass.getText().toString())
          ) {
            mAuth.signInWithEmailAndPassword(edtEmail.getText().toString(),
                edtPass.getText().toString())
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
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

                          SharedPreferences sharedPreferences =
                              getSharedPreferences("ITIPlanner", MODE_PRIVATE);
                          SharedPreferences.Editor editor = sharedPreferences.edit();
                          editor.putString("NAME_KEY", strName);
                          editor.putString("UID_KEY", strId);

                          editor.commit();

                          startActivity(new Intent(MainActivity.this, HomeActivity.class));
                          finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                      });
                    } else {
                      Toast.makeText(MainActivity.this,
                          "Login Failed.\nPlease Enter Valid Credentials.", Toast.LENGTH_SHORT)
                          .show();
                    }
                  }
                });

            break;
          } else {
            edtPass.setError("Please Enter a valid Password.");
            break;
          }
        }
      }
    });
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == 10) {
      Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

      try {

        GoogleSignInAccount googleSignInAccount = task.getResult(ApiException.class);
        firebasebaseLogin(googleSignInAccount);
      } catch (ApiException e) {

      }
    }
  }

  private void firebasebaseLogin(GoogleSignInAccount googleSignInAccount) {

    AuthCredential credential =
        GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
    mAuth.signInWithCredential(credential)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
              Log.e(TAG, "signInWithCredential:success");
              FirebaseUser user = mAuth.getCurrentUser();
              String key = mAuth.getUid();

              UserModel userModel = new UserModel();
              userModel.setName(user.getDisplayName());
              userModel.setEmail(user.getEmail());
              userModel.setMobNo(user.getPhoneNumber());
              myRef.child(key).setValue(userModel);

              SharedPreferences sharedPreferences =
                  getSharedPreferences("ITIPlanner", MODE_PRIVATE);
              SharedPreferences.Editor editor = sharedPreferences.edit();
              editor.putString("NAME_KEY", user.getDisplayName());
              editor.putString("UID_KEY", key);

              editor.commit();

              Intent intent = new Intent(MainActivity.this, HomeActivity.class);
              startActivity(intent);
              finish();

              Intent i = new Intent(MainActivity.this, HomeActivity.class);
              startActivity(i);
              finish();
            } else {
              Log.w(TAG, "signInWithCredential:failure", task.getException());
              Toast.makeText(MainActivity.this, "" + task.getException(), Toast.LENGTH_SHORT)
                  .show();
            }
          }
        });
  }
}
