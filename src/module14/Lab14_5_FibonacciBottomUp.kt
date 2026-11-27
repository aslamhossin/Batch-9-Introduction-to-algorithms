import java.util.Scanner

/**
 * Module 14 — Fibonacci with Bottom-Up Tabulation (O(N))
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

    if (n < 2) {
        println(n)
        return
    }

    val fibo = LongArray(n + 1)
    fibo[0] = 0L
    fibo[1] = 1L

    for (i in 2..n) {
        fibo[i] = fibo[i - 1] + fibo[i - 2]
    }

    println(fibo[n])
}
