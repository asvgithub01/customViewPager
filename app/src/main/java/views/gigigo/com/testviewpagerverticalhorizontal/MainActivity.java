package views.gigigo.com.testviewpagerverticalhorizontal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  ArrayList<ModelObject> lstModelVertical = new ArrayList<>();
  ArrayList<ModelObject> lstModelHorizontalFirstItem = new ArrayList<>();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    createModels();
    VerticalViewPager viewPager = (VerticalViewPager) findViewById(R.id.viewpager);
    viewPager.setAdapter(
        new CustomVerticalPagerAdapter(this, lstModelVertical, lstModelHorizontalFirstItem));

    //RelativeLayout lytRelative = (RelativeLayout) findViewById(R.id.lytRelative);
    //lytRelative.setOnTouchListener(new View.OnTouchListener() {
    //  @Override public boolean onTouch(View v, MotionEvent event) {
    //    return onTouchMethod(event);
    //  }
    //});
  }

  private void createModels() {
    for (int i = 0; i < 5; i++) {
      lstModelVertical.add(new ModelObject("Vertical " + i)); //el i0 nunca se mostrará
      lstModelHorizontalFirstItem.add(new ModelObject("Horizontal " + i));
    }
  }


}

