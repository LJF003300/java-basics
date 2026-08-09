package com.ljf.learning.review.week01;

public class ProductReviewDemo {
    public static void main(String[] args) {
        Product product1 = new Product("键盘",199.5,10);
        Product product2 = new Product("鼠标",99,20);

        System.out.println("键盘库存修改为8：" + product1.setStock(8));
        System.out.println("键盘库存修改为-1：" + product1.setStock(-1));
        System.out.println("键盘库存现在为：" + product1.getStock());
        System.out.println("键盘总价值：" + product1.calculateTotalValue());
        System.out.println("鼠标总库存：" + product2.getStock());
        System.out.println("鼠标总价值：" + product2.calculateTotalValue());

        System.out.println(
                "价格修改为0："
                        + product1.setPrice(0)
        );

        System.out.println(
                "库存修改为0："
                        + product2.setStock(0)
        );

        System.out.println(
                "鼠标当前库存："
                        + product2.getStock()
        );

        System.out.println(
                "鼠标当前库存总价值："
                        + product2.calculateTotalValue()
        );
    }
}
