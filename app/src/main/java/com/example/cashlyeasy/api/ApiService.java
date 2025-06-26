package com.example.cashlyeasy.api;

import com.example.cashlyeasy.model.ApiResponse;
import com.example.cashlyeasy.model.LoginRequest;
import com.example.cashlyeasy.model.RegisterRequest;
import com.example.cashlyeasy.model.PaymentRequest;
import com.example.cashlyeasy.model.TransactionRequest;
import com.example.cashlyeasy.model.TransferRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("register")
    Call<ApiResponse> register(@Body RegisterRequest request);

    @POST("login")
    Call<ApiResponse> login(@Body LoginRequest request);

    @POST("deposit")
    Call<ApiResponse> deposit(@Body TransactionRequest request);

    @POST("withdraw")
    Call<ApiResponse> withdraw(@Body TransactionRequest request);

    @POST("transfer")
    Call<ApiResponse> transfer(@Body TransferRequest request);

    @POST("payment")
    Call<ApiResponse> payment(@Body PaymentRequest request);
}
