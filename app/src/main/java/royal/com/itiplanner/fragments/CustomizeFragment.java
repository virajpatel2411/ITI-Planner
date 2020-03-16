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
import royal.com.itiplanner.models.FinalModel;

public class CustomizeFragment extends Fragment
{
    SearchView searchView;
    ListView listView;
    ArrayList<String> states;
    ArrayAdapter<String> arrayAdapter;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<FinalModel> finalModels = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_customize,container,false);
        //Toast.makeText(getActivity(), "Customize:-Under Development", Toast.LENGTH_SHORT).show();

        searchView = rootView.findViewById(R.id.custom_search_view);
        listView = rootView.findViewById(R.id.custom_search_list);

        states = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Itinerary");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Log.e("Key",dataSnapshot1.getKey());
                    states.add(dataSnapshot1.getKey());
                }
                Log.e("checkpoint",states.get(0));
                arrayAdapter = new ArrayAdapter<String>(rootView.getContext(),android.R.layout.simple_list_item_1,states);

                listView.setAdapter(arrayAdapter);
            }

            @Override public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position,
                long id) {
                myRef = myRef.child(listView.getItemAtPosition(position).toString());
                states = new ArrayList<>();
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                            FinalModel finalModel = new FinalModel();
                            finalModel = dataSnapshot1.getValue(FinalModel.class);
                            finalModels.add(finalModel);
                            states.add(dataSnapshot1.getKey());

                        }
                        Fragment fragment = new PlaceSearchDisplay();
                        Bundle bundle = new Bundle();
                        bundle.putString("Place",dataSnapshot.getKey());
                        fragment.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.frame,fragment).commit();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }



        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                Fragment fragment = new PlaceSearchDisplay();
                Bundle bundle = new Bundle();
                bundle.putString("Place",s);
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frame,fragment).commit();
                return false;
            }

            @Override public boolean onQueryTextChange(String s) {
                arrayAdapter.getFilter().filter(s);
                return false;
            }
        });

        return rootView;
    }
}
