import java.util.Scanner

/**
 * Module 18 — Merge Sort (Divide and Conquer)
 *
 * Sample Input:
 * 5
 * 5 2 9 1 3
 *
 * Expected Output:
 * 1 2 3 5 9
 */
fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val arr = IntArray(n) { sc.nextInt() }

    fun conquer(l: Int, mid: Int, r: Int) {
        val leftSize = mid - l + 1
        val rightSize = r - mid
        val left = IntArray(leftSize) { arr[l + it] }
        val right = IntArray(rightSize) { arr[mid + 1 + it] }

        var i = 0
        var j = 0
        var curr = l

        while (i < leftSize && j < rightSize) {
            if (left[i] < right[j]) {
                arr[curr++] = left[i++]
            } else {
                arr[curr++] = right[j++]
            }
        }
        while (i < leftSize) arr[curr++] = left[i++]
        while (j < rightSize) arr[curr++] = right[j++]
    }

    fun mergeSort(l: Int, r: Int) {
        if (l >= r) return
        val mid = (l + r) / 2
        mergeSort(l, mid)
        mergeSort(mid + 1, r)
        conquer(l, mid, r)
    }

    if (n > 0) mergeSort(0, n - 1)
    println(arr.joinToString(" "))
}
