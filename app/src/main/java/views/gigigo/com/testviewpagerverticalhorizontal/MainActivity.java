package views.gigigo.com.testviewpagerverticalhorizontal;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import views.gigigo.com.tviewpager.CallBackAdapterItemInstanciate;
import views.gigigo.com.tviewpager.CustomHorizontalPagerAdapter;
import views.gigigo.com.tviewpager.OnVHPageChangeListener;
import views.gigigo.com.tviewpager.VerticalViewPager;

public class MainActivity extends AppCompatActivity {

  ArrayList<views.gigigo.com.tviewpager.ModelObject> lstModelVertical = new ArrayList<>();
  ArrayList<views.gigigo.com.tviewpager.ModelObject> lstModelHorizontalFirstItem =
      new ArrayList<>();
  CallBackAdapterItemInstanciate myCallBack = new CallBackAdapterItemInstanciate() {
    @Override public Object OnVerticalInstantiateItem(ViewGroup collection, int position) {
      LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
      ViewGroup layout =
          (ViewGroup) inflater.inflate(R.layout.view_vertical_title, collection, false);
      if (position % 2 == 0) {
        layout.setBackgroundColor(Color.DKGRAY);//par
      } else {
        layout.setBackgroundColor(Color.MAGENTA);//impar
      }
      TextView txtTitle = (TextView) layout.findViewById(R.id.txtTitle);
      txtTitle.setText(lstModelVertical.get(position).getTitle());
      return layout;
    }

    @Override public Object OnHorizontalInstantiateItem(ViewGroup collection, int position) {
      LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
      ViewGroup layout =
          (ViewGroup) inflater.inflate(R.layout.view_horizontal_title, collection, false);
      int index = CustomHorizontalPagerAdapter.getVirtualPosition(position,
          lstModelHorizontalFirstItem.size());
      if (index % 2 == 0) {
        layout.setBackgroundColor(Color.BLUE);//par
      } else {
        layout.setBackgroundColor(Color.GRAY);//impar
      }
      TextView txtTitle = (TextView) layout.findViewById(views.gigigo.com.tviewpager.R.id.txtTitle);
      txtTitle.setText(lstModelHorizontalFirstItem.get(index).getTitle());

      return layout;
    }
  };
  private VerticalViewPager viewPager;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    createModels();
    //VerticalViewPager viewPager = (VerticalViewPager) findViewById(R.id.viewpager);
    //viewPager.setAdapter(
    //    new CustomVerticalPagerAdapter(this, lstModelVertical, lstModelHorizontalFirstItem));

    viewPager = (VerticalViewPager) findViewById(R.id.viewpagerlib);
    viewPager.setAdapter(
        new views.gigigo.com.tviewpager.CustomVerticalPagerAdapter(this, lstModelVertical,
            lstModelHorizontalFirstItem, myCallBack, new OnVHPageChangeListener() {
          @Override public void onChangeHorizontalPage(int position) {
            Log.d("TAG", "Position Horizontal: " + position);

          }

          @Override public void onChangeVerticalPage(int position) {
            Log.d("TAG", "Position Vertical: " + position);
          }
        }));
    viewPager.nextPage(true);
  }

  private void createModels() {
    for (int i = 0; i < 5; i++) {
      lstModelVertical.add(
          new views.gigigo.com.tviewpager.ModelObject("Vertical " + i)); //el i0 nunca se mostrará
      lstModelHorizontalFirstItem.add(
          new views.gigigo.com.tviewpager.ModelObject("Horizontal " + i));
    }
    lstModelVertical.add(new views.gigigo.com.tviewpager.ModelObject("Vertical one extra"));
  }
}

