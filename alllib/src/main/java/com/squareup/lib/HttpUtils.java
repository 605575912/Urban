package com.squareup.lib;

import android.Manifest;
import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.squareup.lib.activity.PermissionsGrantActivity;
import com.squareup.lib.annotation.KeepNotProguard;
import com.squareup.lib.http.CaheInterceptor;
import com.squareup.lib.utils.AppLibUtils;
import com.squareup.lib.utils.FileUtils;
import com.squareup.lib.utils.GsonUtil;
import com.squareup.lib.utils.IProguard;
import com.squareup.lib.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.EventObject;
import java.util.HashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * Created by Administrator on 2017/05/25 0025.
 */

public enum HttpUtils implements IProguard.ProtectClassAndMembers {
    INSTANCE;
    private OkHttpClient mOkHttpClient = new OkHttpClient();

    public OkHttpClient getmOkHttpClient() {
        return mOkHttpClient;
    }

    private static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    /**
     * 默认信任所有的证书
     * TODO 最好加上证书认证，主流App都有自己的证书
     *
     * @return
     */
    @SuppressLint("TrulyRandom")
    private static SSLSocketFactory createSSLSocketFactory() {

        SSLSocketFactory sSLSocketFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()},
                    new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return sSLSocketFactory;
    }

//    ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
//            .tlsVersions(TlsVersion.TLS_1_2)
//            .cipherSuites(
//                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
//                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
//                    CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
//            .build();

    private HttpUtils() {
//        OkHttpClient.Builder client = new OkHttpClient.Builder()
//                .followRedirects(true)
//                .followSslRedirects(true)
//                .retryOnConnectionFailure(true)
//                .cache(null)
//                .connectionSpecs(Collections.singletonList(ConnectionSpec.MODERN_TLS))
//                .connectTimeout(5, TimeUnit.SECONDS)
//                .writeTimeout(5, TimeUnit.SECONDS)
//                .readTimeout(5, TimeUnit.SECONDS);
//        mOkHttpClient = enableTls12OnPreLollipop(client).build();
//        mOkHttpClient.setConnectionSpecs(Collections.singletonList(ConnectionSpec.MODERN_TLS));
//        mOkHttpClient.setConnectTimeout(Constants.HTTP_TIMEOUT_MS, TimeUnit.MILLISECONDS);
        mOkHttpClient = new OkHttpClient
                .Builder()
//                .connectionSpecs(Collections.singletonList(spec))
//                .certificatePinner(new CertificatePinner.Builder()
//                        .add("publicobject.com", "sha256/afwiKY3RxoMmLkuRW1l7QsPZTJPwDS2pdDROQjXw8ig=")
//                        .build())
                .sslSocketFactory(createSSLSocketFactory())
                .hostnameVerifier(new TrustAllHostnameVerifier())
                .cache(new Cache(FileUtils.getFile("cache"), 1000 * 1024))
                .addInterceptor(new CaheInterceptor(BaseApplication.getApplication()))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder().addHeader("TOKEN", AppLibUtils.getToken()).build();
                        return chain.proceed(request);
                    }
                })
                .addNetworkInterceptor(new CaheInterceptor(BaseApplication.getApplication()))
                .build();

    }


    HashMap<String, HttpListener> httpListeners = new HashMap<String, HttpListener>();

    @KeepNotProguard
    public interface HttpListener {
        void success(Object model, String data);

        void failed(Object model);
    }

    public void getAsynMainHttp(final String url, final Class jsonmodel) {
        getAsynHttp(0, url, jsonmodel);
    }

    public String getAsynMainHttp(final File file, final Class jsonmodel) {
        if (file != null && file.exists()) {
            getAsynMainHttp(file.getPath(), jsonmodel);
            return file.getPath();
        } else {
            failed(0, "", "file not exists");
        }
        return "";
    }

    public void getAsynMainHttp(final String url) {
        getAsynHttp(0, url, null);
    }


    public void postAsynMainHttp(final String url, Class jsonmodel, FormBody.Builder builder) {
        postAsynHttp(0, url, jsonmodel, builder);
    }

    public void postAsynMainHttp(String url, FormBody.Builder builder) {
        postAsynHttp(0, url, null, builder);
    }

    public void postAsynThreadHttp(final String url, Class jsonmodel, FormBody.Builder builder) {
        postAsynHttp(1, url, jsonmodel, builder);
    }

    public void postAsynThreadHttp(final String url, Class jsonmodel, FormBody.Builder builder, HttpListener httpListener) {
        if (!TextUtils.isEmpty(url) && httpListener != null) {
            httpListeners.put(url, httpListener);
        }
        postAsynHttp(1, url, jsonmodel, builder);
    }

    public void postAsynThreadHttp(final String url, FormBody.Builder builder) {
        postAsynHttp(1, url, null, builder);
    }

    public void getAsynThreadHttp(final String url, final Class jsonmodel) {
        getAsynHttp(1, url, jsonmodel);
    }

    public void getAsynThreadHttp(final String url, final Class jsonmodel, HttpListener httpListener) {
        if (!TextUtils.isEmpty(url) && httpListener != null) {
            httpListeners.put(url, httpListener);
        }
        getAsynHttp(1, url, jsonmodel);
    }

    public void getAsynThreadHttp(final String url) {
        getAsynHttp(1, url, null);
    }

    private void JSONfor(int type, String url, final Class jsonmodel, String result) {
        LogUtil.w(result);
        Object model = null;
        boolean success = false;
        HttpListener httpListener = httpListeners.get(url);
        if (jsonmodel == null || jsonmodel.getName().equals("java.lang.String")) {
            model = result;
            success = true;
            if (httpListener != null) {
                httpListener.success(model, result);
                httpListeners.remove(url);
            }
        } else {
            try {
                model = GsonUtil.INSTANCE.getGson().fromJson(result, jsonmodel);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.has("msg")) {
                        int status = jsonObject.getInt("status");
                        if (status == 2) {
                            EventBus.getDefault().post("outLogin");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (JsonSyntaxException exception) {

            }
            if (model != null) {
                success = true;
                if (httpListener != null) {
                    httpListener.success(model, result);
                    httpListeners.remove(url);
                }
            } else {
                model = result;
                if (httpListener != null) {
                    httpListener.failed(model);
                    httpListeners.remove(url);
                }
            }
        }
        if (url == null) {
            url = "";
        }
        if (type == 0) {
            EventMainObject mainObject = new EventMainObject(model);
            mainObject.setCommand(url);
            mainObject.setSuccess(success);
            EventBus.getDefault().post(mainObject);
        } else {
            EventThreadObject threadObject = new EventThreadObject(model);
            threadObject.setCommand(url);
            threadObject.setSuccess(success);
            EventBus.getDefault().post(threadObject);
        }
    }


    private void failed(int type, String url, String fail) {
        LogUtil.w("read failed");
        HttpListener httpListener = httpListeners.get(url);
        if (httpListener != null) {
            httpListener.failed(fail);
            httpListeners.remove(url);
        }
        if (type == 0) {
            EventMainObject mainObject = new EventMainObject(fail);
            mainObject.setCommand(url);
            EventBus.getDefault().post(mainObject);
        } else {
            EventThreadObject threadObject = new EventThreadObject(fail);
            threadObject.setCommand(url);
            EventBus.getDefault().post(threadObject);
        }
    }

    private void getAsynHttp(final int type, final String url, final Class jsonmodel) {
        if (TextUtils.isEmpty(url) || !PermissionsGrantActivity.checkAllPermissionsGranted(BaseApplication.getApplication(), new String[]{
                Manifest.permission.INTERNET})
                ) {
            failed(type, url, "未能获取数据");
            return;
        }
        int index = url.indexOf("file:///android_asset/");
        if (index == 0) {
            ThreadManager.Companion.submit(new Runnable() {
                @Override
                public void run() {
                    InputStream inputStream = null;
                    try {
                        String temp = url.substring("file:///android_asset/".length());
                        inputStream = BaseApplication.getApplication().getAssets().open(temp);
                        InputStreamReader inputReader = new InputStreamReader(inputStream);
                        BufferedReader bufReader = new BufferedReader(inputReader);
                        String line;
                        StringBuilder stringBuilder = new StringBuilder();
                        while ((line = bufReader.readLine()) != null) {
                            stringBuilder.append(line);
                        }
                        JSONfor(type, url, jsonmodel, stringBuilder.toString());
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    failed(type, url, "read failed");

                }
            });


        } else if (url.startsWith("https://") || url.startsWith("http://")) {
            Request.Builder requestBuilder = new Request.Builder().url(url);
//            requestBuilder.addHeader("TOKEN", AppLibUtils.getToken());
//            requestBuilder.addHeader("token", AppLibUtils.getToken());
//            requestBuilder.header("TOKEN",AppLibUtils.getToken());

//            RequestBody requestBody = new MultipartBody.Builder()
//                    .setType(MultipartBody.FORM).addFormDataPart("cuserUid", AppLibUtils.getToken())
//                    .build();
//            requestBuilder.post(requestBody);

            Request request = requestBuilder.build();

            Call mcall = mOkHttpClient.newCall(request);
            mcall.enqueue(new

                                  Callback() {
                                      @Override
                                      public void onFailure(Call call, IOException e) {
                                          failed(type, url, "read failed");
                                      }

                                      @Override
                                      public void onResponse(Call call, Response response) throws IOException {
                                          String str = null;
//                                          if (null != response.cacheResponse()) {
//                                              str = response.cacheResponse().toString();
//                                          } else {
                                          if (response.body() != null) {
                                              str = response.body().string();
//                                                  str = response.networkResponse().body().string();
                                          }
//                                          }
                                          if (TextUtils.isEmpty(str) || response.code() != 200) {
                                              failed(type, url, "read failed");
                                          } else {
                                              JSONfor(type, url, jsonmodel, str);
                                          }
                                      }
                                  });
        } else {
            readFile(type, url, jsonmodel);
        }
    }

    private void postAsynHttp(final int type, final String url, final Class jsonmodel, FormBody.Builder builder) {
        if (TextUtils.isEmpty(url) || BaseApplication.getApplication() == null || !PermissionsGrantActivity.checkAllPermissionsGranted(BaseApplication.getApplication(), new String[]{
                Manifest.permission.INTERNET})
                ) {
            return;
        }
        int index = url.indexOf("file:///android_asset/");

        if (index == 0) {
            ThreadManager.Companion.submit(new Runnable() {
                @Override
                public void run() {
                    InputStream inputStream = null;
                    try {
                        String temp = url.substring("file:///android_asset/".length());
                        inputStream = BaseApplication.getApplication().getAssets().open(temp);
                        InputStreamReader inputReader = new InputStreamReader(inputStream);
                        BufferedReader bufReader = new BufferedReader(inputReader);
                        String line;
                        StringBuilder stringBuilder = new StringBuilder();
                        while ((line = bufReader.readLine()) != null) {
                            stringBuilder.append(line);
                        }
                        JSONfor(type, url, jsonmodel, stringBuilder.toString());
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    failed(type, url, "read failed");

                }
            });
        } else if (url.startsWith("https://") || url.startsWith("http://")) {
            if (builder == null) {
                builder = new FormBody.Builder();
            }
            RequestBody formBody = builder
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
            Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failed(type, url, "read failed");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String str = null;
                    if (null != response.body()) {
                        str = response.body().string();
                    }
                    if (TextUtils.isEmpty(str)) {
                        failed(type, url, "read failed");
                    } else {
                        JSONfor(type, url, jsonmodel, str);
                    }
                }
            });
        } else {
            readFile(type, url, jsonmodel);
        }

    }

    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess();

        /**
         * @param progress 下载进度
         */
        void onDownloading(int progress);

        /**
         * 下载失败
         */
        void onDownloadFailed();
    }

    private String getNameFromUrl(String url) {
        int i = url.lastIndexOf("/");
        int n = url.lastIndexOf(".");
        if (i > -1 && n > -1 && i < url.length() - 1) {
            return AppLibUtils.getMd5(url) + url.substring(i + 1, n).replace(".", "");
        }
        return AppLibUtils.getMd5(url);
    }

    private String getTempName(String url) {
        return AppLibUtils.getMd5(url);
    }

    public void upAsynMain(final String url, MultipartBody.Builder builder, final Class jsonmodel) {
        //上传文件：
//            FileShowModel fileShowModel = (FileShowModel) eventObject.getValue();
//            MultipartBody.Builder multipartBody = new MultipartBody.Builder();
//            multipartBody.addFormDataPart("fileType", "jpg");
//            multipartBody.addFormDataPart("fileId", fileShowModel.getParent());
//            multipartBody.addFormDataPart("fileName", fileShowModel.getName());
//            File file = new File(fileShowModel.getPath());
//            multipartBody.addFormDataPart("fileName", fileShowModel.getName(), RequestBody.create(MediaType.parse("image/png"), file));
//
        builder.setType(MultipartBody.FORM);
        final Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        Call call = mOkHttpClient.newCall(request);
        final int type = 0;
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                failed(type, url, "read failed");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = null;
                ResponseBody responseBody = response.body();
                if (null != responseBody) {
                    str = responseBody.string();
                }
                if (TextUtils.isEmpty(str)) {
                    failed(type, url, "read failed");
                } else {
                    JSONfor(type, url, jsonmodel, str);
                }
            }
        });

    }

    //每次下载需要新建新的Call对象
    private Call newCall(long startPoints, String url) {
        Request request = new Request.Builder()
                .url(url)
                .header("RANGE", "bytes=" + startPoints + "-")//断点续传要用到的，指示下载的区间
                .build();
        return getmOkHttpClient().newCall(request);
    }

    public void randomDownload(final String url, final File destination, final OnDownloadListener listener) {
        long len = 0;
        if (destination.exists()) {
            len = destination.length();
        }
        final long startsPoint = len;
        Call call = newCall(startsPoint, url);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onDownloadFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = null;
                ResponseBody responseBody = response.body();
                if (null != responseBody) {
                    str = responseBody.string();
                }
                if (TextUtils.isEmpty(str)) {
                    listener.onDownloadFailed();
                } else {
                    save(responseBody, startsPoint, destination);
                }
            }
        });
    }

    private void save(ResponseBody body, long startsPoint, File destination) {
        InputStream in = body.byteStream();
        long total = body.contentLength();
        FileChannel channelOut = null;
        // 随机访问文件，可以指定断点续传的起始位置
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(destination, "rwd");
            //Chanel NIO中的用法，由于RandomAccessFile没有使用缓存策略，直接使用会使得下载速度变慢，亲测缓存下载3.3秒的文件，用普通的RandomAccessFile需要20多秒。
            channelOut = randomAccessFile.getChannel();
            // 内存映射，直接使用RandomAccessFile，是用其seek方法指定下载的起始位置，使用缓存下载，在这里指定下载位置。
            MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE, startsPoint, body.contentLength());
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                mappedBuffer.put(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                if (channelOut != null) {
                    channelOut.close();
                }
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param url      下载连接
     * @param listener 下载监听
     */
    public void download(final String url, final OnDownloadListener listener) {
        final Request request = new Request.Builder().url(url).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onDownloadFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
//                String savePath = FileUtils.readFile(getNameFromUrl(url));
                try {
                    File file = FileUtils.getFile(getNameFromUrl(url));
                    if (file == null) {
                        listener.onDownloadFailed();
                        return;
                    }
                    File tempfile = FileUtils.getFile(getTempName(url));
                    ResponseBody body = response.body();
                    if (body == null) {
                        listener.onDownloadFailed();
                        return;
                    }
                    is = body.byteStream();
                    long total = body.contentLength();
                    fos = new FileOutputStream(tempfile);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        // 下载中
                        listener.onDownloading(progress);
                    }
                    fos.flush();
                    // 下载完成
                    tempfile.renameTo(file);
                    listener.onDownloadSuccess();
                } catch (Exception e) {
                    listener.onDownloadFailed();
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });
    }

    private void readFile(final int type, final String url, final Class jsonmodel) {
        ThreadManager.Companion.submit(new Runnable() {
            @Override
            public void run() {
                InputStream inputStream = null;
                try {
                    File file = new File(url);
                    if (file.exists()) {
                        inputStream = new FileInputStream(file);
                        InputStreamReader inputReader = new InputStreamReader(inputStream);
                        BufferedReader bufReader = new BufferedReader(inputReader);
                        String line;
                        StringBuilder stringBuilder = new StringBuilder();
                        while ((line = bufReader.readLine()) != null) {
                            stringBuilder.append(line);
                        }
                        JSONfor(type, url, jsonmodel, stringBuilder.toString());
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                failed(type, url, "read failed");

            }
        });
    }
}
