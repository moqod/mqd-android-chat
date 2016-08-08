package com.moqod.android.chat.sample.chatlist;

import com.moqod.android.chat.domain.chats.ChatsInteractor;
import com.moqod.android.chat.sample.common.ChatViewModel;
import injection.ActivityScope;
import com.moqod.android.chat.sample.common.BasePresenter;
import com.moqod.android.chat.sample.common.ChatMapper;
import com.moqod.android.chat.sample.common.ErrorHandler;
import rx.RxUtils;
import rx.Subscription;

import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 08/08/16
 * Time: 15:16
 */
@ActivityScope
public class ChatListPresenter implements BasePresenter<ChatListContract.View>, ChatListContract.EventListener {

    private ChatsInteractor mChatsInteractor;
    private ChatListRouter mRouter;
    private ErrorHandler mErrorHandler;

    private ChatListContract.View mView;

    @Inject
    public ChatListPresenter(ChatsInteractor chatsInteractor, ChatListRouter router, ErrorHandler errorHandler) {
        mChatsInteractor = chatsInteractor;
        mRouter = router;
        mErrorHandler = errorHandler;
    }

    @Override
    public void attachView(ChatListContract.View view) {
        mView = view;
        Subscription subscription = mChatsInteractor.getChats()
                .map(ChatMapper::map)
                .compose(RxUtils.applySchedulers())
                .subscribe(mView::showChatList, mErrorHandler::handleError);
        RxUtils.manage(this, subscription);
    }

    @Override
    public void detachView() {
        RxUtils.unsubscribe(this);
        mView = null;
    }

    @Override
    public void onAddClicked() {
        mRouter.openAddChat();
    }

    @Override
    public void onChatClicked(ChatViewModel viewModel) {
        mRouter.openChatRoom(viewModel.getId());
    }

}
