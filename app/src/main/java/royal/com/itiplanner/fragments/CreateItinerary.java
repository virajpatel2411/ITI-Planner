package royal.com.itiplanner.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import royal.com.itiplanner.R;
import royal.com.itiplanner.adapters.DisplayPlaceAdapter;
import royal.com.itiplanner.models.SearchPlace;

public class CreateItinerary extends Fragment {

  TextView iti_name;
  RecyclerView iti_list;
  ArrayList<SearchPlace> selectedPlaces;
  Button back,share;

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    final View rootView = inflater.inflate(R.layout.fragment_create_itinerary, container, false);

    final String name = getArguments().getString("Name");
    selectedPlaces = (ArrayList<SearchPlace>) getArguments().getSerializable("CreateClass");

    iti_name = rootView.findViewById(R.id.iti_name);
    iti_list = rootView.findViewById(R.id.iti_list);
    back = rootView.findViewById(R.id.back_btn);
    share = rootView.findViewById(R.id.share_btn);

    iti_name.setText(name);

    iti_list.setLayoutManager(new GridLayoutManager(rootView.getContext(),2));
    DisplayPlaceAdapter displayPlaceAdapter = new DisplayPlaceAdapter(selectedPlaces,rootView.getContext());
    iti_list.setAdapter(displayPlaceAdapter);

    back.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Fragment fragment = new PlaceSearchDisplay();
        Bundle bundle = new Bundle();
        bundle.putString("Place",name);
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.frame,fragment).commit();
      }
    });

    share.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

        String shareBody = "";
        shareBody = "Itinerary for "+ name +" is :\n";
        int count=1;
        for(SearchPlace s : selectedPlaces)
        {
          shareBody = shareBody + String.valueOf(count) + ") " +  s.getPlaceName() + "\n";
          count++;
        }

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT,shareBody);
        startActivity(Intent.createChooser(i,"Share Itinerary using"));
      }
    });

    return rootView;
  }
}
