package com.moqod.android.chat.sample.chatroom;

import android.databinding.ViewDataBinding;
import com.moqod.android.binding.BindingAdapter;
import com.moqod.android.binding.BindingViewHolder;
import com.moqod.android.chat.sample.BR;
import com.moqod.android.chat.sample.R;
import com.moqod.android.utils.BetterSortedList;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 03/06/16
 * Time: 18:52
 */
public class MessagesAdapter extends BindingAdapter {

    private BetterSortedList<MessageViewModel> mData;
    private ChatRoomContract.EventListener mEventListener;

    private boolean mHasLoading;

    public MessagesAdapter(ChatRoomContract.EventListener eventListener) {
        mEventListener = eventListener;
        mData = new BetterSortedList<>(MessageViewModel.class, new BetterSortedList.BetterCallback<MessageViewModel>(this) {
            @Override
            public int compare(MessageViewModel o1, MessageViewModel o2) {
                return super.compare(o1, o2) * -1; // reverse order
            }
        });
    }

    public void setLoading() {
        if (!mHasLoading) {
            mHasLoading = true;
            notifyItemInserted(mData.size());
        }
    }

    public void addNewMessages(List<MessageViewModel> messages) {
        mHasLoading = false;
        notifyItemRemoved(mData.size());

        mData.addAll(messages);
    }

    @Override
    protected void bindItem(BindingViewHolder holder, int position, List payloads) {
        if (mHasLoading && position == getItemCount() - 1) return;

        ViewDataBinding binding = holder.getBinding();

        MessageViewModel viewModel = mData.get(position);
        binding.setVariable(BR.model, viewModel);
        binding.setVariable(BR.eventListener, mEventListener);
    }

    @Override
    protected int getLayoutId(int position) {
        if (mHasLoading && position == getItemCount() - 1) {
            return R.layout.widget_loading_list_row;
        }

        MessageViewModel viewModel = mData.get(position);

        if (viewModel.isSelf()) {
            return R.layout.widget_self_message_list_row;
        } else {
            return R.layout.widget_messages_list_row;
        }
    }

    @Override
    public int getItemCount() {
        int size = mData.size();
        if (mHasLoading) size++;
        return size;
    }

}
