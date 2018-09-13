package com.wwh.test.collection;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author wwh
 * @date 2015年11月5日 下午5:09:11
 *
 */
public class TreeSetTest {

	public static void main(String[] args) {
		TreeSet< Integer> tset = new TreeSet<>(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o2.compareTo(o1);
			}
		});
		tset.add(5);
		tset.add(15);
		tset.add(0);
		tset.add(7);
		tset.add(3);
		
		System.out.println(tset);
	}
	
    public static void main1(String[] args) {
        TreeSet<obj> tset = new TreeSet<obj>(new Comparator<obj>() {
            @Override
            public int compare(obj o1, obj o2) {
                int i=  o1.getId().compareTo(o2.getId());
                if(i==0){
                    return o1.getTag().compareTo(o2.getTag());
                }else{
                    return i;
                }
            }
        });

        tset.add(new obj(1l, 1, "a"));
        tset.add(new obj(2l, 1, "a"));
        tset.add(new obj(3l, 1, "a"));

        tset.add(new obj(1l, 1, "b"));
        tset.add(new obj(2l, 2, "b"));
        tset.add(new obj(3l, 2, "b"));

        tset.add(new obj(1l, 1, "c"));
        tset.add(new obj(2l, 2, "c"));
        tset.add(new obj(3l, 3, "c"));

        System.out.println(tset.size());
        for (obj obj : tset) {
            System.out.println(obj);
        }

    }

    static class obj {
        private Long id;
        private Integer tag;
        private String str;

        public obj() {
        }

        public obj(Long id, Integer tag, String str) {
            this.id = id;
            this.tag = tag;
            this.str = str;
        }

        @Override
        public String toString() {
            return "obj [id=" + id + ", tag=" + tag + ", str=" + str + "]";
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Integer getTag() {
            return tag;
        }

        public void setTag(Integer tag) {
            this.tag = tag;
        }

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }
    }

}
