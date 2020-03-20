package com.sxzhongf.deep.in.dubbo.generic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Test {

    public static class SortObj {

        private String B;

        public String getB() {
            return B;
        }

        public void setB(String b) {
            B = b;
        }

        public List<SortObj2> obj2List;

        public SortObj(String b, List<SortObj2> obj2List) {
            B = b;
            this.obj2List = obj2List;
        }

        public List<SortObj2> getObj2List() {
            return obj2List;
        }

        public void setObj2List(List<SortObj2> obj2List) {
            this.obj2List = obj2List;
        }
    }

    public static class SortObj2 {

        private String A;

        public String getA() {
            return A;
        }

        public void setA(String a) {
            A = a;
        }

        public SortObj2(String a) {
            A = a;
        }
    }

    public static void main(String[] args) {
        List<SortObj> a = new ArrayList<>();
        List<SortObj2> obj2List = new ArrayList<>();
        obj2List.add(new SortObj2("A"));
        obj2List.add(new SortObj2("F"));
        obj2List.add(new SortObj2("B"));
        obj2List.add(new SortObj2("E"));
        obj2List.add(new SortObj2("D"));
        obj2List.add(new SortObj2("C"));
        obj2List.add(new SortObj2("U"));
        a.add(new SortObj("G", obj2List));
        a.add(new SortObj("J", obj2List));
        a.add(new SortObj("O", obj2List));
        a.add(new SortObj("P", obj2List));
        a.add(new SortObj("A", obj2List));
        a.add(new SortObj("F", obj2List));
        a.add(new SortObj("H", obj2List));

        a.stream().sorted(
            Comparator.comparing(SortObj::getB)
        ).forEach(sortObj2 -> {
                List<SortObj2> s = sortObj2.getObj2List();
                s.sort(Comparator.comparing(SortObj2::getA));
            }
        );
        a.forEach(i -> {
            System.out.println(i.getB());
            i.getObj2List().forEach(
                o -> {
                    System.out.println("=====" + o.getA());
                }
            );
        });

    }
}
