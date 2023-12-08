import java.util.*

fun main() {
    fun getPositionInMap(lookup: Long, seedMap: List<GeneralSeedMap>): Long {
        val x = seedMap.firstOrNull { f -> lookup in f.source until f.source + f.range }
        return if (x != null) {
            lookup - x.source + x.target
        } else {
            lookup
        }
    }

    fun parseSeedMap(mapText: String): List<Long> {
        val x = mapText.substring(mapText.indexOf(':') + 2, mapText.length)
            .split(" ").map { it.toLong() }
        return x
    }

    fun parseOneMap(mapText: String): MutableList<GeneralSeedMap> {
        val seedList = mutableListOf<GeneralSeedMap>()
        mapText.substring(mapText.indexOf(':') + 2, mapText.length)
            .split("\n")
            .filter { it.isNotEmpty() }
            .forEach { subLine ->
                subLine.split(" ").apply {
                    seedList.add(GeneralSeedMap(this[0].toLong(), this[1].toLong(), this[2].toLong()))
                }
            }
        return seedList
    }

    fun calculatePart1(mapText: String): Long {
        val seedToLocationMaps: SortedMap<Int, MutableList<GeneralSeedMap>> = TreeMap()
        val rawTextMapList = mapText.split("\n\n")
        var ind = 0
        val seedList = parseSeedMap(rawTextMapList[ind++])

        while (ind < rawTextMapList.size) {
            seedToLocationMaps[ind] = parseOneMap(rawTextMapList[ind++])
        }

        val loc = mutableListOf<Long>()
        seedList.forEach { seed ->
            ind = 1
            getPositionInMap(seed, seedToLocationMaps[ind++]!!.toList())
                .also {
                    getPositionInMap(it, seedToLocationMaps[ind++]!!.toList())
                        .also {
                            getPositionInMap(it, seedToLocationMaps[ind++]!!.toList()).also {
                                getPositionInMap(it, seedToLocationMaps[ind++]!!.toList())
                                    .also {
                                        getPositionInMap(it, seedToLocationMaps[ind++]!!.toList())
                                            .also {
                                                getPositionInMap(it, seedToLocationMaps[ind++]!!.toList())
                                                    .also {
                                                        loc.add(
                                                            getPositionInMap(
                                                                it,
                                                                seedToLocationMaps[ind++]!!.toList(),
                                                            ),
                                                        )
                                                    }
                                            }
                                    }
                            }
                        }
                }

//            loc.forEach { logger.info { "Location $it" } }
        }
        return loc.min()
    }

    fun calculatePart2(mapText: String): Long {
        val mapList = mapText.split("\n\n")
        val (seeds, seedSoil, soilFert, fertwater, waterLight, lightTemp, tempHum, humLoc) = mapList
        val seedList = parseSeedMap(seeds)
        val seedSoilMap = parseOneMap(seedSoil)
        val soilFertMap = parseOneMap(soilFert)
        val fertwaterMap = parseOneMap(fertwater)
        val waterLightMap = parseOneMap(waterLight)
        val lightTempMap = parseOneMap(lightTemp)
        val tempHumMap = parseOneMap(tempHum)
        val humLocMap = parseOneMap(humLoc)

        var minLoc = 1000000000L
        var seedBegin: Long = 0
        while (seedBegin < seedList.size) {
            var seed = seedList[seedBegin.toInt()]
            val rangeEnd = seedList[seedBegin.toInt()] + seedList[seedBegin.toInt() + 1]
            while (seed < rangeEnd) {
                val a = getPositionInMap(seed, seedSoilMap)
                val b = getPositionInMap(a, soilFertMap)
                val c = getPositionInMap(b, fertwaterMap)
                val d = getPositionInMap(c, waterLightMap)
                val e = getPositionInMap(d, lightTempMap)
                val f = getPositionInMap(e, tempHumMap)
                val loc = getPositionInMap(f, humLocMap)
                if (loc < minLoc) {
                    minLoc = loc
                }
                seed++
            }
            seedBegin++
            seedBegin++
        }
        return minLoc
    }

    val inputPart1Test = FileReader.readFileDirectlyAsText("Day05_part1Test")
    check(calculatePart1(inputPart1Test) == 35L)

    val inputPart1 = FileReader.readFileDirectlyAsText("Day05_part1")
    check(calculatePart1(inputPart1) == 525792406L)

    check(calculatePart2(inputPart1Test) == 46L)

    check(calculatePart2(inputPart1) == 79004094L)
}

data class GeneralSeedMap(
    val target: Long,
    val source: Long,
    val range: Long,
)

operator fun <T> List<T>.component6(): T {
    return get(5)
}

operator fun <T> List<T>.component7(): T {
    return get(6)
}

operator fun <T> List<T>.component8(): T {
    return get(7)
}
