package com.example.constun.utils

import android.os.Looper
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream
import java.util.logging.Handler

class UploadRequestBody (
    private val file : File,
            private val contentType : String,
                    private val callback :UploadCallback

) : RequestBody(){

interface UploadCallback{
    fun onProgressUpdate(pecentage:Int)
}
    inner class ProgressUpdade(
        private val uploaded: Long,
        private val total: Long
    ):Runnable{
        override fun run() {
            callback.onProgressUpdate((100*uploaded/total).toInt())
                    }

    }

    override fun contentType()= MediaType.parse("$contentType/*")

    override fun contentLength()= file.length()

    override fun writeTo(sink: BufferedSink) {
        val length = file.length()
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        val fileInputStream = FileInputStream(file)
        var uploaded = 0L

        fileInputStream.use {inputStream ->
            var read :Int
            val handler = android.os.Handler(Looper.getMainLooper())

            while (inputStream.read(buffer).also { read = it } !=-1){
                handler.post(ProgressUpdade(uploaded,length))
                uploaded += read
                sink.write(buffer,0,read)
            }
        }
    }
    companion object{
        private  const val DEFAULT_BUFFER_SIZE = 1048
    }
}