package royal.com.itiplanner.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import royal.com.itiplanner.R;
import royal.com.itiplanner.models.PlacesApi;

public class PlaceSearchDisplay extends Fragment {

  TextView textView;
  ProgressDialog pd;
  ListView listView;
  ArrayList<String> places;
  ArrayAdapter<String> arrayAdapter;
  PlacesApi placesApi;

  String place;

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_place_search_display,container,false);

    place = getArguments().getString("Place");

    places = new ArrayList<>();

    textView = rootView.findViewById(R.id.text_search_result);
    listView = rootView.findViewById(R.id.list_search_result);

    Web web_data = new Web(rootView.getContext());
    web_data.execute();

    textView.setText(place);

    return rootView;
  }

  public class Web extends AsyncTask<Void,Void,Void> {

    Context context;

    public Web(Context context) {
      this.context = context;
    }

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      pd = new ProgressDialog(context);
      pd.setTitle("Loading Places");
      pd.setMessage("Please wait until loading...");
      pd.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      super.onPostExecute(aVoid);

      arrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,places);
      listView.setAdapter(arrayAdapter);
      pd.dismiss();
    }

    @Override
    protected Void doInBackground(Void... voids) {
      try {
        String temp = "";
        for(int i=0;i<place.length();i++)
        {
          if(place.charAt(i) != ',')
          {
            temp = temp + place.charAt(i);
          }
        }
        places = placesApi.SearchPlace(temp);
      }catch (Exception e){
        e.printStackTrace();
      }
      return null;
    }
  }
}
