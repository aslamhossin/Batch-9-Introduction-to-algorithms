import java.util.Scanner

/**
 * Module 15 — Knapsack (Paired Weight-Value Input Format)
 *
 * Sample Input:
 * 3 8
 * 3 30
 * 4 50
 * 5 60
 *
 * Expected Output:
 * 90
 */
fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val maxWeight = sc.nextInt()

    val weights = IntArray(n)
    val values = IntArray(n)
    for (i in 0..<n) {
        weights[i] = sc.nextInt()
        values[i] = sc.nextInt()
    }

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
