package royal.com.itiplanner.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Random;
import royal.com.itiplanner.R;
import royal.com.itiplanner.models.SearchPlace;

public class DisplayPlaceAdapter
    extends RecyclerView.Adapter<DisplayPlaceAdapter.DisplayPlaceViewHolder> {

  private ArrayList<SearchPlace> searchPlaces;
  private Context context;
  private OnItemClickListener onItemClickListener;

  public DisplayPlaceAdapter(ArrayList<SearchPlace> searchPlaces, Context context) {
    this.searchPlaces = searchPlaces;
    this.context = context;
  }

  public void setOnItemClickListener(OnItemClickListener listener) {
    onItemClickListener = listener;
  }

  @NonNull @Override
  public DisplayPlaceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
    View view = inflater.inflate(R.layout.list_view_display_place, viewGroup, false);
    DisplayPlaceViewHolder viewHolder = new DisplayPlaceViewHolder(view);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull DisplayPlaceViewHolder displayPlaceViewHolder, int i) {

    String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="
        + searchPlaces.get(i).getPhotoReference()
        + "&key=AIzaSyCQXqjK34UVxzTQW2zH9oB3WimKrYVHGpo";
    Picasso.with(context).load(url).fit().centerInside().into(displayPlaceViewHolder.photo);
    displayPlaceViewHolder.name.setText(searchPlaces.get(i).getPlaceName());
    Random r = new Random();
    String s = String.valueOf(r.nextInt(3) + 3);
    displayPlaceViewHolder.ratings.setText(s);
  }

  @Override public int getItemCount() {
    return searchPlaces.size();
  }

  public interface OnItemClickListener {
    void onItemClick(int position);
  }

  public class DisplayPlaceViewHolder extends RecyclerView.ViewHolder {

    ImageView photo;
    TextView name, ratings;

    public DisplayPlaceViewHolder(@NonNull View itemView) {
      super(itemView);

      photo = itemView.findViewById(R.id.place_img);
      name = itemView.findViewById(R.id.place_name);
      ratings = itemView.findViewById(R.id.ratings);

      itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (onItemClickListener != null) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
              onItemClickListener.onItemClick(position);
            }
          }
        }
      });
    }
  }
}
