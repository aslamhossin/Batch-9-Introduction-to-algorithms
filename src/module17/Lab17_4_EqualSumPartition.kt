import java.util.Scanner

/**
 * Module 17 — Equal Sum Partition (Top-Down DP)
 *
 * Sample Input:
 * 4
 * 1 5 11 5
 *
 * Expected Output:
 * YES
 */
fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val values = IntArray(n) { sc.nextInt() }
    val totalSum = values.sum()

    if (totalSum % 2 == 1) {
        println("NO")
        return
    }

    val half = totalSum / 2
    val dp = Array(n) { IntArray(half + 1) { -1 } }

    fun subsetSum(i: Int, sum: Int): Boolean {
        if (i < 0) return sum == 0
        if (dp[i][sum] != -1) return dp[i][sum] == 1

        val possible = if (values[i] <= sum) {
            subsetSum(i - 1, sum - values[i]) || subsetSum(i - 1, sum)
        } else {
            subsetSum(i - 1, sum)
        }
        dp[i][sum] = if (possible) 1 else 0
        return possible
    }

    println(if (subsetSum(n - 1, half)) "YES" else "NO")
}
