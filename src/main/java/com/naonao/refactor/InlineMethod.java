package com.naonao.refactor;

public class InlineMethod {
    private int numberOfLateDeliveries;

    /**
     * 重构前
     */
    int beforeGetRating() {
        return moreThanFiveLateDeliveries() ? 2 : 1;
    }

    boolean moreThanFiveLateDeliveries() {
        return numberOfLateDeliveries > 5;
    }

    /**
     * 重构后
     */
    int afterGetRating() {
        return numberOfLateDeliveries > 5 ? 2 : 1;
    }

    /**
     * 动机
     * 我经常以简短的函数表现动作意图，这样会使代码更清晰易读。但有时候你会遇到某些函数，其内部代码和
     * 函数名称同样清晰易读。也可能你重构了该函数，使其内容和其名称变得同样清晰。果真如此，你就应该去掉这个函数，直接使用其中的代码，
     * 间接性可能带来帮助，但非必要的间接性总让人不舒服。
     *
     * 另一种需要使用Inline Method 的情况使：你手上有一群组织不甚合理的函数，你可以将他们都内联
     * 到一个大型函数中，再从中提炼出组织合理的小型函数。Kent Beck发现，实施Replace Method with Method Object之前先这么做，往往可以
     * 获得不错的效果。
     * 你可以把所需要的函数（有着你要的行为）的所有调用对象的函数内容都内联到函数对象中，比起既要移动一个函数，又要移动
     * 所调用的其他所有函数，将整个大型函数作为整体来移动会比较简单。
     *
     * 如果别人使用了太多间接层，使得系统中所有的函数都似乎使对另一个函数的简单委托，造成我在这些委托动作之间晕头转向，那么我
     * 通常都会使用Inline Method，当然，间接层有其价值，但不是所有的间接层都有价值。
     * 试着使用内联手法，我可以找出那些有用的间接层，同时将那鞋无用的间接层去除。
     *
     */

    /**
     * 做法
     * 1。 检查函数，确定它具有多态性。
     *      如果子类继承了这个函数，就不要将此函数内联，因为子类无法覆写一个根本不存在的函数。
     * 2。 找出这个函数的所有被调用点。
     * 3。 将这个函数的所有被调用点都替换为函数本体。
     * 4。 编译，测试。
     * 5。 删除该函数的定义。
     */

}
