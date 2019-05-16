package cn.wang.service.servicedemo.ipc;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created to :
 *
 * @author WANG
 * @date 2019/5/16
 */
public class PersonalBean implements Parcelable {
    String name;

    public PersonalBean(String name) {
        this.name = name;
    }

    protected PersonalBean(Parcel in) {
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PersonalBean> CREATOR = new Creator<PersonalBean>() {
        @Override
        public PersonalBean createFromParcel(Parcel in) {
            return new PersonalBean(in);
        }

        @Override
        public PersonalBean[] newArray(int size) {
            return new PersonalBean[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
