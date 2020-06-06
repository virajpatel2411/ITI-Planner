package royal.com.itiplanner.fragments;

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

  TextView iti_name, text, empty_check_text;
  RecyclerView iti_list;
  ArrayList<SearchPlace> selectedPlaces;
  SearchPlace airport;
  Button back, showTrip;

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    final View rootView = inflater.inflate(R.layout.fragment_create_itinerary, container, false);

    final String name = getArguments().getString("Name");
    selectedPlaces = (ArrayList<SearchPlace>) getArguments().getSerializable("CreateClass");

    iti_name = rootView.findViewById(R.id.iti_name);
    text = rootView.findViewById(R.id.text);
    empty_check_text = rootView.findViewById(R.id.empty_check_text);
    iti_list = rootView.findViewById(R.id.iti_list);
    back = rootView.findViewById(R.id.back_btn);
    showTrip = rootView.findViewById(R.id.show_trip_btn);

    iti_name.setText(name);
    text.setText("Your Customize Itinerary is displayed below");
    if (selectedPlaces.size() == 1) {
      empty_check_text.setText("Your Itinerary is Empty, Please select atleast one places");
    }

    airport = selectedPlaces.remove(0);

    iti_list.setLayoutManager(new GridLayoutManager(rootView.getContext(), 2));
    DisplayPlaceAdapter displayPlaceAdapter =
        new DisplayPlaceAdapter(selectedPlaces, rootView.getContext());
    iti_list.setAdapter(displayPlaceAdapter);

    back.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Fragment fragment = new PlaceSearchDisplay();
        Bundle bundle = new Bundle();
        bundle.putString("Place", name);
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction()
            .replace(R.id.frame, fragment)
            .addToBackStack("homeFragment")
            .commit();
      }
    });

    showTrip.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (selectedPlaces.size() == 1) {

        }
        Fragment fragment = new DisplayPlaceList();
        Bundle bundle = new Bundle();
        bundle.putString("Name", name);
        selectedPlaces.add(0, airport);
        bundle.putSerializable("SelectedPlaces", selectedPlaces);
        bundle.putString("Name", name);
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction()
            .replace(R.id.frame, fragment)
            .addToBackStack("homeFragment")
            .commit();
      }
    });

    return rootView;
  }
}
