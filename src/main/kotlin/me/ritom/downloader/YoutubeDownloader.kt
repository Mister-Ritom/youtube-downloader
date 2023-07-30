package me.ritom.downloader

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.*
import kotlin.system.exitProcess


@Suppress("unused")
class YoutubeDownloader(var log:Boolean=true, var outputDirectory: String="", var commands:MutableList<String> = emptyList<String>().toMutableList(), var python3:Boolean=false) {

    init {
        if (outputDirectory.isBlank())outputDirectory=getDefaultDownloads()
    }
    
    private fun log(s:String?) {
        if (log&&s!=null){
            println(s)
        }
    }
    
    fun downloadVideo(id:String) {
        log("Downloading video $id to $outputDirectory")
        val videoUrl = if(id.startsWith("https")) id else "https://www.youtube.com/watch?v=$id"
        try {
            val processBuilder = buildProcess(videoUrl)

            val process = processBuilder.start()
            runProcess(process)

            log("YouTube to MP3 conversion complete!")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun verbose() {
        commands.add("--verbose")
    }

    fun audioOnly(mp3:Boolean=true) {
        commands.add("--extract-audio")
        if (mp3) {
            commands.add("--audio-format")
            commands.add("mp3")
        }
    }

    private fun getRunnable():String {
        return if (python3) "python3"
        else "python"
    }

    private fun buildProcess(url:String):ProcessBuilder {
        var directory = File(outputDirectory)
        var directoryExists = directory.exists()
        if (!directoryExists) {
            if (directory.mkdirs()) {
                directoryExists=true
            }
            else {
                log("Creating downloads directory failed. saving to current directory")
                val currentDirectory = System.getProperty("user.dir");
                directory = File(currentDirectory)
                directoryExists=directory.canWrite()&&directory.canRead()
            }
        }
        if (directoryExists) {
            val builder = ProcessBuilder(getRunnable(),
                "/home/ritom/Downloads/yt-dlp",
                url).redirectErrorStream(true).directory(directory)
            builder.command().addAll(commands)
            return builder
        }
        else {
            log("Can not write to directory $directory. Exiting process")
            exitProcess(1)
        }
    }

    private fun runProcess(process:Process) {

        // Get the process output stream

        // Get the process output stream
        val inputStream = process.inputStream
        val reader = BufferedReader(InputStreamReader(inputStream))

        // Read the output

        // Read the output
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            log(line)
        }

        // Wait for the process to complete

        // Wait for the process to complete
        val exitCode: Int = process.waitFor()
        log("Process exited with code: $exitCode")
    }

    fun downloadPlaylist(id:String) {
        log("Downloading playList $id to $outputDirectory")
        val playlistUrl = if(id.startsWith("https"))id else "https://www.youtube.com/playlist?list=$id"
        try {
            val processBuilder = buildProcess(playlistUrl)
            log("Running ${processBuilder.command().joinToString(" ")}")
            val process = processBuilder.start()
            runProcess(process)
            log("YouTube to MP3 conversion complete!")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getDefaultDownloads(): String {
        val os = System.getProperty("os.name").lowercase(Locale.getDefault())

        return when {
            os.contains("win") -> {
                // On Windows, the "Music" folder is usually under the user's profile directory
                val userProfile = System.getenv("USERPROFILE") ?: ""
                "$userProfile\\Downloads\\Youtube"
            }
            os.contains("mac") || os.contains("nix") || os.contains("nux") -> {
                // On macOS and Linux, the "Music" folder is under the user's home directory
                val userHome = System.getProperty("user.home") ?: ""
                "$userHome/Downloads/Youtube"
            }
            else -> {
                "/Youtube"
            }
        }
    }

}