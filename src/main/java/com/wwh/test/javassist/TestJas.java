package com.wwh.test.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;

/**
 * <pre>
 * Javassist（Java Programming Assistant）使Java字节码操作变得简单。
 * 它是一个用于在Java中编辑字节码的类库; 它使Java程序能够在运行时定义新类，并在JVM加载时修改类文件。
 * 与其他类似的字节码编辑器不同，Javassist提供两个级别的API：源级别和字节码级别。
 * 如果用户使用源级API，他们可以编辑类文件而不需要了解Java字节码的规范。
 * 整个API仅使用Java语言的词汇表进行设计。
 * 您甚至可以以源文本的形式指定插入的字节码; 
 * Javassist即时编译它。另一方面，字节码级API允许用户直接编辑类文件作为其他编辑器。
 * </pre>
 *
 * @author wangwh
 * @date 2018年11月30日下午3:10:30
 * 
 */
public class TestJas {
    public static void main(String[] args) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get("com.wwh.test.javassist.Demo");

        // 添加属性
        CtField cf1 = new CtField(CtClass.intType, "age", ctClass);
        ctClass.addField(cf1);

        // 添加get set 方法
        ctClass.addMethod(CtNewMethod.setter("setAge", cf1));
        ctClass.addMethod(CtNewMethod.getter("getAge", cf1));

        // 添加方法
        CtMethod printMethod = CtMethod.make("public void printAge() {System.out.println(age);}", ctClass);
        ctClass.addMethod(printMethod);

        // 需要修改的方法名称
        String mname = "printMsg";
        CtMethod mold = ctClass.getDeclaredMethod(mname);
        // 修改原有的方法名称
        String nname = mname + "$impl";
        mold.setName(nname);

        // 创建新的方法，复制原来的方法
        CtMethod mnew = CtNewMethod.copy(mold, mname, ctClass, null);
        // 主要的注入代码
        StringBuffer body = new StringBuffer();
        body.append("{\nlong start = System.currentTimeMillis();\n");
        // 调用原有代码，类似于method();($$)表示所有的参数
        body.append("String ret =" + nname + "($$);\n");
        body.append("System.out.println(\"Call to method " + mname + " took \" +\n (System.currentTimeMillis()-start) + " + "\" ms.\");\n");

        body.append("return ret;}");
        // 替换新方法
        mnew.setBody(body.toString());
        // 增加新方法
        ctClass.addMethod(mnew);

        ctClass.writeFile();

        // 调用这个方法当前的类加载器会加载这个修改后的类
        ctClass.toClass();

        Demo test = new Demo();
        test.setMsg("测试。。。");

        test.printMsg();

    }
}
