package com.moqod.android.chat.sample.chatroom;

import android.support.annotation.NonNull;
import com.moqod.android.chat.domain.chats.ChatRoomInteractor;
import com.moqod.android.chat.sample.chatroom.di.UserId;
import com.moqod.android.chat.sample.common.BasePresenter;
import com.moqod.android.chat.sample.common.ChatViewModel;
import com.moqod.android.chat.sample.common.ErrorHandler;
import rx.ObserverAdapter;
import rx.RxUtils;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 20/05/16
 * Time: 16:58
 */
public class ChatRoomPresenter implements BasePresenter<ChatRoomContract.View>, ChatRoomContract.EventListener {

    private static final int PAGE_QUANTITY = 20;

    private ChatRoomContract.View mView;
    private ChatRoomInteractor mInteractor;
    private ErrorHandler mErrorHandler;
    private ChatRoomRouter mRouter;

    private String mChatId;
    private String mCurrentUserId;

    private Subscription mCurrentPageSubscription;

    private BehaviorSubject<Void> mInitializedSubject;

    @Inject
    public ChatRoomPresenter(ChatRoomInteractor interactor, ErrorHandler errorHandler, ChatRoomRouter router,
                             @UserId String currentUserId) {
        mInteractor = interactor;
        mErrorHandler = errorHandler;
        mRouter = router;
        mCurrentUserId = currentUserId;
    }

    public void setChatId(@NonNull String chatId) {
        mChatId = chatId;
    }

    @Override
    public void attachView(ChatRoomContract.View view) {
        mView = view;
        mInitializedSubject = BehaviorSubject.create();

        if (mChatId != null) {
            Subscription subscription = mInteractor.initWithChatId(mChatId)
                    .map(com.moqod.android.chat.sample.common.ChatMapper::map)
                    .compose(RxUtils.applySchedulers())
                    .subscribe(this::initCompleted, mErrorHandler::handleError);
            RxUtils.manage(this, subscription);

            subscription = mInteractor.getNewMessages(mChatId)
                    .map(messageModels -> MessageMapper.map(messageModels, mCurrentUserId))
                    .compose(RxUtils.applySchedulers())
                    .delaySubscription(mInitializedSubject)
                    .subscribe(mView::updateMessagesUi, mErrorHandler::handleError);
            RxUtils.manage(this, subscription);
        }
    }

    @Override
    public void detachView() {
        mInitializedSubject = null;
        RxUtils.unsubscribe(this);
        mView = null;
    }

    @Override
    public void closeChatRoom() {
        mRouter.onCloseChatRoom();
    }

    @Override
    public void onSendTextMessage(String model) {
        mInteractor.sendTextMessage(model, mCurrentUserId)
                .compose(RxUtils.applySchedulers())
                .subscribe(ObserverAdapter.empty());
    }

    @Override
    public void onResendMessage(MessageViewModel model) {
        mInteractor.resendMessage(model.getModel())
                .compose(RxUtils.applySchedulers())
                .subscribe(ObserverAdapter.empty());
    }

    @Override
    public void onLoadMore(int page) {
        if (mCurrentPageSubscription != null) {
            mCurrentPageSubscription.unsubscribe();
        }

        mCurrentPageSubscription = mInteractor.getMessages(mChatId, page * PAGE_QUANTITY, PAGE_QUANTITY)
                .map(messageModels -> MessageMapper.map(messageModels, mCurrentUserId))
                .compose(RxUtils.applySchedulers())
                .delaySubscription(mInitializedSubject)
                .subscribe(mView::updateMessagesUi, mErrorHandler::handleError);
        RxUtils.manage(this, mCurrentPageSubscription);
    }

    private void initCompleted(ChatViewModel chatViewModel) {
        mView.updateChatUi(chatViewModel);
        mInitializedSubject.onNext(null);
    }

}
