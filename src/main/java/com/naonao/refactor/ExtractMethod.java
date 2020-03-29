package com.naonao.refactor;

import java.util.Iterator;
import java.util.List;

public class ExtractMethod {
    private String name;

    public static void main(String[] args) {

    }

    /**
     * 重构前代码
     */
    public void beforeExtractMethod(double amount) {
        printBanner();
        //    print detail
        System.out.println("name" + name);
        System.out.println("amount" + amount);
    }


    private void printBanner() {
    }

    /**
     * 重构后的代码
     * 将这段代码放入一个独立的函数中，并让函数名称解释该函数的用途。
     */
    public void afterExactMethod(double amount) {
        printBanner();
        printDetail(amount);
    }

    private void printDetail(double amount) {
        System.out.println("name" + name);
        System.out.println("amount" + amount);
    }


    /**
     * 动机
     * 为什么喜欢简短而又命名良好的函数：
     * 1, 如果每个函数的粒度都很小，那么函数被复用的几率就会越大。
     * 2, 这会使高层函数读起来像是一系列注释。
     * 3, 如果函数都是细粒度的，函数的复写也会容易些。
     *
     *
     * 函数的长度不是问题，关键在于函数名称和函数本体之间的语义距离。
     * 如果提炼可以强化代码的清晰度，那就去做，就算函数名称比提炼出来的代码还长也无所谓。
     */

    /**
     * 做法
     * 1。 创造一个函数，根据这个函数的意义对他命名（以它【做什么】来名称，而不是以它【怎么做】命名）。
     *          1。即使你想要提炼的代码非常简单，例如只是一条消息或一个函数调用，只要新函数的名称能够以更好的
     *          方式昭示代码意图，你也应该提炼它。但如果你想不出一个更有意义的名称，就别动。
     * 2。 将提炼的代码从愿函数复制到新建的目标函数中。
     * 3。 仔细检查提炼出的代码，看看其中是否引用了【作用域限于原函数】的变量（包括局部变量和原函数参数）。
     * 4。 检查是否有【仅用于被提炼代码段】的临时变量。如果有，在目标函数中将他们为临时变量。
     * 5。 检查被提炼代码段，看看是否有任何局部变量的值被它改变，如果一个临时变量的值被修改了，看看是否可以将被提炼代码处理为一个查询，
     * 并将结果赋值给相关变量。如果很难这样做，或如果被修改的变量不止一个，你就不能仅仅将这段代码原封不动地提炼出来。你可能需要先使用
     * Split Temporary Variable，然后再尝试提炼。也可以用Replace Temp with Query将临时变量消灭调。
     * 6。 将被提炼代码段中需要读取的局部变量，当作参数传给目标函数。
     * 7。 处理完所有局部变量之后，进行编译。
     * 8。 在原函数中，将被提炼代码段替换为对目标函数的调用。
     *      如果你将任何临时变量移到目标函数中，请检查他们原本声明式是否在被提炼代码段的外围，如果是，现在你可以删除这些声明式了。
     * 9。 编译，测试。
     *
     */
}

/**
 * 范例：无局部变量
 */
class Demo1 {
    private List<Order> orders;
    private String name;

    /**
     * 重构前
     */
    void BeforePrintOwing() {
        Iterator<Order> iterator = orders.iterator();
        double outstanding = 0.0;
        //    print banner
        System.out.println("*********************");
        System.out.println("**** Customer Owes **");
        System.out.println("*********************");
        //    calculate outstanding
        while (iterator.hasNext()) {
            Order order = iterator.next();
            outstanding += order.getAmount();
        }
        //    print details
        System.out.println("name:" + name);
        System.out.println("amount:" + outstanding);
    }

    void afterPrintOwing() {
        Iterator<Order> iterator = orders.iterator();
        double outstanding = 0.0;
        printBanner();
        //    calculate outstanding
        while (iterator.hasNext()) {
            Order order = iterator.next();
            outstanding += order.getAmount();
        }
        //    print details
        System.out.println("name:" + name);
        System.out.println("amount:" + outstanding);

    }

    void printBanner() {
        System.out.println("*********************");
        System.out.println("**** Customer Owes **");
        System.out.println("*********************");
    }
}


/**
 * 范例：有局部变量
 * 此重构手法的难点在于局部变量，包括传进原属的【参数】和【原函数声明的临时变量】。
 * 局部变量作用域仅限于原函数，所以当我使用Extract Method 时，必须话费额外的功夫处理这些临时变量。
 * <p>
 * 局部变量最简单的额情况是：被提炼代码段【只是读取】这些【临时变量】的值，并【不修改】他们。
 * 这种情况下我们可以简单地将他们当作参数传递给目标函数。
 * <p>
 * 如果局部变量是个对象，而被提炼代码段调用了会对该对象造成修改的函数，可以如法炮制。你通过只需要将这个对象
 * 作为参数传递给目标函数即可。只有在被提炼代码段真的对一个局部变量赋值的情况下，你才必须采取其他措施。
 */
class Demo2 {
    private List<Order> orders;
    private String name;

    /**
     * 重构前
     */
    void beforePrintOwing() {
        Iterator<Order> iterator = orders.iterator();
        double outstanding = 0.0;
        printBanner();
        //    calculate outstanding
        while (iterator.hasNext()) {
            Order order = iterator.next();
            outstanding += order.getAmount();
        }
        //    print details
        System.out.println("name:" + name);
        System.out.println("amount:" + outstanding);
    }

    /**
     * 重构后
     */
    void afterPrintOwing() {
        Iterator<Order> iterator = orders.iterator();
        double outstanding = 0.0;
        printBanner();
        //    calculate outstanding
        while (iterator.hasNext()) {
            Order order = iterator.next();
            outstanding += order.getAmount();
        }
        printDetail(outstanding);
    }

    void printDetail(double outstanding) {
        //    print details
        System.out.println("name:" + name);
        System.out.println("amount:" + outstanding);
    }

    void printBanner() {
        System.out.println("*********************");
        System.out.println("**** Customer Owes **");
        System.out.println("*********************");
    }

}


class Order {
    private double amount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

/**
 * 范例：对据变量再赋值
 * 如果被提炼代码段对局部变量再赋值，问题就变得复杂了。这里我们只讨论对临时变量赋值的问题。
 * 如果你发现原函数的参数被赋值，应该马上使用Remove Assignments to Parameters。
 * 被赋值的临时变量也分为两种情况。
 * 比较简单的情况是：这个变量只在被提炼代码段中使用，果真如此，你可以将这个临时变量的声明移到被提炼代码段中，然后一起提炼出去。
 * 另一种情况是：被提炼代码段之外的代码也用到了这个变量。这又分为两种情况：如果这个变量在被提炼代码段之后未再被使用，你只需要直接
 * 在目标函数中修改它就可以了；如果被提炼代码段之后的代码还使用这个变量，你就需要让目标函数返回该变量改变后的值。
 */
class Demo3 {
    private List<Order> orders;
    private String name;

    /**
     * 重构前
     */
    void beforePrintOwing() {

        Iterator<Order> iterator = orders.iterator();
        double outstanding = 0.0;
        printBanner();
        //    calculate outstanding
        while (iterator.hasNext()) {
            Order order = iterator.next();
            outstanding += order.getAmount();
        }
        printDetail(outstanding);
    }

    void printDetail(double outstanding) {
        System.out.println("name:" + name);
        System.out.println("amount:" + outstanding);
    }

    void printBanner() {
        System.out.println("*********************");
        System.out.println("**** Customer Owes **");
        System.out.println("*********************");
    }


    /**
     * 重构后
     * Iterator<Order> 的变量iterator只在被提炼代码段中用到，所以可以将它整个搬到函数中。
     * double 变量 outstanding 在被提炼代码段内外都被用到，所以必须让提炼出来的新函数返回它。
     * 编译测试完后，我就把回传值改名，遵循我一贯的命名原则：
     */
    void afterPrintOwing() {
        printBanner();
        double outstanding = getOutstanding();
        printDetail(outstanding);
    }

    double getOutstanding() {
        Iterator<Order> iterator = orders.iterator();
        double result = 0.0;
        while (iterator.hasNext()) {
            Order order = iterator.next();
            result += order.getAmount();
        }
        return result;
    }

    /**
     * 问题：如果需要返回的变量不止一个，又该怎么办呢？
     * 有几个选择。最好的选择通常是：挑选另一块代码来提炼。
     * 我比较喜欢让每个函数都返回一个值，所以会安排多个函数，用以返回多个值。
     * 如果你使用的语言支持【出参数】，可以使用他们带回多个回传值。但那我还是尽可能选择单一返回值。
     * 临时变量往往为数众多，这种情况下，我会尝试先运用Replace Temp with Query 减少临时变量。
     * 如果即使这么做了提炼依旧困难重重，我就会运用Replace Method with Method Object，这个重构
     * 手法不在乎代码中有多少临时变量，也不在乎你如何使用他们。
     */

}
