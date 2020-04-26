package org.simplelang.llvm;

public class LLVMGenerator {

    public static String headerText = "";

    public static String mainText = "";

    public static int reg = 1;

    private static int bufferSize = 256;

    static {
        headerText += LLVMConstants.declarePrintf;
        headerText += LLVMConstants.declareScanf;
        headerText += LLVMConstants.printIntegerNewLine;
        headerText += LLVMConstants.printFloatNewLine;
        headerText += LLVMConstants.declareMalloc;
        headerText += LLVMConstants.declareStrcpy;
        headerText += LLVMConstants.declareRealloc;
        headerText += LLVMConstants.declareStrlen;
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

    public static void subI32(String val1, String val2) {
        mainText += "\t%" + reg + " = sub i32 " + val2 + ", " + val1 + "\n";
        reg++;
    }

    public static void subDouble(String val1, String val2) {
        mainText += "\t%" + reg + " = fsub double " + val2 + ", " + val1 + "\n";
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

    public static void divI32(String val1, String val2) {
        mainText += "\t%" + reg + " = sdiv i32 " + val2 + ", " + val1 + "\n";
        reg++;
    }

    public static void divDouble(String val1, String val2) {
        mainText += "\t%" + reg + " = fdiv double " + val2 + ", " + val1 + "\n";
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
                .append("\tret i32 0 \n}\n");

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

    public static void declareAndAssignString(String variableName, String stringValue) {
        String funcName = "@str-" + reg;
        headerText += funcName + " = private unnamed_addr constant [" + (stringValue.length() + 1) + " x i8] c\"" + stringValue + "\\00\"\n";

        // char *string;
        mainText += "\t%" + reg + " = alloca i32\n";
        reg++;
        mainText += "\t%" + variableName + " = alloca i8*\n";
        mainText += "\tstore i32 0, i32* %" + (reg - 1) + "\n";

        // string = (char *) malloc((6+1)*sizeof(char));
        mainText += "\t%" + reg + " = call noalias i8* @malloc(i64 " + (stringValue.length() + 1) + ")\n";
        mainText += "\tstore i8* %" + reg + ", i8** %" + variableName + "\n";
        reg++;

        // strcpy(string, "string");
        mainText += "\t%" + reg + " = load i8*, i8** %" + variableName + "\n";
        reg++;
        mainText += "\t%" + reg + " = call i8* @strcpy(i8* %" + (reg - 1) + ", i8* getelementptr inbounds ([" + (stringValue.length() + 1) + " x i8], [" + (stringValue.length() + 1) + " x i8]* " + funcName + ", i64 0, i64 0))\n";
        reg++;
    }

    public static void printfStringByVariableName(String variableName) {
        String functName = "@str-" + reg;

        headerText += functName + " = private unnamed_addr constant [4 x i8] c\"%s\\0A\\00\"\n";
        mainText += "\t%" + reg + " = load i8*, i8** %" + variableName + "\n";
        reg++;
        mainText += "\t%" + reg + " = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* " + functName + ", i64 0, i64 0), i8* %" + (reg - 1) + ")\n";
        reg++;
    }

    public static void reasignString(String variableName, String stringValue) {
        String funcName = "@str-" + reg;
        headerText += funcName + " = private unnamed_addr constant [" + (stringValue.length() + 1) + " x i8] c\"" + stringValue + "\\00\"\n";

        // if(strlen(string)<(7+1))
        mainText += "\t%" + reg + " = load i8*, i8** %" + variableName + "\n";
        reg++;
        mainText += "\t%" + reg + " = call i64 @strlen(i8* %" + (reg - 1) + ")\n";
        reg++;
        mainText += "\t%" + reg + " = icmp ult i64 %" + (reg - 1) + ", " + (stringValue.length() + 1) + "\n";
        reg++;
        mainText += "\tbr i1 %" + (reg - 1) + ", label %" + reg + ", label %" + (reg + 3) + "\n";

        int pred = reg;

        // string3 = (char *) realloc(string3, (6+1)*sizeof(char));
        mainText += "\t" + pred + ":                                                ; preds = %0\n";
        reg++;
        mainText += "\t\t%" + reg + " = load i8*, i8** %" + variableName + "\n";
        reg++;
        mainText += "\t\t%" + reg + " = call i8* @realloc(i8* %" + (reg - 1) + ", i64 " + (stringValue.length() + 1) + ")\n";
        reg++;
        mainText += "\t\tstore i8* %" + (reg - 1) + ", i8** %" + variableName + "\n";
        mainText += "\t\tbr label %" + reg + "\n";

        // strcpy(string, "string2");
        mainText += "\t" + reg + ":                                               ; preds = %" + pred + ", %0\n";
        reg++;
        mainText += "\t\t%" + reg + " = load i8*, i8** %" + variableName + "\n";
        reg++;
        mainText += "\t\t%" + reg + " = call i8* @strcpy(i8* %" + (reg - 1) + ", i8* getelementptr inbounds ([" + (stringValue.length() + 1) + " x i8], [" + (stringValue.length() + 1) + " x i8]* " + funcName + ", i64 0, i64 0))\n";
        reg++;
    }

    public static void scanf(String variableName) {
        String functName = "@str-" + reg;
        headerText += functName + " = private unnamed_addr constant [3 x i8] c\"%s\\00\"\n";

        // char *string;
        mainText += "\t%" + reg + " = alloca i32\n";
        reg++;
        mainText += "\t%" + variableName + " = alloca i8*\n";
        mainText += "\tstore i32 0, i32* %" + (reg - 1) + "\n";

        // string = (char *) malloc((6+1)*sizeof(char));
        mainText += "\t%" + reg + " = call noalias i8* @malloc(i64 " + bufferSize + ")\n";
        mainText += "\tstore i8* %" + reg + ", i8** %" + variableName + "\n";
        reg++;

        //scanf("%s",x);
        mainText += "\t%" + reg + " = load i8*, i8** %" + variableName + "\n";
        reg++;
        mainText += "\t%" + reg + " = call i32 (i8*, ...) @__isoc99_scanf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* " + functName + ", i64 0, i64 0), i8* %" + (reg - 1) + ")\n";
        reg++;
    }
}
