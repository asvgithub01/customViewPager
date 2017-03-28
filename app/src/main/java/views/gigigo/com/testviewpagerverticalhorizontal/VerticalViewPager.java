package views.gigigo.com.testviewpagerverticalhorizontal;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class VerticalViewPager extends ViewPager {

  public VerticalViewPager(Context context) {
    super(context);
    init();
  }

  public VerticalViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  private void init() {
    // The majority of the magic happens here
    setPageTransformer(true, new VerticalPageTransformer());
    // The easiest way to get rid of the overscroll drawing that happens on the left and right
    setOverScrollMode(OVER_SCROLL_NEVER);
  }

  private class VerticalPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.75f;

    @Override public void transformPage(View view, float position) {

      if (position < -1) { // [-Infinity,-1)
        // This page is way off-screen to the left.
        view.setAlpha(0);
      } else if (position <= 0) { // [-1,0]
        // Use the default slide transition when moving to the left page
        view.setAlpha(1);
        // Counteract the default slide transition
        view.setTranslationX(view.getWidth() * -position);

        //set Y position to swipe in from top
        float yPosition = position * view.getHeight();
        view.setTranslationY(yPosition);
        view.setScaleX(1);
        view.setScaleY(1);
      } else if (position <= 1) { // [0,1]
        view.setAlpha(1);

        // Counteract the default slide transition
        view.setTranslationX(view.getWidth() * -position);

        // Scale the page down (between MIN_SCALE and 1)
        float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
        view.setScaleX(scaleFactor);
        view.setScaleY(scaleFactor);
      } else { // (1,+Infinity]
        // This page is way off-screen to the right.
        view.setAlpha(0);
      }
    }
  }

  //@Override protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
  //  if (v instanceof HorizontalViewPager) {
  //    return ((HorizontalViewPager) v).canScrollHor(-dx);
  //  } else {
  //    return super.canScroll(v, checkV, dx, x, y);
  //  }
  //}

  /**
   * Swaps the X and Y coordinates of your touch event.
   */

  private MotionEvent swapXY(MotionEvent ev) {
    float width = getWidth();
    float height = getHeight();

    float newX = (ev.getY() / height) * width;
    float newY = (ev.getX() / width) * height;

    ev.setLocation(newX, newY);

    return ev;
  }

  //false false hace bien el horizontal
  @Override public boolean onInterceptTouchEvent(MotionEvent event) {
    System.out.println("VPV ###  VERTICAL " + App.mBInterceptVertical);
    //return false;//

    if (getAdapter().getItemPosition(this.getCurrentItem()) <= 0) {
      App.mBInterceptINH = true;
    } else {
      App.mBInterceptINH = false;
    }

    //SIN ESTO CHUTA EN VERTICAL evaluateIntercept(swapXY(event));

    if (App.mBInterceptVertical) {
      swapXY(event);
      return super.onInterceptTouchEvent(swapXY(event));
    } else {
      return false;
    }
  }
  private float downX;
  private float downY;
  private boolean isTouchCaptured;
  private float upX1;
  private float upY1;
  @Override public boolean onTouchEvent(MotionEvent event) {
    System.out.println("VPV ### onTouchEvent" +event.getAction());

    /**/
    //return  super.onTouchEvent(swapXY(event));
    //  return false;

    if (App.mBInterceptVertical) {
      return super.onTouchEvent(swapXY(event));
    } else {
      return false;
    }
  }


}