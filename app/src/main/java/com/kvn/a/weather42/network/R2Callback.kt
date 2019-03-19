package com.imoon.w2s.imoon.network

import android.content.Context
import com.kvn.a.weather42.constant.Dialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class R2Callback<T>(private val mContext: Context, private val mCallback: Callback<T>) : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>?) {
        try {
            if (response!!.isSuccessful()) {
                mCallback.onResponse(call, response)
            } else {
                // error case
                when (response.code()) {
                    404 -> { // "not found"
                        mCallback.onResponse(call, null)
                    }
                    500 -> { // "server broken"
                        mCallback.onResponse(call, null)
                    }
                    else -> { // "unknown error"
                        mCallback.onResponse(call, response)
                    }
                }
            }

        } catch(e: Exception) {
            mCallback.onResponse(call, null)
        }
    }
    override fun onFailure(call: Call<T>, t: Throwable) {
        try {
            mCallback.onFailure(call, t)
        } catch(e: Exception) {
            Dialog(mContext,"Something went wrong. Please try after sometime").alertDg()
        }
    }

    companion object {
        private val TAG = "RetrofitCallback"
    }
}