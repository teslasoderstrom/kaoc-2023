import Day01Util.Companion.REPLACEMAP

fun main() {
    fun locateKeyWords(text: String, keyWord: String): Pair<Int, Int>? {
        val first = text.indexOf(keyWord)
        val last = text.lastIndexOf(keyWord)

        if (first == -1 || last == -1) {
            return null
        }
        return Pair(first, last)
    }

    fun replaceNumberTermsAsNumbersInString(text: String): MutableMap<Int, String> {
        val foundIndexMap = mutableMapOf<Int, String>()

        REPLACEMAP.forEach { (numberAsWord, toBeReplacedBy) ->
            val found = locateKeyWords(text, numberAsWord)
            if (found != null) {
                foundIndexMap[found.first] = toBeReplacedBy
                foundIndexMap[found.second] = toBeReplacedBy
            }
//            logger().info { "$text: first: ${foundIndexMap[found?.first]} - second: ${foundIndexMap[found?.second]}" }
        }
//        foundIndexMap.forEach { (k, v) -> logger.info { "Detected numbers for $text $k -> $v" } }
        return foundIndexMap
    }

    fun part1(inputPart1: String): Int {
        val lines: List<String> = FileReader.readFileAsLinesUsingReadLines(inputPart1)
        var sumOverAllLines = 0
        lines.forEach { line ->
            val numsAsString = line.replace(Regex("[^0-9]"), "")

            if (numsAsString.isNotEmpty()) {
                sumOverAllLines += (numsAsString.first().toString() + numsAsString.last().toString()).toInt()
            }
        }
        return sumOverAllLines
    }

    fun part2(input: String): Int {
        val lines: List<String> = FileReader.readFileAsLinesUsingReadLines(input)
        var sumOverAllLines = 0
        lines.forEach { line ->
            // Key: Index -> Value: Number
            val allFindingsForOneLineMap = replaceNumberTermsAsNumbersInString(line)

            sumOverAllLines +=
                (
                    allFindingsForOneLineMap[allFindingsForOneLineMap.keys.sorted().min()].toString() +
                        allFindingsForOneLineMap[allFindingsForOneLineMap.keys.sorted().max()].toString()
                    ).toInt()
        }
        return sumOverAllLines
    }

    val inputPart1Test = "Day01_part1Test"
    check(part1(inputPart1Test) == 142)

    val inputPart1 = "Day01_part1"
    check(part1(inputPart1) == 55002)

    val inputPart2Test = "Day01_part2Test"
    check(part2(inputPart2Test) == 281)

    val inputPart2 = "Day01_part2"
    check(part2(inputPart2) == 55093)
}

class Day01Util {

    companion object {
        val REPLACEMAP = mapOf(
            "zero" to "0",
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9",
            "0" to "0",
            "1" to "1",
            "2" to "2",
            "3" to "3",
            "4" to "4",
            "5" to "5",
            "6" to "6",
            "7" to "7",
            "8" to "8",
            "9" to "9",
        )
    }
}
