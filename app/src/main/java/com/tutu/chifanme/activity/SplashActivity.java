package com.tutu.chifanme.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tutu.chifanme.R;
import com.tutu.chifanme.database.DBManager;
import com.tutu.chifanme.utils.StreamUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 闪屏页
 *
 * 作者：曹贵生
 * 邮箱：1595143088@qq.com
 * 说明：闪屏页做数据准备
 */
public class SplashActivity extends Activity {

    private static final int CODE_UPDATE_DIALOG = 0;
    private static final int CODE_URL_ERROR = 1;
    private static final int CODE_NET_ERROR = 2;
    private static final int CODE_JSON_ERROR = 3;
    private static final int CODE_ENTER_HOME = 4;

    // 数据库管理类
    private DBManager dbManager;

    private TextView tvVersion;
    private TextView tvProgress;

    private int mVersionCode; // 版本号
    private String mVersionName; // 版本名
    private String mDesc; // 版本描述
    private String mDownloadUrl; // 下载链接
    private RelativeLayout rlRoot;  // 根布局


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // 向数据库中准备数据
        dbManager = new DBManager(this);
        // 设置当前activity全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        x.Ext.init(getApplication());
//        tvVersion = (TextView) findViewById(R.id.tv_version);
        tvProgress = (TextView) findViewById(R.id.tv_progress);
        rlRoot = (RelativeLayout) findViewById(R.id.activity_main);

//        tvVersion.setText("版本名：" + getVersionName());

        SharedPreferences mPref = getSharedPreferences("config", MODE_PRIVATE);

        boolean auto_update = mPref.getBoolean("auto_update", true);
        if (auto_update) {
            checkVersion();
        } else {
            // 延时两秒发送消息
            mHandle.sendEmptyMessageDelayed(CODE_ENTER_HOME, 2000);
        }

        AlphaAnimation animation = new AlphaAnimation(0.1f, 1f);
        animation.setDuration(2000);
        rlRoot.startAnimation(animation);

    }

    private Handler mHandle = new Handler() {

        @Override
        public void handleMessage(Message msg) {
           switch (msg.what) {
               case CODE_UPDATE_DIALOG:
                   showUpdateDialog();
                   break;
               case CODE_JSON_ERROR:
                   Toast.makeText(SplashActivity.this, "数据错误", Toast.LENGTH_SHORT).show();
                   enterHome();
                   break;
               case CODE_NET_ERROR:
//                   Toast.makeText(SplashActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                   enterHome();
                   break;
               case CODE_URL_ERROR:
                   Toast.makeText(SplashActivity.this, "链接地址错误", Toast.LENGTH_SHORT).show();
                   enterHome();
                   break;
               case CODE_ENTER_HOME:
                   enterHome();
               default:
                   break;
           }
        }
    };

    /**
     * 获取版本名
     * @return
     */
    private String getVersionName() {

        String versionName = "";
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取版本号
     * @return
     */
    private int getVersionCode() {

        int versionCode;
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            versionCode = packageInfo.versionCode;
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 检查新版本
     */
    private void checkVersion() {

        // 开始时间
        final long startTime = System.currentTimeMillis();

        // 启动子线程异步加载数据
        new Thread() {

            @Override
            public void run() {
                Message msg = new Message();
                HttpURLConnection urlConnection = null;
                try {
                    // 本机地址用localhost，但是如果使用模拟器加载本机地址时，可以用ip（10.0.2.2）来替换
                    URL url = new URL("http://10.0.2.2:8080/version.json");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setConnectTimeout(5000); // 设置超时时间
                    urlConnection.setRequestMethod("GET");   // 设置请求方法
                    urlConnection.setReadTimeout(5000);   // 设置请求超时时间
                    urlConnection.connect();

                    int responseCode = urlConnection.getResponseCode();   // 获取响应码
                    if (responseCode == 200) {  // 请求成功
                        InputStream inputStream = urlConnection.getInputStream();
                        String result = StreamUtils.readFromStream(inputStream);
//                        System.out.println("返回result：" + result);

                        JSONObject jsonObject = new JSONObject(result);
                        mVersionName = jsonObject.getString("versionName");
                        mVersionCode = jsonObject.getInt("versionCode");
                        mDesc = jsonObject.getString("desc");
                        mDownloadUrl = jsonObject.getString("downloadUrl");
//                        System.out.println("版本号：" + mVersionCode);

                        // 判断是否有新版本
                        if (mVersionCode > getVersionCode()) {
                            // 服务器版本大于本地app版本，说明有新版
                            msg.what = CODE_UPDATE_DIALOG;
                        } else {
                            // 没有新版本
                            msg.what = CODE_ENTER_HOME;
                        }
                    }

                } catch (MalformedURLException e) {
                    // url错误
                    msg.what = CODE_URL_ERROR;
                    e.printStackTrace();
                } catch (IOException e) {
                    // 网络错误
                    msg.what = CODE_NET_ERROR;
                    e.printStackTrace();
                } catch (JSONException e) {
                    // json格式错误
                    msg.what = CODE_JSON_ERROR;
                    e.printStackTrace();
                } finally {

                    // 结束时间
                    long endTime = System.currentTimeMillis();
                    // 用时多少
                    long useTime = endTime - startTime;
                    if (useTime < 2000) {
                        try {
                            Thread.sleep(2000-useTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    mHandle.sendMessage(msg);
                    if (urlConnection != null) {
                        urlConnection.disconnect();  // 关闭网络链接
                    }
                }
            }
        }.start();
    }

    /**
     * 提示升级的对话框
     */
    private void showUpdateDialog() {
        // 上下文使用this和getApplicationContext有区别，优先使用this
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("发现新版本：" + mVersionName);
        // 设置禁用返回键，但是用户体验太差，不建议这样使用
//        build.setCancelable(false);
        build.setMessage(mDesc);
        build.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.out.println("立即更新");
                download();
            }
        });

        build.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                enterHome();
            }
        });

        // 在显示了对话框是，用户点击返回按钮时调用
        build.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                enterHome();  // 进入主界面
            }
        });
        build.show();
    }

    /**
     * 下载 apk
     */
    private void download() {

        // 判断用户是否有sdcard
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            // 下载链接
            RequestParams params = new RequestParams(mDownloadUrl);

            // 自定义保存路径
            params.setSaveFilePath(Environment.getExternalStorageDirectory() + "/update.apk");
            x.http().get(params, new Callback.ProgressCallback<File>() {   // <> 中需要File，用string无法下载

                // 以下方法都是在主线程执行的
                @Override
                public void onSuccess(File result) {

                    System.out.println("下载成功");
                    //apk下载完成后，调用系统的安装方法
                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.addCategory(Intent.CATEGORY_DEFAULT);   // 默认时default
                    intent.setDataAndType(Uri.fromFile(result), "application/vnd.android.package-archive");
                    startActivityForResult(intent, 0);  // 0:请求码。 当用户取消安装时回调onActivityResult();

                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                    System.out.println("下载失败");

                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                    System.out.println("下载完成");
                }

                @Override
                public void onWaiting() {

                }

                @Override
                public void onStarted() {

                }

                @Override
                public void onLoading(long total, long current, boolean isDownloading) {

                    System.out.println("下载进度" + current+ "/" + total);
                    tvProgress.setVisibility(View.VISIBLE);
                    tvProgress.setText("下载进度：" + current*100/total + "%");
                }
            });
        } else {
            Toast.makeText(SplashActivity.this, "未找到sdcard！", Toast.LENGTH_SHORT).show();
        }
    }

    // 当用户取消安装时调用（当上个页面关闭时调用）
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        enterHome();
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void enterHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
