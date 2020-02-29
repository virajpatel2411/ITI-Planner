package royal.com.itiplanner;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.support.annotation.NonNull;


import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText edtEmail,edtPass;
    Button btnSn,btnSu;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtEmail = findViewById(R.id.edt_email);
        edtPass = findViewById(R.id.edt_password);
        btnSn = findViewById(R.id.btn_sn);
        btnSu = findViewById(R.id.btn_su);

        mAuth = FirebaseAuth.getInstance();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");


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
                while(true) {
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
                        mAuth.signInWithEmailAndPassword(edtEmail.getText().toString(),edtPass.getText().toString()).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    String strUId = mAuth.getUid();

                                    myRef.child(strUId).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                            UserModel userModel = dataSnapshot.getValue(UserModel.class);

                                            String strName  = userModel.getName();
                                            String strId  = mAuth.getUid();



                                            SharedPreferences sharedPreferences = getSharedPreferences("ITIPlanner",MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("NAME_KEY",strName);
                                            editor.putString("UID_KEY",strId);

                                            editor.commit();


                                            //Toast.makeText(MainActivity.this, "Login successfully.", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                            finish();


                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });


                                }
                                else
                                {
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
}
