package androidx.pluginmgr.delegate;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.Instrumentation;
import android.app.UiAutomation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * NOTICE: 如果导入的时候这个类出现了红色,
 * 请删除相关方法,因为不同API版本下这个类的方法稍有增删.
 *
 * @author Lody
 * @version 1.0
 */
public class DelegateInstrumentation extends Instrumentation {

    private Instrumentation mBase;

    /**
     * @param mBase 真正的Instrumentation
     */
    public DelegateInstrumentation(Instrumentation mBase) {
        this.mBase = mBase;
    }

    @Override
    public void onCreate(Bundle arguments) {
        mBase.onCreate(arguments);
    }

    @Override
    public void start() {
        mBase.start();
    }

    @Override
    public void onStart() {
        mBase.onStart();
    }

    @Override
    public boolean onException(Object obj, Throwable e) {
        return mBase.onException(obj, e);
    }

    @Override
    public void sendStatus(int resultCode, Bundle results) {
        mBase.sendStatus(resultCode, results);
    }

    @Override
    public void finish(int resultCode, Bundle results) {
        mBase.finish(resultCode, results);
    }

    @Override
    public void setAutomaticPerformanceSnapshots() {
        mBase.setAutomaticPerformanceSnapshots();
    }

    @Override
    public void startPerformanceSnapshot() {
        mBase.startPerformanceSnapshot();
    }

    @Override
    public void endPerformanceSnapshot() {
        mBase.endPerformanceSnapshot();
    }

    @Override
    public void onDestroy() {
        mBase.onDestroy();
    }

    @Override
    public Context getContext() {
        return mBase.getContext();
    }

    @Override
    public ComponentName getComponentName() {
        return mBase.getComponentName();
    }

    @Override
    public Context getTargetContext() {
        return mBase.getTargetContext();
    }

    @Override
    public boolean isProfiling() {
        return mBase.isProfiling();
    }

    @Override
    public void startProfiling() {
        mBase.startProfiling();
    }

    @Override
    public void stopProfiling() {
        mBase.stopProfiling();
    }

    @Override
    public void setInTouchMode(boolean inTouch) {
        mBase.setInTouchMode(inTouch);
    }

    @Override
    public void waitForIdle(Runnable recipient) {
        mBase.waitForIdle(recipient);
    }

    @Override
    public void waitForIdleSync() {
        mBase.waitForIdleSync();
    }

    @Override
    public void runOnMainSync(Runnable runner) {
        mBase.runOnMainSync(runner);
    }

    @Override
    public Activity startActivitySync(Intent intent) {
        return mBase.startActivitySync(intent);
    }

    @Override
    public void addMonitor(ActivityMonitor monitor) {
        mBase.addMonitor(monitor);
    }

    @Override
    public ActivityMonitor addMonitor(IntentFilter filter, ActivityResult result, boolean block) {
        return mBase.addMonitor(filter, result, block);
    }

    @Override
    public ActivityMonitor addMonitor(String cls, ActivityResult result, boolean block) {
        return mBase.addMonitor(cls, result, block);
    }

    @Override
    public boolean checkMonitorHit(ActivityMonitor monitor, int minHits) {
        return mBase.checkMonitorHit(monitor, minHits);
    }

    @Override
    public Activity waitForMonitor(ActivityMonitor monitor) {
        return mBase.waitForMonitor(monitor);
    }

    @Override
    public Activity waitForMonitorWithTimeout(ActivityMonitor monitor, long timeOut) {
        return mBase.waitForMonitorWithTimeout(monitor, timeOut);
    }

    @Override
    public void removeMonitor(ActivityMonitor monitor) {
        mBase.removeMonitor(monitor);
    }

    @Override
    public boolean invokeMenuActionSync(Activity targetActivity, int id, int flag) {
        return mBase.invokeMenuActionSync(targetActivity, id, flag);
    }

    @Override
    public boolean invokeContextMenuAction(Activity targetActivity, int id, int flag) {
        return mBase.invokeContextMenuAction(targetActivity, id, flag);
    }

    @Override
    public void sendStringSync(String text) {
        mBase.sendStringSync(text);
    }

    @Override
    public void sendKeySync(KeyEvent event) {
        mBase.sendKeySync(event);
    }

    @Override
    public void sendKeyDownUpSync(int key) {
        mBase.sendKeyDownUpSync(key);
    }

    @Override
    public void sendCharacterSync(int keyCode) {
        mBase.sendCharacterSync(keyCode);
    }

    @Override
    public void sendPointerSync(MotionEvent event) {
        mBase.sendPointerSync(event);
    }

    @Override
    public void sendTrackballEventSync(MotionEvent event) {
        mBase.sendTrackballEventSync(event);
    }

    @Override
    public Application newApplication(ClassLoader cl, String className, Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return mBase.newApplication(cl, className, context);
    }

    @Override
    public void callApplicationOnCreate(Application app) {
        mBase.callApplicationOnCreate(app);
    }

    @Override
    public Activity newActivity(Class<?> clazz, Context context, IBinder token, Application application, Intent intent, ActivityInfo info, CharSequence title, Activity parent, String id, Object lastNonConfigurationInstance) throws InstantiationException, IllegalAccessException {
        return mBase.newActivity(clazz, context, token, application, intent, info, title, parent, id, lastNonConfigurationInstance);
    }

    @Override
    public Activity newActivity(ClassLoader cl, String className, Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return mBase.newActivity(cl, className, intent);
    }

    @Override
    public void callActivityOnCreate(Activity activity, Bundle icicle) {
        mBase.callActivityOnCreate(activity, icicle);
    }

    @Override
    public void callActivityOnDestroy(Activity activity) {
        mBase.callActivityOnDestroy(activity);
    }

    @Override
    public void callActivityOnRestoreInstanceState(Activity activity, Bundle savedInstanceState) {
        mBase.callActivityOnRestoreInstanceState(activity, savedInstanceState);
    }

    @Override
    public void callActivityOnPostCreate(Activity activity, Bundle icicle) {
        mBase.callActivityOnPostCreate(activity, icicle);
    }

    @Override
    public void callActivityOnNewIntent(Activity activity, Intent intent) {
        mBase.callActivityOnNewIntent(activity, intent);
    }

    @Override
    public void callActivityOnStart(Activity activity) {
        mBase.callActivityOnStart(activity);
    }

    @Override
    public void callActivityOnRestart(Activity activity) {
        mBase.callActivityOnRestart(activity);
    }

    @Override
    public void callActivityOnResume(Activity activity) {
        mBase.callActivityOnResume(activity);
    }

    @Override
    public void callActivityOnStop(Activity activity) {
        mBase.callActivityOnStop(activity);
    }

    @Override
    public void callActivityOnSaveInstanceState(Activity activity, Bundle outState) {
        mBase.callActivityOnSaveInstanceState(activity, outState);
    }

    @Override
    public void callActivityOnPause(Activity activity) {
        mBase.callActivityOnPause(activity);
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    @Override
    public void callActivityOnUserLeaving(Activity activity) {
        mBase.callActivityOnUserLeaving(activity);
    }

    @Override
    public void startAllocCounting() {
        mBase.startAllocCounting();
    }

    @Override
    public void stopAllocCounting() {
        mBase.stopAllocCounting();
    }

    @Override
    public Bundle getAllocCounts() {
        return mBase.getAllocCounts();
    }

    @Override
    public Bundle getBinderCounts() {
        return mBase.getBinderCounts();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public UiAutomation getUiAutomation() {
        return mBase.getUiAutomation();
    }

    @Override
    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Fragment fragment, Intent intent, int requestCode) {
        return mBase.execStartActivity(who, contextThread, token, fragment, intent, requestCode);
    }

    @Override
    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Fragment fragment, Intent intent, int requestCode, Bundle options) {
        return mBase.execStartActivity(who, contextThread, token, fragment, intent, requestCode, options);
    }

    @Override
    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Activity target, Intent intent, int requestCode) {
        return mBase.execStartActivity(who, contextThread, token, target, intent, requestCode);
    }

    @Override
    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Activity target, Intent intent, int requestCode, Bundle options) {
        return mBase.execStartActivity(who, contextThread, token, target, intent, requestCode, options);
    }

}
