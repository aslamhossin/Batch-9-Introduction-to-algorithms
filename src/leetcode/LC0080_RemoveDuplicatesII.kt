package leetcode

fun removeDuplicates(nums: IntArray): Int {
    var slow =0
    var fast = 0
    while (fast<nums.size){
        if (slow<2 || nums[fast]!= nums[slow-2]){
            
            nums[slow] = nums[fast]
            slow++
        }
        fast++
    }
    return slow
}

fun main() {
    val nums = intArrayOf(1, 1, 1, 2, 2, 3)
    val k = removeDuplicates(nums)
    println("k = $k, result = ${nums.take(k)}")
}
