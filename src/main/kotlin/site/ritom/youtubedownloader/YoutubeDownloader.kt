package site.ritom.youtubedownloader

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader


@Suppress("unused")
class YoutubeDownloader(var ytDlp:File, var outputDirectory: String, var log:Boolean=true, var commands:MutableList<String> = emptyList<String>().toMutableList(), var python3:Boolean=false) {

    private fun log(s:String?) {
        if (log&&s!=null){
            println(s)
        }
    }

    fun verbose(): YoutubeDownloader {
        commands.add("--verbose")
        return this
    }

    fun audioOnly(mp3:Boolean=true): YoutubeDownloader {
        commands.add("--extract-audio")
        if (mp3) {
            commands.add("--audio-format")
            commands.add("mp3")
        }
        return this
    }

    fun videoOnly(): YoutubeDownloader {
        commands.remove("--extract-audio")
        commands.remove("--audio-format")
        commands.remove("mp3")
        return this
    }

    private fun getRunnable():String {
        return if (python3) "python3"
        else "python"
    }

    private fun buildProcess(url:String):ProcessBuilder? {
        if (!ytDlp.isFile){
            println("$ytDlp is not a file.Use a ytdlp file")
            return null
        }
        var directory = File(outputDirectory)
        if (!directory.isDirectory){
            println("$outputDirectory is a file. Use a directory path")
            return null
        }
        var directoryExists = directory.exists()
        if (!directoryExists) {
            if (directory.mkdirs()) {
                directoryExists=true
            }
            else {
                log("Creating downloads directory failed. saving to current directory")
                val currentDirectory = System.getProperty("user.dir")
                directory = File(currentDirectory)
                directoryExists=directory.canWrite()&&directory.canRead()
            }
        }
        return if (directoryExists) {
            val builder = ProcessBuilder(getRunnable(),
                ytDlp.absolutePath,
                url).redirectErrorStream(true).directory(directory)
            builder.command().addAll(commands)
            builder
        } else {
            log("Can not write to directory $directory. Exiting process")
            null
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
        log("Youtube download succeeded")
    }
    fun downloadSingle(id:String) {
        log("Downloading video $id to $outputDirectory")
        val videoUrl = if(id.startsWith("https")) id else "https://www.youtube.com/watch?v=$id"
        try {
            val processBuilder = buildProcess(videoUrl)
            if (processBuilder!=null) {
                val process = processBuilder.start()
                runProcess(process)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun downloadPlaylist(id:String) {
        log("Downloading playList $id to $outputDirectory")
        val playlistUrl = if(id.startsWith("https"))id else "https://www.youtube.com/playlist?list=$id"
        try {
            val processBuilder = buildProcess(playlistUrl)
            if (processBuilder!=null) {
                val process = processBuilder.start()
                runProcess(process)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}