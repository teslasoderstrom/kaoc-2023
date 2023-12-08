fun main() {
    fun whichLimitIsvalidForColor(color: String, anzahl: Int): Boolean {
        when (color) {
            "blue" -> return anzahl < 15
            "green" -> return anzahl < 14
            "red" -> return anzahl < 13
        }
        return false
    }

    fun maxAnzahlOfCubeByColor(color: String, cubes: List<GameDto.Cube>): Int {
        return cubes
            .filter { c -> c.color == color }
            .map { c -> c.anzahl }.toList().max()
    }

    fun detectMaximumCubesNeededAtLeast(games: List<GameDto>): Int {
        var sumOverMinCubes = 0
        games.forEach { game ->
            val blue = maxAnzahlOfCubeByColor("blue", game.cubes)
            val green = maxAnzahlOfCubeByColor("green", game.cubes)
            val red = maxAnzahlOfCubeByColor("red", game.cubes)
            sumOverMinCubes += blue * green * red
        }
        return sumOverMinCubes
    }

    fun parseGame(gameId: Int, text: String): GameDto {
        val gameCubeSet = mutableListOf<GameDto.Cube>()
        val cubes = mutableListOf<GameDto.Cube>()
        text.split(";") // ["3 blue, 4 red"],["1 red, 2 green, 6 blue"]
            .forEach { cubeSetRaw ->

                cubeSetRaw.split(",")
                    .forEach { c ->
                        c.trimStart()
                            .split(" ")
                            .apply {
                                cubes.add(GameDto.Cube(color = get(1), anzahl = get(0).toInt()))
                            }
                    }
            }
        return GameDto(gameId, cubes)
    }

    fun parseAllGames(allGamesAsText: List<String>): List<GameDto> {
        val games = mutableListOf<GameDto>()

        allGamesAsText.forEach { rawGame ->
            val line = rawGame.split(":")
            val gameId = line[0].replace("Game ", "").toInt()
            val game = parseGame(gameId, line[1])
            games.add(game)
        }
        return games
    }

    fun scanCubeIfTheyExceedThelimit(games: List<GameDto>): List<Int> {
        val resultNegativList = mutableListOf<Int>()
        games
            .forEach { game ->
                val s = game.cubes
                    .filter { cube -> whichLimitIsvalidForColor(cube.color, cube.anzahl) }
                    .size
                if (s < game.cubes.size) {
                    resultNegativList.add(game.id)
                } else {
                }
            }
        return resultNegativList
    }

    fun part1(text: List<String>): Int {
        val games = parseAllGames(text)
        val invalidGames = scanCubeIfTheyExceedThelimit(games)
        return games.map { g -> g.id }.toList().minus(invalidGames.toSet()).sum()
    }

    fun part2(text: List<String>): Int {
        val games = parseAllGames(text)
        return detectMaximumCubesNeededAtLeast(games)
    }

    val inputPart1Test = FileReader.readFileAsLinesUsingReadLines("Day02_part1Test")
    check(part1(inputPart1Test) == 8)

    val inputPart1 = FileReader.readFileAsLinesUsingReadLines("Day02_part1")
    check(part1(inputPart1) == 2600)

    check(part2(inputPart1Test) == 2286)

    check(part2(inputPart1) == 86036)
}

data class GameDto(
    val id: Int,
    val cubes: MutableList<Cube>,

) {
    data class Cube(
        val color: String,
        val anzahl: Int,
    )
}
