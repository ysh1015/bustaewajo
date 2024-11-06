package com.hk.transportProject.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.hk.transportProject.model.User;
import com.hk.transportProject.model.LoginResponse;
import com.hk.transportProject.repository.AuthRepository;

import javax.inject.Inject;

public class AuthViewModel extends ViewModel {

    private final AuthRepository authRepository;

    @Inject
    public AuthViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public LiveData<LoginResponse> login(String userId, String password) {
        User user = new User(userId, password);
        return authRepository.login(user);
    }
}
