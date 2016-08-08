package com.moqod.android.chat.sample.addchat;

import android.databinding.ObservableField;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 08/08/16
 * Time: 18:49
 */
public class CreateChatViewModel implements Parcelable {

    public final ObservableField<String> name = new ObservableField<>("");
    public final ObservableField<String> jid = new ObservableField<>("");

    public CreateChatViewModel() {
    }

    protected CreateChatViewModel(Parcel in) {
        name.set(in.readString());
        jid.set(in.readString());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name.get());
        dest.writeString(jid.get());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CreateChatViewModel> CREATOR = new Creator<CreateChatViewModel>() {
        @Override
        public CreateChatViewModel createFromParcel(Parcel in) {
            return new CreateChatViewModel(in);
        }

        @Override
        public CreateChatViewModel[] newArray(int size) {
            return new CreateChatViewModel[size];
        }
    };

    @Override
    public String toString() {
        return "CreateChatViewModel{" +
                "name=" + name.get() +
                ", jid=" + jid.get() +
                '}';
    }
}
