package com.example.chris.twittertrends.di.modules;

import com.example.chris.twittertrends.network.ServiceHelper;
import com.example.chris.twittertrends.network.TwitterApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Chris on 3/21/18.
 */
@Module
public class NetworkModule {
    private static final String BASE_URL = "https://api.twitter.com";

    @Provides @Singleton
    public ServiceHelper provideServiceHelper(TwitterApi twitterApi) {
        return new ServiceHelper(twitterApi);
    }

    @Provides @Singleton
    TwitterApi provideApi(Retrofit retrofit) {
        return retrofit.create(TwitterApi.class);
    }

    @Provides @Singleton
    Retrofit provideRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        return new Retrofit.Builder().baseUrl(BASE_URL)
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
