import java.util.Scanner

/**
 * Module 17 — Subset Sum using Naive Recursion (O(2^N))
 *
 * Sample Input:
 * 4
 * 1 2 4 6
 * 7
 *
 * Expected Output:
 * YES
 */
fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val values = IntArray(n) { sc.nextInt() }
    val target = sc.nextInt()

    fun subsetSum(i: Int, sum: Int): Boolean {
        if (i < 0) return sum == 0
        return if (values[i] <= sum) {
            subsetSum(i - 1, sum - values[i]) || subsetSum(i - 1, sum)
        } else {
            subsetSum(i - 1, sum)
        }
    }

    println(if (subsetSum(n - 1, target)) "YES" else "NO")
}
