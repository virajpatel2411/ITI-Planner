package royal.com.itiplanner;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    @Nullable

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    TextView txtView;
    String name;
    UserModel userModel;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        txtView = rootView.findViewById(R.id.txt_name);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ITIPlanner", MODE_PRIVATE);
        String strName = sharedPreferences.getString("NAME_KEY", "");

        txtView.setText(strName);
        return rootView;
    }
}
