/**
 * Module 3 — Codelab 3.1 Practice Problem (নিজে সমাধান করুন!)
 *
 * সমস্যা (Offline Cache Count):
 * একটি নিউজ অ্যাপে `n` টি আর্টিকেল আছে (0 থেকে n-1 পর্যন্ত) এবং কিছু `links` দেওয়া আছে।
 * সব আর্টিকেল সবার সাথে যুক্ত নাও থাকতে পারে!
 *
 * ইউজার যদি `startArticle` ডাউনলোড করে, তবে `startArticle` সহ মোট **কতগুলো** আর্টিকেলে
 * লিংকের মাধ্যমে পৌঁছানো সম্ভব — সেই সংখ্যাটি (`Int`) রিটার্ন করুন।
 *
 * উদাহরণ ১:
 * n = 6, links = [[0, 1], [1, 2], [3, 4]], startArticle = 0
 * এখানে 0 -> 1 -> 2 যাওয়া যায় (মোট ৩টি আর্টিকেল: 0, 1, 2)।
 * কিন্তু 3, 4 এবং 5-এ যাওয়া যায় না! তাই উত্তর হবে: 3
 */

fun countReachableArticles(
    n: Int,
    links: Array<IntArray>,
    startArticle: Int
): Int {
    // ১. Adjacency List তৈরি করে দেওয়া হলো:
    val adj = Array(n) { ArrayList<Int>() }
    for (edge in links) {
        val a = edge[0]
        val b = edge[1]
        adj[a].add(b)
        adj[b].add(a)
    }

    // ২. Visited Array তৈরি করে দেওয়া হলো:
    val visited = BooleanArray(n)

    var count = 0

    fun dfs(src: Int) {
        visited[src] = true
        count++

        for (child in adj[src]) {
            if (visited[child].not()) {
                dfs(child)
            }
        }
    }

    dfs(startArticle)
    return count
}

// নিচের টেস্ট রানারটি আপনার কোড সঠিক কি না স্বয়ংক্রিয়ভাবে চেক করবে:
fun main() {
    val test1 = countReachableArticles(
        n = 6,
        links = arrayOf(intArrayOf(0, 1), intArrayOf(1, 2), intArrayOf(3, 4)),
        startArticle = 0
    )
    println("Test 1 -> আপনার উত্তর: $test1 | সঠিক উত্তর: 3 | ${if (test1 == 3) "PASS ✅" else "TRY AGAIN ❌"}")

    val test2 = countReachableArticles(
        n = 4,
        links = emptyArray(),
        startArticle = 2
    )
    println("Test 2 -> আপনার উত্তর: $test2 | সঠিক উত্তর: 1 | ${if (test2 == 1) "PASS ✅" else "TRY AGAIN ❌"}")

    val test3 = countReachableArticles(
        n = 5,
        links = arrayOf(intArrayOf(0, 1), intArrayOf(0, 2), intArrayOf(2, 3), intArrayOf(3, 4)),
        startArticle = 0
    )
    println("Test 3 -> আপনার উত্তর: $test3 | সঠিক উত্তর: 5 | ${if (test3 == 5) "PASS ✅" else "TRY AGAIN ❌"}")
}
