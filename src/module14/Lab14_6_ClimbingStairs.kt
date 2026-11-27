import java.util.Scanner

/**
 * Module 14 — Climbing Stairs (LeetCode #70)
 *
 * Sample Input:
 * 5
 *
 * Expected Output:
 * 8
 */
fun climbStairs(n: Int): Int {
    val dp = IntArray(n + 3) { -1 }

    fun ways(k: Int): Int {
        if (k < 3) return k
        if (dp[k] != -1) return dp[k]
        dp[k] = ways(k - 1) + ways(k - 2)
        return dp[k]
    }

    return ways(n)
}

fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    println(climbStairs(n))
}
