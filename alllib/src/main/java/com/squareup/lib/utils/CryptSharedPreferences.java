package com.squareup.lib.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.util.ArrayMap;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class CryptSharedPreferences implements SharedPreferences {
    private static final String TAG = "CryptSharedPreferences"; 
    private static final String CRYPT_PREFIX = "@.";
    private static final String CRYPT_VERSION = "@$ver";
    private static final int VERSION = 1;
    private static ArrayMap<SharedPreferences, SharedPreferences> gSharedPreferencesMap;
    private ArrayMap<OnSharedPreferenceChangeListener, CryptOnSharedPreferenceChangeListener> mListenersMap ;
    private SharedPreferences mSharedPreferences;
    private Editor		mEditor ;
//    private CryptDesUtil mCryptDesUtil;

    public static SharedPreferences getSharedPreferences(Context context, String name, int mode) {
	SharedPreferences sp = context.getSharedPreferences(name, mode);
	return createCryptSharedPreferences(sp);
    }
    
    public static SharedPreferences getDefaultSharedPreferences(Context context){
	SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
	return createCryptSharedPreferences(sp);
    }

    public static SharedPreferences createCryptSharedPreferences(SharedPreferences sp) {
//	int ver = Integer.valueOf(Build.VERSION.SDK);
//	if (ver < 11){
//	    return sp ;
//	}
	if (sp instanceof CryptSharedPreferences) {
	    return sp;
	}
	SharedPreferences crypt_sp = null ;
	synchronized(CryptSharedPreferences.class){
	    if (gSharedPreferencesMap == null){
		gSharedPreferencesMap = new ArrayMap<SharedPreferences, SharedPreferences>();
	    }
	    crypt_sp = gSharedPreferencesMap.get(sp);
	}
	if (crypt_sp == null) {
	    if (!isCryptVersion(sp)) {
		crypt_sp = convertToCryptVersion(sp);
	    } else {
		crypt_sp = new CryptSharedPreferences(sp);
	    }
	    synchronized(CryptSharedPreferences.class){
		gSharedPreferencesMap.put(sp, crypt_sp);
	    }
	}
	return crypt_sp ;
    }

    /**
     * 判断当前配置文件是否为加密处理过的
     * 
     * @param sp
     * @return
     */
    private static boolean isCryptVersion(SharedPreferences sp) {
	if (sp instanceof CryptSharedPreferences){
	    return ((CryptSharedPreferences)sp).contains(CRYPT_VERSION, false);
	}else{
	    return sp.contains(CRYPT_VERSION);
	}
//	SharedPreferences csp = (sp instanceof CryptSharedPreferences) ? sp : new CryptSharedPreferences(sp);
//	return csp.contains(CRYPT_VERSION);
    }

    private static SharedPreferences convertToCryptVersion(SharedPreferences sp) {
	synchronized (sp) {
	    Map<String, ?> all = sp.getAll();
	    Editor ed = sp.edit();
	    CryptSharedPreferences crypt_sp = new CryptSharedPreferences(sp);
	    if (all != null && all.size() > 0) {
		if (all.containsKey(CRYPT_VERSION)) {
		    return crypt_sp;
		}
		ed.clear().commit();
		Set<?> entries = all.entrySet();
		Iterator<?> it = (Iterator<?>) entries.iterator();
		Entry<String, ?> entry = null;
		String key = null;
		Object obj = null;
		CryptEditor crypt_ed = crypt_sp.edit(ed);
		ed.putInt(CRYPT_VERSION, VERSION);
		while (it.hasNext()) {
		    entry = (Entry<String, ?>) it.next();
		    key = entry.getKey();
		    obj = entry.getValue();
		    if (CRYPT_VERSION.equals(key) ) {
			continue;
		    }
		    if (obj instanceof String) {
			crypt_ed.putString(key, (String) obj);
		    } else if (obj instanceof Integer) {
			crypt_ed.putInt(key, (Integer) obj);
		    } else if (obj instanceof Long) {
			crypt_ed.putLong(key, (Long) obj);
		    } else if (obj instanceof Float) {
			crypt_ed.putFloat(key, (Float) obj);
		    } else if (obj instanceof Boolean) {
			crypt_ed.putBoolean(key, (Boolean) obj);
		    } else if (obj instanceof Set) {
			int version = Integer.valueOf(Build.VERSION.SDK);
			if (version >= 11){
			    crypt_ed.putStringSet(key, (Set<String>) obj);
			}
		    }
		}
		crypt_ed.commit();
	    } else {
		ed.putInt(CRYPT_VERSION, VERSION);
		ed.commit();
	    }
	    return crypt_sp;
	}
    }
    
    private static String	toString(SharedPreferences sp){
	if (sp == null){
	    return "null";
	}
	Map<String, ?> all = sp.getAll();
	StringBuilder sb = new StringBuilder();
	if (all != null && all.size() > 0) {
	    Set<?> entries = all.entrySet();
	    Iterator<?> it = (Iterator<?>) entries.iterator();
	    Entry<String, ?> entry = null;
	    String key = null;
	    Object obj = null;
	    while (it.hasNext()) {
		if (sb.length() > 0){
		    sb.append("\n");
		}
		entry = (Entry<String, ?>) it.next();
		key = entry.getKey();
		obj = entry.getValue();
		sb.append(key).append("=").append(obj);
	    }
	}
	return sb.toString();
    }

    private CryptSharedPreferences(SharedPreferences sp) {
	mSharedPreferences = sp;
//	mCryptDesUtil = new CryptDesUtil();
	mListenersMap = new ArrayMap<OnSharedPreferenceChangeListener,
		CryptOnSharedPreferenceChangeListener>();
    }

    private static boolean isEncryptKey(String key){
	return key != null && key.startsWith(CRYPT_PREFIX);
    }


//    private static String encryptKey(String key, CryptDesUtil desUtil){
//	StringBuilder sb = new StringBuilder();
//	sb.append(CRYPT_PREFIX).append(desUtil.encode(key));
//	return sb.toString();
//    }
    private static String encryptKey(String key){
	StringBuilder sb = new StringBuilder();
	sb.append(CRYPT_PREFIX).append(Base64Coder.encodeString(key, CryptMap.getEncodeKeyMap()));
	return sb.toString();
    }

//    private static String decryptKey(String cryptKey,CryptDesUtil desUtil){
//	int index = cryptKey.indexOf(CRYPT_PREFIX);
//	if (index != 0){
//	    return cryptKey;
//	}else{
//	    return desUtil.decode(cryptKey.substring(CRYPT_PREFIX.length()));
//	}
//    }

    private static String decryptKey(String cryptKey){
	int index = cryptKey.indexOf(CRYPT_PREFIX);
	if (index != 0){
	    return cryptKey;
	}else{
	    return Base64Coder.decodeString(cryptKey.substring(CRYPT_PREFIX.length()), CryptMap.getDecodeKeyMap());
	}
    }
    @Override
    public Map<String, ?> getAll() {
	Map<String, ?> all = mSharedPreferences.getAll();

	HashMap<String, Object> nwall = new HashMap<String, Object>();
	Set<?> values = all.entrySet();
	if (values != null) {
	    Iterator<?> it = (Iterator<?>) values.iterator();
	    String key = null;
	    Object val = null;
	    Entry<String, ?> entry = null;
	    while (it.hasNext()) {
		entry = (Entry<String, Object>) it.next();
		key = entry.getKey();
		val = entry.getValue();
//		key = decryptKey(key,mCryptDesUtil);
		key = decryptKey(key);
		if (val instanceof String) {
		    val = Base64Coder.decodeString((String) val, CryptMap.getDecodeValueMap());
		}
		nwall.put(key, val);
	    }
	}
	if (nwall.size() > 0) {
	    all = nwall;
	}
	return all;
    }

    @Override
    public String getString(String key, String defValue) {
//	key = encryptKey(key,mCryptDesUtil);
	key = encryptKey(key);
	if (defValue != null) {
	    defValue = Base64Coder.encodeString(defValue, CryptMap.getEncodeValueMap());
	}
	String value = mSharedPreferences.getString(key, defValue);
	if (value != null) {
	    value = Base64Coder.decodeString(value, CryptMap.getDecodeValueMap());
	}
	return value;
    }

    @TargetApi(11)
    public Set<String> getStringSet(String key, Set<String> defValues) {
	int ver = Integer.valueOf(Build.VERSION.SDK);
	if (ver < 11){
	    return defValues;
	}
//	key = encryptKey(key,mCryptDesUtil); // key加密后才能访问
	key = encryptKey(key);
	Set<String> cryptValues = null;
	Set<String> values = null;
	String strvalue = null;
	if (defValues != null) {// 对默认值加密
	    cryptValues = new HashSet<String>();
	    Iterator<String> it = defValues.iterator();
	    while (it.hasNext()) {
		strvalue = it.next();
		if (strvalue != null) {
		    strvalue = Base64Coder.encodeString(strvalue, CryptMap.getEncodeValueMap());
		}
		cryptValues.add(strvalue);
	    }
	    defValues = cryptValues;
	}
	cryptValues = mSharedPreferences.getStringSet(key, defValues);
	if (cryptValues != null) { // 解密
	    values = new HashSet<String>();
	    Iterator<String> it = cryptValues.iterator();
	    while (it.hasNext()) {
		strvalue = it.next();
		if (strvalue != null) {
		    strvalue = Base64Coder.decodeString(strvalue, CryptMap.getDecodeValueMap());
		}
		values.add(strvalue);
	    }
	}
	return values;
    }

    @Override
    public int getInt(String key, int defValue) {
//	key = encryptKey(key,mCryptDesUtil); // key加密后才能访问
	key = encryptKey(key);
	return mSharedPreferences.getInt(key, defValue);
    }

    @Override
    public long getLong(String key, long defValue) {
//	key = encryptKey(key,mCryptDesUtil); // key加密后才能访问
	key = encryptKey(key);
	return mSharedPreferences.getLong(key, defValue);
    }

    @Override
    public float getFloat(String key, float defValue) {
//	key = encryptKey(key,mCryptDesUtil);// key加密后才能访问
	key = encryptKey(key);
	return mSharedPreferences.getFloat(key, defValue);
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
//	key = encryptKey(key,mCryptDesUtil);// key加密后才能访问
	key = encryptKey(key);
	return mSharedPreferences.getBoolean(key, defValue);
    }

    private boolean contains(String key, boolean cryptIt){
	if (cryptIt){
	    return contains(key);
	}else{
	    return mSharedPreferences.contains(key);
	}
    }

    @Override
    public boolean contains(String key) {
//	key = encryptKey(key,mCryptDesUtil);// key加密后才能访问
	key = encryptKey(key);
	return mSharedPreferences.contains(key);
    }

    @Override
    public Editor edit() {
	synchronized(this){
	    if(mEditor == null){
		Editor ed = mSharedPreferences.edit();
		mEditor =  new CryptEditor(ed/*, mCryptDesUtil*/);
	    }
	    return mEditor ;
	}
    }

    private CryptEditor edit(Editor attachEditor){
	if (attachEditor instanceof CryptEditor)
	    return (CryptEditor)attachEditor;
	else {
	    synchronized(this){
		if (mEditor == null){
		    mEditor = new CryptEditor(attachEditor);
		} else if (mEditor instanceof CryptEditor){
		    CryptEditor crypt_ed = (CryptEditor) mEditor ;
		    if (crypt_ed.mEditor != attachEditor){
			mEditor = new CryptEditor(attachEditor);
		    }
		}else{
		    mEditor = new CryptEditor(attachEditor);
		}
	    }
	    return (CryptEditor)mEditor ;
	}
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
	CryptOnSharedPreferenceChangeListener cryptListener = null ;
	synchronized(this){
	    cryptListener = mListenersMap.get(listener);
	    if ( cryptListener == null){
		cryptListener = new CryptOnSharedPreferenceChangeListener(listener);
		mListenersMap.put(listener, cryptListener);
	    }
	}
	mSharedPreferences.registerOnSharedPreferenceChangeListener(cryptListener);
    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
	CryptOnSharedPreferenceChangeListener cryptListener = null ;
	synchronized(this){
	    cryptListener = mListenersMap.remove(listener);
	}
	if (cryptListener != null){
	    mSharedPreferences.unregisterOnSharedPreferenceChangeListener(cryptListener);
	}
    }

    private static class CryptOnSharedPreferenceChangeListener implements OnSharedPreferenceChangeListener{
	private OnSharedPreferenceChangeListener mListener;
	CryptOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener){
	    mListener = listener ;
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
	    String decryptKey = decryptKey(key);
	    if (mListener != null){
		sharedPreferences = CryptSharedPreferences.createCryptSharedPreferences(sharedPreferences);
		mListener.onSharedPreferenceChanged(sharedPreferences, decryptKey);
	    }
	}

    }

    private static class CryptEditor implements Editor {
	private Editor mEditor = null;
//	private CryptDesUtil mCryptDesUtil;

	private CryptEditor(Editor ed/*, CryptDesUtil cryptDesUtil*/) {
	    mEditor = ed;
//	    mCryptDesUtil = cryptDesUtil;
	}

	@Override
	public Editor putString(String key, String value) {
	    String cryptKey = null, cryptValue = null;
//	    cryptKey = encryptKey(key,mCryptDesUtil);
	    if (!isEncryptKey(key)) {
		cryptKey = encryptKey(key);
		if (value != null) {
		    cryptValue = Base64Coder.encodeString(value, CryptMap.getEncodeValueMap());
		}
	    }else{
		cryptKey = key ;
		cryptValue = value;
	    }
	    mEditor.putString(cryptKey, cryptValue);
	    return this;
	}

	@TargetApi(11)
	public Editor putStringSet(String key, Set<String> values) {
	    int ver = Integer.valueOf(Build.VERSION.SDK);
	    if (ver < 11){ //不支持
		return this;
	    }
	    String cryptKey = null, cryptValue = null;
	    Set<String> cryptValues = null;
//	    cryptKey = encryptKey(key,mCryptDesUtil);
	    if (!isEncryptKey(key)) {
		cryptKey = encryptKey(key);
		if (values != null) {
		    cryptValues = new HashSet<String>();
		    Iterator<String> it = values.iterator();
		    while (it.hasNext()) {
			cryptValue = it.next();
			if (cryptValue != null) {
			    cryptValue = Base64Coder.encodeString(cryptValue, CryptMap.getEncodeValueMap());
			}
			cryptValues.add(cryptValue);
		    }
		}
	    } else{
		cryptKey = key ;
		cryptValues = values ;
	    }
	    mEditor.putStringSet(cryptKey, cryptValues);
	    return this;
	}

	@Override
	public Editor putInt(String key, int value) {
	    String cryptKey = null;
//	    cryptKey = encryptKey(key,mCryptDesUtil);
	    if (!isEncryptKey(key))
		cryptKey = encryptKey(key);
	    else
		cryptKey = key ;
	    mEditor.putInt(cryptKey, value);
	    return this;
	}

	@Override
	public Editor putLong(String key, long value) {
	    String cryptKey = null;
//	    cryptKey = encryptKey(key,mCryptDesUtil);
	    if (!isEncryptKey(key))
		cryptKey = encryptKey(key);
	    else
		cryptKey = key ;
	    mEditor.putLong(cryptKey, value);
	    return this;
	}

	@Override
	public Editor putFloat(String key, float value) {
	    String cryptKey = null;
//	    cryptKey = encryptKey(key,mCryptDesUtil);
	    if (!isEncryptKey(key))
		cryptKey = encryptKey(key);
	    else
		cryptKey = key ;
	    mEditor.putFloat(cryptKey, value);
	    return this;
	}

	@Override
	public Editor putBoolean(String key, boolean value) {
	    String cryptKey = null;
//	    cryptKey = encryptKey(key,mCryptDesUtil);
	    if (!isEncryptKey(key))
		cryptKey = encryptKey(key);
	    else
		cryptKey = key ;
	    mEditor.putBoolean(cryptKey, value);
	    return this;
	}

	@Override
	public Editor remove(String key) {
	    String cryptKey = null;
//	    cryptKey = encryptKey(key,mCryptDesUtil);
	    if (!isEncryptKey(key))
		cryptKey = encryptKey(key);
	    else
		cryptKey = key ;
	    mEditor.remove(cryptKey);
	    return this;
	}

	@Override
	public Editor clear() {
	    mEditor.clear();
	    return this;
	}

	@Override
	public boolean commit() {
	    return mEditor.commit();
	}

	@Override
	public void apply() {
	    mEditor.apply();
	}

    }
}
