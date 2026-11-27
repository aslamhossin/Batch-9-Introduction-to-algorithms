import java.util.Scanner

/**
 * Module 17 — Unbounded Knapsack (Top-Down DP)
 *
 * Sample Input:
 * 3
 * 5 10 15
 * 2 3 5
 * 8
 *
 * Expected Output:
 * 25
 */
fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val values = IntArray(n) { sc.nextInt() }
    val weights = IntArray(n) { sc.nextInt() }
    val maxWeight = sc.nextInt()

    val dp = Array(n) { IntArray(maxWeight + 1) { -1 } }

    fun knapsack(i: Int, cap: Int): Int {
        if (i < 0 || cap <= 0) return 0
        if (dp[i][cap] != -1) return dp[i][cap]

        dp[i][cap] = if (weights[i] <= cap) {
            val pickAgain = knapsack(i, cap - weights[i]) + values[i]
            val skip = knapsack(i - 1, cap)
            maxOf(pickAgain, skip)
        } else {
            knapsack(i - 1, cap)
        }
        return dp[i][cap]
    }

    println(knapsack(n - 1, maxWeight))
}
