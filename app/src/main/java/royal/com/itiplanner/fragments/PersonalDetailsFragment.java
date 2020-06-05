package royal.com.itiplanner.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import royal.com.itiplanner.R;
import royal.com.itiplanner.models.UserModel;

public class PersonalDetailsFragment extends Fragment {

  Button btnBack;
  private DatabaseReference myRef;
  private FirebaseAuth mAuth;
  TextView txtName, txtEmail, txtNumber;
  String phoneN;
  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.fragment_personal_details,container,false);
    btnBack = rootView.findViewById(R.id.btn_personal_details);
    txtName = rootView.findViewById(R.id.p_name);
    txtEmail = rootView.findViewById(R.id.p_email);
    txtNumber = rootView.findViewById(R.id.p_phone);
    mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    myRef = database.getReference("Users");

    myRef.child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
      @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        UserModel userModel = dataSnapshot.getValue(UserModel.class);
        txtName.setText(userModel.getName());
        txtEmail.setText(userModel.getEmail());
        phoneN = userModel.getMobNo();
        if(phoneN==null)
          phoneN = "Phone Number Not available";
        txtNumber.setText(phoneN);
      }

      @Override public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });


    btnBack.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        getActivity().onBackPressed();
      }
    });
    return rootView;
  }
}
