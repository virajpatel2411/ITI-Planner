package royal.com.itiplanner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.util.Arrays;

public class CustomizeFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customize,container,false);

        AutoCompleteTextView autoCompleteTextView = rootView.findViewById(R.id.autocomplete);
        autoCompleteTextView.setAdapter(new SearchAdapter(rootView.getContext(),android.R.layout.simple_list_item_1));

        // Toast.makeText(getActivity(), "Customize:-Under Development", Toast.LENGTH_SHORT).show();
        return rootView;
    }
}
