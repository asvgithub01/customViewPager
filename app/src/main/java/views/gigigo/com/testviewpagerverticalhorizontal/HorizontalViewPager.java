package views.gigigo.com.testviewpagerverticalhorizontal;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by nubor on 28/03/2017.
 */
public class HorizontalViewPager extends ViewPager {

  public HorizontalViewPager(Context context) {
    super(context);
    init();
  }

  public HorizontalViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  private void init() {
    // The majority of the magic happens here
    setPageTransformer(true, new ZoomOutPageTransformer());
    // The easiest way to get rid of the overscroll drawing that happens on the left and right
    setOverScrollMode(OVER_SCROLL_NEVER);
  }



  /**
   * Swaps the X and Y coordinates of your touch event.
   */
  float prevX = 0;
  float prevY = 0;

  private MotionEvent swapXY(MotionEvent ev) {
    float width = getWidth();
    float height = getHeight();

    float newX = (ev.getY() / height) * width;
    float newY = (ev.getX() / width) * height;

    ev.setLocation(newX, newY);

    return ev;
  }
  public boolean canScrollHor(int direction) {
    final int offset = computeHorizontalScrollOffset();
    final int range = computeHorizontalScrollRange() - computeHorizontalScrollExtent();
    if (range == 0) return false;
    if (direction < 0) {
      return offset > 0;
    } else {
      return offset < range - 1;
    }
  }

  @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
   // boolean intercepted = super.onInterceptTouchEvent(ev);
    System.out.println("prevX" + prevX + "************************");
    System.out.println("prevY" + prevY + "************************");
    float dX = prevX - ev.getX();
    float dY = prevY - ev.getY();
    System.out.println("ev.getX()" + ev.getX() + "************************");
    System.out.println("ev.getY()" + ev.getY() + "************************");
    System.out.println("DX" + dX + "************************");
    System.out.println("DY" + dY + "************************");

    prevX = ev.getX();
    prevY = ev.getY();
    if (Math.abs(dX) > Math.abs(dY)) {
      System.out.println("+++++++++VPH HORIZONTAL+++++++++++");
    //  VerticalViewPager.mBIntercept=false;
      //if (((CustomVerticalPagerAdapter) this.getAdapter()).mPosition == 0) {
      //  return true;
      //} else {
      return true;
      //}
    } else {
      System.out.println("+++++++++VPH VERTICAL+++++++++++++++++");
    //  VerticalViewPager.mBIntercept=true;
      //if (((CustomVerticalPagerAdapter) this.getAdapter()).mPosition == 0) {
      //  return false;
      //} else {
      return false;
    }
  }

  @Override public boolean onTouchEvent(MotionEvent ev) {
    return super.onTouchEvent(ev);
  }

  public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;

    public void transformPage(View view, float position) {
      int pageWidth = view.getWidth();
      int pageHeight = view.getHeight();

      if (position < -1) { // [-Infinity,-1)
        // This page is way off-screen to the left.
        view.setAlpha(0);

      } else if (position <= 1) { // [-1,1]
        // Modify the default slide transition to shrink the page as well
        float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
        float vertMargin = pageHeight * (1 - scaleFactor) / 2;
        float horzMargin = pageWidth * (1 - scaleFactor) / 2;
        if (position < 0) {
          view.setTranslationX(horzMargin - vertMargin / 2);
        } else {
          view.setTranslationX(-horzMargin + vertMargin / 2);
        }

        // Scale the page down (between MIN_SCALE and 1)
        view.setScaleX(scaleFactor);
        view.setScaleY(scaleFactor);

        // Fade the page relative to its size.
        view.setAlpha(MIN_ALPHA +
            (scaleFactor - MIN_SCALE) /
                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

      } else { // (1,+Infinity]
        // This page is way off-screen to the right.
        view.setAlpha(0);
      }
    }
  }
  public class DepthPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.75f;

    public void transformPage(View view, float position) {
      int pageWidth = view.getWidth();

      if (position < -1) { // [-Infinity,-1)
        // This page is way off-screen to the left.
        view.setAlpha(0);

      } else if (position <= 0) { // [-1,0]
        // Use the default slide transition when moving to the left page
        view.setAlpha(1);
        view.setTranslationX(0);
        view.setScaleX(1);
        view.setScaleY(1);

      } else if (position <= 1) { // (0,1]
        // Fade the page out.
        view.setAlpha(1 - position);

        // Counteract the default slide transition
        view.setTranslationX(pageWidth * -position);

        // Scale the page down (between MIN_SCALE and 1)
        float scaleFactor = MIN_SCALE
            + (1 - MIN_SCALE) * (1 - Math.abs(position));
        view.setScaleX(scaleFactor);
        view.setScaleY(scaleFactor);

      } else { // (1,+Infinity]
        // This page is way off-screen to the right.
        view.setAlpha(0);
      }
    }
  }
}
