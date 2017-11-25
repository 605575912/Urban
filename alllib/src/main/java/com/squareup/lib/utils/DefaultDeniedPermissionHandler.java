package com.squareup.lib.utils;

import android.content.Context;
import android.content.DialogInterface;

import com.squareup.lib.activity.PermissionHandler;
import com.squareup.lib.activity.PermissionsGrantActivity;

import java.util.ArrayList;
import java.util.List;


public class DefaultDeniedPermissionHandler implements PermissionHandler, IProguard.ProtectClassAndMembers {
    private Context mContext;
    private final static String DLG_REQ_SHOW_PERMISSION_HINT = "show.perms.hints";
    private final static String EXTRA_REQ_PERMISSIONS = "permissions";
    private final static String EXTRA_POSITIVE_BTN_TEXT = "positive_btn_text";
    private final static String EXTRA_NEGATIVE_BTN_TEXT = "negative_btn_text";
    private final static String EXTRA_DIALOG_STYLE = "dialog_style";
    public final static int DIALOG_STYLE_HINT1 = 0;
    public final static int DIALOG_STYLE_HINT2 = 1;

    protected Context getContext() {
        return mContext;
    }

    public DefaultDeniedPermissionHandler(Context context) {
        mContext = context;
    }

    @Override
    public void onPermissionsResult(String[] grantedpermissions, String[] denied_permissions) {
        String[] required_perms = getReqiredPermissions(denied_permissions);
        if (required_perms != null) {
//	    showRequestedPermissionsHint(required_perms,R.string.perm_continue, R.string.perm_quit , DIALOG_STYLE_HINT2);
        }
    }

    private String[] getReqiredPermissions(String[] permissions) {
        if (permissions == null) {
            return null;
        }
        List<String> rets = new ArrayList<String>();
        for (String s : permissions) {
            if (isRequestedPermission(s)) {
                rets.add(s);
            }
        }
        if (rets.size() > 0) {
            String[] sa = new String[rets.size()];
            rets.toArray(sa);
            return sa;
        } else {
            return null;
        }
    }

    /**
     * 判断是否为必需使用的权限
     *
     * @param permission
     * @return
     */
    protected boolean isRequestedPermission(String permission) {
        return false;
    }

    /**
     * 弹窗选择
     *
     * @param choice DialogInterface.BUTTON_POSITIVE or   BUTTON_NEGATIVE
     */
    protected void onDialogChoice(int choice) {
        if (choice == DialogInterface.BUTTON_NEGATIVE) {
//    	    ToastUtil.show(mContext, "权限不足，无法使用该功能。");
        }
    }
    // @Override
    // public void onSystemWriteSettings(boolean success) {
    // }

//    /**
//     * 显示必需权限的提示内容
//     *
//     * @param reqired_permissions
//     */
//    protected void showRequestedPermissionsHint(String[] required_permissions, int positive_btn_text_id, int negative_btn_text_id){
//	showRequestedPermissionsHint(required_permissions, positive_btn_text_id, negative_btn_text_id, DIALOG_STYLE_HINT2);
//    }

//    protected void showRequestedPermissionsHint(String[] required_permissions, int positive_btn_text_id, int negative_btn_text_id, int dlg_style){
//	String positive_btn_text=null;
//	String negative_btn_text = null;
//	if (positive_btn_text_id != 0){
//	    positive_btn_text = mContext.getString(positive_btn_text_id);
//	}
//	if (negative_btn_text_id != 0){
//	    negative_btn_text = mContext.getString(negative_btn_text_id);
//	}
//	showRequestedPermissionsHint(required_permissions, positive_btn_text, negative_btn_text, dlg_style);
//    }

//    protected void showRequestedPermissionsHint(String[] required_permissions, String positive_btn_text, String negative_btn_text, int dlg_style) {
//	DialogChoiceReceiver receiver = new DialogChoiceReceiver(this);
//	IntentFilter filter = new IntentFilter(MMIntent.ACTION_SERVICE_DLG_CHOICE);
//	getContext().registerReceiver(receiver, filter);
//	if (mContext instanceof Activity){
//	    showRequestedPermissionsHintInUI((Activity)mContext, required_permissions, positive_btn_text, negative_btn_text, false, dlg_style);
//	}else{
//	    Intent intent = DialogDelegateActivity.getLaunchMeIntent(getContext(),
//		    PermissionDialogFactory.class.getName());
//	    intent.putExtra(EXTRA_REQ_PERMISSIONS, required_permissions);
//	    if (positive_btn_text != null) {
//		intent.putExtra(EXTRA_POSITIVE_BTN_TEXT, positive_btn_text);
//	    }
//	    if (negative_btn_text != null) {
//		intent.putExtra(EXTRA_NEGATIVE_BTN_TEXT, negative_btn_text);
//	    }
//	    MMIntent.setCalling(intent, DLG_REQ_SHOW_PERMISSION_HINT);
//	    getContext().startActivity(intent);
//	}
//    }

//    protected void showMyApplicationInfo(){
//	showMyApplicationInfo(mContext);
//    }

//    private static void formatAppPermissionsInfo(Context context, String[] required_perms, LinearLayout parent) {
//	List<String> perms = new ArrayList<String>(Arrays.asList(required_perms));
//	// 去重，将相似的权限去掉，剩下一条即可
//	if (perms.contains(Manifest.permission.READ_SMS) && perms.contains(Manifest.permission.RECEIVE_SMS)) {
//	    perms.remove(Manifest.permission.READ_SMS);
//	}
//	if (perms.contains(ManifestCompat.permission.READ_EXTERNAL_STORAGE)
//		&& perms.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//	    perms.remove(ManifestCompat.permission.READ_EXTERNAL_STORAGE);
//	}
//	String tmpstr = null;
//	int index = 0;
//	for (String s : perms) {
//	    index++;
//	    View view = View.inflate(context, R.layout.mm_prem_dialog_item, null);
//	    TextView title = (TextView) view.findViewById(R.id.title);
//	    TextView dec = (TextView) view.findViewById(R.id.dec);
//	    tmpstr = AppPermissionsFunctionDescript.getPermissionLabel(context, s);
//	    title.setText("权限" + index + ":" + tmpstr);
//	    tmpstr = AppPermissionsFunctionDescript.getPermissionFunctionDescript(s);
//	    if (TextUtils.isEmpty(tmpstr)) {
//		tmpstr = AppPermissionsFunctionDescript.getPermissionDescription(context, s);
//	    }
//	    dec.setText(tmpstr);
//	    parent.addView(view);
//	}
//    }

//    private static void showRequestedPermissionsHintInUI(final Activity activity , String[] required_permissions, String positive_btn_text,
//		String negative_btn_text, final boolean from_service, int dlg_style) {
//	    MMAlertDialogBuilder builder = new MMAlertDialogBuilder(activity); // 先得到构造器
//	    View dialogView = null;
//	    //情况一、应用启动时
//	    if(dlg_style == DIALOG_STYLE_HINT1){
//		builder.setTitle("消灭顽固提示窗的正确方法");
//		 dialogView = View.inflate(activity, R.layout.mm_prem_dialog, null);
//		 final LinearLayout way = (LinearLayout) dialogView.findViewById(R.id.prem_message_way);
//		 LinearLayout noticeShow =  (LinearLayout) dialogView.findViewById(R.id.show_prem_notice);
//		 final LinearLayout notice = (LinearLayout) dialogView.findViewById(R.id.prem_message_notice);
//		 LinearLayout premsg = (LinearLayout) dialogView.findViewById(R.id.prem_message);
//		 final ImageView img = (ImageView) dialogView.findViewById(R.id.show_pre_img);
//		 TextView text = (TextView) dialogView.findViewById(R.id.show_pre_text);
//		 TextView pre_way_one = (TextView) dialogView.findViewById(R.id.pre_way_one);
//		 TextView pre_way_two = (TextView) dialogView.findViewById(R.id.pre_way_two);
//		 formatAppPermissionsInfo(activity, required_permissions,premsg);
//
//		 pre_way_one.setText(Html.fromHtml(activity.getResources().getString(R.string.perm_way_one)));
//		 pre_way_two.setText(Html.fromHtml(activity.getResources().getString(R.string.perm_way_two)));
//		 text.setText(Html.fromHtml("<u>权限获取说明</u>"));
//		 noticeShow.setOnClickListener(new OnClickListener() {
//		     boolean isShowNotice = false;
//		    @Override
//		    public void onClick(View v) {
//			if(isShowNotice){
//			    isShowNotice = false;
//			    img.setBackgroundResource(R.drawable.arrow_down);
//			    notice.setVisibility(View.GONE);
//			    way.setVisibility(View.VISIBLE);
//			}else{
//			    isShowNotice = true;
//			    img.setBackgroundResource(R.drawable.arrow_up);
//			    notice.setVisibility(View.VISIBLE);
//			    way.setVisibility(View.GONE);
//			}
//		    }
//		});
//	    }else{//情况二、针对某功能权限提示
//		builder.setTitle("亲,需要授予权限解锁新技能");
//		dialogView = View.inflate(activity, R.layout.mm_prem_dialog_for_single, null);
//		TextView premnotice = (TextView) dialogView.findViewById(R.id.prem_notice);
//		TextView premsg = (TextView) dialogView.findViewById(R.id.prem_message);
////		premnotice.setMovementMethod(ScrollingMovementMethod.getInstance());// 设置可滚动
//		premnotice.setText(Html.fromHtml(activity.getResources().getString(R.string.perm_way_three)));
//		String info = AppPermissionsFunctionDescript.formatAppPermissionsInfo(activity, required_permissions);
//		premsg.setText(info);
//	    }
//
//
//	    builder.setView(dialogView);
//	    boolean show_positive_btn = !TextUtils.isEmpty(positive_btn_text);
//	    boolean show_negative_btn = !TextUtils.isEmpty(negative_btn_text);
//	    // builder.setMessage("您还有以下权限未申请,如果未申请可能不能正常使用MM商场全部功能?\r\n\r\n"+info);
//	    if (!show_positive_btn && !show_negative_btn) {
//		builder.setCancelable(true);
//		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
//		    @Override
//		    public void onCancel(DialogInterface dialog) {
//			dialog.dismiss(); // 关闭dialog
////			onDialogChoice(DialogInterface.BUTTON_NEGATIVE);
//			DialogDelegateActivity.sendChoiceBroadIntent(activity, DLG_REQ_SHOW_PERMISSION_HINT, DialogInterface.BUTTON_NEGATIVE);
//			if (from_service ){
//			    activity.finish();
//			}
//		    }
//		});
//	    } else {
//		builder.setCancelable(false);
//	    }
//	    if (show_positive_btn) {
//		builder.setPositiveButton(positive_btn_text, new DialogInterface.OnClickListener() {
//		    @Override
//		    public void onClick(DialogInterface dialog, int which) {
//			dialog.dismiss(); // 关闭dialog
////			onDialogChoice(which);
//			DialogDelegateActivity.sendChoiceBroadIntent(activity, DLG_REQ_SHOW_PERMISSION_HINT, which);
//			if (from_service ){
//			    activity.finish();
//			}
//		    }
//		});
//	    }
//	    if (show_negative_btn) {
//		builder.setNegativeButton(negative_btn_text, new DialogInterface.OnClickListener() {
//		    @Override
//		    public void onClick(DialogInterface dialog, int which) {
//			dialog.dismiss(); // 关闭dialog
////			onDialogChoice(which);
//			DialogDelegateActivity.sendChoiceBroadIntent(activity, DLG_REQ_SHOW_PERMISSION_HINT, which);
//			if (from_service ){
//			    activity.finish();
//			}
//		    }
//		});
//	    }
//	    final AlertDialog dialog = builder.create();
//	    dialog.show();
//	    View view = dialog.getWindow().getDecorView();
//	    LinearLayout parentPanel = (LinearLayout) view.findViewById(R.id.parentPanel);
//	    LayoutParams params = (LayoutParams) parentPanel.getLayoutParams();
//	    params.leftMargin = 30;
//	    params.rightMargin = 30;
//	    parentPanel.setLayoutParams(params);
//	    FrameLayout customLayout = (FrameLayout) view.findViewById(R.id.customPanel);
//	    customLayout.setPadding(0, 0, 0, 0);
//	    TextView title = (TextView) view.findViewById(R.id.alertTitle);
//	    title.setTextSize(18);
//    }
//
//    private static void showMyApplicationInfo(Context context) {
//	Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//	Uri uri = Uri.fromParts("package", context.getPackageName(), null);
//	final int apiLevel = MobileAdapter.getInstance().getVersion();
//	if (apiLevel >= 9) { // 2.3（ApiLevel 9）以上，使用SDK提供的接口
//	    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//	    uri = Uri.fromParts("package", context.getPackageName(), null);
//	    intent.setData(uri);
//	} else { // 2.3以下，使用非公开的接口（查看InstalledAppDetails源码）
//		 // 2.2和2.1中，InstalledAppDetails使用的APP_PKG_NAME不同。
//	    final String appPkgName = (apiLevel == 8 ? "pkg" : "com.android.settings.ApplicationPkgName");
//	    intent.setAction(Intent.ACTION_VIEW);
//	    intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
//	    intent.putExtra(appPkgName, context.getPackageName());
//	}
//	context.startActivity(intent);
//    }
//   private void showPermissionSetting(Context context){
//       //小米
//       ApplicationInfo info = context.getApplicationInfo();
//       Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");
//       i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//       i.setClassName("com.android.settings", "com.miui.securitycenter.permission.AppPermissionsEditor");
//       i.putExtra("extra_package_uid", info.uid);
//       context.startActivity(i);
//   }

//    static class PermissionDialogFactory extends AbstractDialogFactory{
//	String	[] mRequiredPermissions ;
//	String	mPositiveBtnText ;
//	String	mNegativeBtnText ;
//	int		mDialogStyle ;
//	protected PermissionDialogFactory(DialogDelegateActivity activity, int theme) {
//	    super(activity, theme);
//	    showPermissionDialog();
//	}
//
//	protected PermissionDialogFactory(DialogDelegateActivity activity) {
//	    super(activity);
//	    showPermissionDialog();
//	}
//
//	private void fillParams(){
//	    Intent intent = mCallerActivity.getIntent();
//	    mRequiredPermissions = intent.getStringArrayExtra(EXTRA_REQ_PERMISSIONS);
//	    mPositiveBtnText = intent.getStringExtra(EXTRA_POSITIVE_BTN_TEXT);
//	    mNegativeBtnText = intent.getStringExtra(EXTRA_NEGATIVE_BTN_TEXT);
//	    mDialogStyle = intent.getIntExtra(EXTRA_DIALOG_STYLE, DIALOG_STYLE_HINT2);
//	}
//
//	private void showPermissionDialog(){
//	    fillParams();
//	    showRequestedPermissionsHintInUI(mCallerActivity, mRequiredPermissions, mPositiveBtnText, mNegativeBtnText, true, mDialogStyle);
//	}
//    }
//
//    static class DialogChoiceReceiver extends BroadcastReceiver{
//	DefaultDeniedPermissionHandler	   mPermissionHandler ;
//	DialogChoiceReceiver(DefaultDeniedPermissionHandler handler){
//	    mPermissionHandler = handler;
//	}
//
//	@Override
//	public void onReceive(Context context, Intent intent) {
//	    if (intent == null){
//		unRegisterMe();
//		return ;
//	    }
//	    String action = intent.getAction() ;
//	    String callingid = MMIntent.getCalling(intent);
//	    if (!MMIntent.ACTION_SERVICE_DLG_CHOICE.equals(action) || !DLG_REQ_SHOW_PERMISSION_HINT.equals(callingid)){
//		return ;
//	    }
//	    int choice  = intent.getIntExtra(MMIntent.FIELD_DIALOG_CHOICE, 0);
//	    mPermissionHandler.onDialogChoice(choice);
//	    unRegisterMe();
//	}
//
//	private void unRegisterMe(){
//	    try{
//		DialogDelegateActivity.unregisterChoiceReceiver(mPermissionHandler.getContext(), this);
//	    }catch(Exception e){
//	    }
//	}
//    }
}
