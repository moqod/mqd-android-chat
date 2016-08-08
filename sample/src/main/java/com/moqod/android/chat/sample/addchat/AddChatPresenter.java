package com.moqod.android.chat.sample.addchat;

import com.moqod.android.chat.domain.chats.ChatsInteractor;
import com.moqod.android.chat.domain.chats.model.ChatModel;
import com.moqod.android.chat.sample.common.BasePresenter;
import com.moqod.android.chat.sample.common.ErrorHandler;
import rx.RxUtils;

import javax.inject.Inject;

public final class AddChatPresenter implements BasePresenter<AddChatMvpContract.View>, AddChatMvpContract.EventListener {

    private final ChatsInteractor mInteractor;
    private AddChatRouter mRouter;
    private ErrorHandler mErrorHandler;

    private AddChatMvpContract.View mView;

    @Inject
    public AddChatPresenter(ChatsInteractor interactor, AddChatRouter router, ErrorHandler errorHandler) {
        mInteractor = interactor;
        mRouter = router;
        mErrorHandler = errorHandler;
    }

    @Override
    public void attachView(AddChatMvpContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void onCreateClicked(CreateChatViewModel viewModel) {
        mInteractor.addChat(new ChatModel(viewModel.jid.get(), viewModel.name.get()))
                .compose(RxUtils.applySchedulers())
                .subscribe(this::updateUi, mErrorHandler::handleError);
    }

    @Override
    public void onCloseClicked() {
        mRouter.closeAddChat();
    }

    private void updateUi(ChatModel chatModel) {
        mRouter.closeAddChat();
    }

}