package royal.com.itiplanner.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import royal.com.itiplanner.R;
import royal.com.itiplanner.adapters.SearchAdapter;

public class CustomizeFragment extends Fragment {
  AutoCompleteTextView autoCompleteTextView;
  SearchAdapter searchAdapter;

  @Nullable
  @Override
  public View onCreateView(@NonNull final LayoutInflater inflater,
      @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
    final View rootView = inflater.inflate(R.layout.fragment_customize, container, false);

    autoCompleteTextView = rootView.findViewById(R.id.custom_search_view);
    searchAdapter = new SearchAdapter(rootView.getContext(), android.R.layout.simple_list_item_1);
    autoCompleteTextView.setAdapter(searchAdapter);

    autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String s = autoCompleteTextView.getText().toString();
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
        Fragment fragment = new PlaceSearchDisplay();
        Bundle bundle = new Bundle();
        bundle.putString("Place", s);
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction()
            .replace(R.id.frame, fragment)
            .addToBackStack("homeFragment")
            .commit();
      }
    });
    return rootView;
  }
}
