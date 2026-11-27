import java.util.Scanner

/**
 * Module 17 — Subset Sum using Top-Down DP Memoization (O(N * S))
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

    val dp = Array(n) { IntArray(target + 1) { -1 } }

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

    println(if (subsetSum(n - 1, target)) "YES" else "NO")
}
