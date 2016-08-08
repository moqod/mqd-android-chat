package com.moqod.android.chat.sample.chatroom;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.view.View;
import com.moqod.android.chat.sample.BR;
import me.chuvashev.databindingutils.ObservableString;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 24/05/16
 * Time: 19:16
 */
public class OutgoingMessageViewModel extends BaseObservable {

    public ObservableString body = new ObservableString("");
    private boolean mVideoStreamAvailable;

    public OutgoingMessageViewModel() {
        body.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                notifyPropertyChanged(BR.videoStreamVisibility);
                notifyPropertyChanged(BR.sendTextVisibility);
            }
        });
    }

    public void setVideoStreamAvailable(boolean videoStreamAvailable) {
        mVideoStreamAvailable = videoStreamAvailable;
        notifyPropertyChanged(BR.videoStreamVisibility);
    }

    public String extractText() {
        String result = body.get();
        body.set("");

        return result;
    }

    @Bindable
    public int getVideoStreamVisibility() {
        return getSendTextVisibility() != View.VISIBLE && mVideoStreamAvailable ? View.VISIBLE : View.GONE;
    }

    @Bindable
    public int getSendTextVisibility() {
        return body.get().isEmpty() ? View.GONE : View.VISIBLE;
    }

}
