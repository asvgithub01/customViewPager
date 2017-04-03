package views.gigigo.com.tviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

/**
 * Created by nubor on 27/03/2017.
 */
public class CustomVerticalPagerAdapter<T> extends PagerAdapter {
  ArrayList<T> mLstVertical = new ArrayList<>();
  ArrayList<T> mLstHorizontalFirstItem = new ArrayList<>();
  private Context mContext;
  public int mPosition=0;
  private CallBackAdapterItemInstanciate mCallback;

  public CustomVerticalPagerAdapter(Context context) {
    mContext = context;
  }

  public CustomVerticalPagerAdapter(Context context, ArrayList<T> lstModelVertical,
      ArrayList<T> lstModelHorizontalFirstItem, CallBackAdapterItemInstanciate callback) {
    mContext = context;
    mLstVertical = lstModelVertical;
    mLstHorizontalFirstItem = lstModelHorizontalFirstItem;
    mCallback =callback;
  }

  @Override public Object instantiateItem(ViewGroup collection, int position) {
    LayoutInflater inflater = LayoutInflater.from(mContext);
    ViewGroup layout;
    if (position == 0) {
      layout = (ViewGroup) inflater.inflate(R.layout.view_viewpager_preview_lib, collection, false);
     HorizontalViewPager viewpagerHorizontal = (HorizontalViewPager) layout.findViewById(R.id.viewpagerHorizontal);
      viewpagerHorizontal.setAdapter(
          new CustomHorizontalPagerAdapter(mContext, mLstHorizontalFirstItem,mCallback));
      viewpagerHorizontal.setCurrentItem(viewpagerHorizontal.getAdapter().getCount()/2,false);

    } else {
       layout=(ViewGroup) mCallback.OnVerticalInstantiateItem(collection,position);
    }
    collection.addView(layout);
    mPosition=position;
    //todo all shit, view page inflate title view
    return layout;
  }

  @Override public void destroyItem(ViewGroup collection, int position, Object view) {
    collection.removeView((View) view);
  }
  public int getRealCount() {

    return this.getCount();
    //todo
    //return ModelObject.values().length;

  }
  @Override public int getCount() {
    return mLstVertical.size();
    //todo
    //return ModelObject.values().length;
  }

  @Override public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  //@Override public CharSequence getPageTitle(int position) {
  //  ModelObject customPagerEnum = mLstVertical.get(position);
  //  return customPagerEnum.getTitle();
  //}
}
