package leetcode

import java.util.Scanner

fun dailyTemperatures(temperatures: IntArray): IntArray {
    val answers = IntArray(temperatures.size)
    val stack = ArrayDeque<Int>()
    for (i in temperatures.indices) {
        while (stack.isNotEmpty() && temperatures[i] > temperatures[stack.last()]) {
            val prev = stack.removeLast()
            answers[prev] = i - prev
        }
        stack.addLast(i)
    }
    return answers
}

fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val temperatures = IntArray(n) { sc.nextInt() }
    println(dailyTemperatures(temperatures).joinToString(" "))
}
