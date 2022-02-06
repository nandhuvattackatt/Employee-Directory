package com.nandhu.employeedata.api;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nandhu.employeedata.utils.App;
import com.nandhu.employeedata.utils.Constants;
import com.nandhu.employeedata.utils.DeviceUUIDFactory;
import com.nandhu.employeedata.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class APIRequest {
    Context context;
    RequestQueue requestQueue;
    String path = "";
    JSONObject params = new JSONObject();
    private final String TAG = this.getClass().getName();

    private Map<String, String> header;
    private Map<String, String> param;
    JSONObject parameters;

    public APIRequest(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
        header = new HashMap<>();
        param = new HashMap<>();
        parameters = new JSONObject();

    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void addHeader(String key, String value) {
        header.put(key, value);
    }





    public void executeRequest(final VolleyCallback callback) {

        if (Utils.isNetworkAvailable(context)) {
            Utils.log(TAG, "--------------------------URL----------------------------");
            Utils.log(TAG, Constants.BASE_URL + getPath());

            addHeader("Content-Type", "application/json");

            try {
                Utils.log(TAG, "--------------------------Params----------------------------");
                Utils.log(TAG, parameters.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            requestQueue.getCache().clear();

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    Constants.BASE_URL + getPath(), null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                Utils.log(TAG, "--------------------------Response----------------------------");
                                Utils.log(TAG, response.toString());

                                callback.getSuccessResponse(response);


                            } catch (Exception e) {
                                callback.getErrorResponse(e.getMessage());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            try {
                                Utils.log(TAG, "--------------------------Response Error----------------------------");
                                Utils.log(TAG, error.getMessage());
                                callback.getErrorResponse(error.getMessage());
                            } catch (Exception e) {
                                Utils.log(TAG, "Error " + e.getMessage());
                                callback.getErrorResponse("Something Went Wrong!");
                            }
                        }
                    }
            ) {

                @Override
                public Map<String, String> getHeaders() {
                    Utils.log(TAG, "--------------------------Header----------------------------");
                    Utils.log(TAG, header.toString());

                    return header;
                }
            };

            jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                    20000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonArrayRequest);

        } else {
            callback.getErrorResponse("Network connectivity issue.");
        }
    }


    public interface VolleyCallback {
        public void getSuccessResponse(JSONArray response);

        public void getErrorResponse(String error);
    }

}
