package views.gigigo.com.tviewpager;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by nubor on 27/03/2017.
 */
public class CustomHorizontalPagerAdapter extends PagerAdapter {

  ArrayList<ModelObject> mLstHorizontalFirstItem = new ArrayList<>();
  private Context mContext;

  public CustomHorizontalPagerAdapter(Context context) {
    mContext = context;
  }

  public CustomHorizontalPagerAdapter(Context context,
      ArrayList<ModelObject> lstModelHorizontalFirstItem) {
    mContext = context;
    mLstHorizontalFirstItem = lstModelHorizontalFirstItem;

  }



  @Override public Object instantiateItem(ViewGroup collection, int pos) {
    LayoutInflater inflater = LayoutInflater.from(mContext);
    ViewGroup layout;

    layout = (ViewGroup) inflater.inflate(R.layout.view_horizontal_title_lib, collection, false);

    if (getVirtualPosition(pos) % 2 == 0) {
      layout.setBackgroundColor(Color.BLUE);//par
    } else {
      layout.setBackgroundColor(Color.GRAY);//impar
    }
    TextView txtTitle = (TextView) layout.findViewById(R.id.txtTitle);
    txtTitle.setText(mLstHorizontalFirstItem.get(getVirtualPosition(pos)).getTitle());

    collection.addView(layout);

    //todo all shit, view page inflate title view
    return layout;
  }

  @Override public void destroyItem(ViewGroup collection, int position, Object view) {
    collection.removeView((View) view);
  }
  int getVirtualPosition(int realPosition) {
    int virtualPos=realPosition % mLstHorizontalFirstItem.size();

    System.out.println("realPosition"+realPosition);
    System.out.println("virtualPos"+virtualPos);
    System.out.println("this.getCount()"+this.getCount());

    return virtualPos;
  }


  public int getRealCount() {
      return mLstHorizontalFirstItem.size();
  }

  @Override public int getCount() {
   return  mLstHorizontalFirstItem.size()*1000;// Integer.MAX_VALUE;
    // return mLstHorizontalFirstItem.size();
  }

  @Override public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  @Override public CharSequence getPageTitle(int position) {
    ModelObject customPagerEnum = mLstHorizontalFirstItem.get(position);
    return customPagerEnum.getTitle();
  }
}
