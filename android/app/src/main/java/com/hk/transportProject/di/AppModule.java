package com.hk.transportProject.di;

import com.hk.transportProject.network.AuthService;
import com.hk.transportProject.network.RetrofitClient;
import com.hk.transportProject.repository.AuthRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.Provides;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    @Singleton
    public AuthService provideAuthService(){
        return RetrofitClient.getRetrofitInstance().create(AuthService.class);
    }

    @Provides
    @Singleton
    public AuthRepository provideAuthRepository(AuthService authService){
        return new AuthRepository(authService);
    }

}
