package royal.com.itiplanner;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    @Nullable

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    TextView txtView;
    String name;
    UserModel userModel;
    RecyclerView recyclerView;


    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        txtView = rootView.findViewById(R.id.txt_name);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ITIPlanner", MODE_PRIVATE);
        String strName = sharedPreferences.getString("NAME_KEY", "");
        String personName = "";

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (acct != null) {
            personName = acct.getDisplayName();
        }
        if(strName == "" && personName != "")
        {
            strName = personName;
        }

        txtView.setText(strName);

        String place[] = {"Kerala","Goa","Chennai","Kashmir","Shimla"};
        String amt[] = {"4000","5000","6000","7000","8000"};
        String no_of_people[] = {"4","5","6","7","8"};
        String no_of_days[] = {"4","5","7","5","4"};
        int imgs[] = {R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.one,R.drawable.two};

        ArrayList<HomePageItineraryModel> arrayList = new ArrayList<>();
        for(int i=0;i<amt.length;i++)
        {
            HomePageItineraryModel homePageItineraryModel = new HomePageItineraryModel();
            homePageItineraryModel.setAmt(amt[i]);
            homePageItineraryModel.setNo_of_days(no_of_days[i]);
            homePageItineraryModel.setNo_of_people(no_of_people[i]);
            homePageItineraryModel.setPlace(place[i]);
            arrayList.add(homePageItineraryModel);
        }

        recyclerView = rootView.findViewById(R.id.rec_home);
        RecyclerHomeAdapter recyclerHomeAdapter = new RecyclerHomeAdapter(rootView.getContext(),arrayList,imgs);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        recyclerView.setAdapter(recyclerHomeAdapter);
        return rootView;
    }
}
