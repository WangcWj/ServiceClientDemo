// IMyAidlInterface.aidl
package cn.wang.service.servicedemo.ipc;

// Declare any non-default types here with import statements
import cn.wang.service.servicedemo.ipc.IntentBean;

import cn.wang.service.servicedemo.ipc.PersonalBean;

interface IMyAidlInterface {

    List<IntentBean> getBeans();

    void addBeansUserIn(in IntentBean intent);

    void addBeansUserOut(out IntentBean intent);

    void addBeansUserInOut(inout IntentBean intent);

}
