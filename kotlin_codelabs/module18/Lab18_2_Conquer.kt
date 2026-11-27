import java.util.Scanner

/**
 * Module 18 — Conquer Step (Merging Two Sorted Arrays)
 *
 * Sample Input:
 * 3
 * 1 4 7
 * 3
 * 2 5 8
 *
 * Expected Output:
 * 1 2 4 5 7 8
 */
fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val a = IntArray(n) { sc.nextInt() }
    val m = sc.nextInt()
    val b = IntArray(m) { sc.nextInt() }

    val merged = IntArray(n + m)
    var i = 0
    var j = 0
    var curr = 0

    while (i < n && j < m) {
        if (a[i] < b[j]) {
            merged[curr++] = a[i++]
        } else {
            merged[curr++] = b[j++]
        }
    }
    while (i < n) merged[curr++] = a[i++]
    while (j < m) merged[curr++] = b[j++]

    println(merged.joinToString(" "))
}
