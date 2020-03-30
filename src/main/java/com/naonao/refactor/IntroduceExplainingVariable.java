package com.naonao.refactor;

import java.util.Random;

/**
 * 引入解释性变量
 * 你有一个复杂的表达式
 * 将该复杂的表达式（或其中一部分）的结果放进一个临时变量，
 * 以此变量名称来解释表达式用途。
 */
public class IntroduceExplainingVariable {
    String platform="";
    String browser="";
    int resize=0;
    void before() {
        if ((platform.toUpperCase().contains("MAC"))
                && (browser.toUpperCase().contains("IE"))
                && wasInitialized() && resize > 0) {
            //    do something
        }
    }

    private boolean wasInitialized() {
        return new Random().nextBoolean();
    }


    void after() {
        final boolean isMacOS = platform.toUpperCase().contains("MAC");
        final boolean isIEBrowser = browser.toUpperCase().contains("IE");
        final boolean wasResized = resize > 0;
        if (isMacOS && isIEBrowser && wasInitialized() && wasResized) {
            //    do something
        }
    }
}
/**
 * 动机
 * 表达式有可能非常复杂而难以阅读。这种情况下，临时变量可以帮助你将表达式分解为比较容易管理的形式。
 * <p>
 * 在条件逻辑中，Introduce Explaining Variable 特别有价值：你可以用这项重构将每个条件子句提炼出来，
 * 以一个良好命名的零食变量来解释对应条件子句的意义。使用这项重构的另一中情况是，在较长算法中，可以用
 * 临时变量来解释每一步运算的意义。
 * <p>
 * Introduce Explaining Variable 是一个很常见的重构手法，但我要承认，我并不常用它。我几乎总是
 * 尽量使用Extract Method 来解释一段代码的意义。毕竟临时变量只在它所处的那个函数中才有意义，局限性比较大，
 * 函数则可以在对象的整个生命中都有用，并且可以被其他对象使用。但有时候，当局部变量使Extract Method难以进行
 * 时，我就使用 Introduce Explaining Variable；
 * <p>
 * <p>
 * 做法
 */

/**
 * 做法
 * 1。 声明一个final临时变量，将待分解之复杂表达式之中的一部分动作的运算结果赋值给他。
 * 2。 将表达式中的运算结果这一部分，替换为上述临时变量
 * 3。 编译，测试
 *      如果被替换的这一部分在代码中重复出现，你可以每次一个，逐一替换。
 * 4。 重复上述过程，处理表达式的其他部分。
 *
 */
