package cn.wang.service.servicedemo.ipc;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created to :
 *
 * @author WANG
 * @date 2019/5/15
 */
public class IntentBean implements Parcelable {
    private String name;
    private int age;

    protected IntentBean(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    public IntentBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static final Creator<IntentBean> CREATOR = new Creator<IntentBean>() {
        @Override
        public IntentBean createFromParcel(Parcel in) {
            return new IntentBean(in);
        }

        @Override
        public IntentBean[] newArray(int size) {
            return new IntentBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }

    public void readFromParcel(Parcel dest) {
        dest.readString();
        dest.readInt();
    }

    @Override
    public String toString() {
        return "IntentBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
