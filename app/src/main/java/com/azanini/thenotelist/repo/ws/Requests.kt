package com.azanini.thenotelist.repo.ws

import com.azanini.thenotelist.entities.Notebook

open class RequestBase(var token: String)

open class ResponseBase(var errorMessage: String = "", var errorCode: Number = 0)

data class GetNotebooksResponse(val notebooks: ArrayList<Notebook>) : ResponseBase()