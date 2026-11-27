/**
 * Module 14 — Basic Recursion (Sum 1..5)
 *
 * Expected Output:
 * 15
 */
fun main() {
    fun rec(n: Int): Int {
        if (n > 5) return 0
        val sum = rec(n + 1)
        return sum + n
    }

    println(rec(1))
}
