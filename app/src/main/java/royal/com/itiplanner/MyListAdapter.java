package royal.com.itiplanner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyListAdapter extends ArrayAdapter {
    Context context;
    String[] str;
    int[] img;
    TextView txt;
    ImageView imgV;
    public MyListAdapter(@NonNull Context context, String [] text, int [] images) {
        super(context, R.layout.list_view, text);
        this.img = images;
        this.context = context;
        this.str = text;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.list_view,null,false);
        txt = rootView.findViewById(R.id.txt);
        imgV = rootView.findViewById(R.id.img);
        txt.setText(str[position]);
        imgV.setImageResource(img[position]);
        return rootView;
    }

}
