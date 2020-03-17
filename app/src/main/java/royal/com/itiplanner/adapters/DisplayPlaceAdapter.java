package royal.com.itiplanner.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import royal.com.itiplanner.R;
import royal.com.itiplanner.models.SearchPlace;

public class DisplayPlaceAdapter extends RecyclerView.Adapter<DisplayPlaceAdapter.DisplayPlaceViewHolder> {

  private ArrayList<SearchPlace> searchPlaces;
  private Context context;

  public DisplayPlaceAdapter(ArrayList<SearchPlace> searchPlaces, Context context) {
    this.searchPlaces = searchPlaces;
    this.context = context;
  }

  @NonNull @Override
  public DisplayPlaceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
    View view = inflater.inflate(R.layout.list_view_display_place,viewGroup,false);
    DisplayPlaceViewHolder viewHolder = new DisplayPlaceViewHolder(view);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull DisplayPlaceViewHolder displayPlaceViewHolder, int i) {

    String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+searchPlaces.get(i).getPhotoReference()+"&key=AIzaSyCQXqjK34UVxzTQW2zH9oB3WimKrYVHGpo";
    Picasso.with(context).load(url).fit().centerInside().into(displayPlaceViewHolder.photo);
    displayPlaceViewHolder.name.setText(searchPlaces.get(i).getPlaceName());
    Random r = new Random();
    String s = String.valueOf(r.nextInt(3)+3);
    displayPlaceViewHolder.ratings.setText(s);
  }

  @Override public int getItemCount() {
    return searchPlaces.size();
  }

  public class DisplayPlaceViewHolder extends RecyclerView.ViewHolder {

    ImageView photo;
    TextView name,ratings;

    public DisplayPlaceViewHolder(@NonNull View itemView) {
      super(itemView);

      photo = itemView.findViewById(R.id.place_img);
      name = itemView.findViewById(R.id.place_name);
      ratings = itemView.findViewById(R.id.ratings);

    }
  }
}
