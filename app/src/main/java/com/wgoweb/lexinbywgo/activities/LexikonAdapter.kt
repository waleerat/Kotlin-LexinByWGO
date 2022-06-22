package com.wgoweb.lexinbywgo.activities

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wgoweb.lexinbywgo.databinding.ResultListBinding
import com.wgoweb.lexinbywgo.model.InflectionModel
import com.wgoweb.lexinbywgo.model.PhoneticModel
import com.wgoweb.lexinbywgo.model.ResultModel
import java.io.IOException
import android.widget.Toast

import android.media.AudioManager




class LexikonAdapter(
    private val context: Context,
    private val items: List<ResultModel>?
): RecyclerView.Adapter<LexikonAdapter.GetViewBindingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetViewBindingHolder {

        val itemBinding = ResultListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GetViewBindingHolder(context, itemBinding)
    }

    override fun onBindViewHolder(holder: GetViewBindingHolder, position: Int) {
        val rowBinding: ResultModel = items!![position]
        holder.bind(rowBinding)
    }

    override fun getItemCount(): Int = items!!.size

    class GetViewBindingHolder(val context: Context,
                               private val itemBinding: ResultListBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {


        fun bind(rowData: ResultModel) {
            var sound = getPlySound(rowData.baseLang!!.phonetic)
            itemBinding.tvOrd.text  = rowData.ord
            itemBinding.tvListen.text = sound
            itemBinding.tvOrdClass.text = rowData.ordClass
            itemBinding.tvInflection.text  = getInflection(rowData.baseLang!!.inflection!!)
            itemBinding.tvMeaning.text = rowData.baseLang!!.meaning

            if (sound != "") {
                itemBinding.tvListen.setOnClickListener {
                    playAudio(rowData.baseLang!!.phonetic!!.audioUrl)
                    /*if (onClickListener != null) {
                        onClickListener!!.onClick(itemBinding.root)
                    }*/
                }
            }

        }

        private lateinit var mediaPlayer: MediaPlayer
        private fun playAudio(audioUrl: String) {

            // initializing media player
            mediaPlayer = MediaPlayer()

            // below line is use to set the audio
            // stream type for our media player.
          //  mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)

            // below line is use to set our
            // url to our media player.
            try {
                mediaPlayer.setDataSource(audioUrl)
                // below line is use to prepare
                // and start our media player.
                mediaPlayer.prepare()
                mediaPlayer.start()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        fun getPlySound(phonetic: PhoneticModel?) : String{
            if (phonetic != null) {
                return " LYSSNA "
            } else { return  "" }
        }

        fun getInflection(inflectionItems: List<InflectionModel>) : String {
            var ordString: String = ""

            if (inflectionItems.count() > 0) {
                ordString = "〈"
                for (prop in inflectionItems) {
                    ordString += prop.content+", "
                }

                if (inflectionItems.count() == 3) {
                    ordString.substring(ordString.length-2)
                } else if (inflectionItems.count() == 2) {
                    ordString += "-"
                } else {
                    ordString += "-, -"
                }
                ordString +=  "〉"
            }

            return ordString
        }
    }



}