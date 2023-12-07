import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

class FileReader {

    companion object {

        fun readFileAsLinesUsingReadLines(fileName: String): List<String> = File("src/$fileName.txt").readLines()
        fun readFileDirectlyAsText(fileName: String): String = File("src/$fileName.txt").readText(Charsets.UTF_8)
    }
}
