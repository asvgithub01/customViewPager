package views.gigigo.com.testviewpagerverticalhorizontal;

import android.app.Application;
import android.view.MotionEvent;

/**
 * Created by nubor on 28/03/2017.
 */
public class App extends Application {
  static boolean mBInterceptVertical=false;
  static boolean mBInterceptHorizontal=true;
  static boolean mBInterceptINH=true;


  private static float downX;
  private static float downY;
  private static boolean isTouchCaptured;
  private static float upX1;
  private static float upY1;

  public static boolean onTouchMethod(MotionEvent event) {
    switch (event.getAction()) {
      case MotionEvent.ACTION_UP: {
        System.out.println("@@@ ACTION_UP");
        downX = 0;
        downY = 0;
        isTouchCaptured=false;
      }
      break;
      case MotionEvent.ACTION_DOWN: {
        downX = event.getRawX();
        downY = event.getRawY();
        System.out.println("@@@ ACTION_DOWN" + downX + ":" + downY);
        isTouchCaptured = true;
      }
      break;
      case MotionEvent.ACTION_MOVE: {
        System.out.println("@@@ ACTION_MOVE" + upX1 + ":" + upY1);
        if (isTouchCaptured) {
          upX1 = event.getRawX();
          upY1 = event.getRawY();

          float deltaX = upX1 - downX;
          float deltaY = upY1 - downY;
          //HORIZONTAL SCROLL
          if (Math.abs(deltaX) > Math.abs(deltaY)) {
            System.out.println(" @@@ HORIZONTAL" + Math.abs(deltaX));
              if (Math.abs(deltaX) > 140) {
            // left or right
            App.mBInterceptHorizontal = true;
            App.mBInterceptVertical = false;
                App.mBInterceptINH=true;
              }
            System.out.println("@@@ HORIZONTAL  mBInterceptVertical" + App.mBInterceptVertical);
            System.out.println("@@@ HORIZONTAL  mBInterceptHorizontal" + App.mBInterceptHorizontal);
          }
          //VERTICAL SCROLL
          else {
            System.out.println(" @@@VERTICAL" + Math.abs(deltaY));
               if (Math.abs(deltaY) > 200) {
            App.mBInterceptHorizontal = false;
            App.mBInterceptVertical = true;
                 App.mBInterceptINH=false;
              }
            System.out.println("@@@ VERTICAL  mBInterceptVertical" + App.mBInterceptVertical);
            System.out.println("@@@ VERTICAL  mBInterceptHorizontal" + App.mBInterceptHorizontal);
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
