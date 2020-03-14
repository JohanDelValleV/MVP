package com.example.mvplogin.login.presenter;

import com.example.mvplogin.login.model.IUser;
import com.example.mvplogin.login.view.ILoginView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class LoginPresenter implements ILoginPresenter {
    private String token;
    public static String BASE_URL = "https://1917ca75.ngrok.io/api/v1/";
    AsyncHttpClient client = new AsyncHttpClient();
    ILoginView iLoginView;
    IUser iUser;

    public LoginPresenter(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
    }

    @Override
    public void clear() {
        iLoginView.onClearText();
    }

    @Override
    public void doLogin(final String name, final String password) {
        RequestParams params = new RequestParams();
        params.put("username", name);
        params.put("password", password);

        client.post(BASE_URL + "login", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                try {
                    JSONObject json = new JSONObject(str);
                    token = (String) json.get("token");
                    Boolean isLoginSuccess = true;
                    final int code = iUser.checkUserValidity(name, password);
                    if (code != 0) isLoginSuccess = false;
                    final Boolean result = isLoginSuccess;
                    iLoginView.onLoginResult(result, -1, token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }

            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }

    @Override
    public void setProgressBarVisibility(int visibility) {
        iLoginView.onSetProgressBarVisibility(visibility);
    }

}
