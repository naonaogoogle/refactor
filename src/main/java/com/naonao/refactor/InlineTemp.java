package com.naonao.refactor;

/**
 * 内联临时变量
 * 你有一个临时变量，植被一个简单的表达式赋值一次，而他妨碍了其他重构手法。
 *
 * 将所有的对该变量的引用动作，替换为对它赋值的那个表达式自身。
 */
public class InlineTemp {

    boolean beforeRefactor() {
        Order order = new Order();
        double hasPrice = order.getAmount();
        return hasPrice > 1000;
    }
    boolean afterRefactor() {
        Order order = new Order();
        return order.getAmount() > 1000;
    }

    /**
     * 动机
     * Inline Temp 多半是作为Replace Temp with Query 的一部分使用的，所以真正的动机出啊先在后者那。
     * 唯一单独使用Inline Temp的情况是：你发现某个临时变量被赋予讴歌函数调用的返回值。
     * 一般来说，这样的临时变量不会有任何危害，可以放心地把它留在那。但如果这个临时变量妨碍了其他的重构手法
     * 例如Extract Method，你就应该将它内联化。
     */

    /**
     * 做法
     * 1。 检查给临时变量赋值的语句，确保等号右边的表达式没有副作用。
     * 2。 如果这个临时变量被未被声明为final，那就将它什么为final，然后编译。
     *      这可以检查该临时变量是否真的只被赋值一次。
     * 3。 找到该联试变量所有引用点，将他们替换为【为临时变量赋值】的表达式。
     * 4。 每次修改后，编译并测试。
     * 5。 修改完所有的引用点后，删除该临时变量的声明与赋值语句。
     * 6。 编译，测试。
     *
     */

}
