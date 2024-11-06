package com.hk.transportProject;

import com.hk.transportProject.model.LoginResponse;
import com.hk.transportProject.model.User;
import com.hk.transportProject.network.AuthService;
import com.hk.transportProject.repository.AuthRepository;

import org.junit.Rule;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.LiveData;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private AuthService authService;
    private AuthRepository authRepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        // ViewModel에 Mock Repository 주입
        authRepository = new AuthRepository(authService);
    }

    @Test
    public void testLoginSuccess(){
        // Given
        User user = new User("testUser", "testPassword");
        LoginResponse mockResponse = new LoginResponse(true, "로그인 성공");

        Call<LoginResponse> mockCall = mock(Call.class);
        // when(authService.login(any(User.class))).thenReturn(mockCall);

        when(authService.login(any(User.class))).thenAnswer(invocation -> {
            // 확인용 println
            User userArgument = invocation.getArgument(0);
            System.out.println("AuthRepositoryTestSuccess - ID: " + userArgument.getUserId());
            System.out.println("AuthRepositoryTestSuccess - Password: " + userArgument.getPassword());

            doAnswer(invocation1 -> {
                Callback<LoginResponse> callback = invocation1.getArgument(0);
                callback.onResponse(mockCall, Response.success(mockResponse));
                return null;
            }).when(mockCall).enqueue(any(Callback.class));

            return mockCall;
            });

        // Retrofit Call 객체 enqueue 직접 제어
        /*
        doAnswer(invocation -> {
            Callback<LoginResponse> callback = invocation.getArgument(0);
            callback.onResponse(mockCall, Response.success(mockResponse));
            return null;
        }).when(mockCall).enqueue(any(Callback.class));
        */

        // when
        LiveData<LoginResponse> responseLiveData = authRepository.login(user);

        // Then
        assertEquals(true, responseLiveData.getValue().isSuccess());
        assertEquals("로그인 성공", responseLiveData.getValue().getMessage());

    }

    public void testLoginFailure(){
        // Given
        User user = new User("wrongUser", "wrongPassword");
        LoginResponse mockResponse = new LoginResponse(false, "로그인 실패");

        Call<LoginResponse> mockCall = mock(Call.class);
        when(authService.login(any(User.class))).thenAnswer(invocation -> {
            // 확인용 println
            User userArgument = invocation.getArgument(0);
            System.out.println("AuthRepositoryTestSuccess - ID: " + userArgument.getUserId());
            System.out.println("AuthRepositoryTestSuccess - Password: " + userArgument.getPassword());

            doAnswer(invocation1 -> {
                Callback<LoginResponse> callback = invocation1.getArgument(0);
                callback.onResponse(mockCall, Response.success(mockResponse));
                return null;
            }).when(mockCall).enqueue(any(Callback.class));

            return mockCall;
        });

        /*
        doAnswer(invocation -> {
            Callback<LoginResponse> callback = invocation.getArgument(0);
            callback.onResponse(mockCall, Response.success(mockResponse));
            return null;
        }).when(mockCall).enqueue(any(Callback.class));
        */
        // When
        LiveData<LoginResponse> responseLiveData = authRepository.login(user);

        // Then
        assertNotNull(responseLiveData);
        assertNotNull(responseLiveData.getValue());
        assertEquals(false, responseLiveData.getValue().isSuccess());
        assertEquals("로그인 실패", responseLiveData.getValue().getMessage());
    }
}
