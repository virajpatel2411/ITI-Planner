package royal.com.itiplanner.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.ArrayList;

public class BitmapImageAdapter extends PagerAdapter {

  Context context;
  ArrayList<ImageView> bitmaps;

  public BitmapImageAdapter(Context context, ArrayList<ImageView> bitmaps) {
    this.context = context;
    this.bitmaps = bitmaps;
  }

  @Override public void notifyDataSetChanged() {
    super.notifyDataSetChanged();
  }

  @Override
  public int getCount() {
    return bitmaps.size();
  }

  @Override
  public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
    return view == (ImageView) o;
  }

  @NonNull
  @Override
  public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
    ImageView imageView = new ImageView(context);
    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    imageView = bitmaps.get(position);
    container.addView(imageView, 0);
    return imageView;
  }

  @Override
  public void destroyItem(@NonNull final ViewGroup container, final int position,
      @NonNull final Object object) {
    container.removeView((ImageView) object);
  }
}
