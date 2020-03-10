package royal.com.itiplanner.fragments;

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
import java.util.ArrayList;
import royal.com.itiplanner.R;
import royal.com.itiplanner.adapters.RecyclerDisplayAdapter;
import royal.com.itiplanner.models.DisplayItineraryModel;
import royal.com.itiplanner.models.FinalModel;

public class ItineraryDisplayFragment extends Fragment {

  RecyclerView recyclerView;
  

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.fragment_display,container,false);
    recyclerView = rootView.findViewById(R.id.rec_display);
    ArrayList<FinalModel> finalModels =
        (ArrayList<FinalModel>) getArguments().getSerializable("FINAL");

    ArrayList<String> city = getArguments().getStringArrayList("CITY");
    Log.e("viraj",finalModels.toString());

    final ArrayList<DisplayItineraryModel> arrayList = new ArrayList<>();

    int i=0;
    for(FinalModel f : finalModels){
      DisplayItineraryModel displayItineraryModel = new DisplayItineraryModel();
      displayItineraryModel.setFinalModel(f);
      displayItineraryModel.setCity(city.get(i));
      arrayList.add(displayItineraryModel);
      i++;
    }

    RecyclerDisplayAdapter
        recyclerDisplayAdapter = new RecyclerDisplayAdapter(rootView.getContext(),arrayList);
    recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
    recyclerView.setAdapter(recyclerDisplayAdapter);


    return rootView;
  }
}
