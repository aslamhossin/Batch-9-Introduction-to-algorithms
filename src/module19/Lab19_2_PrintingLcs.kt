import java.util.Scanner

/**
 * Module 19 — Longest Common Subsequence (Length + String Reconstruction)
 *
 * Sample Input:
 * abcde ace
 *
 * Expected Output:
 * LCS length: 3
 * LCS: ace
 */
fun main() {
    val sc = Scanner(System.`in`)
    val s1 = sc.next()
    val s2 = sc.next()

    val n = s1.length
    val m = s2.length
    val dp = Array(n + 1) { IntArray(m + 1) }

    for (i in n - 1 downTo 0) {
        for (j in m - 1 downTo 0) {
            dp[i][j] = if (s1[i] == s2[j]) {
                1 + dp[i + 1][j + 1]
            } else {
                maxOf(dp[i + 1][j], dp[i][j + 1])
            }
        }
    }

    val sb = StringBuilder()
    var i = 0
    var j = 0
    while (i < n && j < m) {
        if (s1[i] == s2[j]) {
            sb.append(s1[i])
            i++
            j++
        } else if (dp[i + 1][j] >= dp[i][j + 1]) {
            i++
        } else {
            j++
        }
    }

    val lcs = sb.toString()
    println("LCS length: ${lcs.length}")
    println("LCS: $lcs")
}
