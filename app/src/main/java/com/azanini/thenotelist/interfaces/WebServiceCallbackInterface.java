package com.azanini.thenotelist.interfaces;

import com.azanini.thenotelist.repo.ws.ResponseBase;

public interface WebServiceCallbackInterface<T extends ResponseBase> {

    void onResponse(T  response);

}
