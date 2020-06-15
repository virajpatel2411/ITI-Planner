package royal.com.itiplanner.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import royal.com.itiplanner.R;
import royal.com.itiplanner.adapters.BitmapImageAdapter;
import royal.com.itiplanner.adapters.RecyclerDisplayAdapter;
import royal.com.itiplanner.models.DisplayItineraryModel;
import royal.com.itiplanner.models.FinalModel;
import royal.com.itiplanner.models.HomePageItineraryModel;
import royal.com.itiplanner.models.PlaceModel;

public class FragmentRecommendation extends Fragment {
  ViewPager viewPager;
  FirebaseStorage storage;
  StorageReference mStorageRef;
  String state;
  RecyclerView recyclerView;
  ArrayList<String> places;
  ArrayList<ImageView> bitmaps;
  ArrayList<HomePageItineraryModel> arrayList;
  ArrayList<DisplayItineraryModel> displayItineraryModels;

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_display, container, false);
    viewPager = rootView.findViewById(R.id.img_slider);
    recyclerView = rootView.findViewById(R.id.rec_display);
    storage = FirebaseStorage.getInstance();

    bitmaps = new ArrayList<>();
    state = getArguments().getString("state");
    places = getArguments().getStringArrayList("places");
    arrayList = (ArrayList<HomePageItineraryModel>) getArguments().getSerializable("model");

    displayItineraryModels = new ArrayList<>();

    for (HomePageItineraryModel homePageItineraryModel : arrayList) {
      if (!homePageItineraryModel.getState().equals(state)) {
        continue;
      }
      FinalModel finalModel = new FinalModel();
      ArrayList<PlaceModel> placeModels = new ArrayList<>();
      DisplayItineraryModel displayItineraryModel = new DisplayItineraryModel();
      displayItineraryModel.setCity(homePageItineraryModel.getCity());
      Log.e("lkh", Integer.valueOf(homePageItineraryModel.getNo_of_days()) + "" + places.size());
      int per_days =
          (int) ((double) Integer.valueOf(homePageItineraryModel.getNo_of_days()) / places.size());
      int per_price = (int) (Double.valueOf(homePageItineraryModel.getAmt()) / places.size());
      if (per_days == 0) {
        per_days++;
      }
      int tot_days = 0, tot_price = 0;
      for (String placeName : homePageItineraryModel.getPlace()) {
        PlaceModel placeModel = new PlaceModel();
        placeModel.setPlace(placeName);
        Log.e("viraj",placeName);
        placeModel.setPrice(String.valueOf(per_price));
        tot_price += per_price;
        placeModel.setNoOfDays(String.valueOf(per_days));
        tot_days += per_days;
        placeModels.add(placeModel);
      }
      finalModel.setPlaceModels(placeModels);
      finalModel.setDaysCount(String.valueOf(tot_days));
      ArrayList<String> temp = new ArrayList<>();
      finalModel.setTags(temp);
      finalModel.setRatings("");
      displayItineraryModel.setFinalModel(finalModel);
      displayItineraryModels.add(displayItineraryModel);
    }

    RecyclerDisplayAdapter recyclerDisplayAdapter =
        new RecyclerDisplayAdapter(rootView.getContext(), displayItineraryModels, "Search");
    recyclerView.setAdapter(recyclerDisplayAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

    new PostTask(getActivity().getApplicationContext(), bitmaps).execute();

    return rootView;
  }

  private class PostTask extends AsyncTask<Void, Void, Void> {
    Context context;
    ArrayList<ImageView> bitmaps;
    int j;

    public PostTask(Context context, ArrayList<ImageView> bitmaps) {
      this.context = context;
      this.bitmaps = bitmaps;
    }

    public void displayImage() {

      BitmapImageAdapter bitmapImageAdapter =
          new BitmapImageAdapter(context, bitmaps);
      viewPager.setAdapter(bitmapImageAdapter);
    }

    @Override protected Void doInBackground(Void... voids) {

      for (j = 1; j <= 5; j++) {

        mStorageRef = storage.getReferenceFromUrl("gs://iti-planner.appspot.com").child(state);

        mStorageRef.child(j + ".jpg").getDownloadUrl().addOnSuccessListener(
            new OnSuccessListener<Uri>() {
              @Override public void onSuccess(Uri uri) {
                ImageView imageView = new ImageView(context);
                String url = uri.toString();
                Glide.with(context).load(url).into(imageView);
                bitmaps.add(imageView);
                if (bitmaps.size() == 5) {
                  displayImage();
                }
              }
            });
      }

      return null;
    }
  }
}
