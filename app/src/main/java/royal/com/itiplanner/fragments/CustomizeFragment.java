package royal.com.itiplanner.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.Serializable;
import java.util.ArrayList;
import royal.com.itiplanner.R;
import royal.com.itiplanner.adapters.SearchAdapter;
import royal.com.itiplanner.models.FinalModel;

public class CustomizeFragment extends Fragment
{
    AutoCompleteTextView autoCompleteTextView;
    SearchAdapter searchAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_customize,container,false);
        //Toast.makeText(getActivity(), "Customize:-Under Development", Toast.LENGTH_SHORT).show();

        autoCompleteTextView = rootView.findViewById(R.id.custom_search_view);
        searchAdapter = new SearchAdapter(rootView.getContext(),android.R.layout.simple_list_item_1);
        autoCompleteTextView.setAdapter(searchAdapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = autoCompleteTextView.getText().toString();
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                Fragment fragment = new PlaceSearchDisplay();
                Bundle bundle = new Bundle();
                bundle.putString("Place",s);
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frame,fragment).commit();
            }
        });


        return rootView;
    }
}
