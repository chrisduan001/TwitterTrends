package com.example.chris.twittertrends.interactor;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.chris.twittertrends.R;
import com.example.chris.twittertrends.entities.TokenEntity;
import com.example.chris.twittertrends.network.ServiceHelper;
import com.example.chris.twittertrends.util.AppSharedPreference;

import java.net.ConnectException;
import java.net.UnknownHostException;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * Created by Chris on 3/22/18.
 */

public abstract class BaseInteractor<RESPONSE, CALLBACK extends BaseCallback> {
    private Disposable disposable;

    protected CALLBACK callback;
    protected ServiceHelper serviceHelper;
    protected AppSharedPreference sharedPreference;

    public BaseInteractor(ServiceHelper serviceHelper, AppSharedPreference sharedPreference) {
        this.serviceHelper = serviceHelper;
        this.sharedPreference = sharedPreference;
    }

    public void call(CALLBACK callback) {
        this.callback = callback;
    }

    public void disposeRequest() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    //region network logic
    //Check token first then make the api request
    protected void makeNetworkCall() {
        String token = sharedPreference.getToken();

        if (TextUtils.isEmpty(token)) {
            execute(getAccessToken());
            return;
        }

        serviceHelper.setToken(token);
        execute(getNetworkCall());
    }

    private void execute(@NonNull Single<RESPONSE> ob) {
        disposable = ob.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RESPONSE>() {
                    @Override
                    public void accept(RESPONSE response) throws Exception {
                        processNetworkResponse(response);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        processError(throwable);
                    }
                });
    }

    //Get token and save it to the sharedpreference
    private Single<RESPONSE> getAccessToken() {
        return serviceHelper.getOauthToken()
                .flatMap(new Function<TokenEntity, SingleSource<RESPONSE>>() {
                    @Override
                    public SingleSource<RESPONSE> apply(TokenEntity tokenEntity) throws Exception {
                        sharedPreference.setToken(tokenEntity.accessToken);
                        serviceHelper.setToken(tokenEntity.accessToken);
                        return getNetworkCall();
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        processError(throwable);
                    }
                });
    }

    protected abstract Single<RESPONSE> getNetworkCall();
    protected abstract void processNetworkResponse(RESPONSE response);
    //endregion

    //This will need more work to find out each different error type and generate corresponding error message
    //based on business needs
    private void processError(Throwable e) {
        if (e == null) {
            callback.onError(R.string.error_generic);

            return;
        }

        if (e instanceof UnknownHostException || e instanceof ConnectException) {
            callback.onError(R.string.error_connection);
        } else if (e instanceof HttpException) {
            HttpException ex = (HttpException) e;
            if (ex.code() >= 400 && ex.code() < 500) {
                callback.onError(R.string.error_auth);

                return;
            }

            callback.onError(R.string.error_generic);
        } else {
            callback.onError(R.string.error_generic);
        }
    }
}
