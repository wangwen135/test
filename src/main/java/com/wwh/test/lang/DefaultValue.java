package com.wwh.test.lang;

public class DefaultValue {

    private Long priority;

    private Boolean binaryContent;

    public static void main(String[] args) {
        DefaultValue dv = new DefaultValue();
        System.out.println(dv.getPriority());

        System.out.println(dv.isBinaryContent());

    }

    public void setPriority(long priority) {
        this.priority = priority;

    }

    public long getPriority() {
        return priority;
    }

    public boolean isBinaryContent() {
        return binaryContent;
    }

    public void setBinaryContent(boolean binaryContent) {
        this.binaryContent = binaryContent;

    }
}
