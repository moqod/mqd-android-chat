package com.moqod.android.chat.sample.addchat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.moqod.android.chat.sample.R;
import com.moqod.android.chat.sample.addchat.injection.AddChatStubComponent;
import com.moqod.android.chat.sample.common.BindingFragment;
import injection.Injector;
import com.moqod.android.chat.sample.databinding.FragmentAddChatBinding;

import javax.inject.Inject;


public class AddChatFragment extends BindingFragment<FragmentAddChatBinding> implements AddChatMvpContract.View {

    public static AddChatFragment newInstance() {
        return new AddChatFragment();
    }

    @Inject AddChatPresenter mPresenter;

    private CreateChatViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.getComponent(getActivity(), AddChatStubComponent.class).inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_chat;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new CreateChatViewModel();

        FragmentAddChatBinding binding = getBinding();
        binding.setEventListener(mPresenter);
        binding.setModel(mViewModel);

        mPresenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
    }

}
