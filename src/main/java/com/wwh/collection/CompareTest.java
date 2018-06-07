package com.wwh.collection;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author wwh
 * @date 2015年9月9日 上午10:19:39
 *
 */
public class CompareTest {

    public static void main(String[] args) {
        System.out.println("SSSSSSSSSSSSSSSSSSSSSS");
        TreeSet<Entity> tset = new TreeSet<Entity>(new Comparator<Entity>() {
            @Override
            public int compare(Entity o1, Entity o2) {
                    return 0 - o1.getId().compareTo(o2.getId());
            }
        });

        Entity e1 = new Entity();
        e1.setId(100l);
        Entity e2 = new Entity();
        e2.setId(50l);
        e2.setTag(2);
        Entity e3 = new Entity();
        e3.setId(50l);
        e3.setTag(0);
        Entity e4 = new Entity();
        e4.setId(60l);
        e4.setTag(1);
        Entity e5 = new Entity();
        e5.setId(60l);

        Entity e6 = new Entity();
        e6.setId(50l);
        e6.setTag(2);
        e6.setS("sss");
        
        tset.add(e1);
        tset.add(e2);
        tset.add(e3);
        tset.add(e4);
        tset.add(e5);
        tset.add(e6);

        for (Entity entity : tset) {
            System.out.println(entity);
        }
        
        System.out.println("第一个 id ："+ tset.first().getId());
    }
}

class Entity {
    private Long id;

    private Integer tag;

    private String s;

    @Override
    public String toString() {
        return "Entity [id=" + id + ", tag=" + tag + ", s=" + s + "]";
    }

    /**
     * 获取 id
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置 id
     *
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取 tag
     *
     * @return the tag
     */
    public Integer getTag() {
        return tag;
    }

    /**
     * 设置 tag
     *
     * @param tag
     *            the tag to set
     */
    public void setTag(Integer tag) {
        this.tag = tag;
    }

    /**
     * 获取 s
     *
     * @return the s
     */
    public String getS() {
        return s;
    }

    /**
     * 设置 s
     *
     * @param s
     *            the s to set
     */
    public void setS(String s) {
        this.s = s;
    }

}