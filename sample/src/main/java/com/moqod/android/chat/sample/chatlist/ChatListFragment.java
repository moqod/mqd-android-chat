package com.moqod.android.chat.sample.chatlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.moqod.android.chat.sample.R;
import com.moqod.android.chat.sample.chatlist.injection.ChatListComponent;
import com.moqod.android.chat.sample.common.BindingFragment;
import com.moqod.android.chat.sample.common.ChatViewModel;
import injection.Injector;
import com.moqod.android.chat.sample.databinding.FragmentChatListBinding;

import javax.inject.Inject;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 08/08/16
 * Time: 15:11
 */
public class ChatListFragment extends BindingFragment<FragmentChatListBinding> implements ChatListContract.View {

    public static ChatListFragment newInstance() {
        return new ChatListFragment();
    }

    @Inject ChatListPresenter mPresenter;

    private ChatListAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.getComponent(getActivity(), ChatListComponent.class).inject(this);

        mAdapter = new ChatListAdapter(mPresenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat_list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentChatListBinding binding = getBinding();
        binding.setEventListener(mPresenter);

        RecyclerView recyclerView = binding.chatListRecycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        mPresenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
    }

    @Override
    public void showChatList(List<ChatViewModel> list) {
        mAdapter.setData(list);
    }

    @Override
    public void showLoading() {

    }

}
