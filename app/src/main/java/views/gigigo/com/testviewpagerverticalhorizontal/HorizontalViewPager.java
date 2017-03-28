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
    System.out.println("VPH ### HORIZONTAL" + App.mBInterceptHorizontal);

    if (App.mBInterceptHorizontal) {
      return super.onInterceptTouchEvent(ev);
    } else {
      return false;
    }
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    System.out.println("VPH ### onTouchEvent");
    //if (App.mBInterceptINH) {
    //  switch (event.getAction()) {
    //    case MotionEvent.ACTION_UP: {
    //      System.out.println("VPH @@@ ACTION_up");
    //    }
    //    case MotionEvent.ACTION_DOWN: {
    //      System.out.println("VPH @@@ ACTION_DOWN");
    //      downX = event.getRawX();
    //      downY = event.getRawY();
    //      System.out.println("VPH @@@ ACTION_DOWN" + downX + ":" + downY);
    //      isTouchCaptured = true;
    //    }
    //    case MotionEvent.ACTION_MOVE: {
    //      if (isTouchCaptured) {
    //        upX1 = event.getRawX();
    //        upY1 = event.getRawY();
    //        System.out.println("VPH @@@ ACTION_MOVE" + upX1 + ":" + upY1);
    //        float deltaX = upX1 - downX;
    //        float deltaY = upY1 - downY;
    //        //HORIZONTAL SCROLL
    //        if (Math.abs(deltaX) > Math.abs(deltaY)) {
    //          System.out.println("VPH @@@ HORIZONTAL" + Math.abs(deltaX));
    //          // if (Math.abs(deltaX) > min_distance) {
    //          // left or right
    //          App.mBInterceptHorizontal = true;
    //          App.mBInterceptVertical = false;
    //          // }
    //          System.out.println("@@@ HORIZONTAL  mBInterceptVertical" + App.mBInterceptVertical);
    //          System.out.println(
    //              "@@@ HORIZONTAL  mBInterceptHorizontal" + App.mBInterceptHorizontal);
    //        }
    //        //VERTICAL SCROLL
    //        else {
    //          System.out.println("VPH @@@VERTICAL" + Math.abs(deltaY));
    //          // if (Math.abs(deltaY) > min_distance) {
    //          App.mBInterceptHorizontal = false;
    //          App.mBInterceptVertical = true;
    //          // }
    //          System.out.println("@@@ VERTICAL  mBInterceptVertical" + App.mBInterceptVertical);
    //          System.out.println("@@@ VERTICAL  mBInterceptHorizontal" + App.mBInterceptHorizontal);
    //        }
    //      }
    //    }
    //    break;
    //
    //    case MotionEvent.ACTION_CANCEL: {
    //      isTouchCaptured = false;
    //      eventSent = false;
    //    }
    //  }
    //}
    //SIN ESTO O CON ESTO EL VERTICAL FUNCIONA

    //  return false;

    if (App.mBInterceptHorizontal) {
      return super.onTouchEvent(event);
    } else {
      return false;
    }
  }

  private float downX;
  private float downY;
  private boolean isTouchCaptured;
  private float upX1;
  private float upY1;
  private float upX2;
  private float upY2;
  private float x1, x2;
  static final int min_distance = 20;

  boolean eventSent = false;

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
        view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
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
        float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
        view.setScaleX(scaleFactor);
        view.setScaleY(scaleFactor);
      } else { // (1,+Infinity]
        // This page is way off-screen to the right.
        view.setAlpha(0);
      }
    }
  }
}
