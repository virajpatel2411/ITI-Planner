package royal.com.itiplanner.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import royal.com.itiplanner.R;
import royal.com.itiplanner.adapters.DisplayPlaceAdapter;
import royal.com.itiplanner.models.PlacesApi;
import royal.com.itiplanner.models.SearchPlace;

public class PlaceSearchDisplay extends Fragment implements
    DisplayPlaceAdapter.OnItemClickListener {

  TextView textView;
  ProgressDialog pd;
  RecyclerView place_list;
  ArrayList<SearchPlace> places;
  ArrayList<SearchPlace> selectedPlaces;
  SearchPlace airport;
  PlacesApi placesApi;
  RequestQueue requestQueue;
  Button createItinerary;
  ArrayList<String> placesNames;

  String place;

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    final View rootView =
        inflater.inflate(R.layout.fragment_place_search_display, container, false);

    Toast.makeText(rootView.getContext(),
        "Clicking an element again and again alternatively adds or removes places",
        Toast.LENGTH_LONG).show();

    place = getArguments().getString("Place");

    pd = new ProgressDialog(rootView.getContext());
    pd.setTitle("Loading Places");
    pd.setMessage("Please wait until loading...");
    pd.show();

    places = new ArrayList<>();
    placesApi = new PlacesApi();
    selectedPlaces = new ArrayList<>();
    airport = new SearchPlace();
    placesNames = new ArrayList<>();

    textView = rootView.findViewById(R.id.text_search_result);
    place_list = rootView.findViewById(R.id.list_search_result);
    place_list.setLayoutManager(new GridLayoutManager(rootView.getContext(), 2));
    createItinerary = rootView.findViewById(R.id.create_iti_btn);

    requestQueue = Volley.newRequestQueue(rootView.getContext());
    Airport(rootView.getContext(), place);
    SearchPlaceCustomize(rootView.getContext(), place);

    textView.setText(place);

    createItinerary.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Fragment fragment = new CreateItinerary();
        Bundle bundle = new Bundle();
        bundle.putString("Name", place);
        selectedPlaces.add(0, airport);
        bundle.putSerializable("CreateClass", selectedPlaces);
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction()
            .replace(R.id.frame, fragment)
            .addToBackStack("homeFragment")
            .commit();
      }
    });

    return rootView;
  }

  private void Airport(Context context, String input) {
    StringBuilder sb =
        new StringBuilder("https://maps.googleapis.com/maps/api/place/textsearch/json?");
    sb.append("query=airport in " + input);
    sb.append("&language=en&key=AIzaSyCQXqjK34UVxzTQW2zH9oB3WimKrYVHGpo");

    String url = sb.toString();

    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
        new Response.Listener<JSONObject>() {
          @Override public void onResponse(JSONObject response) {
            JSONArray results = null;
            try {
              results = response.getJSONArray("results");

              airport.setPlaceName(results.getJSONObject(0).getString("name"));
              airport.setRatings(results.getJSONObject(0).getInt("rating"));
              airport.setPhotoReference(
                  "CmRZAAAAXfDRsvzaMknKnWMv1mMAkWm2HjC8mxRDzEcDajyysTnf08NfD9WOOv6_jPqaQfcZd1QWZyM4MBnklVgZXwXQ_kp3ZHZmDeAaE9bGP8ls1gYnZN5IN9jLHpS3-E6GEItQEhDYKCmiDAZHSFf_jIptpkWIGhQrKR_CnvszAyOR1ZCuuCc5NPMQkw");

              JSONObject geometry = results.getJSONObject(0).getJSONObject("geometry");
              JSONObject location = geometry.getJSONObject("location");

              airport.setLatitude(location.getDouble("lat"));
              airport.setLongitude(location.getDouble("lng"));

              Random r = new Random();
              airport.setNumberOfDays(0);
              airport.setBudget(0);
            } catch (JSONException e) {
              Log.e("ERROR:", "AIRPORT NOT ADDED");
              e.printStackTrace();
            }
          }
        }, new Response.ErrorListener() {
      @Override public void onErrorResponse(VolleyError error) {
        Log.e("ERROR:", "AIRPORT NOT ADDED");
        error.printStackTrace();
      }
    });
    requestQueue.add(request);
  }

  private void SearchPlaceCustomize(final Context context, String input) {

    StringBuilder sb =
        new StringBuilder("https://maps.googleapis.com/maps/api/place/textsearch/json?");
    sb.append("query=tourist attraction in " + input);
    sb.append("&language=en&key=AIzaSyCQXqjK34UVxzTQW2zH9oB3WimKrYVHGpo");

    String url = sb.toString();

    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
        new Response.Listener<JSONObject>() {
          @Override public void onResponse(JSONObject response) {
            JSONArray results = null;
            try {
              results = response.getJSONArray("results");

              for (int i = 0; i < results.length(); i++) {

                SearchPlace s = new SearchPlace();

                s.setPlaceName(results.getJSONObject(i).getString("name"));
                s.setRatings(results.getJSONObject(i).getInt("rating"));

                JSONArray photos = results.getJSONObject(i).getJSONArray("photos");
                s.setPhotoReference(photos.getJSONObject(0).getString("photo_reference"));

                JSONObject geometry = results.getJSONObject(i).getJSONObject("geometry");
                JSONObject location = geometry.getJSONObject("location");

                s.setLatitude(location.getDouble("lat"));
                s.setLongitude(location.getDouble("lng"));

                Random r = new Random();
                s.setNumberOfDays(r.nextInt(2) + 1);
                s.setBudget(r.nextInt(1501) + 500);

                places.add(s);
              }

              DisplayPlaceAdapter displayPlaceAdapter = new DisplayPlaceAdapter(places, context);
              place_list.setAdapter(displayPlaceAdapter);
              displayPlaceAdapter.setOnItemClickListener(PlaceSearchDisplay.this);
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

  @Override public void onItemClick(int position) {
    SearchPlace searchPlace = places.get(position);
    String s = searchPlace.getPlaceName();
    if (!placesNames.contains(s)) {
      selectedPlaces.add(searchPlace);
      placesNames.add(s);
      Toast.makeText(getContext(), "Added " + s, Toast.LENGTH_SHORT).show();
    } else {
      selectedPlaces.remove(searchPlace);
      placesNames.remove(s);
      Toast.makeText(getContext(), "Removed " + s, Toast.LENGTH_SHORT).show();
    }
  }
}
