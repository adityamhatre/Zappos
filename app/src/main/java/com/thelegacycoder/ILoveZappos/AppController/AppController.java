package com.thelegacycoder.ILoveZappos.AppController;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;

import com.thelegacycoder.ILoveZappos.Models.ProductItem;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aditya on 002, 2 Feb, 2017.
 */


public class AppController extends Application {
    public static final String BASE_URL = "https://api.zappos.com/";
    private static AppController appController;
    private Retrofit retrofit;

    private Context context;
    private ProgressDialog progressDialog;
    private List<ProductItem> products;

    private static final Object lock = new Object();

    @Override

    public void onCreate() {
        super.onCreate();
        initRetroFit();
        synchronized (lock) {
            appController = this;
        }
    }

    private void initRetroFit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized AppController getInstance() {
        return appController;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }


    public void showLoading(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void dismissLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void setProducts(List<ProductItem> products) {
        this.products = products;
    }

    public List<ProductItem> getProducts() {
        return products;
    }
}
