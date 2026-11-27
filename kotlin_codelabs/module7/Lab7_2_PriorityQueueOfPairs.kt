import java.util.PriorityQueue

/**
 * Module 7 — Min-Heap PriorityQueue of Pairs
 *
 * Expected Output:
 * 5 2
 */
fun main() {
    val pq = PriorityQueue(compareBy<Pair<Int, Int>> { it.first }.thenBy { it.second })
    pq.add(10 to 1)
    pq.add(5 to 2)

    val top = pq.peek()
    println("${top.first} ${top.second}")
}
