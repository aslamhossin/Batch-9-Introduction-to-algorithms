import java.util.Scanner

/**
 * Module 17 — Coin Change II (LeetCode #518)
 *
 * Sample Input:
 * 5 3
 * 1 2 5
 *
 * Expected Output:
 * 4
 */
fun change(amount: Int, coins: IntArray): Int {
    val n = coins.size
    val dp = Array(n) { IntArray(amount + 1) { -1 } }

    fun ways(i: Int, sum: Int): Int {
        if (i < 0) return if (sum == 0) 1 else 0
        if (dp[i][sum] != -1) return dp[i][sum]

        dp[i][sum] = if (coins[i] <= sum) {
            ways(i, sum - coins[i]) + ways(i - 1, sum)
        } else {
            ways(i - 1, sum)
        }
        return dp[i][sum]
    }

    return ways(n - 1, amount)
}

fun main() {
    val sc = Scanner(System.`in`)
    val amount = sc.nextInt()
    val n = sc.nextInt()
    val coins = IntArray(n) { sc.nextInt() }
    println(change(amount, coins))
}
