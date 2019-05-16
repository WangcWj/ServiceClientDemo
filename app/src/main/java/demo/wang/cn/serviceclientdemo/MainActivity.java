package demo.wang.cn.serviceclientdemo;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

import cn.wang.service.servicedemo.ipc.IMyAidlInterface;
import cn.wang.service.servicedemo.ipc.IntentBean;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        Log.e("WANG", "MainActivity.onCreate." + getCurProcessname(this));
/**    log.e
 *
 * 2019-05-15 16:55:26.129 7695-7695/cn.wang.service.servicedemo E/WANG: AidlService.onBind.已经绑定成功!
 * 2019-05-15 16:55:26.132 7606-7606/demo.wang.cn.serviceclientdemo E/WANG: MainActivity.onServiceConnected.demo.wang.cn.serviceclientdemo
 * 2019-05-15 16:55:45.699 7695-7714/cn.wang.service.servicedemo E/WANG: AidlService.getBeans.processname  cn.wang.service.servicedemo
 * 2019-05-15 16:55:45.701 7606-7606/demo.wang.cn.serviceclientdemo E/WANG: 获取服务端的数据: [IntentBean{name='wang', age=0}, IntentBean{name='wang', age=1}]
 * 2019-05-15 16:56:00.000 7695-7714/cn.wang.service.servicedemo E/WANG: AidlService.addBeans.processname  cn.wang.service.servicedemo
 * 2019-05-15 16:56:00.001 7695-7714/cn.wang.service.servicedemo E/WANG: AidlService.addBeans.IntentBean{name='client', age=100}
 * 2019-05-15 16:56:45.487 3318-7754/? E/ChromeSync: [Sync,SyncIntentOperation] Error handling the intent: Intent { act=android.intent.action.PACKAGE_ADDED dat=package:cn.wang.service.servicedemo flg=0x4000010 cmp=com.google.android.gms/.chimera.GmsIntentOperationService (has extras) }.
 *
 */
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                Intent intent = new Intent();
//                intent.setPackage("cn.wang.service.servicedemo");
//                intent.setAction("wang.service");
                intent.setComponent(new ComponentName("cn.wang.service.servicedemo", "cn.wang.service.servicedemo.AidlService"));
                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btn2:
                if (null == iMyAidlInterface) {
                    return;
                }
                try {
                    List<IntentBean> beans = iMyAidlInterface.getBeans();
                    Log.e("WANG", "获取服务端的数据: " + beans.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                    Log.e("WANG", "MainActivity.onClick.getBeans.Exception  " + e);
                }

                break;

            case R.id.btn3:
                if (null == iMyAidlInterface) {
                    return;
                }
                IntentBean bean = new IntentBean();
                bean.setAge(100);
                bean.setName("client");
                try {
                    iMyAidlInterface.addBeansUserIn(bean);
                    String name = bean.getName();
                    Log.e("WANG", "当前add的bean的name  " + name);
                } catch (RemoteException e) {
                    Log.e("WANG", "MainActivity.onClick.addBeans.Exception  " + e);
                    e.printStackTrace();
                }


                break;
            default:
                break;
        }
    }

    private String getCurProcessname(Context context) {
        int i = Process.myPid();
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (null != manager) {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = manager.getRunningAppProcesses();
            if (null != runningAppProcesses && runningAppProcesses.size() > 0) {
                for (ActivityManager.RunningAppProcessInfo info : runningAppProcesses) {
                    if (info.pid == i) {
                        return info.processName;
                    }
                }
            }
        }
        return "";
    }

    IMyAidlInterface iMyAidlInterface;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iMyAidlInterface = null;
        }
    };

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();
    }
}
