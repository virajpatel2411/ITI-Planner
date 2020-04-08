package royal.com.itiplanner.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.ArrayList;

public class ImageAdapter extends PagerAdapter {

    Context context;
    ArrayList<ImageView> imageViews;

    public  ImageAdapter(Context context,ArrayList<ImageView> imageViews)
    {
        this.context = context;
        this.imageViews = imageViews;
    }

    @Override
    public int getCount() {
        return imageViews.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==(ImageView)o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        container.addView(imageViews.get(position),0);
        return imageViews.get(position);
    }

    @Override
    public void destroyItem(@NonNull final ViewGroup container, final int position, @NonNull final Object object) {
        container.removeView((ImageView)object);
    }
}
