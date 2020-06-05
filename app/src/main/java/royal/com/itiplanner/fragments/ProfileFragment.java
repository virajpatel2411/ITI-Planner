package royal.com.itiplanner.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import royal.com.itiplanner.activities.MainActivity;
import royal.com.itiplanner.R;
import royal.com.itiplanner.adapters.MyListAdapter;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {
    ListView listView;
    Button btnLogout;
    String[] str = {"Personal Details", "History", "Feedback", "Scheduled Trips" ,"About Us"};
    int[] img = { R.drawable.ic_account, R.drawable.ic_history, R.drawable.ic_feedback, R.drawable.ic_help, R.drawable.ic_settings};
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        Fragment fragment2 = new PersonalDetailsFragment();
                        getFragmentManager().beginTransaction().replace(R.id.frame,fragment2).addToBackStack("homeFragment").commit();
                        break;
                    case 1:
                        Fragment fragment = new HistoryFragment();
                        getFragmentManager().beginTransaction().replace(R.id.frame,fragment).addToBackStack("homeFragment").commit();

                        break;
                    case 2:
                        Fragment fragment1 = new FeedbackFragment();
                        getFragmentManager().beginTransaction().replace(R.id.frame,fragment1).addToBackStack("homeFragment").commit();

                        break;
                    case 3:
                        break;
                    case 4:
                        Fragment fragment3 = new AboutUsFragment();
                        getFragmentManager().beginTransaction().replace(R.id.frame,fragment3).addToBackStack("homeFragment").commit();
                        break;
                }
            }
        });


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
