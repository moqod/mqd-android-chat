package com.moqod.android.chat.data.xmpp;

import android.util.Log;
import com.moqod.android.chat.Logging;
import com.moqod.android.chat.XMPPConfiguration;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import rx.AsyncEmitter;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;
import rx.subscriptions.Subscriptions;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 23/05/16
 * Time: 14:17
 */
public class RxXMPP {

    private static final String TAG = "SMACK";

    private static final Integer CONNECTED = Integer.MAX_VALUE;
    private static final Integer DISCONNECTED = Integer.MIN_VALUE;

    private final XMPPConfiguration mConfiguration;

    private XMPPTCPConnection mConnection;

    private BehaviorSubject<Integer> mAuthenticatedSubject = BehaviorSubject.create();
    private Observable<Integer> mAuthenticatedNotification;

    private Subject<Message, Message> mMessagesSubject = new SerializedSubject<>(PublishSubject.create());

    public RxXMPP(XMPPConfiguration configuration) {
        mConfiguration = configuration;

        mAuthenticatedNotification = mAuthenticatedSubject.filter(state -> state == CONNECTED);
    }

    public Observable<Message> getNewMessages() {
        return Observable.create(subscriber -> {
            try {
                ensureConnection();
            } catch (SmackException | IOException | XMPPException e) {
                e.printStackTrace();
                subscriber.onError(e);
            }

            subscriber.add(Subscriptions.create(() -> mConnection.disconnect()));

            subscriber.add(mMessagesSubject.subscribe(subscriber));
            startListenChatMessages();
        });
    }

    public Observable<Message> sendMessage(Message message) {
        return Observable.<Message>fromEmitter(asyncEmitter -> {
            try {
                ensureConnection();

                if (mConnection != null && mConnection.isConnected() && mConnection.isAuthenticated()) {
                    Message.Type type = message.getType();

                    if (type == Message.Type.chat) {
                        ChatManager chatManager = ChatManager.getInstanceFor(mConnection);
                        mConnection.addStanzaIdAcknowledgedListener(message.getStanzaId(), packet -> {
                            if (Logging.ENABLED) {
                                Log.i(TAG, "processPacket: " + packet.toString());
                            }
                            asyncEmitter.onNext(message);
                            asyncEmitter.onCompleted();
                        });
                        chatManager.createChat(message.getTo()).sendMessage(message);
                    } else if (type == Message.Type.groupchat) {
                        MultiUserChatManager multiUserChatManager = MultiUserChatManager.getInstanceFor(mConnection);
                        MultiUserChat multiUserChat = multiUserChatManager.getMultiUserChat(message.getTo());
                        if (multiUserChat.isJoined()) {
                            multiUserChat.sendMessage(message);
                        }
                    }
                }
            } catch (IOException | XMPPException | SmackException e) {
                if (Logging.ENABLED) {
                    e.printStackTrace();
                }
                asyncEmitter.onError(e);
            }
        }, AsyncEmitter.BackpressureMode.BUFFER)
                .timeout(mConfiguration.getSendMessageTimeout(), TimeUnit.SECONDS)
                .delaySubscription(mAuthenticatedNotification);
    }

    private synchronized void ensureConnection() throws IOException, XMPPException, SmackException {
        if (mConnection == null) {
            createConnection();
        }
        if (mConnection != null && !mConnection.isConnected()) {
            connect();
        }
    }

    private void createConnection() throws SmackException, IOException, XMPPException {
        XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
        configBuilder.setDebuggerEnabled(Logging.ENABLED);
        configBuilder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        configBuilder.setResource(mConfiguration.getResource());
        configBuilder.setServiceName(mConfiguration.getDomain());
        configBuilder.setHost(mConfiguration.getHost());
        configBuilder.setPort(mConfiguration.getPort());
        XMPPTCPConnection.setUseStreamManagementResumptionDefault(false);
        XMPPTCPConnection.setUseStreamManagementDefault(true);

        mConnection = new XMPPTCPConnection(configBuilder.build());
        ReconnectionManager.setEnabledPerDefault(true);
        ReconnectionManager.setDefaultFixedDelay(mConfiguration.getReconnectionDelay());

        mConnection.addConnectionListener(mConnectionListener);
    }

    private void connect() throws SmackException, IOException, XMPPException {
        mConnection.connect();
    }

    private void startListenChatMessages() {
        ChatManager chatManager = ChatManager.getInstanceFor(mConnection);
        chatManager.addChatListener((chat, createdLocally) -> chat.addMessageListener(mChatMessageListener));
    }

    private final ChatMessageListener mChatMessageListener = (chat, message) -> mMessagesSubject.onNext(message);

    private ConnectionListener mConnectionListener = new ConnectionListener() {

        @Override
        public void connected(final XMPPConnection connection) {
            if (Logging.ENABLED) {
                Log.d(TAG, "Connected!");
            }
            if (!connection.isAuthenticated()) {
                try {
                    mConnection.login(mConfiguration.getJid(), mConfiguration.getPassword());
                } catch (XMPPException | SmackException | IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void connectionClosed() {
            if (Logging.ENABLED) {
                Log.d(TAG, "ConnectionClosed!");
            }
        }

        @Override
        public void connectionClosedOnError(Exception arg0) {
            if (Logging.ENABLED) {
                Log.d(TAG, "ConnectionClosedOn Error: " + arg0);
            }
            mAuthenticatedSubject.onNext(DISCONNECTED);
        }

        @Override
        public void reconnectingIn(int arg0) {
            if (Logging.ENABLED) {
                Log.d(TAG, "Reconnectingin " + arg0);
            }
        }

        @Override
        public void reconnectionFailed(Exception arg0) {
            if (Logging.ENABLED) {
                Log.d(TAG, "ReconnectionFailed!");
            }
        }

        @Override
        public void reconnectionSuccessful() {
            if (Logging.ENABLED) {
                Log.d(TAG, "ReconnectionSuccessful");
            }
        }

        @Override
        public void authenticated(XMPPConnection arg0, boolean arg1) {
            if (Logging.ENABLED) {
                Log.d(TAG, "Authenticated!");
            }
            mAuthenticatedSubject.onNext(CONNECTED);
        }
    };

}
