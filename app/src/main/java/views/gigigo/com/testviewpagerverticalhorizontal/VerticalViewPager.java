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

  @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
    System.out.println("mBIntercept" + mBIntercept + "************************");
    return mBIntercept;
    ////boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
    //swapXY(ev);
    //
    ////System.out.println("prevX" + prevX + "************************");
    ////System.out.println("prevY" + prevY + "************************");
    ////float dX = prevX - ev.getX();
    ////float dY = prevY - ev.getY();
    ////System.out.println("ev.getX()" + ev.getX() + "************************");
    ////System.out.println("ev.getY()" + ev.getY() + "************************");
    ////
    ////System.out.println("DX" + dX + "************************");
    ////System.out.println("DY" + dY + "************************");
    ////
    ////prevX = ev.getX();
    ////prevY = ev.getY();
    ////if (Math.abs(dX) > Math.abs(dY)) {
    ////  System.out.println("************************HORIZONTAL************************");
    ////  //if (((CustomVerticalPagerAdapter) this.getAdapter()).mPosition == 0) {
    ////  //  return true;
    ////  //} else {
    ////  return false;
    ////  //}
    ////} else {
    ////  System.out.println("************************VERTICAL************************");
    ////  //if (((CustomVerticalPagerAdapter) this.getAdapter()).mPosition == 0) {
    ////  //  return false;
    ////  //} else {
    ////  return true;
    ////}
    //switch (ev.getAction()) {
    //  case MotionEvent.ACTION_MOVE: {
    //    downX = ev.getX();
    //    downY = ev.getY();
    //
    //    if (!isTouchCaptured) {
    //      upX1 = ev.getX();
    //      upY1 = ev.getY();
    //      isTouchCaptured = true;
    //    } else {
    //      upX2 = ev.getX();
    //      upY2 = ev.getY();
    //      float deltaX = upX1 - upX2;
    //      float deltaY = upY1 - upY2;
    //      //HORIZONTAL SCROLL
    //      if (Math.abs(deltaX) > Math.abs(deltaY)) {
    //
    //        System.out.println("###HORIZONTAL");
    //        //return false; //test
    //        if (Math.abs(deltaX) > min_distance) {
    //          // left or right
    //          if (deltaX < 0) {
    //            return false;
    //            //if(!eventSent && mSwiperListener!=null){
    //            //  mSwiperListener.onLeftSwipe();
    //            //  eventSent = true;
    //            //  return false; //test
    //            //}
    //          }
    //          if (deltaX > 0) {
    //            return false;
    //            //if(!eventSent && mSwiperListener!=null){
    //            //  if(mSwiperListener.onRightSwipe()){
    //            //    eventSent = true;
    //            //    return false;
    //            //  }
    //            //}
    //          }
    //        } else {
    //          //not long enough swipe...
    //        }
    //      }
    //      //VERTICAL SCROLL
    //      else {
    //        System.out.println("###VERTICAL");
    //
    //        if (Math.abs(deltaY) > min_distance) {
    //          // top or down
    //          if (deltaY < 0) {
    //            return super.onInterceptTouchEvent(swapXY(ev));
    //          }
    //          if (deltaY > 0) {
    //            return super.onInterceptTouchEvent(swapXY(ev));
    //          }
    //        } else {
    //          //not long enough swipe...
    //        }
    //      }
    //    }
    //  }
    //  break;
    //  case MotionEvent.ACTION_UP:
    //  case MotionEvent.ACTION_CANCEL: {
    //    isTouchCaptured = false;
    //    eventSent = false;
    //  }
    //}
    //return false;
  }

  //if (((CustomVerticalPagerAdapter) this.getAdapter()).mPosition == 0) {
  //  return false;
  //} else {
  //  return true;
  //}

  //return intercepted;
  // return true;
  //}
  static boolean mBIntercept = true;

  @Override public boolean onTouchEvent(MotionEvent event) {
    // mBIntercept=true;
    mBIntercept = evaluateIntercept(swapXY(event));
    if (mBIntercept) {
      return super.onTouchEvent(swapXY(event));
    } else {
      return super.onTouchEvent(event);
    }
  }

  private boolean evaluateIntercept(MotionEvent event) {
    switch (event.getAction()) {
      case MotionEvent.ACTION_MOVE: {
        downX = event.getX();
        downY = event.getY();

        if (!isTouchCaptured) {
          upX1 = event.getX();
          upY1 = event.getY();
          isTouchCaptured = true;
        } else {
          upX2 = event.getX();
          upY2 = event.getY();
          float deltaX = upX1 - upX2;
          float deltaY = upY1 - upY2;
          //HORIZONTAL SCROLL
          if (Math.abs(deltaX) > Math.abs(deltaY)) {
            System.out.println("###HORIZONTAL");
            if (Math.abs(deltaX) > min_distance) {
              // left or right
              return false;
            }
          }
          //VERTICAL SCROLL
          else {
            System.out.println("###VERTICAL");
            if (Math.abs(deltaY) > min_distance) {
              return true;
            }
          }
        }
      }
      break;
      case MotionEvent.ACTION_UP:
      case MotionEvent.ACTION_CANCEL: {
        isTouchCaptured = false;
        eventSent = false;
      }
    }
    return false;
  }

  SwiperListener mSwiperListener;
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

  public void setmSwiperListener(SwiperListener mSwiperListener) {
    this.mSwiperListener = mSwiperListener;
  }

  public static interface SwiperListener {
    public boolean onLeftSwipe();

    public boolean onDownSwipe();

    public boolean onTopSwipe();

    public boolean onRightSwipe();
  }
}