package com.moqod.android.chat.sample.chatlist;

import com.moqod.android.binding.BindingAdapter;
import com.moqod.android.binding.BindingViewHolder;
import com.moqod.android.chat.sample.R;
import com.moqod.android.chat.sample.common.ChatViewModel;
import com.moqod.android.chat.sample.databinding.ItemChatListBinding;
import com.moqod.android.utils.BetterSortedList;

import javax.inject.Inject;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 08/08/16
 * Time: 15:38
 */
public class ChatListAdapter extends BindingAdapter<ItemChatListBinding> {

    private final BetterSortedList<ChatViewModel> mData;
    private final ChatListContract.EventListener mEventListener;

    public ChatListAdapter(ChatListContract.EventListener eventListener) {
        mEventListener = eventListener;
        mData = new BetterSortedList<>(ChatViewModel.class, new BetterSortedList.BetterCallback<>(this));
    }

    @Override
    protected void bindItem(BindingViewHolder<ItemChatListBinding> holder, int position, List<Object> payloads) {
        ItemChatListBinding binding = holder.getBinding();
        binding.setModel(mData.get(position));
        binding.setEventListener(mEventListener);
    }

    @Override
    protected int getLayoutId(int position) {
        return R.layout.item_chat_list;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<ChatViewModel> data) {
        mData.updateAll(data);
    }

}
