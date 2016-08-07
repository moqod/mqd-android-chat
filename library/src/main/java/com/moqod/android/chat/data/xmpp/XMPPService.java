package com.moqod.android.chat.data.xmpp;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import com.moqod.android.chat.Logging;
import com.moqod.android.chat.XMPPConfiguration;
import com.moqod.android.chat.domain.messages.MessagesRepository;
import com.moqod.android.chat.domain.messages.models.MessageModel;
import com.moqod.android.chat.domain.messages.models.MessageState;
import org.jivesoftware.smack.packet.Message;
import rx.Observable;
import rx.ObserverAdapter;
import rx.RxUtils;
import rx.Subscription;
import rx.schedulers.Schedulers;

import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 23/05/16
 * Time: 15:00
 */
public class XMPPService extends Service {

    public static void run(Context context, XMPPConfiguration configuration) {
        Intent intent = new Intent(context, XMPPService.class);
        intent.putExtra(KEY_XMPP_CONFIGURATION, configuration);
        context.startService(intent);
    }

    public static void stop(Context context) {
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(context);
        broadcastManager.sendBroadcast(new Intent(ACTION_STOP));
    }

    private static final String KEY_XMPP_CONFIGURATION = "xmpp_configuration";
    private static final String ACTION_STOP = "stop_xmpp";

    @Inject
    MessagesRepository mMessagesRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        LocalBroadcastManager.getInstance(this).registerReceiver(mStopReceiver, new IntentFilter(ACTION_STOP));

        RxXMPP xmpp = new RxXMPP(null);

        Subscription subscription = xmpp.getNewMessages()
                .flatMap(xmppMessage -> {
                    MessageModel messageModel = Mapper.map(xmppMessage);

                    if (messageModel != null) {
                        return mMessagesRepository.put(messageModel);
                    } else {
                        return Observable.empty();
                    }
                })
                .retry()
                .subscribeOn(Schedulers.io())
                .subscribe(new ObserverAdapter<Object>() {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (Logging.ENABLED) {
                            e.printStackTrace();
                        }
                    }
                });
        RxUtils.manage(this, subscription);

        subscription = mMessagesRepository.notifyUpdates()
                .filter(messageModel -> messageModel.getState() == MessageState.STATE_OUTGOING)
                .flatMap(messageModel -> {
                    messageModel.setState(MessageState.STATE_SENDING);
                    return mMessagesRepository.put(messageModel);
                })
                .flatMap(messageModel -> {
                    Message message = Mapper.map(messageModel);

                    return xmpp.sendMessage(message)
                            .flatMap(xmppMessage -> {
                                messageModel.setState(MessageState.STATE_SENT);
                                return mMessagesRepository.put(messageModel);
                            })
                            .onErrorResumeNext(throwable -> {
                                messageModel.setState(MessageState.STATE_ERROR);
                                return mMessagesRepository.put(messageModel);
                            });
                })
                .subscribeOn(Schedulers.io())
                .subscribe(ObserverAdapter.empty());
        RxUtils.manage(this, subscription);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mStopReceiver);
        RxUtils.unsubscribe(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private final BroadcastReceiver mStopReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            stopSelf();
        }
    };

}
