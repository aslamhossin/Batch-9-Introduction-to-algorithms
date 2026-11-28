package leetcode

fun insert(intervals: Array<IntArray>, newInterval: IntArray): Array<IntArray> {
    return arrayOf()
}

fun main() {
    val intervals = arrayOf(intArrayOf(1, 3), intArrayOf(6, 9))
    val newInterval = intArrayOf(2, 5)
    val ans = insert(intervals, newInterval)
    println(ans.joinToString(", ") { it.contentToString() }) // Expected: [1, 5], [6, 9]
}
