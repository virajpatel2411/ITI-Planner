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

public class ItineraryDisplayFragment extends Fragment {

  RecyclerView recyclerView;
  ArrayList<DisplayItineraryModel> arrayList;
  private FirebaseStorage storage;
  private StorageReference mStorageRef;
  ArrayList<ImageView> bitmaps;
  ViewPager viewPager;
  View rootView;
  String state;
  int j;

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    rootView = inflater.inflate(R.layout.fragment_display, container, false);
    recyclerView = rootView.findViewById(R.id.rec_display);
    viewPager = rootView.findViewById(R.id.img_slider);
    ArrayList<FinalModel> finalModels =
        (ArrayList<FinalModel>) getArguments().getSerializable("FINAL");
    bitmaps = new ArrayList<>();
    ArrayList<String> city = getArguments().getStringArrayList("CITY");
    state = getArguments().getString("STATE");
    //bitmaps = getArguments().getParcelableArrayList("IMAGES");
    Log.e("viraj", finalModels.toString());
    //Log.v("virajp", String.valueOf(bitmaps.size()));
    //Log.v("virajp",String.valueOf(bitmaps.get(0)));
    storage = FirebaseStorage.getInstance();
    arrayList = new ArrayList<>();

    int i = 0;


    for(i=0;i<finalModels.size();i++)
    {
      DisplayItineraryModel displayItineraryModel = new DisplayItineraryModel();

      displayItineraryModel.setFinalModel(finalModels.get(i));
      displayItineraryModel.setCity(city.get(i));
      arrayList.add(displayItineraryModel);
    }


    Log.e("abc", "viraj");
    RecyclerDisplayAdapter recyclerDisplayAdapter =
        new RecyclerDisplayAdapter(rootView.getContext(), arrayList,"Search");
    recyclerView.setAdapter(recyclerDisplayAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

    new PostTask(getActivity().getApplicationContext(), bitmaps).execute();

    return rootView;
  }

  private class PostTask extends AsyncTask<Void, Void, String> {
    Context context;
    ArrayList<ImageView> bitmaps;

    public PostTask(Context context, ArrayList<ImageView> bitmaps) {
      this.context = context;
      this.bitmaps = bitmaps;
    }

    @Override protected void onPostExecute(String aVoid) {



    }

    public void displayImage()
    {
      Log.e("abc", bitmaps.size() + "");

      Log.e("abc", "jgrjig");

      BitmapImageAdapter bitmapImageAdapter =
          new BitmapImageAdapter(context, bitmaps);
      viewPager.setAdapter(bitmapImageAdapter);
    }

    @Override protected String doInBackground(Void... voids) {

     for (j = 1; j <= 5; j++) {


        mStorageRef = storage.getReferenceFromUrl("gs://iti-planner.appspot.com").child(state);

        mStorageRef.child(j + ".jpg").getDownloadUrl().addOnSuccessListener(
            new OnSuccessListener<Uri>() {
              @Override public void onSuccess(Uri uri) {
                ImageView imageView = new ImageView(context);
                String url = uri.toString();
                Log.e("abc", url);
                Log.e("abc","before");
                Glide.with(context).load(url).into(imageView);
                Log.e("abc","after");
                bitmaps.add(imageView);
                if(j==5)
                  Log.e("abc","method called");
                  displayImage();
              }

            });
      }

        return "hello";

    }
  }
}
