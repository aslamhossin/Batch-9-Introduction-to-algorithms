package leetcode

import java.util.Scanner
import kotlin.text.iterator

fun isValid(s: String): Boolean {
    val stack = ArrayDeque<Char>()
    for (c in s) {
        if (c == '(' || c == '{' || c == '[') {
            stack.addLast(c)
        } else {
            if (stack.isEmpty()) return false
            val top = stack.removeLast()
            if ((c == ')' && top != '(') || (c == '}' && top != '{') || (c == ']' && top != '[')) {
                return false
            }
        }
    }
    return stack.isEmpty()
}

fun main() {
    val sc = Scanner(System.`in`)
    val s = if (sc.hasNext()) sc.next() else ""
    println(isValid(s))
}
