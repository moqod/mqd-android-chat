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

    private static final int DEFAULT_RECONNECTION_DELAY = 30; // sec
    private static final int DEFAULT_SEND_MESSAGE_TIMEOUT = 5; // sec

    private String mHost;
    private int mPort;
    private String mDomain;
    private String mResource;
    private String mJid;
    private String mPassword;
    private int mReconnectionDelay = DEFAULT_RECONNECTION_DELAY;
    private int mSendMessageTimeout = DEFAULT_SEND_MESSAGE_TIMEOUT;

    public XMPPConfiguration(String host, int port, String domain, String resource, String jid, String password) {
        mHost = host;
        mPort = port;
        mDomain = domain;
        mResource = resource;
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

    public int getReconnectionDelay() {
        return mReconnectionDelay;
    }

    public void setReconnectionDelay(int reconnectionDelay) {
        mReconnectionDelay = reconnectionDelay;
    }

    public int getSendMessageTimeout() {
        return mSendMessageTimeout;
    }

    public void setSendMessageTimeout(int sendMessageTimeout) {
        mSendMessageTimeout = sendMessageTimeout;
    }

    @Override
    public String toString() {
        return "XMPPConfiguration{" +
                "mHost='" + mHost + '\'' +
                ", mPort=" + mPort +
                ", mDomain='" + mDomain + '\'' +
                ", mResource='" + mResource + '\'' +
                ", mJid='" + mJid + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mReconnectionDelay=" + mReconnectionDelay +
                ", mSendMessageTimeout=" + mSendMessageTimeout +
                '}';
    }

    protected XMPPConfiguration(Parcel in) {
        mResource = in.readString();
        mDomain = in.readString();
        mHost = in.readString();
        mPort = in.readInt();
        mJid = in.readString();
        mPassword = in.readString();
        mReconnectionDelay = in.readInt();
        mSendMessageTimeout = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mResource);
        dest.writeString(mDomain);
        dest.writeString(mHost);
        dest.writeInt(mPort);
        dest.writeString(mJid);
        dest.writeString(mPassword);
        dest.writeInt(mReconnectionDelay);
        dest.writeInt(mSendMessageTimeout);
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
