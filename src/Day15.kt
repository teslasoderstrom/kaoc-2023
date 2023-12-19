/*

 looking for the mirror line not regarding if columns left or above are mirrored also

 */
fun main() {
    fun calculatePart2(text: List<String>): Long {
        return 0L
    }

    fun calculate(split: String): Int {
        var res = 0

        split.trim().toCharArray().forEach { c ->
            res = ((res + c.code) * 17).mod(256)
//            println(res)
        }
//        println(res)
        return res
    }

    fun part1(text: List<String>): Int {
        var rsult = 0
        text.filter { f -> f.isNotEmpty() }
            .forEach { t ->
                rsult += calculate(t)
                println("%$t%")
            }
        println(rsult)
        return rsult
    }

    fun part2(text: List<String>): Long {
        return calculatePart2(text)
    }

    var inputPart1Test = FileReader.readFileDirectlyAsText("Day15_part1Test")
    var text = inputPart1Test.split(",")
    var p1T = part1(text)
    println("p1T: $p1T")
    check(p1T == 1320)

    inputPart1Test = FileReader.readFileDirectlyAsText("Day15_part1")
    text = inputPart1Test.split(",")
    val p1 = part1(text)
    println("p1: $p1")
    check(p1 == 501680) // wrong
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
