package org.simplelang.llvm;

public class LLVMGenerator {

    public static String headerText = "";

    public static String mainText = "";

    public static int reg = 1;

    private static String declarePrintf = "declare i32 @printf(i8*, ...)\n";

    private static String declareScanf = "declare i32 @__isoc99_scanf(i8*, ...)\n";

    private static String printIntegerNewLine = "@strpi = constant [4 x i8] c\"%d\\0A\\00\"\n";

    private static String printFloatNewLine = "@strpd = constant [4 x i8] c\"%f\\0A\\00\"\n";

    static {
        headerText += declarePrintf;
        headerText += declareScanf;
        headerText += printIntegerNewLine;
        headerText += printFloatNewLine;
    }

    public static void printfString(String string) {
        int textLength = string.length() + 2;
        headerText += "@str-" + reg + " = constant [" + textLength + " x i8] c\"" + string + "\\0A\\00\"\n";
        mainText += "\t%" + reg + " = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([" + textLength + " x i8], [" + textLength + " x i8]* @str-" + reg + ", i64 0, i64 0))\n";
        reg++;
    }

    public static void printfI32(String number) {
        mainText += "\t%" + reg + " = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @strpi, i32 0, i32 0), i32 " + number + ")\n";
        reg++;
    }

    public static void printfDouble(String number) {
        mainText += "\t%" + reg + " = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @strpd, i32 0, i32 0), double " + number + ")\n";
        reg++;
    }

    public static void printfI32FromVariable(String id) {
        loadI32(id);
        mainText += "\t%" + reg + " = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @strpi, i32 0, i32 0), i32 %" + (reg - 1) + ")\n";
        reg++;
    }

    public static void printfDoubleFromVariable(String id) {
        loadDouble(id);
        mainText += "\t%" + reg + " = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @strpd, i32 0, i32 0), double %" + (reg - 1) + ")\n";
        reg++;
    }

    public static void declareI32(String id) {
        mainText += "\t%" + id + " = alloca i32\n";
    }

    public static void declareDouble(String id) {
        mainText += "\t%" + id + " = alloca double\n";
    }

    public static void assignI32(String id, String value) {
        mainText += "\tstore i32 " + value + ", i32* %" + id + "\n";
    }

    public static void assignDouble(String id, String value) {
        mainText += "\tstore double " + value + ", double* %" + id + "\n";
    }

    public static void addI32(String val1, String val2) {
        mainText += "\t%" + reg + " = add i32 " + val1 + ", " + val2 + "\n";
        reg++;
    }

    public static void addDouble(String val1, String val2) {
        mainText += "\t%" + reg + " = fadd double " + val1 + ", " + val2 + "\n";
        reg++;
    }

    public static void multI32(String val1, String val2) {
        mainText += "\t%" + reg + " = mul i32 " + val1 + ", " + val2 + "\n";
        reg++;
    }

    public static void multDouble(String val1, String val2) {
        mainText += "\t%" + reg + " = fmul double " + val1 + ", " + val2 + "\n";
        reg++;
    }

    public static void sitofp(String id) {
        mainText += "\t%" + reg + " = sitofp i32 " + id + " to double\n";
        reg++;
    }

    public static void fptosi(String id) {
        mainText += "\t%" + reg + " = fptosi double " + id + " to i32\n";
        reg++;
    }

    public static String generate() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(headerText)
                .append("define i32 @main() nounwind{\n")
                .append(mainText)
                .append("ret i32 0 }\n");

        return stringBuilder.toString();
    }

    public static void loadI32(String id) {
        mainText += "\t%" + reg + " = load i32, i32* %" + id + "\n";
        reg++;
    }

    public static void loadDouble(String id) {
        mainText += "\t%" + reg + " = load double, double* %" + id + "\n";
        reg++;
    }

}
