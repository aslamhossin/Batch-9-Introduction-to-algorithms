/**
 * Module 14 — Factorial using Recursion
 *
 * Expected Output:
 * 120
 */
fun main() {
    fun fact(n: Int): Int {
        if (n <= 1) return 1
        return n * fact(n - 1)
    }

    println(fact(5))
}
