/**
 * Module 3 — Codelab 3.1: একদম বেসিক থেকে Graph ও DFS (Kotlin)
 * Android Engineer Interview Preparation
 *
 * বাস্তব উদাহরণ (Offline News Prefetcher):
 * ধরুন একটি নিউজ অ্যাপে ৫টি আর্টিকেল আছে (0, 1, 2, 3, 4)।
 * প্রতিটি আর্টিকেলের নিচে "Related Articles" লিংক থাকে:
 *   Article 0 <--> Article 1
 *   Article 0 <--> Article 2
 *   Article 1 <--> Article 3
 *   Article 1 <--> Article 4
 *
 * ইউজার যখন অফলাইনে যাওয়ার আগে Article 0 ওপেন করে,
 * আমরা DFS চালিয়ে এর সাথে যুক্ত সবগুলো আর্টিকেল খুঁজে বের করব।
 */

fun traverseRelatedArticles(
    totalArticles: Int,
    links: Array<IntArray>,
    startArticle: Int
): List<Int> {
    // ধাপ ১: Adjacency List তৈরি (প্রতিটি নোডের সাথে কারা যুক্ত তার তালিকা)
    // adj[0] মানে Article 0-এর সাথে কোন কোন আর্টিকেল সরাসরি যুক্ত
    val adj = Array(totalArticles) { ArrayList<Int>() }

    for (edge in links) {
        val a = edge[0]
        val b = edge[1]
        adj[a].add(b) // a থেকে b-তে যাওয়া যায়
        adj[b].add(a) // b থেকেও a-তে আসা যায় (Undirected Graph)
    }

    // ধাপ ২: Visited Array তৈরি (যাতে একই আর্টিকেলে বারবার ঘুরে Infinite Loop না হয়!)
    // Android টিপস: Array<Boolean> না লিখে সবসময় BooleanArray লিখবেন (মেমোরি সাশ্রয়ী)
    val visited = BooleanArray(totalArticles)

    // ফলাফল রাখার তালিকা
    val resultOrder = ArrayList<Int>()

    // ধাপ ৩: মূল রিকার্সিভ DFS ফাংশন
    fun dfs(current: Int) {
        // ৩.১: বর্তমান নোডটিকে 'দেখা হয়েছে' (true) মার্ক করি
        visited[current] = true

        // ৩.২: আমাদের লিস্টে নোডটি যোগ করি
        resultOrder.add(current)

        // ৩.৩: বর্তমান নোডের প্রতিটি প্রতিবেশী (neighbor) চেক করি
        for (neighbor in adj[current]) {
            // যদি প্রতিবেশীটি আগে ভিজিট না হয়ে থাকে, তবে তার গভীরে ঢুকে যাই!
            if (!visited[neighbor]) {
                dfs(neighbor)
            }
        }
    }

    // শুরু করি startArticle থেকে
    dfs(startArticle)

    return resultOrder
}

fun main() {
    val totalArticles = 5
    val links = arrayOf(
        intArrayOf(0, 1),
        intArrayOf(0, 2),
        intArrayOf(1, 3),
        intArrayOf(1, 4)
    )

    val visitedOrder = traverseRelatedArticles(
        totalArticles = totalArticles,
        links = links,
        startArticle = 0
    )

    println("DFS Traversal Order from Article 0: $visitedOrder")
    // আউটপুট: [0, 1, 3, 4, 2]
}
