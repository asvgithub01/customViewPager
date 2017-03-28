package views.gigigo.com.testviewpagerverticalhorizontal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
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

  RelativeLayout lytRelative = (RelativeLayout) findViewById(R.id.lytRelative);
    lytRelative.setOnTouchListener(new View.OnTouchListener() {
      @Override public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_UP: {
            System.out.println(" @@@ ACTION_UP");

          }
          break;
          case MotionEvent.ACTION_DOWN: {
            System.out.println("VPH @@@ ACTION_DOWN");
            downX = event.getRawX();
            downY = event.getRawY();
            System.out.println("VPH @@@ ACTION_DOWN" + downX + ":" + downY);
            isTouchCaptured = true;
          } break;
          case MotionEvent.ACTION_MOVE: {
            System.out.println("VPH @@@ ACTION_MOVE" + upX1 + ":" + upY1);
            if (isTouchCaptured) {
              upX1 = event.getRawX();
              upY1 = event.getRawY();

              float deltaX = upX1 - downX;
              float deltaY = upY1 - downY;
              //HORIZONTAL SCROLL
              if (Math.abs(deltaX) > Math.abs(deltaY)) {
                System.out.println(" @@@ HORIZONTAL" + Math.abs(deltaX));
                if (Math.abs(deltaX) > 20) {
                  // left or right
                  App.mBInterceptHorizontal = true;
                  App.mBInterceptVertical = false;
                }
                System.out.println("@@@ HORIZONTAL  mBInterceptVertical" + App.mBInterceptVertical);
                System.out.println(
                    "@@@ HORIZONTAL  mBInterceptHorizontal" + App.mBInterceptHorizontal);
              }
              //VERTICAL SCROLL
              else {
                System.out.println(" @@@VERTICAL" + Math.abs(deltaY));
                if (Math.abs(deltaY) > 20) {
                  App.mBInterceptHorizontal = false;
                  App.mBInterceptVertical = true;
                }
                System.out.println("@@@ VERTICAL  mBInterceptVertical" + App.mBInterceptVertical);
                System.out.println("@@@ VERTICAL  mBInterceptHorizontal" + App.mBInterceptHorizontal);
              }
              return true;
            }
          }break;
          case MotionEvent.ACTION_CANCEL: {
            isTouchCaptured = false;
          }break;
        }
        return true;
      }
    });


  }

  private void createModels() {
    for (int i = 0; i < 5; i++) {
      lstModelVertical.add(new ModelObject("Vertical " + i)); //el i0 nunca se mostrará
      lstModelHorizontalFirstItem.add(new ModelObject("Horizontal " + i));
    }
  }

  private float downX;
  private float downY;
  private boolean isTouchCaptured;
  private float upX1;
  private float upY1;


}

