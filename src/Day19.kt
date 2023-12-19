import org.springframework.expression.Expression
import org.springframework.expression.ExpressionParser
import org.springframework.expression.spel.standard.SpelExpressionParser

fun main() {
    val expressionParser: ExpressionParser = SpelExpressionParser()
    var wflows: Map<String, List<String>> = emptyMap()

    fun parseExpression(express: String): Boolean {
        val expression: Expression = expressionParser.parseExpression(express)
//        println("parseExpression: $express -> ${expression.value}")
        return expression.value as Boolean
    }

    fun calculateExpression(rating: MutableMap<String, Int>): Long {
        var result = ""
        var sumOfAccepted = 0L

        val initialWFlow = "in"
        var flowKey = initialWFlow
        var counter = 0
        var flowStep = ""
        do {
            flowStep = wflows[flowKey]!![counter++]

            var ind = flowStep.indexOf(":")

            println("-----")
            if (ind >= 0) {
                val workFlowCondition = flowStep.substring(0, ind)
                val workFlowResult = flowStep.substring(flowStep.indexOf(":") + 1, flowStep.length)
                val placeholderRemoved = workFlowCondition.replace(
                    workFlowCondition.substring(0, 1),
                    rating[workFlowCondition.substring(0, 1)].toString(),
                )
                val parsed = parseExpression(placeholderRemoved)
                if (parsed) {
                    flowKey = workFlowResult
                    counter = 0
                }
                println("workFlowCondition: $workFlowCondition ($placeholderRemoved) -> $parsed")
                println("flowKey by cond: $flowKey -> ${wflows[flowKey]} / counter: $counter")
            } else {
                counter = 0
                println("flowKey direct: $flowKey -> ${wflows[flowKey]!![counter]}")
                println("flowKey new: $flowKey -> ${wflows[flowKey]!![counter]}")
                flowKey = flowStep.substring(flowStep.indexOf(":") + 1, flowStep.length)
            }
        } while (flowKey != "A" && flowKey != "R" && counter < wflows[flowKey]!!.size)
        if (flowKey == "A") {
            sumOfAccepted += rating.values.sum().toLong()
//            println("Accepted $rating = $sumOfAccepted")
        }
        return sumOfAccepted
    }

    fun rate(text: String): Long {
        var sumOfAccepted = 0L
        val (_, ratingsText) = text.split("\n\n")

        val allRatings = mutableListOf<Map<String, Int>>()
        ratingsText.trim().split("\n")
            .forEach { r ->
                val rating = mutableMapOf<String, Int>()
                val s = r.substring(1, r.length - 1).split(",")
                s.forEach { rr ->
                    val k = rr.substring(0, 1)
                    val v = rr.substring(rr.indexOf("=") + 1, rr.length).toInt()
                    rating[k] = v
                }
                allRatings.add(rating)

                sumOfAccepted += calculateExpression(rating)
            }

        return sumOfAccepted
    }

    fun parseWorkflows(text: String): Map<String, List<String>> {
        val (workflows, _) = text.split("\n\n")

        val workflowMap = mutableMapOf<String, List<String>>()
        workflows.split("\n").forEach { workflow ->
            workflowMap[workflow.substring(0, workflow.indexOf("{"))] =
                workflow.substring(workflow.indexOf("{") + 1, workflow.indexOf("}")).split(",").toList()
        }
//        workflowMap.forEach { w -> println("Workflow $w") }
        return workflowMap
    }

    fun part1(text: String): Long {
        wflows = parseWorkflows(text)
        return rate(text)
    }

    var inputPart1Test = FileReader.readFileDirectlyAsText("Day19_part1Test")
    var sumOfAccepted = part1(inputPart1Test)
    println(sumOfAccepted)
    check(sumOfAccepted == 19114L)

    inputPart1Test = FileReader.readFileDirectlyAsText("Day19_part1")
    sumOfAccepted = part1(inputPart1Test)

    println(sumOfAccepted)
    check(sumOfAccepted == 368523L)

//    val inputPart2Test = FileReader.readFileAsLinesUsingReadLines("Day13_part2Test")
//    check(part1(inputPart2Test) == 71503)
//
//    val inputPart2 = FileReader.readFileAsLinesUsingReadLines("Day13_part2")
//    check(part1(inputPart2) == 36749103)
}
