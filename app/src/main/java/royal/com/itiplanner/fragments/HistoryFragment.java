package royal.com.itiplanner.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import royal.com.itiplanner.R;
import royal.com.itiplanner.adapters.DisplayItineraryAdapter;
import royal.com.itiplanner.adapters.RecyclerDisplayAdapter;
import royal.com.itiplanner.models.DisplayItineraryModel;
import royal.com.itiplanner.models.FinalModel;

import static android.content.Context.MODE_PRIVATE;

public class HistoryFragment extends Fragment {
  RecyclerView recyclerView;
  FirebaseDatabase database;
  DatabaseReference myRef1,myRef2,myRef;
  ArrayList<String> states;
  ArrayList<String> ids;
  ArrayList<DisplayItineraryModel> arrayList;
  ArrayList<FinalModel> finalModels;
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    final View rootView = inflater.inflate(R.layout.fragment_history,container,false);
    recyclerView = rootView.findViewById(R.id.rec_history);
    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ITIPlanner", MODE_PRIVATE);
    String uId = sharedPreferences.getString("UID_KEY", "");
    database = FirebaseDatabase.getInstance();

    finalModels = new ArrayList<>();
    arrayList = new ArrayList<>();
    states = new ArrayList<>();
    ids = new ArrayList<>();

    myRef = database.getReference("History");
    myRef = myRef.child(uId);
    myRef.addValueEventListener(new ValueEventListener() {
      @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
        {
          String key = dataSnapshot1.getKey();
          states.add(key);
        }


        myRef1 = database.getReference("Itinerary");
        myRef1.addValueEventListener(new ValueEventListener() {
          @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
              String key = dataSnapshot1.getKey();
              ids.add(key);
            }


            for(String id:ids)
            {
              Log.e("a",id);
              myRef2 = database.getReference("Itinerary").child(id);
              myRef2.addValueEventListener(new ValueEventListener() {
                @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    if(states.contains(dataSnapshot1.getKey())){
                      FinalModel finalModel = dataSnapshot1.getValue(FinalModel.class);
                      finalModels.add(finalModel);
                      DisplayItineraryModel displayItineraryModel = new DisplayItineraryModel();
                      displayItineraryModel.setCity(dataSnapshot1.getKey());
                      displayItineraryModel.setFinalModel(finalModel);
                      arrayList.add(displayItineraryModel);
                    }
                  }

                  Log.e("a",arrayList.toString());
                  RecyclerDisplayAdapter
                      recyclerDisplayAdapter = new RecyclerDisplayAdapter(rootView.getContext(),arrayList);
                  recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
                  recyclerView.setAdapter(recyclerDisplayAdapter);


                }

                @Override public void onCancelled(@NonNull DatabaseError databaseError) {

                }
              });
            }


          }

          @Override public void onCancelled(@NonNull DatabaseError databaseError) {

          }
        });


      }

      @Override public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });


    return rootView;
  }
}
