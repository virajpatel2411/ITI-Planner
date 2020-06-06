package royal.com.itiplanner.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import royal.com.itiplanner.R;
import royal.com.itiplanner.models.HomePageItineraryModel;
import royal.com.itiplanner.models.UserModel;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
  TextView txtView;
  String name;
  UserModel userModel;
  ListView listView;
  ProgressBar progressBar;
  @Nullable

  private FirebaseAuth mAuth;
  private DatabaseReference myRef;

  @Override
  public View onCreateView(@NonNull final LayoutInflater inflater,
      @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.fragment_home, container, false);
    progressBar = rootView.findViewById(R.id.progress);
    txtView = rootView.findViewById(R.id.txt_name);
    SharedPreferences sharedPreferences =
        getActivity().getSharedPreferences("ITIPlanner", MODE_PRIVATE);
    String strName = sharedPreferences.getString("NAME_KEY", "");
    String personName = "";

    mAuth = FirebaseAuth.getInstance();
    GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
    if (acct != null) {
      personName = acct.getDisplayName();
    }
    if (strName == "" && personName != "") {
      strName = personName;
    }

    txtView.setText(strName);

    ArrayList<HomePageItineraryModel> arrayList = new ArrayList<>();

    listView = rootView.findViewById(R.id.list_home);

    new FetchData(rootView.getContext(), listView, arrayList).execute();

    return rootView;
  }

  private class FetchData extends AsyncTask<Void, Void, Void> {

    ListView listView;
    ArrayList<HomePageItineraryModel> arrayList;
    ArrayList<String> places;
    Context context;
    int i = 0, k = 1;
    String state;
    ArrayList<String> imageViews;
    ArrayList<String> states;
    private FirebaseStorage storage;
    private StorageReference mStorageRef;

    public FetchData(Context context, ListView listView,
        ArrayList<HomePageItineraryModel> arrayList) {
      this.listView = listView;
      this.arrayList = arrayList;
      this.context = context;
    }

    private void displayRecycler(ArrayList<String> states) {
      progressBar.setVisibility(View.INVISIBLE);
      ArrayList<String> newStates = new ArrayList<>();
      newStates.add(states.get(0));
      for (String s : states) {
        if (!newStates.contains(s)) {
          newStates.add(s);
        }
      }

      final ArrayAdapter arrayAdapter =
          new ArrayAdapter(context, android.R.layout.simple_list_item_1,
              newStates);

      getActivity().runOnUiThread(new Runnable() {
        @Override public void run() {
          listView.setAdapter(arrayAdapter);

          listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Fragment fragment = new FragmentRecommendation();
              Bundle bundle = new Bundle();
              bundle.putString("state", listView.getItemAtPosition(position).toString());
              bundle.putStringArrayList("places", places);
              bundle.putSerializable("model", arrayList);
              fragment.setArguments(bundle);
              getFragmentManager().beginTransaction()
                  .replace(R.id.frame, fragment)
                  .addToBackStack("homeFragment")
                  .commit();
            }
          });
        }
      });
    }

    @Override protected Void doInBackground(Void... voids) {
      progressBar.setIndeterminate(true);
      progressBar.setVisibility(View.VISIBLE);
      HttpURLConnection urlConnection = null;
      BufferedReader reader = null;
      String forecastJsonStr = null;
      states = new ArrayList<>();
      storage = FirebaseStorage.getInstance();
      try {

        URL url = new URL("https://iti-planner.herokuapp.com/user/" + mAuth.getUid());
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();
        int lengthOfFile = urlConnection.getContentLength();
        InputStream inputStream = urlConnection.getInputStream();
        StringBuffer buffer = new StringBuffer();
        if (inputStream == null) {
          return null;
        }
        reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;

        while ((line = reader.readLine()) != null) {
          buffer.append(line + "\n");
        }

        if (buffer.length() == 0) {
          return null;
        }
        forecastJsonStr = buffer.toString();

        imageViews = new ArrayList<>();
        final JSONObject jsonArray = new JSONObject(forecastJsonStr);
        for (i = 0; i < jsonArray.length(); i++) {
          JSONObject jsonObject = jsonArray.getJSONObject("" + i);
          HomePageItineraryModel homePageItineraryModel = new HomePageItineraryModel();
          homePageItineraryModel.setPlace(jsonObject.getString("id"));
          homePageItineraryModel.setAmt(jsonObject.getString("totalCost"));
          homePageItineraryModel.setNo_of_days(jsonObject.getString("daysCount"));
          JSONArray jsonArray1 = jsonObject.getJSONArray("places");
          places = new ArrayList<>();
          for (int j = 0; j < jsonArray1.length(); j++) {
            places.add(jsonArray1.get(j).toString());
          }
          state = jsonObject.getString("city");
          homePageItineraryModel.setState(state);
          arrayList.add(homePageItineraryModel);
          states.add(state);
        }

        if (states.size() == jsonArray.length()) {
          displayRecycler(states);
        }
      } catch (IOException e) {
        Log.e("abc", "Error ", e);
      } catch (JSONException e) {
        e.printStackTrace();
      } finally {
        if (urlConnection != null) {
          urlConnection.disconnect();
        }
        if (reader != null) {
          try {
            reader.close();
          } catch (final IOException e) {
            Log.e("abc", "Error closing stream", e);
          }
        }
      }
      return null;
    }
  }
}
