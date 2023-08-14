import site.ritom.youtubedownloader.YoutubeDownloader
import site.ritom.youtubedownloader.YtDlpDownloader
import java.util.*

fun main(args: Array<String>) {
    println("Hellow mom")
    val ytDlpDownloader = YtDlpDownloader()
    val downloadFolder = getDefaultDownloads()
    val ytDlp = ytDlpDownloader.downloadYtDlp(downloadFolder)
    val youtubeDownloader = YoutubeDownloader(ytDlp,downloadFolder)

}

private fun downloadAudioSingle(youtubeDownloader: YoutubeDownloader) {
    //AUDIO
    youtubeDownloader.audioOnly()
    youtubeDownloader.downloadPlaylist("https://www.youtube.com/watch?v=_-vsHpF4sZo&list=RDGMEMHDXYb1_DDSgDsobPsOFxpAVM_-vsHpF4sZo&start_radio=1")
}

private fun downloadVideoSingle(youtubeDownloader: YoutubeDownloader) {
    //AUDIO
    youtubeDownloader.videoOnly()
    youtubeDownloader.downloadPlaylist("https://www.youtube.com/watch?v=_-vsHpF4sZo&list=RDGMEMHDXYb1_DDSgDsobPsOFxpAVM_-vsHpF4sZo&start_radio=1")
}

private fun downloadAudioPlaylist(youtubeDownloader: YoutubeDownloader) {
    //AUDIO
    youtubeDownloader.audioOnly()
    youtubeDownloader.downloadPlaylist("https://www.youtube.com/watch?v=_-vsHpF4sZo&list=RDGMEMHDXYb1_DDSgDsobPsOFxpAVM_-vsHpF4sZo&start_radio=1")
}

private fun downloadVideoPlaylist(youtubeDownloader: YoutubeDownloader) {
    //AUDIO
    youtubeDownloader.videoOnly()
    youtubeDownloader.downloadPlaylist("https://www.youtube.com/watch?v=_-vsHpF4sZo&list=RDGMEMHDXYb1_DDSgDsobPsOFxpAVM_-vsHpF4sZo&start_radio=1")
}

private fun getDefaultDownloads(): String {
    val os = System.getProperty("os.name").lowercase(Locale.getDefault())

    return when {
        os.contains("win") -> {
            val userProfile = System.getenv("USERPROFILE") ?: ""
            "$userProfile\\Downloads\\Youtube"
        }
        os.contains("mac") || os.contains("nix") || os.contains("nux") -> {
            val userHome = System.getProperty("user.home") ?: ""
            "$userHome/Downloads/Youtube"
        }
        else -> {
            "/Youtube"
        }
    }
}