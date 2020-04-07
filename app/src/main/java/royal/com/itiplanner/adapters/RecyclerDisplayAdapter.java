package royal.com.itiplanner.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;
import royal.com.itiplanner.R;
import royal.com.itiplanner.fragments.SpecificItineraryDisplay;
import royal.com.itiplanner.fragments.SpecificItineraryHistoryFragment;
import royal.com.itiplanner.models.DisplayItineraryModel;
import royal.com.itiplanner.models.FinalModel;
import royal.com.itiplanner.models.PlaceModel;

public  class RecyclerDisplayAdapter extends RecyclerView.Adapter<RecyclerDisplayAdapter.ViewHolder> {

  Context context;
  ArrayList<DisplayItineraryModel> arrayList;
  String fragmentName;
  String displayCity;
  String[] afterWords = {"Weekend","Extravaganza","Business","Family","Summer"};
  public RecyclerDisplayAdapter(Context context, ArrayList<DisplayItineraryModel> arrayList,String fragmentName) {

    this.context = context;
    this.arrayList = arrayList;
    this.fragmentName = fragmentName;

  }

  @NonNull @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
  {
    LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
    View view  = layoutInflater.inflate(R.layout.list_view_display_itinerary,viewGroup,false);
    ViewHolder viewHolder = new ViewHolder(view);
    return viewHolder;
  }

  @Override public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {


    viewHolder.txtDays.setText(arrayList.get(i).getFinalModel().getDaysCount());
    String city = arrayList.get(i).getCity();
    displayCity="";
    for(int j=0;j<city.length();j++)
    {
      displayCity += city.charAt(j);
      if(city.charAt(j)==',' || city.charAt(j)==' '){
        break;
      }
    }
    displayCity += afterWords[new Random().nextInt(5)];
    viewHolder.txtPlace.setText(displayCity);
    int amt=0;
    for(PlaceModel placeModel : arrayList.get(i).getFinalModel().getPlaceModels()){
      amt += Integer.valueOf(placeModel.getPrice());
    }
    String amount = String.valueOf(amt);
    viewHolder.txtBudget.setText(amount);


    final int pos = i;

    viewHolder.mView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        FinalModel finalModel = new FinalModel();
        finalModel = arrayList.get(pos).getFinalModel();
        String city = arrayList.get(pos).getCity();

        Log.e("viraj","Recycler Click");

        Fragment fragment;
        if(fragmentName=="Search")
        {
           fragment = new SpecificItineraryDisplay();
        }
        else
        {
           fragment = new SpecificItineraryHistoryFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("FINAL",finalModel);
        bundle.putString("CITY",displayCity);
        fragment.setArguments(bundle);
        FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frame,fragment).addToBackStack("homeFragment").commit();
      }
    });
  }

  @Override public int getItemCount() {
    return arrayList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    TextView txtPlace, txtBudget, txtDays;
    View mView;
    public ViewHolder(@NonNull final View itemView) {
      super(itemView);

      txtPlace = itemView.findViewById(R.id.dis_place);
      txtBudget = itemView.findViewById(R.id.dis_budget);
      txtDays = itemView.findViewById(R.id.dis_days);

      mView = itemView;

    }
  }
}