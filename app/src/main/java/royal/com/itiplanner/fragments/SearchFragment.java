package royal.com.itiplanner.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.Serializable;
import java.util.ArrayList;
import royal.com.itiplanner.R;
import royal.com.itiplanner.models.FinalModel;

public class SearchFragment extends Fragment {
  SearchView searchView;
  ListView listView;
  ArrayList<String> states;
  ArrayAdapter<String> arrayAdapter;
  FirebaseDatabase database;
  DatabaseReference myRef;
  ArrayList<FinalModel> finalModels;
  ProgressBar progressBar;

  @Nullable
  @Override
  public View onCreateView(@NonNull final LayoutInflater inflater,
      @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
    final View rootView = inflater.inflate(R.layout.fragment_search, container, false);
    progressBar = rootView.findViewById(R.id.progress);
    searchView = rootView.findViewById(R.id.search_view);
    listView = rootView.findViewById(R.id.list_search);
    finalModels = new ArrayList<>();
    states = new ArrayList<>();

    progressBar.setIndeterminate(true);
    progressBar.setVisibility(View.VISIBLE);

    database = FirebaseDatabase.getInstance();
    myRef = database.getReference("Itinerary");

    myRef.addValueEventListener(new ValueEventListener() {
      @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
          states.add(dataSnapshot1.getKey());
        }
        arrayAdapter =
            new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1,
                states);

        progressBar.setVisibility(View.INVISIBLE);
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
            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

              FinalModel finalModel = new FinalModel();
              finalModel = dataSnapshot1.getValue(FinalModel.class);
              finalModels.add(finalModel);
              states.add(dataSnapshot1.getKey());
            }

            Fragment fragment = new ItineraryDisplayFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("FINAL", (Serializable) finalModels);
            bundle.putStringArrayList("CITY", states);

            bundle.putString("STATE", listView.getItemAtPosition(position).toString());

            fragment.setArguments(bundle);

            getFragmentManager().beginTransaction()
                .replace(R.id.frame, fragment)
                .addToBackStack("homeFragment")
                .commit();
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
        });
      }
    });

    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override public boolean onQueryTextSubmit(String s) {
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
