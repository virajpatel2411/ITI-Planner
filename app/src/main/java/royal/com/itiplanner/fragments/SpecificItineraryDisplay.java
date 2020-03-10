package royal.com.itiplanner.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import royal.com.itiplanner.R;
import royal.com.itiplanner.adapters.DisplayItineraryAdapter;
import royal.com.itiplanner.models.FinalModel;
import royal.com.itiplanner.models.PlaceModel;

public class SpecificItineraryDisplay extends Fragment {

  ListView listView;
  TextView txtDays,txtBudget;
  ArrayList<String> days,places,prices;
  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.fragment_specific_itinerary,container,false);

    FinalModel finalModel = (FinalModel) getArguments().getSerializable("FINAL");
    String city = getArguments().getString("CITY");

    listView = rootView.findViewById(R.id.spec_rec);

    txtBudget = rootView.findViewById(R.id.spec_budget);
    txtDays = rootView.findViewById(R.id.spec_days);

    days = new ArrayList<>();
    places = new ArrayList<>();
    prices = new ArrayList<>();

    TextView txt = rootView.findViewById(R.id.txt_1);
    txt.setText(city);

    int amt = 0;
    int no_days = 0;

    for(PlaceModel placeModel : finalModel.getPlaceModels())
    {
      String day = placeModel.getNoOfDays();
      String place = placeModel.getPlace();
      String price = placeModel.getPrice();
      days.add(day);
      places.add(price);
      prices.add(place);
      amt += Integer.valueOf(price);
      no_days += Integer.valueOf(day);
    }

    txtBudget.setText(String.valueOf(amt));
    txtDays.setText(String.valueOf(no_days));


    DisplayItineraryAdapter
        displayItineraryAdapter = new DisplayItineraryAdapter(getContext(),days,places,prices);

    listView.setAdapter(displayItineraryAdapter);

    return rootView;
  }
}
