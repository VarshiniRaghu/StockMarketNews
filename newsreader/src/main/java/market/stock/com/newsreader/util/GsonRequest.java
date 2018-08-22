package market.stock.com.newsreader.util;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Volley adapter for JSON requests that will be parsed into Java objects by Gson.
 */
public class GsonRequest<T> extends Request<T> {
    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Map<String, String> headers;
    private JSONObject params;
    private Map<String, String> formBody;
    private final Listener<T> listener;
    private String contentType;

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     * @param clazz Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     */
    public GsonRequest(String url, Class<T> clazz, Map<String, String> headers,
                       Listener<T> listener, ErrorListener errorListener) {
        this(url, clazz, headers, "application/json; charset=UTF-8", Method.GET, listener, errorListener);
    }

    public GsonRequest(String url, Class<T> clazz, Map<String, String> headers,
                       JSONObject params,
                       Listener<T> listener, ErrorListener errorListener) {
        this(url, clazz, headers, "application/json; charset=UTF-8", Method.POST, listener, errorListener);
        this.params = params;
    }

    public GsonRequest(String url, Class<T> clazz, Map<String, String> headers,
                       Map<String, String> formBody,
                       Listener<T> listener, ErrorListener errorListener) {
        this(url, clazz, headers, "application/x-www-form-urlencoded; charset=UTF-8", Method.POST, listener, errorListener);
        this.formBody = formBody;
    }

    public GsonRequest(String url, Class<T> clazz, Map<String, String> headers, String contentType, int method,
                       Listener<T> listener, ErrorListener errorListener) {

        super(method, url, errorListener);
        this.clazz = clazz;
        this.listener = listener;
        this.params = null;
        this.contentType = contentType;

        // accept gzip encoding
        if (headers == null) {
            this.headers = new HashMap<>();
        } else {
            this.headers = headers;
        }

        this.headers.put("Accept-Encoding", "gzip, deflate, sdch");
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }
    @Override
    public byte[] getBody() throws AuthFailureError {
        try {
            if (params != null) {
                return params.toString().getBytes(getParamsEncoding());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return super.getBody();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return this.formBody;
    }

    @Override
    public String getBodyContentType() {
        return this.contentType;
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {

            String json;
            String encoding = response.headers.get("Content-Encoding");
            if(encoding != null && encoding.equals("gzip")) {
                json = unpackData(response.data);
            } else {
                json = new String(
                        response.data, HttpHeaderParser.parseCharset(response.headers));
            }

            return Response.success(
                    gson.fromJson("{\"response\":"+json+"}", clazz), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError){
        if(volleyError.networkResponse != null && volleyError.networkResponse.data != null){
            String encoding = volleyError.networkResponse.headers.get("Content-Encoding");
            if(encoding != null && encoding.equals("gzip")) {
                return new VolleyError(new String(unpackData(volleyError.networkResponse.data)));
            }
        }
        return volleyError;
    }

    private String unpackData(byte[] data) {
        String output = "";
        try {
            GZIPInputStream gStream = new GZIPInputStream(new ByteArrayInputStream(data));
            InputStreamReader reader = new InputStreamReader(gStream);
            BufferedReader in = new BufferedReader(reader);
            String read;
            while ((read = in.readLine()) != null) {
                output += read;
            }
            reader.close();
            in.close();
            gStream.close();
        } catch (IOException e) {
            return null;
        }

        return output;
    }
}