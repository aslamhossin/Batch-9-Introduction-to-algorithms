package leetcode

import java.util.Scanner

/**
 * LeetCode #128: Longest Consecutive Sequence
 *
 * - Optimal (HashSet): Time O(n), Space O(n) — expands only from sequence starts (`num - 1 !in numSet`).
 * - Alternative (Sorted Scan): Time O(n log n), Space O(1) — compares `curr` and `next` after sorting.
 */
fun longestConsecutive(nums: IntArray): Int {
    val numSet = nums.toHashSet()
    var longestStreak = 0

    for (num in numSet) {
        if (num - 1 !in numSet) {
            var current = num
            var count = 1
            while (current + 1 in numSet) {
                current++
                count++
            }
            longestStreak = maxOf(count, longestStreak)
        }
    }
    return longestStreak
}

fun longestConsecutiveSorted(nums: IntArray): Int {
    if (nums.isEmpty()) return 0
    nums.sort()

    var longest = 1
    var streak = 1

    for (i in 0..<nums.size - 1) {
        val curr = nums[i]
        val next = nums[i + 1]
        when {
            next == curr -> continue
            next == curr + 1 -> {
                streak++
                longest = maxOf(longest, streak)
            }
            else -> streak = 1
        }
    }
    return longest
}

fun main() {
    val sc = Scanner(System.`in`)
    if (!sc.hasNextInt()) return
    val n = sc.nextInt()
    val nums = IntArray(n) { sc.nextInt() }
    println(longestConsecutive(nums))
}
