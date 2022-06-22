package com.wgoweb.lexinbywgo.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.wgoweb.lexinbywgo.R
import com.wgoweb.lexinbywgo.databinding.ActivityMainBinding
import com.wgoweb.lexinbywgo.model.LexikonModel
import com.wgoweb.lexinbywgo.network.LexikonService
import com.wgoweb.lexinbywgo.utility.MSP.Constants
import com.wgoweb.lexinbywgo.utility.MSP.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    // A global variable for Progress Dialog
    private var mProgressDialog: Dialog? = null
    // A global variable for the SharedPreferen
    private var mOrdResponse: LexikonModel? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setupActionBar()
        //getLexikonReslult()

    }

 
    /**
     *  Get Movies by retrofit gradle
     */
    private fun getLexikonReslult() {

        if (Constants.isNetworkAvailable(this@MainActivity)) {

            /** Create root url for the Retrofit */
            val service = ServiceBuilder.buildService(LexikonService::class.java)

            val url = "https://lexin.nada.kth.se/lexin/service?searchinfo=to,swe_fin,hus&output=JSON"
            val stringCall: Call<String?>? = service.getStringResponse(url)!!
            stringCall!!.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    if (response.isSuccessful) {
                        // todo: do something with the response string
                        hideProgressDialog() // Hides the progress dialog

                        val gson = Gson()
                        mOrdResponse = gson.fromJson(response.body()!!, LexikonModel::class.java)

                        Log.i("Gson", " >> " + (mOrdResponse!!.result?.get(0)?.ord ?: ""))
                        Log.i("ordList", " >> end " )

                        setupUI()
                    } else {
                        // check the response code.
                        val sc = response.code()
                        when (sc) {
                            400 -> {
                                Log.e("Error 400", ">> Bad Request")
                            }
                            404 -> {
                                Log.e("Error 404", ">> Not Found")
                            }
                            else -> {
                                Log.e("Error", ">> Generic Error  $sc" )
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    hideProgressDialog() // Hides the progress dialog
                    Log.e("onFailure", " >> onFailure" + t.message.toString())

                }
            })
        } else {
            Toast.makeText(
                this@MainActivity,
                "No internet connection available.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * set the result in the UI elements.
     */
    private fun setupUI() {

        if (mOrdResponse != null) {
            Log.i("mOrdResponse", " >>  setupUI")

            if (mOrdResponse!!.result?.isNotEmpty()!!) {
                binding.rvViewItems.visibility = View.VISIBLE
                binding.tvNoItemFound.visibility = View.GONE
                binding.rvViewItems.layoutManager = GridLayoutManager(this, 1)
                //binding.rvViewItems.layoutManager = LinearLayoutManager(this@MainActivity)
                binding.rvViewItems.setHasFixedSize(true)

                val itemAdapter = LexikonAdapter(this@MainActivity, mOrdResponse!!.result)
                // adapter instance is set to the recyclerview to inflate the items.
                binding.rvViewItems.adapter = itemAdapter
            } else {
                binding.rvViewItems.visibility = View.GONE
                binding.tvNoItemFound.visibility = View.VISIBLE
            }

            hideProgressDialog() // Hides the progress dial
        }

    }

    /**
     * The Custom Progress Dialog.
     */
    private fun showCustomProgressDialog() {
        mProgressDialog = Dialog(this)
        mProgressDialog!!.setContentView(R.layout.dialog_custom_progress)
        //Start the dialog and display it on screen.
        mProgressDialog!!.show()
    }

    /**
     * dismiss the progress dialog
     */
    private fun hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog!!.dismiss()
        }
    }

    private fun setupActionBar() {
        /*
        setSupportActionBar(binding.toolbarCustom)
        binding.tvTitle2.text = "Lexin"

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.lexin60)
        }
        binding.toolbarCustom.setNavigationOnClickListener {
            onBackPressed()
        }*/
    }
}


/*
*

            /** set the required param in the service */
            val listCall: Call<LexikonModel> = service.getQueryOrd("to,we_fin,hus")

            val listCall: Call<LexikonModel> = service.getStringResponse(url)
            // Used to show the progress dialog
            showCustomProgressDialog()
            // Retrofit callback executor.
            listCall.enqueue(object : Callback<LexikonModel> {

                override fun onResponse(
                    call: Call<LexikonModel>,
                    response: Response<LexikonModel>,

                ) {
                    // Check if the response is success or not.
                    Log.i("debug", " >> response > " + response)
                    if (response.isSuccessful) {
                        //Log.i("debug", " >> listCall OK")
                        hideProgressDialog() // Hides the progress dialog

                        /** response body */
                        val ordList: LexikonModel = response.body()!!
                        mResultJsonString = Gson().toJson(ordList)
                        Log.i("debug", " >>response.body()!! > " + response.body())
                        Log.i("debug", " >>ordList > " + ordList)
                        //setupUI()

                    } else {
                        // check the response code.
                        val sc = response.code()
                        when (sc) {
                            400 -> {
                                Log.e("Error 400", ">> Bad Request")
                            }
                            404 -> {
                                Log.e("Error 404", ">> Not Found")
                            }
                            else -> {
                                Log.e("Error", ">> Generic Error  $sc" )
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<LexikonModel>, t: Throwable) {
                    hideProgressDialog() // Hides the progress dialog
                    Log.e("onFailure", " >> onFailure" + t.message.toString())
                }
            })
* */