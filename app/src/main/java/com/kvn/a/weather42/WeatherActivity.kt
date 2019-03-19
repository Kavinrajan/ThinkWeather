package com.kvn.a.weather42

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import com.imoon.w2s.imoon.network.Api
import com.imoon.w2s.imoon.network.ApiClient
import com.imoon.w2s.imoon.network.R2Callback
import com.kvn.a.weather42.constant.Constants
import com.kvn.a.weather42.constant.Dialog
import com.kvn.a.weather42.constant.NetworkDetect
import com.kvn.a.weather42.data.ForecastResult
import kotlinx.android.synthetic.main.slide_a1.*
import kotlinx.android.synthetic.main.slide_a2.*
import kotlinx.android.synthetic.main.slide_a3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class WeatherActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private var myViewPagerAdapter: MyViewPagerAdapter? = null
    private lateinit var dotsLayout: LinearLayout
    private lateinit var  dots: Array<TextView?>
  ///  private lateinit var layouts: IntArray
    private var detector: NetworkDetect? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        setContentView(R.layout.activity_weather)

        initUI()
    }

    private fun initUI() {

        detector = NetworkDetect(this)

        viewPager = findViewById(R.id.view_pager) as ViewPager
        dotsLayout = findViewById(R.id.layoutDots) as LinearLayout


        setUI()
    }

    private fun setUI() {

        // adding bottom dots
        addBottomDots(0)

        // making notification bar transparent
         changeStatusBarColor()

        myViewPagerAdapter = MyViewPagerAdapter()
        viewPager.setAdapter(myViewPagerAdapter)
        /*viewPager.setCurrentItem(2, true)*/
        viewPager.currentItem = 0
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener)
    }

    private fun addBottomDots(currentPage: Int) {
        dots = arrayOfNulls(layouts.size)
        val colorsActive = resources.getIntArray(R.array.array_dot_active)
        val colorsInactive = resources.getIntArray(R.array.array_dot_inactive)

        dotsLayout.removeAllViews()
        for (i in dots!!.indices) {
            dots[i] = TextView(this)
            dots[i]!!.text = Html.fromHtml("&#8226;")
            dots[i]!!.textSize = 35f
            dots[i]!!.setTextColor(colorsInactive[currentPage])
            dotsLayout.addView(dots[i])
        }

        if (dots.size > 0)
            dots[currentPage]!!.setTextColor(colorsActive[currentPage])
    }

    private fun getItem(i: Int): Int {
        return viewPager.getCurrentItem() + i
    }

    //	viewpager change listener
    internal var viewPagerPageChangeListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {

        override fun onPageSelected(position: Int) {
            addBottomDots(position)
            var city = "600020"
            if (position == 0) city = "10001"
            else if (position == 1) city = "600042"
            else if (position == 2) city = "10005"
            if(detector!!.isConnectingToInternet) {
                callWeatherData(city, position)
            } else {
                Dialog(this@WeatherActivity,getString(R.string.poor_internet)).alertDg()
            }
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {

        }

        override fun onPageScrollStateChanged(arg0: Int) {

        }
    }

    private fun callWeatherData(city: String, position: Int ) {
        try {
            val apiService = ApiClient.client.create(Api::class.java)
            val call = apiService.cityWeather(Constants.apiKEY ,city)
            val mWeatherCallback  = object : Callback<ForecastResult> {
                override fun onResponse(call: Call<ForecastResult>, response: Response<ForecastResult>?) {
                    if (response != null) {
                        val data = response.body()!!
                        if(data.cod.equals("200")) {
                            weatherDataSuccess(data, position)
                        } else {
                            Dialog(this@WeatherActivity,getString(R.string.no_data)).alertDg()
                        }
                    } else {
                        Dialog(this@WeatherActivity,getString(R.string.went_wrong)).alertDg()
                    }
                }

                override fun onFailure(call: Call<ForecastResult>, t: Throwable) {
                    Dialog(this@WeatherActivity,getString(R.string.poor_internet)).alertDg()
                }
            }

            call.enqueue(R2Callback(this, mWeatherCallback ))

        } catch (e: Exception) {
            Log.e("callWeatherData", e.toString())
            Dialog(this@WeatherActivity,getString(R.string.went_wrong)).alertDg()
        }
    }



    private fun weatherDataSuccess(list :ForecastResult, position: Int) {
        try {

            val c = Calendar.getInstance().getTime()
            val df = SimpleDateFormat("dd-MMM")
            val formattedDate = df.format(c)

            val temp_speed = list.list.get(0)
            val temperature = temp_speed.temp.max

            if (position == 0) {
                runOnUiThread {
                    tv_citys1.text = list.city.name
                    tv_dates1.text = "Date : " + formattedDate
                    tv_temps1.text = "Temperature : " + temperature + " ºC"
                    tv_winds1.text = "Wind speed: " + temp_speed.speed + " m/h"
                }
            } else if (position == 1) {
                runOnUiThread {
                    tv_citys2.text = list.city.name

                    tv_dates2.text = "Date : " + formattedDate
                    tv_temps2.text = "Temperature : " + temperature + " ºC"
                    tv_winds2.text = "Wind speed: " + temp_speed.speed + " m/h"
                }
            } else if (position == 2) {
                runOnUiThread {
                    tv_citys3.text = list.city.name
                    tv_dates3.text = "Date : " + formattedDate
                    tv_temps3.text = "Temperature : " + temperature + " ºC"
                    tv_winds3.text = "Wind speed: " + temp_speed.speed + " m/h"
                }
            }

            /*otp_sent_to_txt.text =  getString(R.string.otp_re_sent_to)
                btn_submit_otp.text = getString(R.string.submit)
                login_resend_otp.isClickable = true
                login_resend_otp.setTextColor(resources.getColor(R.color.colorPrimary))*/

        } catch (e: Exception) {

        }
    }
    /**
     * Making notification bar transparent
     */
    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    /**
     * View pager adapter
     */
    inner class MyViewPagerAdapter : PagerAdapter() {
        private var layoutInflater: LayoutInflater? = null

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val view = layoutInflater!!.inflate(layouts[position], container, false)
            container.addView(view)

            return view
        }

        override fun getCount(): Int {
            return layouts.size
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }


        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }
    }

    companion object {
        val layouts = intArrayOf(R.layout.slide_a1, R.layout.slide_a2, R.layout.slide_a3)
    }
}
