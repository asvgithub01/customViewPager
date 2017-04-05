package views.gigigo.com.tviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nubor on 27/03/2017.
 */
public class CustomHorizontalPagerAdapter<T> extends PagerAdapter {

  List<T> mLstHorizontalFirstItem = new ArrayList<>();
  private Context mContext;
  CallBackAdapterItemInstanciate mCallback;

  public CustomHorizontalPagerAdapter(Context context,
      List<T> lstModelHorizontalFirstItem, CallBackAdapterItemInstanciate callback) {
    mContext = context;
    mLstHorizontalFirstItem = lstModelHorizontalFirstItem;
    mCallback = callback;
  }

  @Override public Object instantiateItem(ViewGroup collection, int pos) {

    //LayoutInflater inflater = LayoutInflater.from(mContext);
    //ViewGroup layout;

    ViewGroup layout = (ViewGroup) mCallback.OnHorizontalInstantiateItem(collection, pos);

    collection.addView(layout);

    //todo all shit, view page inflate title view
    return layout;
  }

  @Override public void destroyItem(ViewGroup collection, int position, Object view) {
    collection.removeView((View) view);
  }

  public static int getVirtualPosition(int realPosition, int dataSize) {
    int virtualPos = realPosition % dataSize;

    System.out.println("realPosition" + realPosition);
    System.out.println("virtualPos" + virtualPos);
    System.out.println("this.getCount()" + dataSize * 10);

    return virtualPos;
  }

  public int getVirtualPosition(int realPosition) {

    return getVirtualPosition(realPosition, mLstHorizontalFirstItem.size());
  }

  public int getRealCount() {
    return mLstHorizontalFirstItem.size();
  }

  @Override public int getCount() {
    return mLstHorizontalFirstItem.size() * 10;// Integer.MAX_VALUE;
    // return mLstHorizontalFirstItem.size();
  }

  @Override public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  //@Override public CharSequence getPageTitle(int position) {
  //  T customPagerEnum = mLstHorizontalFirstItem.get(position);
  //  return customPagerEnum.getTitle();
  //}
}
