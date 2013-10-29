package com.example.ariamobile;
import com.loopj.android.http.*;
/**
 * Created by Mariano on 26/10/13.
 */
public class AriaRestClient {

    private static final String BASE_URL = "http://lineasmaestras.com/eclipse/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
