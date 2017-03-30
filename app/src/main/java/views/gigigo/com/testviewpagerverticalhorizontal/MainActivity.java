package views.gigigo.com.testviewpagerverticalhorizontal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  ArrayList<views.gigigo.com.tviewpager.ModelObject> lstModelVertical = new ArrayList<>();
  ArrayList<views.gigigo.com.tviewpager.ModelObject> lstModelHorizontalFirstItem = new ArrayList<>();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    createModels();
    //VerticalViewPager viewPager = (VerticalViewPager) findViewById(R.id.viewpager);
    //viewPager.setAdapter(
    //    new CustomVerticalPagerAdapter(this, lstModelVertical, lstModelHorizontalFirstItem));

    views.gigigo.com.tviewpager.VerticalViewPager viewPager = (views.gigigo.com.tviewpager.VerticalViewPager) findViewById(R.id.viewpagerlib);
    viewPager.setAdapter(
        new views.gigigo.com.tviewpager.CustomVerticalPagerAdapter(this, lstModelVertical, lstModelHorizontalFirstItem));
  }

  private void createModels() {
    for (int i = 0; i < 5; i++) {
      lstModelVertical.add(new views.gigigo.com.tviewpager.ModelObject("Vertical " + i)); //el i0 nunca se mostrará
      lstModelHorizontalFirstItem.add(new views.gigigo.com.tviewpager.ModelObject("Horizontal " + i));
    }
    lstModelVertical.add(new views.gigigo.com.tviewpager.ModelObject("Vertical one extra"));
  }


}

