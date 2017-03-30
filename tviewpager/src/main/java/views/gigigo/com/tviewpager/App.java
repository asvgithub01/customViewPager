package views.gigigo.com.tviewpager;

import android.support.v4.view.ViewPager;
import android.view.MotionEvent;

/**
 * Created by nubor on 28/03/2017.
 */
public class App  {
  static boolean mBInterceptVertical = false;
  static boolean mBInterceptHorizontal = true;
  static boolean mBInterceptINH = true;

  public static ViewPager mVerticalVP;
  public static ViewPager mHorizontalVP;

  private static float downX;
  private static float downY;
  private static boolean isTouchCaptured;
  private static float upX1;
  private static float upY1;

  public static boolean onTouchMethod(ViewPager vp, MotionEvent event) {
    switch (event.getAction()) {
      case MotionEvent.ACTION_UP: {
       // System.out.println("@@@ ACTION_UP");
        downX = 0;
        downY = 0;
        isTouchCaptured = false;
      }
      break;
      case MotionEvent.ACTION_DOWN: {
        downX = event.getRawX();
        downY = event.getRawY();
       // System.out.println("@@@ ACTION_DOWN" + downX + ":" + downY);
        isTouchCaptured = true;
      }
      break;
      case MotionEvent.ACTION_MOVE: {
       // System.out.println("@@@ ACTION_MOVE" + upX1 + ":" + upY1);
        if (isTouchCaptured) {
          upX1 = event.getRawX();
          upY1 = event.getRawY();

          float deltaX = upX1 - downX;
          float deltaY = upY1 - downY;
          //HORIZONTAL SCROLL
          if (Math.abs(deltaX) > Math.abs(deltaY)) {
          //  System.out.println(" @@@ HORIZONTAL" + Math.abs(deltaX));
            if (Math.abs(deltaX) > 240) {
              // left or right
              //fixme comprobarlo si es un instance of
              if (!App.mBInterceptHorizontal && mVerticalVP.getCurrentItem()==0) {

                int idxVideoTo = mHorizontalVP.getCurrentItem();// %((CustomHorizontalPagerAdapter)mHorizontalVP.getAdapter()).getRealCount();
                if (downX > upX1) {
                  idxVideoTo = idxVideoTo + 1;
                } else {
                  idxVideoTo = idxVideoTo - 1;
                }

                if (idxVideoTo < 0) idxVideoTo = 0;
                if (idxVideoTo >= mHorizontalVP.getAdapter().getCount())  {
                  idxVideoTo =  mHorizontalVP.getAdapter().getCount();
                }
                mHorizontalVP.setCurrentItem(idxVideoTo,false); //wiija el false elimina la animacion y hace q funcione en tiempo
               // mHorizontalVP.
              }
              if(mVerticalVP.getCurrentItem()==0) {
                App.mBInterceptHorizontal = true;
                App.mBInterceptVertical = false;
                App.mBInterceptINH = true;
              }
            }

           // System.out.println("@@@ HORIZONTAL  mBInterceptVertical" + App.mBInterceptVertical);
           // System.out.println("@@@ HORIZONTAL  mBInterceptHorizontal" + App.mBInterceptHorizontal);
          }
          //VERTICAL SCROLL
          else {
            System.out.println(" @@@VERTICAL" + Math.abs(deltaY));
            if (Math.abs(deltaY) > 320) {

              if (!App.mBInterceptVertical) {

                int idxVideoTo = mVerticalVP.getCurrentItem();
                if (downY > upY1) {
                  idxVideoTo = idxVideoTo + 1;
                } else {
                  idxVideoTo = idxVideoTo - 1;
                }

                if (idxVideoTo < 0) idxVideoTo = 0;
                if (idxVideoTo >= vp.getAdapter().getCount() - 1) {
                  idxVideoTo = mVerticalVP.getAdapter().getCount() - 1;
                }
                mVerticalVP.setCurrentItem(idxVideoTo);
              }
              App.mBInterceptHorizontal = false;
              App.mBInterceptVertical = true;
              App.mBInterceptINH = false;
            }
           // System.out.println("@@@ VERTICAL  mBInterceptVertical" + App.mBInterceptVertical);
           // System.out.println("@@@ VERTICAL  mBInterceptHorizontal" + App.mBInterceptHorizontal);
          }
          return true;
        }
      }
      break;
      case MotionEvent.ACTION_CANCEL: {
        isTouchCaptured = false;
      }
      break;
    }
    return true;
  }
}
