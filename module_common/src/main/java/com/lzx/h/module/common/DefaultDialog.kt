package com.lzx.h.module.common

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.drawable.AnimationDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by liangzhenxiong on 2017/10/18.
 */

class DefaultDialog {
    internal var dialog: Dialog? = null

    fun showLoading(activity: Activity, defaultLinstener: DefaultLinstener) {
        val dialog = Dialog(activity, R.style.DialogStyle)
        dialog.setContentView(R.layout.loading_layout)
        val imageview = dialog.findViewById<View>(R.id.imageview) as ImageView
        val tv_confirm = dialog.findViewById<View>(R.id.tv_confirm) as TextView
        tv_confirm.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                dialog.cancel()
            }
        })

        dialog.setOnCancelListener(object : DialogInterface.OnCancelListener {
            override fun onCancel(p0: DialogInterface?) {
                (imageview.drawable as AnimationDrawable).stop()
                if (defaultLinstener != null) {
                    defaultLinstener.confirm()
                }
            }

        })
        (imageview.drawable as AnimationDrawable).start()
        //        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.my_progress);
        //        animation.setRepeatCount(-1);
        //        animation.setInterpolator(new LinearInterpolator());
        //        imageview.setAnimation(animation);
        //        animation.start();
        dialog.setCancelable(false)
        try {
            dialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    //    public void showSingle(Activity activity, String content, final DefaultLinstener defaultLinstener) {
    //        final Dialog dialog = new Dialog(activity, R.style.DialogStyle);
    //        dialog.setContentView(R.layout.sigiledialog_layout);
    ////        ImageView imageview = (ImageView) dialog.findViewById(R.id.imageview);
    //        TextView tv_confirm = (TextView) dialog.findViewById(R.id.tv_confirm);
    //        TextView centent = (TextView) dialog.findViewById(R.id.centent);
    //        centent.setText(content);
    //        tv_confirm.setOnClickListener(new View.OnClickListener() {
    //            @Override
    //            public void onClick(View v) {
    //                dialog.cancel();
    //            }
    //        });
    //        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
    //            @Override
    //            public void onCancel(DialogInterface dialog) {
    //                if (defaultLinstener != null) {
    //                    defaultLinstener.confirm();
    //                }
    //            }
    //        });
    //        dialog.setCancelable(false);
    //        try {
    //            dialog.show();
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //    }

    //    public void show(Activity activity) {
    //        final Dialog dialog = new Dialog(activity, R.style.DialogStyle);
    //        dialog.setContentView(R.layout.loading_layout);
    //        ImageView imageview = (ImageView) dialog.findViewById(R.id.imageview);
    //        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.my_progress);
    //        animation.setRepeatCount(-1);
    //        animation.setInterpolator(new LinearInterpolator());
    //        imageview.setAnimation(animation);
    //        animation.start();
    //        dialog.setCancelable(false);
    //        try {
    //            dialog.show();
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //    }


    //    public void show(Activity activity, String con, String ok, String cancle, final DefaultLinstener defaultLinstener) {
    //        final Dialog dialog = new Dialog(activity, R.style.DialogStyle);
    //        dialog.setContentView(R.layout.dialog_layout);
    //        dialog.setCancelable(true);
    //        TextView centent = (TextView) dialog.findViewById(R.id.centent);
    //        TextView tv_cancle = (TextView) dialog.findViewById(R.id.tv_cancle);
    //        TextView tv_confirm = (TextView) dialog.findViewById(R.id.tv_confirm);
    //        centent.setText(con);
    //        tv_cancle.setText(cancle);
    //        tv_confirm.setText(ok);
    //        tv_confirm.setOnClickListener(new View.OnClickListener() {
    //            @Override
    //            public void onClick(View v) {
    //                if (defaultLinstener != null) {
    //                    defaultLinstener.confirm();
    //                }
    //                dialog.dismiss();
    //            }
    //        });
    //        tv_cancle.setOnClickListener(new View.OnClickListener() {
    //            @Override
    //            public void onClick(View v) {
    //                if (defaultLinstener != null) {
    //                    defaultLinstener.cancle();
    //                }
    //                dialog.dismiss();
    //            }
    //        });
    //        try {
    //            dialog.show();
    //        } catch (Exception e) {
    //        }
    //    }
    //
    //
    //
    //
    //
    //    public void showList(Activity activity, String titletext, List<BaseViewItem> data, final DefaultLinstener defaultLinstener) {
    //        if (dialog == null) {
    //            dialog = new Dialog(activity, R.style.DialogStyle);
    //        }
    //        dialog.setContentView(R.layout.showlist_layout);
    //        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler);
    //        TextView title = (TextView) dialog.findViewById(R.id.title);
    //        title.setText(titletext);
    //        RecyclerViewAdapter adapter = new RecyclerViewAdapter(activity, data);
    //        if (recyclerView != null) {
    //            WrapContentLinearLayoutManager wrapContentLinearLayoutManager = new WrapContentLinearLayoutManager(activity);
    //            recyclerView.setHasFixedSize(true);
    //            recyclerView.setLayoutManager(wrapContentLinearLayoutManager);
    //            recyclerView.setAdapter(adapter);
    //        }
    //        TextView tv_confirm = (TextView) dialog.findViewById(R.id.tv_confirm);
    //        tv_confirm.setOnClickListener(new View.OnClickListener() {
    //            @Override
    //            public void onClick(View v) {
    //                dialog.cancel();
    //            }
    //        });
    //        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
    //            @Override
    //            public void onCancel(DialogInterface dialog) {
    //                if (defaultLinstener != null) {
    //                    defaultLinstener.confirm();
    //                }
    //            }
    //        });
    //        dialog.setCancelable(false);
    //        try {
    //            dialog.show();
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //    }

    fun cancel() {
        if (dialog != null) {
            dialog!!.cancel()
        }
    }
}
