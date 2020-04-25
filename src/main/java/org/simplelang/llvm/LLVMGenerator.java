package org.simplelang.llvm;

public class LLVMGenerator {

    public static String headerText = "";

    public static String mainText = "";

    public static int reg = 1;


    public static void printfI32(String id) {
        loadI32(id);
        mainText += "%" + reg + " = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @strpi, i32 0, i32 0), i32 %" + (reg - 1) + ")\n";
        reg++;
    }

    public static void printfDouble(String id) {
        loadDouble(id);
        mainText += "%" + reg + " = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @strpd, i32 0, i32 0), double %" + (reg - 1) + ")\n";
        reg++;
    }

    public static void declareI32(String id) {
        mainText += "%" + id + " = alloca i32\n";
    }

    public static void declareDouble(String id) {
        mainText += "%" + id + " = alloca double\n";
    }

    public static void assignI32(String id, String value) {
        mainText += "store i32 " + value + ", i32* %" + id + "\n";
    }

    public static void assignDouble(String id, String value) {
        mainText += "store double " + value + ", double* %" + id + "\n";
    }

    public static void loadI32(String id) {
        mainText += "%" + reg + " = load i32, i32* %" + id + "\n";
        reg++;
    }

    public static void loadDouble(String id) {
        mainText += "%" + reg + " = load double, double* %" + id + "\n";
        reg++;
    }

    public static void addI32(String val1, String val2) {
        mainText += "%" + reg + " = add i32 " + val1 + ", " + val2 + "\n";
        reg++;
    }

    public static void addDouble(String val1, String val2) {
        mainText += "%" + reg + " = fadd double " + val1 + ", " + val2 + "\n";
        reg++;
    }

    public static void multI32(String val1, String val2) {
        mainText += "%" + reg + " = mul i32 " + val1 + ", " + val2 + "\n";
        reg++;
    }

    public static void multDouble(String val1, String val2) {
        mainText += "%" + reg + " = fmul double " + val1 + ", " + val2 + "\n";
        reg++;
    }

    public static void sitofp(String id) {
        mainText += "%" + reg + " = sitofp i32 " + id + " to double\n";
        reg++;
    }

    public static void fptosi(String id) {
        mainText += "%" + reg + " = fptosi double " + id + " to i32\n";
        reg++;
    }

    public static String generate() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("declare i32 @printf(i8*, ...)\n")
                .append("declare i32 @__isoc99_scanf(i8*, ...)\n")
                .append("@strpi = constant [4 x i8] c\"%d\\0A\\00\"\n")
                .append("@strpd = constant [4 x i8] c\"%f\\0A\\00\"\n")
                .append("@strs = constant [3 x i8] c\"%d\\00\"\n")
                .append(headerText)
                .append("define i32 @main() nounwind{\n")
                .append(mainText)
                .append("ret i32 0 }\n");

        return stringBuilder.toString();
    }

}
