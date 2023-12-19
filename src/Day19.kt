import org.springframework.expression.Expression
import org.springframework.expression.ExpressionParser
import org.springframework.expression.spel.standard.SpelExpressionParser

fun main() {
    val expressionParser: ExpressionParser = SpelExpressionParser()
    var wflows: Map<String, List<String>> = emptyMap()

     val ACCEPTED: String = "A"
     val REJECTED: String = "R"

    fun parseExpression(express: String): Boolean {
        val expression: Expression = expressionParser.parseExpression(express)
        return expression.value as Boolean
    }

    fun calculateExpression(rating: MutableMap<String, Int>): Long {
        var sumOfAccepted = 0L

        var flowKey = "in"
        var counter = 0
        var step: String
        do {
            step = wflows[flowKey]!![counter++]
            val ind = step.indexOf(":")
            val workFlowResult = step.substring(step.indexOf(":") + 1, step.length)
            if (ind >= 0) {
                val workFlowCondition = step.substring(0, ind)

                val placeholderRemoved = workFlowCondition.replace(
                    workFlowCondition.substring(0, 1),
                    rating[workFlowCondition.substring(0, 1)].toString(),
                )
                if (parseExpression(placeholderRemoved)) {
                    flowKey = workFlowResult
                    counter = 0
                }
            } else {
                counter = 0
                flowKey = workFlowResult
            }
        } while (flowKey != ACCEPTED && flowKey != REJECTED && counter < wflows[flowKey]!!.size)
        if (flowKey == ACCEPTED) {
            sumOfAccepted += rating.values.sum().toLong()
        }
        return sumOfAccepted
    }

    fun rate(text: String): Long {
        var sumOfAccepted = 0L
        val (_, ratingsText) = text.split("\n\n")

        ratingsText.trim().split("\n")
            .forEach { r ->
                val rating = mutableMapOf<String, Int>()
                val s = r.substring(1, r.length - 1).split(",")
                s.forEach { rr ->
                    val k = rr.substring(0, 1)
                    val v = rr.substring(rr.indexOf("=") + 1, rr.length).toInt()
                    rating[k] = v
                }
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

}

