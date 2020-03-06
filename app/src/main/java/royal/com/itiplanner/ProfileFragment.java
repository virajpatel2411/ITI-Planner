package royal.com.itiplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {
    ListView listView;
    Button btnLogout;
    String[] str = {"Personal Details", "History", "Feedback", "About Us", "Settings"};
    int[] img = {R.drawable.ic_account, R.drawable.ic_history, R.drawable.ic_feedback, R.drawable.ic_help, R.drawable.ic_settings};
    private FirebaseAuth mAuth;
    private String strGoogle;
    private GoogleSignInClient mGoogleSignInClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        listView = rootView.findViewById(R.id.list);
        mAuth = FirebaseAuth.getInstance();


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ITIPlanner", MODE_PRIVATE);
        strGoogle = sharedPreferences.getString("GOOGLE_KEY", "");

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getResources().getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(),googleSignInOptions);

       // mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), googleSignInOptions);
        MyListAdapter listAdapter = new MyListAdapter(getContext(), str, img);
        listView.setAdapter(listAdapter);
        btnLogout = rootView.findViewById(R.id.btn_lo);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ITIPlanner", MODE_PRIVATE);
                String uId = sharedPreferences.getString("UID_KEY", "");
                if (!uId.equals("")) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("NAME_KEY", "");
                    editor.putString("UID_KEY", "");

                    editor.commit();
                }
                if (strGoogle.equals("GOOGLE")){

                    mAuth.signOut();

                    // Google sign out
                    mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(),
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();

                                }
                            });
                }else {


                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();


                }




            }

        });
        return rootView;
    }
}
