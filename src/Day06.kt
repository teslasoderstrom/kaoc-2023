fun main() {
    fun distanceCompleted(buttonHold: Int, raceTimeMax: Long) = (raceTimeMax - buttonHold) * buttonHold

    fun distanceByRaceOptions(race: Race, buttonStartDown: Int): Int {
        var raceOptions = 0
        var buttonDown = buttonStartDown
        while (buttonDown < race.time) {
            val distance = distanceCompleted(buttonDown++, race.time)
            if (distance > race.distance) {
                raceOptions++
            }
        }
        return raceOptions
    }

    fun calculatePart1(rawTexts: List<String>, buttonStartDown: Int): Long {
        val (times, distances) = parseTextWithTitleAndSeparator(rawTexts, ":")
        var races = 1
        for (time in times) {
            races *= distanceByRaceOptions(Race(time, distances[times.indexOf(time)]), buttonStartDown)
        }
        return races.toLong()
    }

    fun part1(text: List<String>, buttonStartDown: Int): Long {
        return calculatePart1(text, buttonStartDown)
    }

    val inputPart1Test = FileReader.readFileAsLinesUsingReadLines("Day06_part1Test")
    check(part1(inputPart1Test, 1) == 288L)

    val inputPart1 = FileReader.readFileAsLinesUsingReadLines("Day06_part1")
    check(part1(inputPart1, 1) == 1312850L)

    val inputPart2Test = FileReader.readFileAsLinesUsingReadLines("Day06_part2Test")
    check(part1(inputPart2Test, 14) == 71503L)

    val inputPart2 = FileReader.readFileAsLinesUsingReadLines("Day06_part2")
    check(part1(inputPart2, 14) == 36749103L)
}

data class Race(
    val time: Long,
    val distance: Long,
)
