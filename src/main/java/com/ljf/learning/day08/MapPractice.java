package com.ljf.learning.day08;
import java.util.HashMap;
import java.util.Map;

public class MapPractice {
    public static void main(String[] args) {
        Map<String, Integer> scores =
                new HashMap<>();

        scores.put("张三", 85);
        scores.put("李四", 90);
        scores.put("王五", 78);

        scores.put("赵六",88);
        Integer oldScore01 = scores.put("李四",96);
        System.out.println("李四旧成绩：" + oldScore01);
        System.out.println("李四新成绩：" + scores.get("李四"));

        Integer oldScore = scores.put("张三", 95);
        System.out.println("张三旧成绩：" + oldScore);
        System.out.println("张三新成绩：" + scores.get("张三"));

        System.out.println("Map大小：" + scores.size());
        System.out.println("包含李四：" + scores.containsKey("李四"));
        System.out.println("不存在的学生：" + scores.get("不存在"));

        if(scores.containsKey("赵六")){
            System.out.println("赵六成绩：" + scores.get("赵六"));
        }else {
            System.out.println("查无此人");
        }

        if(scores.containsKey("钱七")){
            System.out.println("钱七成绩：" + scores.get("钱七"));
        }else{
            System.out.println("查无此人");
        }

        Integer removedScore01 = scores.remove("王五");
        System.out.println("删除王五的打印值：" + removedScore01);

        Integer removedScore02 = scores.remove("钱七");
        System.out.println("删除钱七的返回值：" + removedScore02);
        System.out.println("删除后的map大小：" + scores.size());


        int fullValue = 0;
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            fullValue += entry.getValue();

        }
        System.out.println("总成绩：" + fullValue);
    }
}