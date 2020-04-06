package royal.com.itiplanner.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import royal.com.itiplanner.R;
import royal.com.itiplanner.adapters.DisplayPlaceAdapter;
import royal.com.itiplanner.models.SearchPlace;

public class DisplayPlaceList extends Fragment {

  RecyclerView shortest_path_list;
  TextView iti_name,text;
  ArrayList<SearchPlace> selectedPlaces;
  ArrayList<SearchPlace> outputPlacesList;
  SearchPlace airport;
  Button share,download;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View rootView = inflater.inflate(R.layout.fragment_display_place_list, container, false);

    final String name = getArguments().getString("Name");
    selectedPlaces = (ArrayList<SearchPlace>) getArguments().getSerializable("SelectedPlaces");
    //airport = (SearchPlace) getArguments().getSerializable("Airport");

    iti_name = rootView.findViewById(R.id.iti_name);
    text = rootView.findViewById(R.id.text);
    shortest_path_list = rootView.findViewById(R.id.shortest_path_list);
    share = rootView.findViewById(R.id.share_btn);
    download = rootView.findViewById(R.id.download_btn);

    outputPlacesList = new ArrayList<>();

    iti_name.setText(name);
    text.setText("Your Itinerary with a shortest path starting from "+ selectedPlaces.get(0).getPlaceName() + " is displayed below");

    share.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

        String shareBody = "";
        shareBody = "Itinerary for "+ name +" is :\n";
        int count=1;
        for(SearchPlace s : selectedPlaces)
        {
          shareBody = shareBody + String.valueOf(count) + ") " +  s.getPlaceName() + "\n";
          count++;
        }

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT,shareBody);
        startActivity(Intent.createChooser(i,"Share Itinerary using"));
      }
    });

    //selectedPlaces.add(0,airport);
    outputPlacesList = generate(selectedPlaces);

    shortest_path_list.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
    DisplayPlaceAdapter displayPlaceAdapter = new DisplayPlaceAdapter(outputPlacesList,rootView.getContext());
    shortest_path_list.setAdapter(displayPlaceAdapter);

    return rootView;
  }

  public double euclidean(SearchPlace o1, SearchPlace o2)
  {
    return Math.sqrt(Math.pow(o1.getLatitude()-o2.getLatitude(),2)+Math.pow(o1.getLongitude()-o2.getLongitude(),2));
  }

  public ArrayList<SearchPlace> generate(ArrayList<SearchPlace> l2)  // generates distance matrix
  {
    double[][] right = new double[l2.size()][l2.size()];
    for (int i=0;i<l2.size();i++)
    {
      for(int j=0;j<l2.size();j++)
      {
        right[i][j]= euclidean(l2.get(i),l2.get(j));
      }
    }
    return algorithm(l2,right);
  }

  public ArrayList<SearchPlace> algorithm(ArrayList<SearchPlace> l1,double[][] a)
  {
    int index=0;
    ArrayList visited=new ArrayList();
    ArrayList<SearchPlace> target=new ArrayList<SearchPlace>();
    HashMap<Integer,SearchPlace> h1=new HashMap<Integer,SearchPlace>();

    for (int i=0;i<l1.size();i++)
    {
      h1.put(i,l1.get(i));
    }
    ArrayList<SearchPlace> l3=new ArrayList<SearchPlace>();
    for (SearchPlace m: l1)
    {
      l3.add(m);
    }
    target.add(h1.get(index));
    l3.remove(h1.get(index));
    visited.add(index);
    while(!l3.isEmpty())
    {
      int m3=shortest(h1,a,index,visited);
      target.add(h1.get(m3));
      l3.remove(h1.get(m3));
      index=m3;
      visited.add(index);
    }
    Log.e("MSG: ","-------output-----");
    return target;
  }

  public int shortest(HashMap<Integer, SearchPlace> h2,double[][] b,int index,ArrayList d1)
  {
    int a=index;
    double min=999999;
    int k=0;
    for(int i=0;i<h2.size();i++)
    {
      if(a!=i && !d1.contains(i))
      {
        if(b[a][i]<min)
        {
          min=b[a][i];
          k=i;
        }
      }
    }
    return k;
  }
}
