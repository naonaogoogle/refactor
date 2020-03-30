package com.naonao.refactor;

/**
 * 以查询取代临时变量
 * 你的程序以一个临时变量保存某一个表达式运算结果
 * 将这个表达式提炼到一个独立函数中，将这个临时变量的所有引用点替换为对新函数的调用。
 * 此后，新函数就可被其他函数使用。
 */
public class ReplaceTempWithQuery {

    private double quantity;
    private double itemPrice;

    /**
     * 重构前
     */
    public double beforeGetTotalPrice() {
        double basePrice = quantity * itemPrice;
        if (basePrice > 1000) {
            return basePrice * 0.95;
        } else {
            return basePrice * 0.98;
        }
    }

    public double afterGetT0talPrice() {
        if (basePrice() > 1000) {
            return basePrice() * 0.95;
        } else {
            return basePrice() * 0.98;
        }
    }

    double basePrice() {
        return quantity * itemPrice;
    }
}

/**
 * 动机
 * 临时变量的问题在于：他们是暂时的，而且只能在所属函数内使用。由于临时变量只在所属函数内可见，所以
 * 他们会驱使你写出更长的函数，因为只有这样你才能访问到需要的临时变量。如果把临时变量替代为一个查询，
 * 那么同一个类中的所有函数都可以获得这份信息。这将给你带来极大的帮助，使你能够为这个类编写更清晰的代码。
 *
 * Replace Temp with Query 往往是你运用Extract Method之前必不可少的一个步骤。局部变量会使代码
 * 难以被提炼，所有你应该尽可能把他们替换为查询式。
 *
 * 这个重构手法较为简单的情况使：临时变量只被赋值一次，或者赋值给临时变量的表达式不受其他条件影戏。
 * 其他情况比较棘手，但也有可能发生。你可能需要先运用Split Temporary Variable或Separate Query
 * from Modifier 使情况变得简单一些，然后再替换临时变量。如果你想替换的临时变量是用来收集结果的
 * （例如循环中的累加值），就需要将某些程序逻辑（例如循环）赋值到查询函数中去。
 *
 */

/**
 * 做法
 * 首先是简单情况
 * 1。 找出只被赋值一次的临时变量。
 *      如果某个临时变量被赋值超过一次，考虑使用Split Temporary Variable 将它分割成多个变量。
 * 2。 将该临时变量声明为final。
 * 3。 编译。
 *      这确保该临时变量的确只被赋值一次。
 * 4。 将【对该临时变量赋值】之语句的等号右侧部分提炼到一个独立函数中。
 *      首先将函数声明为private，日后你可能会发现有更多类需要使用它，那时放松对他的保护也很容易。
 *      确保提炼出来的函数无任何副作用，也就是说该函数并不修改任何对象内容，
 *      如果它有副作用，就对它进行Separate Query from Modifier。
 * 5。 编译，测试。
 * 6。 在该临时变量身上实施Inline Temp。
 */

/**
 * 运用此手法，可能会担心性能问题。和其他性能问题一样，我们现在不管他，因为它十有八九根本不会造成任何影响。
 * 如果是性能真出了问题，你也可能在优化时期解决它。代码组织良好，你往往能够发现更有效的优化方案：如果没有
 * 进行重构，好的优化方案可能与你失之交臂。如果性能实在太糟糕，要把临时变量放回去也是很容易的。
 */

/**
 * 范例
 * 首先，容易个简单函数开始：
 */
class Demo4 {
    private double quantity;
    private double itemPrice;

    double beforeGetPrice() {
        double basePrice = quantity * itemPrice;
        double discountFactor;
        if (basePrice > 1000) discountFactor = 0.95;
        else discountFactor = 0.98;
        return basePrice * discountFactor;
    }

    double afterGetPrice() {
        final double basePrice = quantity * itemPrice;
        final double discountFactor;
        if (basePrice > 1000) discountFactor = 0.95;
        else discountFactor = 0.98;
        return basePrice * discountFactor;
    }

    /**
     * 最终重构结果
     */
    double finalGetPrice() {
        return basePrice() * discountFactor();
    }
    private double basePrice() {
        return quantity * itemPrice;
    }

    private double discountFactor() {
        if (basePrice() > 1000) return 0.95;
        else return 0.98;
    }

}
