import java.util.Scanner

/**
 * Module 15 — 0/1 Knapsack using Top-Down DP Memoization
 *
 * Sample Input:
 * 3
 * 60 100 120
 * 10 20 30
 * 50
 *
 * Expected Output:
 * 220
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
            val pick = knapsack(i - 1, cap - weights[i]) + values[i]
            val skip = knapsack(i - 1, cap)
            maxOf(pick, skip)
        } else {
            knapsack(i - 1, cap)
        }
        return dp[i][cap]
    }

    println(knapsack(n - 1, maxWeight))
}
