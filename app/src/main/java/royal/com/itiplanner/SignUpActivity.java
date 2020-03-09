package royal.com.itiplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
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

public class SignUpActivity extends AppCompatActivity {

    EditText edtEmail, edtMob, edtPass, edtName;
    Button btnNext;
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
        mAuth = FirebaseAuth.getInstance();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                while (true) {


                    if (TextUtils.isEmpty(edtEmail.getText().toString()) || !Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
                        edtEmail.setError("Please enter a valid Email ID!");
                        break;
                    }
                    if (TextUtils.isEmpty(edtPass.getText().toString()) || edtPass.getText().toString().length() < 6) {
                        edtPass.setError(" Password too short. Minimum 6 alphanumeric characters.");
                        Toast.makeText(SignUpActivity.this, "pass<6", Toast.LENGTH_SHORT).show();
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


                        mAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(), edtPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String key = mAuth.getUid();
                                    UserModel userModel = new UserModel();
                                    userModel.setName(edtName.getText().toString());
                                    userModel.setEmail(edtEmail.getText().toString());
                                    userModel.setMobNo(edtMob.getText().toString());
                                    //userModel.setPassWord(edtPass.getText().toString());
                                    myRef.child(key).setValue(userModel);
                                    //Toast.makeText(SignUpActivity.this, myRef.child(key).getParent().toString(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(SignUpActivity.this, "SignUp Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        break;
                    }
                }
            }
        });
    }
}
