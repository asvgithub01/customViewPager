package views.gigigo.com.tviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nubor on 27/03/2017.
 */
public class CustomVerticalPagerAdapter<T> extends PagerAdapter {

  private final Context mContext;
  private final CallBackAdapterItemInstanciate mCallback;
  private final OnVHPageChangeListener onPageChangeListener;

  private List<T> mLstVertical = new ArrayList<>();
  private List<T> mLstHorizontalFirstItem = new ArrayList<>();

  private HorizontalViewPager viewpagerHorizontal;

  public CustomVerticalPagerAdapter(Context context, List<T> lstModelVertical,
      List<T> lstModelHorizontalFirstItem, CallBackAdapterItemInstanciate callback) {
    this(context, lstModelVertical, lstModelHorizontalFirstItem, callback, null);
  }

  public CustomVerticalPagerAdapter(Context context, List<T> lstModelVertical,
      List<T> lstModelHorizontalFirstItem, CallBackAdapterItemInstanciate callback,
      OnVHPageChangeListener onPageChangeListener) {
    mContext = context;
    mLstVertical = lstModelVertical;
    mLstHorizontalFirstItem = lstModelHorizontalFirstItem;
    mCallback = callback;
    this.onPageChangeListener = onPageChangeListener;
  }

  @Override public Object instantiateItem(ViewGroup collection, int position) {
    LayoutInflater inflater = LayoutInflater.from(mContext);
    ViewGroup layout;
    if (position == 0) {
      layout = (ViewGroup) inflater.inflate(R.layout.view_viewpager_preview_lib, collection, false);
      viewpagerHorizontal = (HorizontalViewPager) layout.findViewById(R.id.viewpagerHorizontal);
      viewpagerHorizontal.setAdapter(
          new CustomHorizontalPagerAdapter(mContext, mLstHorizontalFirstItem, mCallback));
      viewpagerHorizontal.setCurrentItem(viewpagerHorizontal.getAdapter().getCount() / 2, false);
      viewpagerHorizontal.setOnVHPageChangeListener(onPageChangeListener);
    } else {
      layout = (ViewGroup) mCallback.OnVerticalInstantiateItem(collection, position);
    }
    collection.addView(layout);
    //todo all shit, view page inflate title view
    return layout;
  }

  @Override public void destroyItem(ViewGroup collection, int position, Object view) {
    collection.removeView((View) view);
  }

  public int getRealCount() {
    return this.getCount();
  }

  @Override public int getCount() {
    return mLstVertical.size();
  }

  @Override public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  public OnVHPageChangeListener getOnPageChangeListener() {
    return onPageChangeListener;
  }

  public void setCurrentHorizontalItem(int position, boolean smoothScroll) {
    if (viewpagerHorizontal != null) {
      viewpagerHorizontal.setCurrentItem(position, smoothScroll);
    }
  }
}
