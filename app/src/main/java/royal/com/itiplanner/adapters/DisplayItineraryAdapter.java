package royal.com.itiplanner.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import royal.com.itiplanner.R;

public class DisplayItineraryAdapter extends ArrayAdapter {

  Context context;
  ArrayList<String> days, price, place;
  TextView txtDays, txtPrice, txtPlace;

  public DisplayItineraryAdapter(@NonNull Context context,
      ArrayList days, ArrayList price, ArrayList place) {
    super(context, R.layout.list_specific_itinerary, days);
    this.context = context;
    this.days = days;
    this.price = price;
    this.place = place;
  }

  @NonNull @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

    LayoutInflater layoutInflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    View rootView = layoutInflater.inflate(R.layout.list_specific_itinerary, parent, false);

    txtDays = rootView.findViewById(R.id.txt_Days);
    txtPlace = rootView.findViewById(R.id.txtPlace);
    txtPrice = rootView.findViewById(R.id.txtPrice);

    txtDays.setText(days.get(position));
    txtPrice.setText(price.get(position));
    txtPlace.setText(place.get(position));

    return rootView;
  }
}