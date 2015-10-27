package com.wwh.validator;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author wwh
 * @date 2015年8月25日 下午1:33:16
 *
 */
public class PEntity {

    @NotNull(message = "姓名不能为空")
    private String name;

    @NotNull(message = "性别不能为空")
    private Integer sex;

    private Integer age;

    @NotNull(message = "地址不能为空", groups = { GroupDefaultValue.class })
    private String address;

    /**
     * 获取 name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置 name
     *
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取 sex
     *
     * @return the sex
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设置 sex
     *
     * @param sex
     *            the sex to set
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 获取 age
     *
     * @return the age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 设置 age
     *
     * @param age
     *            the age to set
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 获取 address
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置 address
     *
     * @param address
     *            the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

}
