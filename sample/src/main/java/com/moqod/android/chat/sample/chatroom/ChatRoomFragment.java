package com.moqod.android.chat.sample.chatroom;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.moqod.android.chat.sample.R;
import com.moqod.android.chat.sample.chatroom.di.ChatRoomComponent;
import com.moqod.android.chat.sample.common.BindingFragment;
import com.moqod.android.chat.sample.common.Injector;
import com.moqod.android.chat.sample.databinding.FragmentChatRoomBinding;
import com.moqod.android.utils.EndlessRecyclerViewScrollListener;

import javax.inject.Inject;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 18/04/16
 * Time: 16:08
 */
public class ChatRoomFragment extends BindingFragment<FragmentChatRoomBinding> implements ChatRoomContract.View {

    public static ChatRoomFragment newInstance(String chatId) {
        ChatRoomFragment fragment = new ChatRoomFragment();

        Bundle args = new Bundle(1);
        args.putString(KEY_CHAT_ID, chatId);
        fragment.setArguments(args);

        return fragment;
    }

    private static final String KEY_CHAT_ID = "chat_id";

    @Inject ChatRoomPresenter mPresenter;

    private MessagesAdapter mMessagesAdapter;
    private OutgoingMessageViewModel mOutgoingMessageViewModel;

    private String mChatId;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Injector.getComponent(context, ChatRoomComponent.class).inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        mChatId = arguments.getString(KEY_CHAT_ID);
        mPresenter.setChatId(mChatId);
        mMessagesAdapter = new MessagesAdapter(mPresenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat_room;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentChatRoomBinding binding = getBinding();
        binding.setEventListener(mPresenter);
        mOutgoingMessageViewModel = new OutgoingMessageViewModel();
        binding.setMessage(mOutgoingMessageViewModel);

        RecyclerView recyclerView = binding.chatRecyclerView;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mMessagesAdapter);

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                mMessagesAdapter.setLoading();
                mPresenter.onLoadMore(page);
            }
        });

        mMessagesAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                if (positionStart == 0 && layoutManager.findFirstVisibleItemPosition() == 0) {
                    recyclerView.smoothScrollToPosition(0);
                }
            }
        });

        mPresenter.attachView(this);
        mPresenter.onLoadMore(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
    }

    @Override
    public void updateChatUi(ChatViewModel chatViewModel) {
        FragmentChatRoomBinding binding = getBinding();
        if (binding != null) {
            binding.setModel(chatViewModel);
        }
    }

    @Override
    public void updateMessagesUi(List<MessageViewModel> newMessages) {
        mMessagesAdapter.addNewMessages(newMessages);
    }

}
