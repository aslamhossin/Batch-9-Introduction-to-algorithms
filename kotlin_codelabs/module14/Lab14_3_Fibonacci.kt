import java.util.Scanner

/**
 * Module 14 — Fibonacci using Naive Recursion (O(2^N))
 *
 * Sample Input:
 * 6
 *
 * Expected Output:
 * 8
 */
fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()

    fun fibo(k: Int): Int {
        if (k < 2) return k
        return fibo(k - 1) + fibo(k - 2)
    }

    println(fibo(n))
}
