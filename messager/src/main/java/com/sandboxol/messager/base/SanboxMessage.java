package com.sandboxol.messager.base;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by winson on 2019/08/29 .
 */
public class SanboxMessage implements Parcelable {
    public int what;
    public int arg0;
    public int arg1;
    public String other;
    private Bundle mExtras;

    public SanboxMessage() {
        this.mExtras = new Bundle();
    }

    /**
     * 传递复杂对象需要设置，比如Parcelable
     *
     * @param loader
     */
    public void setClassLoader(ClassLoader loader) {
        if (mExtras != null) {
            mExtras.setClassLoader(loader);
        }
    }

    public SanboxMessage(int what) {
        this(what, 0, 0, null);
    }

    public SanboxMessage(int what, int arg0) {
        this(what, arg0, 0, null);
    }

    public SanboxMessage(int what, int arg0, int arg1) {
        this(what, arg0, arg1, null);
    }

    public SanboxMessage(int what, int arg0, int arg1, String other) {
        this();
        this.what = what;
        this.arg0 = arg0;
        this.arg1 = arg1;
        this.other = other;

    }

    protected SanboxMessage(Parcel in) {
        what = in.readInt();
        arg0 = in.readInt();
        arg1 = in.readInt();
        other = in.readString();
        mExtras = in.readBundle();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(what);
        dest.writeInt(arg0);
        dest.writeInt(arg1);
        dest.writeString(other);
        dest.writeBundle(mExtras);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SanboxMessage> CREATOR = new Creator<SanboxMessage>() {
        @Override
        public SanboxMessage createFromParcel(Parcel in) {
            return new SanboxMessage(in);
        }

        @Override
        public SanboxMessage[] newArray(int size) {
            return new SanboxMessage[size];
        }
    };

    public String getStringExtra(String key) {

        return getStringExtra(key, "");
    }

    public String getStringExtra(String key, String defaultValue) {

        if (mExtras != null) {
            return mExtras.getString(key, defaultValue);
        }
        return "";
    }

    public void setWhat(int what) {
        this.what = what;
    }

    public void setArg0(int arg0) {
        this.arg0 = arg0;
    }

    public void setArg1(int arg1) {
        this.arg1 = arg1;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public void putExtras(Bundle mExtras) {
        this.mExtras = mExtras;
    }

    public void putExtra(String key, String value) {
        if (mExtras == null) {
            mExtras = new Bundle();
        }
        mExtras.putString(key, value);
    }

    public int getIntExtra(String key) {

        return getIntExtra(key, 0);
    }

    public int getIntExtra(String key, int defaultValue) {

        if (mExtras != null) {
            return mExtras.getInt(key, defaultValue);
        }
        return 0;
    }

    public void putExtra(String key, int value) {
        if (mExtras == null) {
            mExtras = new Bundle();
        }
        mExtras.putInt(key, value);
    }

    public long getLongExtra(String key) {

        return getLongExtra(key, 0);
    }

    public long getLongExtra(String key, long defaultValue) {

        if (mExtras != null) {
            return mExtras.getLong(key, defaultValue);
        }
        return 0;
    }

    public void putExtra(String key, long value) {
        if (mExtras == null) {
            mExtras = new Bundle();
        }
        mExtras.putLong(key, value);
    }

    public boolean getBooleanExtra(String key) {


        return getBooleanExtra(key, false);
    }

    public boolean getBooleanExtra(String key, boolean defaultValue) {

        if (mExtras != null) {
            return mExtras.getBoolean(key, defaultValue);
        }
        return false;
    }

    public void putExtra(String key, boolean value) {
        if (mExtras == null) {
            mExtras = new Bundle();
        }
        mExtras.putBoolean(key, value);
    }

    public Serializable getSerializableExtra(String name) {
        return mExtras == null ? null : mExtras.getSerializable(name);
    }

    public void putExtra(String key, Serializable value) {
        if (mExtras == null) {
            mExtras = new Bundle();
        }
        mExtras.putSerializable(key, value);
    }

    public <T extends Parcelable> T getParcelableExtra(String name) {
        return mExtras == null ? null : mExtras.<T>getParcelable(name);
    }

    public void putExtra(String name, Parcelable value) {
        if (mExtras == null) {
            mExtras = new Bundle();
        }
        mExtras.putParcelable(name, value);
    }

    public int getWhat() {
        return what;
    }

    public int getArg0() {
        return arg0;
    }

    public int getArg1() {
        return arg1;
    }

    public String getOther() {
        return other;
    }

    public Bundle getExtras() {
        return mExtras;
    }
}
