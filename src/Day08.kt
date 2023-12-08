fun main() {
    fun buildUpNetwork(text: List<String>): Network {
        var line = 0
        val instructions = text[line++]

        val networkMap: MutableMap<String, Pair<String, String>> = mutableMapOf()

        while (line < text.size) {
            if (text[line].isNotEmpty()) {
                networkMap[text[line].substring(0, 3)] = Pair(text[line].substring(7, 10), text[line].substring(12, 15))
            }
            line++
        }
        return Network(instructions, networkMap)
    }

    fun navigate(network: Network): Int {
        var instructions = 0
        var step = "AAA"
        var stepCounter = 0
        while (step != "ZZZ") {
            val currentStep = network.instructions[instructions++]
            when (currentStep) {
                'R' -> step = network.networkMap[step]!!.second
                'L' -> step = network.networkMap[step]!!.first
            }
            if (instructions >= network.instructions.length) {
                instructions = 0
            }
            stepCounter++
        }
        return stepCounter
    }

    fun calculatePart1(text: List<String>): Int {
        val network = buildUpNetwork(text)
        val steps = navigate(network)

        return steps
    }

    fun part1(text: List<String>): Int {
        return calculatePart1(text)
    }

    val inputPart1Test = FileReader.readFileAsLinesUsingReadLines("Day08_part1Test")
    check(part1(inputPart1Test) == 2)

    val inputPart1 = FileReader.readFileAsLinesUsingReadLines("Day08_part1")
    check(part1(inputPart1) == 13207)

    val inputPart2Test = FileReader.readFileAsLinesUsingReadLines("Day08_part2Test")
    check(part1(inputPart2Test) == 71503)

    val inputPart2 = FileReader.readFileAsLinesUsingReadLines("Day08_part2")
    check(part1(inputPart2) == 36749103)
}

data class Network(
    val instructions: String,
    val networkMap: MutableMap<String, Pair<String, String>>,
)
