package royal.com.itiplanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.util.PatternsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.regex.Pattern;
import royal.com.itiplanner.R;
import royal.com.itiplanner.models.UserModel;

public class SignUpActivity extends AppCompatActivity {

  EditText edtEmail, edtMob, edtPass, edtName;
  Button btnNext, btnBack;
  private FirebaseAuth mAuth;
  private DatabaseReference myRef;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);
    btnNext = findViewById(R.id.btn_next);
    edtEmail = findViewById(R.id.edt_email);
    edtMob = findViewById(R.id.edt_number);
    edtPass = findViewById(R.id.edt_password);
    edtName = findViewById(R.id.edt_name);
    btnBack = findViewById(R.id.signup_back);
    mAuth = FirebaseAuth.getInstance();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    myRef = database.getReference("Users");

    btnBack.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        onBackPressed();
      }
    });

    btnNext.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        while (true) {
          if (!validateName(edtName.getText().toString())) {
            edtName.setError("Name cannot be null");
            break;
          } else if (!validateEmail(edtEmail.getText().toString())) {
            edtEmail.setError("Please enter a valid Email ID!");
            break;
          } else if (!validateMob(edtMob.getText().toString())) {
            edtMob.setError("Enter Valid Mobile Number");
            break;
          } else if (!validatePassEmpty(edtPass.getText().toString())) {
            edtPass.setError(" Password too short. Minimum 6 alphanumeric characters.");
            break;
          } else if (!validatePassAlphabet(edtPass.getText().toString())) {
            edtPass.setError("Please at least use a character from 0-9.");
            break;
          } else if (!validatePassNumeric(edtPass.getText().toString())) {
            edtPass.setError("Please at least use a character from A-Z.");
            break;
          } else {

            mAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(),
                edtPass.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                      String key = mAuth.getUid();
                      UserModel userModel = new UserModel();
                      userModel.setName(edtName.getText().toString());
                      userModel.setEmail(edtEmail.getText().toString());
                      userModel.setMobNo(edtMob.getText().toString());
                      myRef.child(key).setValue(userModel);
                      Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                      startActivity(intent);
                      finish();
                    } else {
                      Toast.makeText(SignUpActivity.this, "SignUp Failed", Toast.LENGTH_SHORT)
                          .show();
                    }
                  }
                });
            break;
          }
        }
      }
    });
  }

  public boolean validateName(String name) {
    if (name.equals("")) {
      return false;
    }
    return true;
  }

  public boolean validateEmail(String email) {
    if (email.equals("") || !PatternsCompat.EMAIL_ADDRESS.matcher(
        email).matches()) {
      return false;
    }
    return true;
  }

  public boolean validateMob(String mobNo) {
    if (mobNo.equals("") || (mobNo.length() < 8 || mobNo.length() > 10)) {
      return false;
    }
    return true;
  }

  public boolean validatePassEmpty(String pass) {
    if (pass.equals("")
        || pass.length() < 6) {
      return false;
    }
    return true;
  }

  public boolean validatePassAlphabet(String pass) {
    if (Pattern.matches("[a-zA-Z]+$", pass)) {
      return false;
    }
    return true;
  }

  public boolean validatePassNumeric(String pass) {
    if (Pattern.matches("[0-9]+$", pass)) {
      return false;
    }
    return true;
  }

  @Override public void onBackPressed() {
    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
    startActivity(intent);
    finish();
  }
}
