import kotlin.math.absoluteValue

/*

 looking for the mirror line not regarding if columns left or above are mirrored also

 */
fun main() {
    fun calculatePart2(text: List<String>): Long {
        return 0L
    }

    fun textToMap(text: List<String>): Map<Int, String> {
        var count = 0
        return text.filter { f -> f.isNotBlank() }.associateBy { count++ }
    }

    fun detectHorizontalMirroredLines(linesMap: Map<Int, String>): Int {
        var count = 0
        var mirrorcount = false
        do {
            var count2 = 0
            do {
                if (mirrorcount && count != count2 && linesMap[count] == linesMap[count2] && (count2 - count).absoluteValue == 1) {
                    return count + 1
                } else if (count != count2 && linesMap[count] != linesMap[count2]) {
                    mirrorcount = true
                }
                count2++
            } while (count2 < linesMap.size)
            count++
        } while (count < linesMap.size)
        return 0
    }

    fun verticalColumn(linesMap: Map<Int, String>, column: Int): String {
        var col = ""
        var count = 0
        do {
            col += linesMap[count]!!.substring(column, column + 1)
        } while (count++ < linesMap.size - 1)
        return col
    }

    fun detectVerticalMirroredLines(linesMap: Map<Int, String>): Int {
        var count = 0
        do {
            var count2 = 0
            val verticalColumn1 = verticalColumn(linesMap, count)
            do {
                val verticalColumn2 = verticalColumn(linesMap, count2)
//                println("verticalColumn1: $verticalColumn1 verticalColumn2: $verticalColumn2")

                if (count != count2 && verticalColumn1 == verticalColumn2 && (count2 - count).absoluteValue == 1
                ) {
                    return (count + 1)
                }
            } while (count2++ < linesMap[0]!!.length - 1)
        } while (count++ < linesMap[0]!!.length - 1)
        return 0
    }

    fun part1(text: List<String>): Int {
        var rsult = 0
        text.forEach { t ->
            val linesMap = textToMap(t.split("\n"))
            if (linesMap.isNotEmpty()) {
                val mirrorLine = detectHorizontalMirroredLines(linesMap)
                val vLine = detectVerticalMirroredLines(linesMap)
                rsult += vLine * 100 + mirrorLine
                println("mirrorLine: $mirrorLine verticalLine: $vLine Result: ${vLine * 100 + mirrorLine}")
            }
        }

        return rsult
    }

    fun part2(text: List<String>): Long {
        return calculatePart2(text)
    }

    var inputPart1Test = FileReader.readFileDirectlyAsText("Day13_part1Test")
    var matrix = inputPart1Test.split("\n\n")
    var p1T = part1(matrix)
    println("p1T: $p1T")
    check(p1T == 807)

    inputPart1Test = FileReader.readFileDirectlyAsText("Day13_part1")
    matrix = inputPart1Test.split("\n\n")
    p1T = part1(matrix)
    println("p1T: $p1T")
    check(p1T == 39732)

//    val inputPart2Test = FileReader.readFileAsLinesUsingReadLines("Day09_part2Test")
//    val p2T = part2(inputPart1Test)
//    println(p1T)
// //    check(part2(inputPart2Test) == 6)
//
//    val inputPart2 = FileReader.readFileAsLinesUsingReadLines("Day09_part2")
//    val p2 = part2(inputPart1Test)
//    println(p1T)
//    check(part2(inputPart2) == 36749103)
}
