package me.ritom.downloader

import java.io.*
import java.net.HttpURLConnection
import java.net.URL


class YtDlpDownloader {
    private fun downloadFile(log:Boolean,urlLink: String, file: File) {

        try {
            val buffer = ByteArray(1024)
            var downloaded = 0.00
            var readbyte: Int  //Stores the number of bytes written in each iteration.
            var percentOfDownload: Double
            val url = URL(urlLink)
            val http: HttpURLConnection = url.openConnection() as HttpURLConnection
            val input = BufferedInputStream(http.inputStream)
            val ouputFile = FileOutputStream(file)
            val bufferOut = BufferedOutputStream(ouputFile, 1024)
            while (input.read(buffer, 0, 1024).also { readbyte = it } >= 0) {
                //Writing the content onto the file.
                bufferOut.write(buffer, 0, readbyte)
                //TotalDownload is the total bytes written onto the file.
                downloaded += readbyte.toDouble()
                //Calculating the percentage of download.
                percentOfDownload = downloaded * 100 / http.contentLengthLong
                //Formatting the percentage up to 2 decimal points.
                val percent = String.format("%.2f", percentOfDownload)
                if (log)
                    println("Downloaded $percent%")
            }
            if (log)
                println("Your download is now complete.")
            bufferOut.close()
            input.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun downloadYtDlp(path:String):File {
        var file = File(path)
        if (file.isDirectory)file=File("$path/ytdlp")
        val link = "https://github.com/yt-dlp/yt-dlp/releases/latest/download/yt-dlp"
        downloadFile(true,link,file)
        return file
    }

}