package views.gigigo.com.tviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by nubor on 28/03/2017.
 */
public class HorizontalViewPager extends ViewPager {

  private OnVHPageChangeListener onVHPageChangeListener;

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
    //setPageTransformer(true, new ZoomOutPageTransformer());
   // setPageTransformer(true, new DepthPageTransformer());
    // The easiest way to get rid of the overscroll drawing that happens on the left and right
    setOverScrollMode(OVER_SCROLL_NEVER);
    if( UtilTouchPager.mHorizontalVP==null)  UtilTouchPager.mHorizontalVP = this;

    setOnPageChangeListener(onPageChangeListener);
  }

  /**
   * Swaps the X and Y coordinates of your touch event.
   */
  @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
   // System.out.println("VPH ### HORIZONTAL" + UtilTouchPager.mBInterceptHorizontal);

    if (UtilTouchPager.mBInterceptHorizontal) {
      return super.onInterceptTouchEvent(ev);
    } else {
      return false;
    }
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
   // System.out.println("VPH ### onTouchEvent");

    if (UtilTouchPager.mBInterceptINH) UtilTouchPager.onTouchMethod(this, event);

    if (UtilTouchPager.mBInterceptHorizontal) {
      return super.onTouchEvent(event);
    } else {
      return false;
    }
  }

  @Override public void setAdapter(PagerAdapter adapter) {
    super.setAdapter(adapter);
    CustomHorizontalPagerAdapter horizontalPagerAdapter = (CustomHorizontalPagerAdapter) adapter;
    this.setOffscreenPageLimit(horizontalPagerAdapter.getRealCount()+1);
  }

  public void setOnVHPageChangeListener(OnVHPageChangeListener onVHPageChangeListener) {
    this.onVHPageChangeListener = onVHPageChangeListener;
  }

  public class ZoomOutPageTransformer implements PageTransformer {
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

  public class DepthPageTransformer implements PageTransformer {
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

  private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override public void onPageSelected(int position) {
      if (onVHPageChangeListener != null) {
        onVHPageChangeListener.onChangeHorizontalPage(position);
      }
    }

    @Override public void onPageScrollStateChanged(int state) {

    }
  };
}
