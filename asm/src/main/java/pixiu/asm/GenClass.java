package pixiu.asm;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 生成class文件
 */
public class GenClass {
    public static void main(String[] args) throws IOException {
        genClass();
        genInterface();
    }

    private static void genClass() throws IOException {
        //生成一个类只需要ClassWriter组件即可
        ClassWriter cw = new ClassWriter(0);
        //通过visit方法确定类的头部信息
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC,
                "asm/jzw/Hello", null, "java/lang/Object", null);

        // 定义类的属性
        cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC,
                "STR", "java/lang/String", null, "hello world").visitEnd();

        // 定义成员的属性
        cw.visitField(Opcodes.ACC_PUBLIC,
                "mem", "I", null, new Integer(1)).visitEnd();

        //定成员的方法
        cw.visitMethod(Opcodes.ACC_PUBLIC, "helloWorld",
                "(Ljava/lang/String;I)java/lang/String", null, null).visitEnd();

        //使cw类已经完成
        cw.visitEnd();

        // 生成字节码
        byte[] data = cw.toByteArray();
        //将cw转换成字节数组写到文件里面去

        File file = new File("Hello.class");
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(data);
        fout.close();
    }

    private static void genInterface() throws IOException {
        //生成一个类只需要ClassWriter组件即可
        ClassWriter cw = new ClassWriter(0);
        //通过visit方法确定类的头部信息
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT + Opcodes.ACC_INTERFACE,
                "com/asm3/Comparable", null, "java/lang/Object", new String[]{"com/asm3/Mesurable"});
        //定义类的属性
        cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC,
                "LESS", "I", null, new Integer(-1)).visitEnd();
        cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC,
                "EQUAL", "I", null, new Integer(0)).visitEnd();
        cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC,
                "GREATER", "I", null, new Integer(1)).visitEnd();
        //定义类的方法
        cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT, "compareTo",
                "(Ljava/lang/Object;)I", null, null).visitEnd();
        cw.visitEnd(); //使cw类已经完成
        //将cw转换成字节数组写到文件里面去
        byte[] data = cw.toByteArray();
        File file = new File("Comparable.class");
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(data);
        fout.close();
    }




}
