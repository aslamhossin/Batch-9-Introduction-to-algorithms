import java.util.Scanner

/**
 * Module 5 — Codelab 5.4: Count Sub Islands (LeetCode #1905)
 * রেফারেন্স: Module 5/count_sub_islands.cpp এর সম্পূর্ণ Kotlin রূপান্তর।
 *
 * বিশেষ নোট: এখানে আবার '1' = মাটি (Land) এবং '0' = পানি (Water)!
 *
 * নমুনা Input:
 * 3 3
 * 110
 * 110
 * 001
 * 100
 * 100
 * 011
 *
 * আউটপুট:
 * 1
 */
fun main() {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val m = sc.nextInt()

    val grid1 = Array(n) { CharArray(m) }
    for (i in 0..<n) {
        grid1[i] = sc.next().toCharArray()
    }

    val grid2 = Array(n) { CharArray(m) }
    for (i in 0..<n) {
        grid2[i] = sc.next().toCharArray()
    }

    fun valid(r: Int, c: Int): Boolean {
        return r in 0..<n && c in 0..<m
    }

    val visited = Array(n) { BooleanArray(m) }
    val dr = intArrayOf(-1, 1, 0, 0)
    val dc = intArrayOf(0, 0, -1, 1)

    // grid2 এর একটি দ্বীপের সব ঘর grid1 এও মাটি ('1') কি না তা ট্র্যাক করার ফ্ল্যাগ
    var isSubIsland = true

    fun dfs(r: Int, c: Int) {
        visited[r][c] = true
        if (grid1[r][c] == '0') {
            // সাবধান: এখানে return করা যাবে না! পুরো দ্বীপটা visited করতে হবে।
            isSubIsland = false
        }

        for (d in dr.indices) {
            val nr = r + dr[d]
            val nc = c + dc[d]

            if (valid(nr, nc) && !visited[nr][nc] && grid2[nr][nc] == '1') {
                dfs(nr, nc)
            }
        }
    }

    var subIslands = 0
    for (i in 0..<n) {
        for (j in 0..<m) {
            if (!visited[i][j] && grid2[i][j] == '1') {
                isSubIsland = true // নতুন দ্বীপ শুরু করার আগে ধরে নিই এটি sub-island (true)
                dfs(i, j)
                if (isSubIsland) {
                    subIslands++
                }
            }
        }
    }

    println(subIslands)
}
