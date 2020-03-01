package royal.com.itiplanner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerHomeAdapter extends RecyclerView.Adapter<RecyclerHomeAdapter.ViewHolder>{

    private ArrayList<HomePageItineraryModel> homePageItineraryModels;
    private Context context;
    private int[] imgs;

    public RecyclerHomeAdapter(Context context,ArrayList<HomePageItineraryModel> homePageItineraryModels,int imgs[])
    {
        this.context = context;
        this.homePageItineraryModels = homePageItineraryModels;
        this.imgs = imgs;
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

        ImageAdapter imageAdapter = new ImageAdapter(context,imgs);
        viewHolder.viewPager.setAdapter(imageAdapter);
        //Log.e("Viraj",viewHolder.txtNoOfPersons.getText().toString());
        viewHolder.txtNoOfPersons.setText(homePageItineraryModels.get(i).getNo_of_people());
        viewHolder.txtBudget.setText(homePageItineraryModels.get(i).getAmt());
        viewHolder.txtPlace.setText(homePageItineraryModels.get(i).getPlace());
    }

    @Override
    public int getItemCount() {
        return homePageItineraryModels.size();
    }

    public static  class ViewHolder extends RecyclerView.ViewHolder {

        ViewPager viewPager;
        TextView txtPlace, txtBudget, txtNoOfPersons;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            viewPager = itemView.findViewById(R.id.img_slider);
            txtPlace = itemView.findViewById(R.id.rec_place);
            txtBudget = itemView.findViewById(R.id.rec_budget);
            txtNoOfPersons = itemView.findViewById(R.id.rec_no_of_person);

        }
    }
}
