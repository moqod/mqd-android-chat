package com.moqod.android.chat.sample.common.injection;

import com.moqod.android.chat.sample.common.ErrorHandler;
import dagger.Module;
import dagger.Provides;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 08/08/16
 * Time: 20:01
 */
@Module
public class ErrorHandlerModule {

    private ErrorHandler mErrorHandler;

    public ErrorHandlerModule(ErrorHandler errorHandler) {
        mErrorHandler = errorHandler;
    }

    @Provides
    public ErrorHandler provideErrorHandler() {
        return mErrorHandler;
    }

}
