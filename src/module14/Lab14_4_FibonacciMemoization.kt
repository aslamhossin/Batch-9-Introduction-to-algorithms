import java.util.Scanner

/**
 * Module 14 — Fibonacci with Top-Down Memoization (O(N))
 *
 * Sample Input:
 * 10
 *
 * Expected Output:
 * 55
 */
fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()

    val dp = LongArray(n + 2) { -1L }

    fun fibo(k: Int): Long {
        if (k < 2) return k.toLong()
        if (dp[k] != -1L) return dp[k]
        dp[k] = fibo(k - 1) + fibo(k - 2)
        return dp[k]
    }

    println(fibo(n))
}
