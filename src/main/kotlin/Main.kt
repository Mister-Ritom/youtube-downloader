import me.ritom.downloader.YoutubeDownloader
import java.io.File

fun main(args: Array<String>) {
    println("Hellow mom")
    val downloader = YoutubeDownloader(File("/home/ritom/Downloads/yt-dlp"))
    downloader.python3 = true
    downloader.downloadVideo("E6eHm8Bk0SI")
}