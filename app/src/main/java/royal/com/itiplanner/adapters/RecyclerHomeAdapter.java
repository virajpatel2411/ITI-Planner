package royal.com.itiplanner.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import royal.com.itiplanner.R;
import royal.com.itiplanner.models.HomePageItineraryModel;

public class RecyclerHomeAdapter extends RecyclerView.Adapter<RecyclerHomeAdapter.ViewHolder>{

    private ArrayList<HomePageItineraryModel> homePageItineraryModels;
    private Context context;
    ArrayList<String> imageViews;

    public RecyclerHomeAdapter(Context context,ArrayList<HomePageItineraryModel> homePageItineraryModels,
        ArrayList<String> imageViews)
    {
        this.context = context;
        this.homePageItineraryModels = homePageItineraryModels;
        this.imageViews = imageViews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.list_view_home,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //set viewpager
        Log.e("virajj",imageViews.size()+"");
        Log.e("virajj",imageViews.get(i).toString());
        Glide.with(context).load(imageViews.get(i)).into(viewHolder.imageView);
        //viewHolder.imageView.setImageDrawable(imageView.getDrawable());
        viewHolder.txtNoOfPersons.setText(homePageItineraryModels.get(i).getNo_of_days());
        viewHolder.txtBudget.setText(homePageItineraryModels.get(i).getAmt());
        viewHolder.txtPlace.setText(homePageItineraryModels.get(i).getPlace());
    }

    @Override
    public int getItemCount() {
        return homePageItineraryModels.size();
    }

    public static  class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView txtPlace, txtBudget, txtNoOfPersons;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_slider);
            txtPlace = itemView.findViewById(R.id.rec_place);
            txtBudget = itemView.findViewById(R.id.rec_budget);
            txtNoOfPersons = itemView.findViewById(R.id.rec_no_of_person);

        }
    }
}
