package com.burakkarakoca.koronagncelveriler.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.burakkarakoca.koronagncelveriler.R
import com.burakkarakoca.koronagncelveriler.adapter.InfoRecyclerViewAdapter
import com.burakkarakoca.koronagncelveriler.model.CovidResponse
import com.burakkarakoca.koronagncelveriler.model.TurkeyInfo
import com.burakkarakoca.koronagncelveriler.service.CovidAPI
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private var BASE_URL: String = "https://pomber.github.io/"
    private var lastDayOfInfo: Int? = null

    var recyclerview : RecyclerView? = null
    var infoRecyclerViewAdapter : InfoRecyclerViewAdapter? = null
    var turkeyInfoResponseList: ArrayList<TurkeyInfo>? = null

    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        recyclerview = findViewById(R.id.recyclerview)

        getCovidDataFromJSON()


        mAdView.adListener = object: AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(errorCode : Int) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }

    }

    private fun getCovidDataFromJSON(){

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(CovidAPI::class.java)
        val callCovidInfo = service.getCovidData()

        callCovidInfo.enqueue(object : Callback<CovidResponse>{
            override fun onFailure(call: Call<CovidResponse>, t: Throwable) {
                println("err : " + t.localizedMessage)
            }

            override fun onResponse(
                call: Call<CovidResponse>,
                response: Response<CovidResponse>
            ) {

                if(response.isSuccessful){
                    // recyclerview
                    lastDayOfInfo = response.body()!!.Turkey.size - 1

                    val responseList: List<TurkeyInfo> = response.body()!!.Turkey.reversed().take(lastDayOfInfo!!-46)
                    turkeyInfoResponseList = ArrayList<TurkeyInfo>(responseList)

                    var vakaSayisi = response.body()!!.Turkey[lastDayOfInfo!!].confirmed
                    var olumSayisi = response.body()!!.Turkey[lastDayOfInfo!!].deaths
                    var iyilesmeSayisi = response.body()!!.Turkey[lastDayOfInfo!!].recovered

                    vaka_textView.text = ""+vakaSayisi
                    olum_textView.text = ""+olumSayisi
                    tedavi_textView.text = ""+iyilesmeSayisi

                    recyclerview!!.layoutManager = LinearLayoutManager(applicationContext)
                    infoRecyclerViewAdapter = InfoRecyclerViewAdapter(turkeyInfoResponseList!!)
                    recyclerview!!.adapter = infoRecyclerViewAdapter

                }

            }

        })

    }


}
