package net.team1515.morteam.network;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by David on 11/13/2015.
 */
public class CookieRequest extends StringRequest {
    private static final String SET_COOKIE_KEY = "set-cookie";
    private static final String COOKIE_KEY = "Cookie";
    public static final String SESSION_COOKIE = "connect.sid";

    private final Map<String, String> params;
    private SharedPreferences preferences;

    public CookieRequest(int method, String url, SharedPreferences preferences, Listener<String> listener, ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.params = null;
        this.preferences = preferences;
    }

    public CookieRequest(int method, String url, Map<String, String> params, SharedPreferences preferences, Listener<String> listener, ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.params = params;
        this.preferences = preferences;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        //Store seession-id cookie in storage
        if(response.headers.containsKey(SET_COOKIE_KEY) && response.headers.get(SET_COOKIE_KEY).startsWith(SESSION_COOKIE)) {
            String cookie = response.headers.get(SET_COOKIE_KEY);
            if (cookie.length() > 0) {
                String[] splitCookie = cookie.split(";");
                String[] splitSessionId = splitCookie[0].split("=");
                cookie = splitSessionId[1];
                Editor editor = preferences.edit();
                editor.putString(SESSION_COOKIE, cookie);
                editor.apply();
            }
        }
        return super.parseNetworkResponse(response);
    }

    @Override
    protected Map<String, String> getParams() {
        return params;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();
        if(headers == null || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<>();
        }

        //Insert session-id cookie into header
        String sessionId = preferences.getString(SESSION_COOKIE, "");
        if(sessionId.length() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append(SESSION_COOKIE);
            builder.append("=");
            builder.append(sessionId);
            if(headers.containsKey(COOKIE_KEY)) {
                builder.append("; ");
                builder.append(headers.get(COOKIE_KEY));
            }
            headers.put(COOKIE_KEY, builder.toString());
        }

        return headers;
    }
}
