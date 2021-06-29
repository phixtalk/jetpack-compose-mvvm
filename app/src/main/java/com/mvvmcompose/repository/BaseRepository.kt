package com.mvvmcompose.repository

import com.mvvmcompose.util.ApiException
import com.mvvmcompose.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class BaseRepository {
    suspend fun<T: Any> safeApiCall(call: suspend () -> Response<T>) : Resource<T> {
        return withContext(Dispatchers.IO) {//run all api requests inside a corountine block in a single location
            val response = call() // we run the lamba function parameter and get a Response object wrapping the data
            if (response.isSuccessful) {//isSuccessful -> Returns true if code() is in the range [200..300).
                Resource.Success(response.body()) //we return the data wrapped in a Resource class
                //note that the response.body() can be empty in cases like delete requests
            } else {
                /*
                * Now the error message response will depend on how the api error response is implemented at the server end
                * We will assume that this particular api returns a json object with a message parameter as seen below:
                * {
                *     "isSuccessful": false,
                *     "message": "Invalid parameters"
                * }
                * describing the error. Note that this may be handled differently by different api responses,
                * so find out how your error response object looks like first then modify this to suit your usecase
                * Also advise the backend team to keep it consistent across all apis
                * We can get the error from the response error body
                * */
                val error = response.errorBody()?.string() //get the error response object as a string

                val message = StringBuilder()
                error?.let{
                    try{//if the error body is in the form of a json object as described above
                        message.append(JSONObject(it).getString("message")) //get the message property from the json object
                    }catch(e: JSONException){
                        /**
                         * in the try block above, we assumed that the error response is a json object, so we attempted to get it's message property
                         * but in a case of 404 error, we don't get any object response, just a plain string, so we return the string instead
                         */
                        message.append(it)
                    }
                    message.append("\n")
                }
                message.append("Error Code: ${response.code()}")
                Resource.Error("$message")
            }
        }
    }
}