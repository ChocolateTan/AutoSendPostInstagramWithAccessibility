package com.example.don.testaccessibility;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by don on 2/14/17.
 */

public class MyAccessibilityService extends AccessibilityService {
  private static final String TAG = MyAccessibilityService.class.getSimpleName();
  // 大多数的手机包名一样，联想部分机型的手机不一样
  private String[] packageNames = {"com.ziines.it", "com.instagram.android"};
  private final int OPEN_GALLERY = 1000;
  private final int PICK_IMAGE = 2000;
  private final int PICK_IMAGE_NEXT = 2001;
  private final int PICK_FILTER = 3000;
  private final int PICK_FILTER_NEXT = 3001;
  private final int INPUT_TEXT = 4000;
  private final int SHARE = 5000;

  private WeakReference<MyAccessibilityService> weakReference;
  private Handler mHandler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      if (null == weakReference || null == weakReference.get()) {
        Log.i(TAG, "weakReference=" + weakReference);
        return;
      }
      switch (msg.what) {
        case OPEN_GALLERY: {
          weakReference.get().goToPickPhoto();
        }
        break;
        case PICK_IMAGE: {
          weakReference.get().pickPhoto();
        }
        break;
        case PICK_IMAGE_NEXT: {
          weakReference.get().clickPhotoNext();
        }
        break;
        case PICK_FILTER: {
          weakReference.get().clickFilterNext();
        }
        break;
        case PICK_FILTER_NEXT: {
//          weakReference.get().cli
        }
        break;
        case INPUT_TEXT: {
          weakReference.get().inputText();
        }
        break;
        case SHARE: {
          weakReference.get().share();
        }
        break;
      }
    }
  };

  //  private String[] packageNames = { "com.android.packageinstaller", "com.lenovo.security", "com.lenovo.safecenter" };
  @Override
  public void onCreate() {
    super.onCreate();
    weakReference = new WeakReference<>(this);
//    AccessibilityServiceInfo mAccessibilityServiceInfo = new AccessibilityServiceInfo();
    // 响应事件的类型，这里是全部的响应事件（长按，单击，滑动等）
//    mAccessibilityServiceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
    // 过滤的包名
//    mAccessibilityServiceInfo.packageNames = packageNames;
//    setServiceInfo(mAccessibilityServiceInfo);
  }

  @Override
  public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
    if(!MyApplication.sIsRun || !MyApplication.sIsOn) {
      MyApplication.sIsRun=true;
      mHandler.sendEmptyMessage(OPEN_GALLERY);
    }
//    mHandler.sendEmptyMessageDelayed(PICK_IMAGE_NEXT, 1000);
  }

  @Override
  public void onInterrupt() {

  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  public void goToPickPhoto() {
    findByClassName(getRootInActiveWindow(), "com.instagram.base.activity.tabactivity.IgTabWidget", new FindNodeDelegate() {
      @Override
      public void doFindSuccess(AccessibilityNodeInfo node) {
        if (null != node) {
          Log.i(TAG, "find node goToPickPhoto");

          node.getChild(2).performAction(AccessibilityNodeInfo.ACTION_CLICK);
//          node.recycle();
          mHandler.sendEmptyMessageDelayed(PICK_IMAGE, 3000);
        } else {
          Log.i(TAG, "no node goToPickPhoto");
        }
      }
    });
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  public void pickPhoto() {
    findByClassName(getRootInActiveWindow(), "android.support.v7.widget.RecyclerView", new FindNodeDelegate() {
      @Override
      public void doFindSuccess(AccessibilityNodeInfo node) {
        if (null != node) {
          Log.i(TAG, "find node pickPhoto=" + node.getClassName());
          node.getChild(3).performAction(AccessibilityNodeInfo.ACTION_CLICK);
//          node.recycle();
          mHandler.sendEmptyMessageDelayed(PICK_IMAGE_NEXT, 3000);
        } else {
          Log.i(TAG, "no node pickPhoto");
        }
      }
    });
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  public void clickPhotoNext() {
    Log.i(TAG, "clickPhotoNext");
    findByResID3(getRootInActiveWindow(), "com.instagram.android:id/next_button_textview", new FindNodeDelegate() {
      @Override
      public void doFindSuccess(AccessibilityNodeInfo node) {
        node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        mHandler.sendEmptyMessageDelayed(PICK_FILTER, 2000);
      }
    });
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  public void clickFilterNext() {
    findByResID3(getRootInActiveWindow(), "com.instagram.android:id/next_button_textview", new FindNodeDelegate() {
      @Override
      public void doFindSuccess(AccessibilityNodeInfo node) {
        if (null != node) {
          Log.i(TAG, "find node clickFilterNext=" + node.getClassName());
          node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
          mHandler.sendEmptyMessageDelayed(INPUT_TEXT, 2000);

//          mHandler.sendEmptyMessageDelayed(, 3000);
        } else {
          Log.i(TAG, "no node clickFilterNext");
        }
      }
    });
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  public void inputText() {
    findByResID3(getRootInActiveWindow(), "com.instagram.android:id/caption_text_view", new FindNodeDelegate() {
      @Override
      public void doFindSuccess(AccessibilityNodeInfo node) {
        if (null != node) {
          Log.i(TAG, "find node inputText=" + node.getClassName());

//          node.getText();//.setText("hi hi" + System.currentTimeMillis());
          Bundle arguments = new Bundle();
          arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "hi hi" + System.currentTimeMillis());
          node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
          mHandler.sendEmptyMessageDelayed(SHARE, 2000);
        } else {
          Log.i(TAG, "no node inputText");
        }
      }
    });
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  public void share() {
    findByResID3(getRootInActiveWindow(), "com.instagram.android:id/next_button_textview", new FindNodeDelegate() {
      @Override
      public void doFindSuccess(AccessibilityNodeInfo node) {
        if (null != node) {
          Log.i(TAG, "find node share=" + node.getClassName());

          MyApplication.sIsRun=false;
          node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        } else {
          Log.i(TAG, "no node share");
        }
      }
    });
  }


  public interface FindNodeDelegate {
    void doFindSuccess(AccessibilityNodeInfo node);
  }

  //找到所有节点
  public void findByClassName(AccessibilityNodeInfo info, String className, FindNodeDelegate delegate) {
    if (null != info && info.getChildCount() == 0) {
//      Log.i(TAG, "Text:" + info.getText());
      if (info.getClassName().toString().equals(className)) {
        delegate.doFindSuccess(info);
      }
    } else {
      if (null != info) {
        for (int i = 0; i < info.getChildCount(); i++) {
          if (info.getChild(i) != null) {
//          Log.i(TAG, "child widget----------------------------");
//          Log.i(TAG, "getClassName：" + info.getChild(i).getClassName());
            if (info.getClassName().toString().equals(className)) {
              delegate.doFindSuccess(info);
              break;
            }
            findByClassName(info.getChild(i), className, delegate);
          }
        }
      }
    }
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
  public void recycle(AccessibilityNodeInfo info, String matchFlag) {
    if (info != null) {
      if (info.getChildCount() == 0) {
        if (matchFlag.equals(info.getViewIdResourceName())) {
          Log.e(TAG, "###don info" + info.getClassName() + " : " + info.getContentDescription() + " : " + info.getText() + " : " + info.getViewIdResourceName());
          info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//          mHandler.sendEmptyMessageDelayed(PICK_FILTER, 1000);
        }
      } else {
        int size = info.getChildCount();
        for (int i = 0; i < size; i++) {
          AccessibilityNodeInfo childInfo = info.getChild(i);
          if (childInfo != null) {
//            Log.e(TAG, "index: " + i + " info" + childInfo.getClassName() + " : " + childInfo.getContentDescription()+" : "+info.getText());
            recycle(childInfo, matchFlag);
          }
        }
      }
    }
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
  public void findByResID3(AccessibilityNodeInfo info, String matchFlag, FindNodeDelegate delegate) {
    if(null != info) {
      List<AccessibilityNodeInfo> result = info.findAccessibilityNodeInfosByViewId(matchFlag);
      if (result == null) {
        Log.i(TAG, "# result = null");
      } else {
        for (int i = 0, size = result.size(); i < size; i++) {
          Log.e(TAG, "###don info" + result.get(i).getClassName() + " : " + result.get(i).getContentDescription() + " : " + result.get(i).getText() + " : " + result.get(i).getViewIdResourceName());
          delegate.doFindSuccess(result.get(i));
        }
      }
    }
  }
}