package leetcode

import java.util.Scanner

fun isPalindrome(s: String): Boolean {
    var left = 0
    var right = s.length - 1
    while (left < right) {
        while (left < right && !s[left].isLetterOrDigit()) {
            left++
        }
        while (left < right && !s[right].isLetterOrDigit()) {
            right--
        }
        if (s[left].lowercaseChar() != s[right].lowercaseChar()) return false
        left++
        right--
    }
    return true
}

fun main() {
    val sc = Scanner(System.`in`)
    val s = if (sc.hasNextLine()) sc.nextLine() else ""
    println(isPalindrome(s))
}
