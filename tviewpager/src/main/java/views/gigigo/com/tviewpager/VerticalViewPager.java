package views.gigigo.com.tviewpager;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Display;
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
    UtilTouchPager.mVerticalVP = this;

    if (UtilTouchPager.pixelsChangedX == 0) {
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
        Display display = null;
        display = this.getDisplay();

        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        UtilTouchPager.pixelsChangedX = width / 6;
        int height = size.y;
        UtilTouchPager.pixelsChangedY = height / 8;
      } else {
        UtilTouchPager.pixelsChangedX = 240;
        UtilTouchPager.pixelsChangedY = 320;
      }
    }
  }

  @Override public void setAdapter(PagerAdapter adapter) {
    super.setAdapter(adapter);
    CustomVerticalPagerAdapter customAdapter = (CustomVerticalPagerAdapter) adapter;
    this.setOffscreenPageLimit(customAdapter.mLstVertical.size() + 1);
  }

  private class VerticalPageTransformer implements PageTransformer {
    private static final float MIN_SCALE = 0.75f;

    @Override public void transformPage(View view, float position) {
     if(position==-1 || position==1)
       view.setVisibility(GONE);
      else
        view.setVisibility(VISIBLE);
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

  @Override public boolean onInterceptTouchEvent(MotionEvent event) {
    // System.out.println("VPV ###  VERTICAL " + UtilTouchPager.mBInterceptVertical);

    if (getAdapter().getItemPosition(this.getCurrentItem()) <= 0) {
      UtilTouchPager.mBInterceptINH = true;
    } else {
      UtilTouchPager.mBInterceptINH = false;
    }

    if (UtilTouchPager.mBInterceptVertical) {
      swapXY(event);
      return super.onInterceptTouchEvent(swapXY(event));
    } else {
      return false;
    }
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    //System.out.println("VPV ### onTouchEvent" + event.getAction());

    if (UtilTouchPager.mBInterceptVertical) UtilTouchPager.onTouchMethod(this, event);

    if (UtilTouchPager.mBInterceptVertical) {
      return super.onTouchEvent(swapXY(event));
    } else {
      return false;
    }
  }
}