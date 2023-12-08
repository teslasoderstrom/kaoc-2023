fun main() {
    fun isAssignedToStar(x: Int, y: Int, length: Int, occurences: List<Occurence>): Pair<Int, Int>? {
        for (lin in y - 1..y + 1) {
            if (lin >= 0) {
                for (col in x - 1..x + length) {
                    if (col >= 0) {
                        val isAssigned = occurences
                            .filter { o -> o.specialChar == '*' }
                            .filter { o -> o.posX == col }.any { o -> o.posY == lin }
                        if (isAssigned) {
                            return Pair(col, lin)
                        }
                    }
                }
            }
        }
        return null
    }

    fun calculatePartNumbersIfAssignedToStar(occurences: List<Occurence>): MutableMap<Pair<Int, Int>, MutableList<Int>> {
        val starAssignedMap: MutableMap<Pair<Int, Int>, MutableList<Int>> = mutableMapOf()

        occurences
            .filter { occurence -> occurence.specialChar == null }
            .also { ocs ->
                ocs.map { occurence ->
                    val numberIsAssignedToStar = isAssignedToStar(
                        occurence.posX,
                        occurence.posY,
                        occurence.number.toString().length,
                        occurences,
                    )
                    if (numberIsAssignedToStar != null) {
                        if (starAssignedMap.containsKey(numberIsAssignedToStar)) {
                            val occList = starAssignedMap[numberIsAssignedToStar]
                            occList!!.add(occurence.number!!)
                            starAssignedMap[numberIsAssignedToStar] = occList
                        } else {
                            starAssignedMap[numberIsAssignedToStar] = mutableListOf(occurence.number!!)
                        }
                    }
                }
            }
        return starAssignedMap
    }

    fun isAssignedToMotor(x: Int, y: Int, length: Int, occurences: List<Occurence>): Boolean {
        for (lin in y - 1..y + 1) {
            if (lin >= 0) {
                for (col in x - 1..x + length) {
                    if (col >= 0) {
                        val isAssigned = occurences
                            .filter { o -> o.specialChar != null }
                            .filter { o -> o.posX == col }.any { o -> o.posY == lin }
                        if (isAssigned) {
                            return true
                        }
                    }
                }
            }
        }
        return false
    }

    fun calculatePartNumbersIfAssignedToMotor(occurences: List<Occurence>): Int {
        return occurences
            .filter { occurence -> occurence.specialChar == null }
            .filter { occurence ->
                isAssignedToMotor(
                    occurence.posX,
                    occurence.posY,
                    occurence.number.toString().length,
                    occurences,
                )
            }
            .sumOf { occurence -> occurence.number!! }
    }

    fun buildTheMatrix(text: List<String>): MutableList<Occurence> {
        var nummer = ""
        var lineNumber = 0
        val occurences = mutableListOf<Occurence>()
        text.forEach { line ->
            // letzte Zahl vor dem Zeilenende muss gerettet werden
            if (nummer != "") {
                occurences.add(Occurence(line.length - nummer.length, lineNumber, nummer.toInt()))
            }
            lineNumber++
            nummer = ""
            var posX = 0
            line.toCharArray().forEach { zeichen ->
                if (zeichen.isDigit()) {
                    nummer += zeichen
                } else if (!zeichen.isDigit()) {
                    if (nummer != "") {
                        occurences.add(Occurence(posX - nummer.length, lineNumber, nummer.toInt()))
                        nummer = ""
                    }
                    if (zeichen != '.') {
                        occurences.add(Occurence(posX, lineNumber, null, zeichen))
                    }
                }
                posX++
            }
        }
        return occurences
    }

    fun calculatePart2(text: List<String>): Int {
        val matrix = buildTheMatrix(text)

        val starAssignedMap = calculatePartNumbersIfAssignedToStar(matrix)

        return starAssignedMap
            .filter { f -> f.value.size == 2 }
            .map { star ->
                star.value[0] * star.value[1]
            }.sum()
    }

    fun part1(text: List<String>): Int {
        return calculatePartNumbersIfAssignedToMotor(buildTheMatrix(text))
    }

    fun part2(text: List<String>): Int {
        return calculatePart2(text)
    }

    val inputPart1Test = FileReader.readFileAsLinesUsingReadLines("Day03_part1Test")
    check(part1(inputPart1Test) == 4361)

    val inputPart1 = FileReader.readFileAsLinesUsingReadLines("Day03_part1")
    check(part1(inputPart1) == 530495)

    check(part2(inputPart1Test) == 467835)

    check(part2(inputPart1) == 80253814)
}

data class Occurence(
    val posX: Int,
    val posY: Int,
    val number: Int? = null,
    val specialChar: Char? = null,
)
