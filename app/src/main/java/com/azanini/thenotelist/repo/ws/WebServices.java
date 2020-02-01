package com.azanini.thenotelist.repo.ws;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.azanini.thenotelist.R;
import com.azanini.thenotelist.entities.BusinessException;
import com.azanini.thenotelist.entities.Notebook;
import com.azanini.thenotelist.enums.BusinessErrorCode;
import com.azanini.thenotelist.enums.Environment;
import com.azanini.thenotelist.interfaces.WebServiceCallbackInterface;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WebServices {

    private static Environment environment = Environment.DEV;

    public static final int NO_CONNECTION_ERROR = -1;
    public static final int NO_ERROR = 0;

    public static void getNotebooks(Context context, WebServiceCallbackInterface<GetNotebooksResponse> responseCallback) {
        RequestBase requestBase = new RequestBase("a token");
        sendWsRequest(context, Request.Method.GET, context.getString(R.string.WS_URL_GET_NOTEBOOKS), requestBase, responseCallback, GetNotebooksResponse.class);
    }

    private static <T extends ResponseBase> void sendWsRequest(Context context, int requestMethod, String url, final RequestBase request, final WebServiceCallbackInterface<T> responseCallback, final Class<T> responseClass) {

        // json_request is unused because Volley need an JsonArrayRequest in order to parse an JsonArrayResponse from this endpoint.
        // I would make any endpoint returns a JsonObject with commons error parameters, just like ResposneBase is
        // Then, it would be much easier to the app to parse the response and to use ir. In that scenario, that json_request would be used
        JSONObject json_request;
        try {
            json_request = new JSONObject(new Gson().toJson(request));
        } catch (JSONException e) {
            throw new BusinessException(BusinessErrorCode.FAILED_TO_PARSE_GSON);
        }

        JsonArrayRequest req = new JsonArrayRequest(requestMethod, environment.getBaseUrl() + url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Type type = new TypeToken<List<Notebook>>() {
                }.getType();
                ArrayList<Notebook> notebookList = new Gson().fromJson(response.toString(), type);
                T resp = (T)new GetNotebooksResponse(notebookList);

                // Line below is the best way, I think, to manage generic json objects as response, but I cant because of response's json format
                // T resp2 = new Gson().fromJson(response.toString(), responseClass);
                responseCallback.onResponse(resp);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                T resp = new Gson().fromJson("{}", responseClass);
                resp.setErrorCode(NO_CONNECTION_ERROR);
                responseCallback.onResponse(resp);
            }
        });
        Connection.getInstance(context).add(req);
    }


}
