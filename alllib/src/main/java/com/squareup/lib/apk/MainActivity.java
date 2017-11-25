//package com.squareup.lib.apk;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.os.Environment;
//import android.widget.ListAdapter;
//
//import com.squareup.lib.BaseApplication;
//
//import java.io.File;
//import java.util.Collection;
//import java.util.List;
//
//import androidx.pluginmgr.PluginManager;
//import androidx.pluginmgr.environment.PlugInfo;
//
//public class MainActivity extends Activity {
//    //
//    private PluginManager plugMgr;
//
//    private static final String sdcard = Environment
//            .getExternalStorageDirectory().getAbsolutePath();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        PluginManager.init(BaseApplication.getApplication());
//        plugMgr = PluginManager.getSingleton();
//
//        String pluginSrcDir = sdcard;
//
////		plugListView.setOnItemClickListener(new OnItemClickListener() {
////			@Override
////			public void onItemClick(AdapterView<?> parent, View view,
////									int position, long id) {
////				plugItemClick(position);
////			}
////		});
//
////		final Context context = this;
////		pluginLoader.setOnClickListener(new View.OnClickListener() {
//
////
////			@Override
////			public void onClick(View v) {
////				final String dirText = pluginDirTxt.getText().toString().trim();
////				if (TextUtils.isEmpty(dirText)) {
////					Toast.makeText(context, getString(R.string.pl_dir),
////							Toast.LENGTH_LONG).show();
////					return;
////				}
////				if (plugLoading) {
////					Toast.makeText(context, getString(R.string.loading),
////							Toast.LENGTH_LONG).show();
////					return;
////				}
////				String strDialogTitle = getString(R.string.dialod_loading_title);
////				String strDialogBody = getString(R.string.dialod_loading_body);
////				final ProgressDialog dialogLoading = ProgressDialog.show(
////						context, strDialogTitle, strDialogBody, true);
////        new Thread(new Runnable() {
////            @Override
////            public void run() {
//                boolean plugLoading = false;
//                plugLoading = true;
//                try {
//                    List<PlugInfo> plugs = plugMgr
//                            .loadPlugin(new File("/sdcard/crash/a.apk"));
//                    PluginManager.getSingleton().dump();
////                    setPlugins(plugs);
//                    plugMgr.startMainActivity(this, plugs.get(0));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
////							dialogLoading.dismiss();
//                }
//                plugLoading = false;
////            }
////        }).start();
////			}
////		});
//    }
//
////    private void plugItemClick(int position) {
////        PlugInfo plug = (PlugInfo) plugListView.getItemAtPosition(position);
////
////    }
////
////    private void setPlugins(final Collection<PlugInfo> plugs) {
//////        if (plugs == null || plugs.isEmpty()) {
//////            return;
//////        }
//////        final ListAdapter adapter = new PlugListViewAdapter(this, plugs);
//////        runOnUiThread(new Runnable() {
//////            public void run() {
//////                plugListView.setAdapter(adapter);
//////            }
//////        });
////    }
//}
