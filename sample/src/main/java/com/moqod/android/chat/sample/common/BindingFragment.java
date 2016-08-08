package com.moqod.android.chat.sample.common;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.moqod.android.lifecycle.LifecycleFragment;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 03/05/16
 * Time: 00:48
 */
public abstract class BindingFragment<B extends ViewDataBinding> extends LifecycleFragment {

    private B mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return mBinding.getRoot();
    }

    @LayoutRes
    protected abstract int getLayoutId();

    public B getBinding() {
        return mBinding;
    }

}
