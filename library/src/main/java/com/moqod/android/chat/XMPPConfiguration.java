package com.moqod.android.chat;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 05/08/16
 * Time: 14:50
 */
public class XMPPConfiguration implements Parcelable {

    private String mResource;
    private String mDomain;
    private String mHost;
    private int mPort;
    private String mJid;
    private String mPassword;

    public XMPPConfiguration(String resource, String domain, String host, int port, String jid, String password) {
        mResource = resource;
        mDomain = domain;
        mHost = host;
        mPort = port;
        mJid = jid;
        mPassword = password;
    }

    public String getResource() {
        return mResource;
    }

    public String getDomain() {
        return mDomain;
    }

    public String getHost() {
        return mHost;
    }

    public int getPort() {
        return mPort;
    }

    public String getJid() {
        return mJid;
    }

    public String getPassword() {
        return mPassword;
    }

    @Override
    public String toString() {
        return "XMPPConfiguration{" +
                "mResource='" + mResource + '\'' +
                ", mDomain='" + mDomain + '\'' +
                ", mHost='" + mHost + '\'' +
                ", mPort=" + mPort +
                ", mJid='" + mJid + '\'' +
                ", mPassword='" + mPassword + '\'' +
                '}';
    }

    protected XMPPConfiguration(Parcel in) {
        mResource = in.readString();
        mDomain = in.readString();
        mHost = in.readString();
        mPort = in.readInt();
        mJid = in.readString();
        mPassword = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mResource);
        dest.writeString(mDomain);
        dest.writeString(mHost);
        dest.writeInt(mPort);
        dest.writeString(mJid);
        dest.writeString(mPassword);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<XMPPConfiguration> CREATOR = new Creator<XMPPConfiguration>() {
        @Override
        public XMPPConfiguration createFromParcel(Parcel in) {
            return new XMPPConfiguration(in);
        }

        @Override
        public XMPPConfiguration[] newArray(int size) {
            return new XMPPConfiguration[size];
        }
    };

}
