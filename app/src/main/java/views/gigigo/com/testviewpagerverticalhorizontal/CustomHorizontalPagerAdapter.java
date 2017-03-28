package views.gigigo.com.testviewpagerverticalhorizontal;

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

  @Override public Object instantiateItem(ViewGroup collection, int position) {
    LayoutInflater inflater = LayoutInflater.from(mContext);
    ViewGroup layout;

    layout = (ViewGroup) inflater.inflate(R.layout.view_horizontal_title, collection, false);
    if (position % 2 == 0) {
      layout.setBackgroundColor(Color.RED);//par
    } else {
      layout.setBackgroundColor(Color.BLACK);//impar
    }
    TextView txtTitle = (TextView)layout.findViewById(R.id.txtTitle);
    txtTitle.setText(mLstHorizontalFirstItem.get(position).getTitle());

    collection.addView(layout);

    //todo all shit, view page inflate title view
    return layout;
  }

  @Override public void destroyItem(ViewGroup collection, int position, Object view) {
    collection.removeView((View) view);
  }

  @Override public int getCount() {
    return mLstHorizontalFirstItem.size();
    //todo
    //return ModelObject.values().length;
  }

  @Override public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  @Override public CharSequence getPageTitle(int position) {
    ModelObject customPagerEnum = mLstHorizontalFirstItem.get(position);
    return customPagerEnum.getTitle();
  }
}
