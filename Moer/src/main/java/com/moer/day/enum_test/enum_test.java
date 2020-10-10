package com.moer.day.enum_test;

import java.util.Arrays;

public class enum_test {


    public enum Week{
        ONE(0),
        TOW(1),
        THREE(2),
        FOUR(33),
        FREE(4);

        Week() {

        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        private  int code;
        Week(int value){
            this.code = value;
        }

        public static void main(String[] args) {

            System.out.println(Week.FOUR.ordinal());
            System.out.println(Week.FOUR.code);
            System.out.println(Week.FOUR.getCode());
            System.out.println(Week.valueOf("FREE").code);
            System.out.println(Week.FOUR.compareTo(FREE));
            Week.FOUR.setCode(27);
            System.out.println(Week.FOUR.code);



            Class aa = FOUR.getDeclaringClass();
            if (aa.isEnum()){
                Week [] weeks = (Week[]) aa.getEnumConstants();
                System.out.println(Arrays.toString(weeks));

            }
            try {
                System.out.println(aa.getMethod("getCode"));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        }
    }

}
