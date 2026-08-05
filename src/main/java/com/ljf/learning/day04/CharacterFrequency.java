package com.ljf.learning.day04;

public class CharacterFrequency {
    public static void main(String[] args) {
        String text = "banana";
        int[] counts = new int[26];

        for(int i =0; i < text.length();i++){
            char current = text.charAt(i);

            if(current >= 'a' && current <= 'z'){
                int index = current - 'a';
                counts[index]++;//counts[]数组中0对应a,计数
            }
        }

        for(int i = 0; i <counts.length; i++){
            if(counts[i]>0){
                char letter = (char)('a' + i);
                System.out.println(letter + ":" + counts[i] + "次");
            }
        }

    }

}
