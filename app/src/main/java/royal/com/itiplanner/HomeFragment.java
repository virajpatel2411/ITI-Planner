package royal.com.itiplanner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {
    @Nullable

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    TextView txtView;
    String name;
    UserModel userModel;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home,container,false);

        mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        myRef = firebaseDatabase.getReference("Users");



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {

                    userModel = dataSnapshot1.getValue(UserModel.class);
                    name = userModel.getName();
                    break;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                 name = "ABC";
            }
        });

        Toast.makeText(getContext(), name , Toast.LENGTH_SHORT).show();

        txtView = rootView.findViewById(R.id.txt_name);
        //txtView.setText(name);

        return rootView;
    }
}
