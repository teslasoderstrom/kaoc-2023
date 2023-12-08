import kotlin.math.pow

fun main() {
    fun parsedCardSet(cards: String) =
        cards.trim().split(" ").map { s -> s.trim() }.filter { s -> s.isNotBlank() }.map { s -> s.toInt() }

    fun calculatePart1(text: List<String>): Int {
        var sum = 0.0
        text.forEach { line ->
            val cards = line.substring(line.indexOf(':') + 2, line.length)
                .split("|")

            val winningCards = parsedCardSet(cards[0])
            val myCards = parsedCardSet(cards[1])

            val intersect = (myCards.intersect(winningCards.toSet()).size)

            if (intersect > 0) {
                sum += 2.0.pow(intersect - 1)
            }
        }
        return sum.toInt()
    }

    fun part1(text: List<String>): Int {
        return calculatePart1(text)
    }

    val inputPart1Test = FileReader.readFileAsLinesUsingReadLines("Day04_part1Test")
    check(part1(inputPart1Test) == 13)

    val inputPart1 = FileReader.readFileAsLinesUsingReadLines("Day04_part1")
    check(part1(inputPart1) == 26914)
}
