package royal.com.itiplanner.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import royal.com.itiplanner.R;
import royal.com.itiplanner.adapters.DisplayPlaceAdapter;
import royal.com.itiplanner.adapters.RecyclerHomeAdapter;
import royal.com.itiplanner.models.PlacesApi;
import royal.com.itiplanner.models.SearchPlace;

public class PlaceSearchDisplay extends Fragment {

  TextView textView;
  ProgressDialog pd;
  RecyclerView place_list;
  ArrayList<SearchPlace> places;
  PlacesApi placesApi;
  RequestQueue requestQueue;

  String place;

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_place_search_display,container,false);

    place = getArguments().getString("Place");

    pd = new ProgressDialog(rootView.getContext());
    pd.setTitle("Loading Places");
    pd.setMessage("Please wait until loading...");
    pd.show();

    places = new ArrayList<>();
    placesApi = new PlacesApi();

    textView = rootView.findViewById(R.id.text_search_result);
    place_list = rootView.findViewById(R.id.list_search_result);
    place_list.setLayoutManager(new GridLayoutManager(rootView.getContext(),2));

    requestQueue = Volley.newRequestQueue(rootView.getContext());
    SearchPlaceCustomize(rootView.getContext(),place);

    textView.setText(place);

    return rootView;
  }

  private void SearchPlaceCustomize(final Context context,String input) {

    StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/textsearch/json?");
    sb.append("query=tourist attraction in " + input);
    sb.append("&language=en&key=AIzaSyCQXqjK34UVxzTQW2zH9oB3WimKrYVHGpo");

    String url = sb.toString();

    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
        new Response.Listener<JSONObject>() {
          @Override public void onResponse(JSONObject response) {
            JSONArray results = null;
            try {
              results = response.getJSONArray("results");

              for (int i=0;i<results.length();i++){

                SearchPlace s = new SearchPlace();

                s.setPlaceName(results.getJSONObject(i).getString("name"));
                s.setRatings(results.getJSONObject(i).getInt("rating"));

                JSONArray photos = results.getJSONObject(i).getJSONArray("photos");
                s.setPhotoReference(photos.getJSONObject(0).getString("photo_reference"));

                places.add(s);
              }

              DisplayPlaceAdapter displayPlaceAdapter = new DisplayPlaceAdapter(places,context);
              place_list.setAdapter(displayPlaceAdapter);

            } catch (JSONException e) {
              e.printStackTrace();
            }
          }
        }, new Response.ErrorListener() {
      @Override public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
      }
    });

    requestQueue.add(request);
    pd.dismiss();
  }
}
