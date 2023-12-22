fun main() {
    var matrix = emptyList<String>()
    var counter = 1

    fun isReachableGardenPlot(position: Coordinate) : Boolean  {
        val p = matrix[position.posY][position.posX]

        println("isReachableGardenPlot $position = ${p!='#'}")

        return p != '#'
    }

    fun lookingForStart(text: List<String>): List<Coordinate> {
        var position = Coordinate(0,0)
        text.forEach { l ->
            if (l.any { c -> c == 'S' }) {
                val line = text.indexOf(l)
                val column = text[line].indexOf("S")
                position = Coordinate(line, column)
            }
        }
        return listOf(position)
    }

    fun findValidSorroundingForPosition(position: Coordinate): List<Coordinate> {
        val positions = mutableListOf<Coordinate>()

        if (position.posY - 1 >= 0) {
            val north = Coordinate(posX = position.posX, posY = position.posY - 1)
            if (isReachableGardenPlot(north)) {
                positions.add(north)
            }
        } else {
            println("skipped north $position")
        }

        if (position.posY + 1 < matrix.size) {
            val south = Coordinate(posX = position.posX, posY = position.posY + 1)
            if (isReachableGardenPlot(south)) {
                positions.add(south)
            }
        } else {
            println("skipped south $position")
        }

        if (position.posX + 1 < matrix[0].length) {
            val east = Coordinate(posX = position.posX + 1, posY = position.posY)
            if (isReachableGardenPlot(east)) {
                positions.add(east)
            }
        } else {
            println("skipped east $position")
        }

        if (position.posX - 1 >= 0) {
            val west = Coordinate(posX = position.posX - 1, posY = position.posY)
            if (isReachableGardenPlot(west)) {
                positions.add(west)
            }
        } else {
            println("skipped west $position")
        }
        println("findValidSorroundingForPosition: $positions")
        return positions
    }

    fun findValidSorroundings(positions: List<Coordinate>): List<Coordinate> {
        val res: MutableList<Coordinate> = emptyList<Coordinate>().toMutableList()
        positions.forEach { p ->
            res.addAll(findValidSorroundingForPosition(p))
        }
        counter++
        println("findValidSorroundings: $res")
        return res
    }

    fun walkThrough(): Int {
        var occurences = lookingForStart(matrix)

        do {
            occurences = findValidSorroundings(occurences)
        } while (counter++ < 7)
        return occurences.distinct().size
    }

    fun part1(): Int {
        return walkThrough()
    }

    matrix = FileReader.readFileAsLinesUsingReadLines("Day21_part1Test")

    var sumOfAccepted = part1()
    println(sumOfAccepted)
    check(sumOfAccepted == 16)

//    inputPart1Test = FileReader.readFileAsLinesUsingReadLines("Day21_part1")
//    sumOfAccepted = part1(inputPart1Test)

//    println(sumOfAccepted)
}

data class Coordinate(
    val posX: Int,
    val posY: Int,
)
