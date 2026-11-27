import java.util.Scanner

/**
 * Module 17 — Count of Subsets with Given Sum (Top-Down DP)
 *
 * Sample Input:
 * 4
 * 1 2 3 3
 * 6
 *
 * Expected Output:
 * 3
 */
fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val values = IntArray(n) { sc.nextInt() }
    val target = sc.nextInt()

    val dp = Array(n) { IntArray(target + 1) { -1 } }

    fun countSubsets(i: Int, sum: Int): Int {
        if (i < 0) return if (sum == 0) 1 else 0
        if (dp[i][sum] != -1) return dp[i][sum]

        dp[i][sum] = if (values[i] <= sum) {
            countSubsets(i - 1, sum - values[i]) + countSubsets(i - 1, sum)
        } else {
            countSubsets(i - 1, sum)
        }
        return dp[i][sum]
    }

    println(countSubsets(n - 1, target))
}
