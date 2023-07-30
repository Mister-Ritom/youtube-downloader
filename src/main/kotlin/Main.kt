import me.ritom.downloader.YoutubeDownloader

fun main(args: Array<String>) {
    println("Hellow mom")
    val downloader = YoutubeDownloader()
    downloader.python3 = true
    downloader.downloadVideo("E6eHm8Bk0SI")
}