import java.util.Scanner

/**
 * Module 19 — Longest Common Subsequence (LCS) Length with Memoization
 *
 * Sample Input:
 * abcde ace
 *
 * Expected Output:
 * 3
 */
fun main() {
    val sc = Scanner(System.`in`)
    val a = sc.next()
    val b = sc.next()

    val n = a.length
    val m = b.length
    val dp = Array(n) { IntArray(m) { -1 } }

    fun lcs(i: Int, j: Int): Int {
        if (i < 0 || j < 0) return 0
        if (dp[i][j] != -1) return dp[i][j]

        dp[i][j] = if (a[i] == b[j]) {
            1 + lcs(i - 1, j - 1)
        } else {
            maxOf(lcs(i - 1, j), lcs(i, j - 1))
        }
        return dp[i][j]
    }

    println(lcs(n - 1, m - 1))
}
