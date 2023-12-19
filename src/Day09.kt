fun main() {
    fun differencesBetweenNumbers(numbers: MutableList<Long>) = numbers.windowed(2) { (a, b) -> b - a }.toMutableList()

    fun continueHistory(numbersList: MutableList<Long>): Long {
        var actualNumbers = numbersList
        val numbers: MutableList<Long> = mutableListOf()
        do {
            actualNumbers = differencesBetweenNumbers(actualNumbers)
            numbers.add(actualNumbers.last())
            println(actualNumbers)
        } while (actualNumbers.sum() != 0L)

        numbers.sum() + numbersList.last()
//            println(lastElement)

        return numbers.sum() + numbersList.last()
    }

    fun calculatePart1(numbersLists: MutableList<MutableList<Long>>): Long {
        var continueHistory = 0L
        numbersLists.forEach { n ->
            continueHistory += continueHistory(n)
            println("continueHistory: $continueHistory")
        }
        println(continueHistory)
        return continueHistory
    }

    fun calculatePart2(text: List<String>): Long {
        return 0L
    }

    fun part1(text: List<String>): Long {
        val numberLists = separateStringByEmptyStringToLong(text)
        return calculatePart1(numberLists)
    }

    fun part2(text: List<String>): Long {
        return calculatePart2(text)
    }

    val inputPart1Test = FileReader.readFileAsLinesUsingReadLines("Day09_part1Test")
    val p1T = part1(inputPart1Test)
    println("p1T: $p1T")
    check(part1(inputPart1Test) == 114L)

    val inputPart1 = FileReader.readFileAsLinesUsingReadLines("Day09_part1")
    val p1 = part1(inputPart1)
    println("p1: $p1")
//    check(part1(inputPart1) == 1708206166)

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
