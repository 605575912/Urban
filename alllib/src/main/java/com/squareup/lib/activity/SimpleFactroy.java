//package com.squareup.lib.activity;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.content.res.Configuration;
//import android.content.res.Resources;
//import android.os.Bundle;
//import android.view.KeyEvent;
//import android.view.MotionEvent;
//import android.view.View;
//
//import com.squareup.lib.EventMainObject;
//import com.squareup.lib.EventThreadObject;
//import com.squareup.lib.utils.IProguard;
//
///**
// * Created by lzx on 2016/11/11.
// */
//
//public class SimpleFactroy implements IProguard.ProtectClass {
//    public Activity mactivity;
//
//    public Activity getActivity() {
//        return mactivity;
//    }
//
//    protected void setContentView(int layoutResID) {
//        mactivity.setContentView(layoutResID);
//
//    }
//
//    public Resources getResources() {
//        return mactivity.getResources();
//    }
//
//    protected void setContentView(View view) {
//        mactivity.setContentView(view);
//    }
//
//    protected void finish() {
//        mactivity.finish();
//    }
//
//    protected void startActivity(Intent intent) {
//        mactivity.startActivity(intent);
//    }
//
//    protected Intent getIntent() {
//        return mactivity.getIntent();
//    }
//
//    protected View findViewById(int id) {
//        return mactivity.findViewById(id);
//    }
//
//    public SimpleFactroy(Activity mactivity) {
//        this.mactivity = mactivity;
//    }
//
//    public SimpleFactroy() {
//
//    }
//
//    protected void onCreate(Bundle savedInstanceState) {
//    }
//
//    protected void onSaveInstanceState(Bundle outState) {
//    }
//
//    protected void onPause() {
//
//    }
//
//    public void onEventMain(EventMainObject event) {
//    }
//
//    public void onEventThread(EventThreadObject event) {
//    }
//
//    protected void onStop() {
//
//    }
//
//
//    protected void onPostCreate(Bundle savedInstanceState) {
//
//    }
//
//
//    protected void onRestart() {
//
//    }
//    protected void onBackPressed() {
//
//    }
//
//    protected void onStart() {
//
//    }
//
//
//    protected void onNewIntent(Intent intent) {
//
//    }
//
//    public void onConfigurationChanged(Configuration newConfig) {
//    }
//
//    public void onWindowFocusChanged(boolean hasFocus) {
//    }
//
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        return false;
//    }
//
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        return false;
//    }
//
//    public boolean onTouchEvent(MotionEvent event) {
//        return false;
//    }
//
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        return false;
//    }
//
//
//    protected void onResume() {
//
//    }
//
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//    }
//
//
//    protected void onDestroy() {
//    }
//}
