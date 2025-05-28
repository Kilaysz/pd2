package hw_pd2.hw2;
import java.io.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;


public class CodeGenerator2 {
    public static void main(String[] args) throws IOException {
         args = new String[]{"C:\\Users\\ASUS\\Desktop\\Coding\\Java\\hw_pd2\\hw2\\tc1.txt"};
         String fileName = args[0];
         try {
             Reader.read(fileName);
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
}

class Reader {
    public static ArrayList<ArrayList <String>> classcontent = new ArrayList<>();
    public static HashMap <String, ArrayList<String>> mapping = new HashMap<>();
    public static String accessModifier;
    public static String name;
    public static String classname;
    public static String parameter;
    public static String returnType;
    public static Pattern CLASS_BLOCK = Pattern.compile("\\s*class\\s+(\\w+)\\s*(\\{)*");
    public static Pattern ATTRIBUTE = Pattern.compile("\\s*(\\w*)\\s*:*\\s*([+-])+(\\w+[\\[\\<\\w*\\>\\]]*)\\s+(\\w+)");
    public static Pattern METHOD = Pattern.compile("\\s*(\\w*)\\s*:*\\s*([+-])(\\w+)\\s*(\\(.*?\\))\\s+(\\w*)");
    public static Pattern GETTER = Pattern.compile("\\s*(\\w*)\\s*:*\\s*([+-])+get(\\w+)\\(\\)+\\s*(\\w*)");
    public static Pattern SETTER = Pattern.compile("\\s*(\\w*)\\s*:*\\s*([+-])+set(\\w+)(\\(.*?\\))\\s+(\\w*)");
    public static ArrayList <String> class_temp = new ArrayList<>();
    public static void read(String file) throws IOException {  
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        String add;
        int i = 0;
        String currentClassName = "";
        HashMap <String, String> returnMethod = new HashMap<>();
        returnMethod.put("int", "return 0");
        returnMethod.put("String", "return \"\"");
        returnMethod.put("boolean", "return false");
        returnMethod.put("void", "");
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            Matcher match_class = CLASS_BLOCK.matcher(line);
            Matcher match_attribute = ATTRIBUTE.matcher(line);
            Matcher match_method = METHOD.matcher(line);
            Matcher getter = GETTER.matcher(line);
            Matcher setter = SETTER.matcher(line);
            if(line.contains("classDiagram")){
                continue;
            } else if(match_class.find()){
                currentClassName = match_class.group(1);
                if(mapping.containsKey(match_class.group(1))){
                    continue;
                } else {
                    classcontent.add(new ArrayList<String>());
                    class_temp.add("public class "+ match_class.group(1)+" {");
                    mapping.put(match_class.group(1), classcontent.get(i));
                    mapping.get(match_class.group(1)).addAll(class_temp);
                    i++;
                }
            } else if(getter.matches()){
                accessModifier =  getter.group(2).equals("+")? "public" : "private";
                name = getter.group(3);
                classname = getter.group(1).isEmpty()?currentClassName:getter.group(1);
                add = "    " + accessModifier  + " " + getter.group(4)+ " " + "get" + name + "()" + " {";
                class_temp.add(add);
                name = Character.toUpperCase(getter.group(3).charAt(0))+getter.group(3).substring(1);
                add = "        "+"return "+ name + ";";
                class_temp.add(add);
                class_temp.add("    "+"}");
                mapping.get(classname).addAll(class_temp);
            } else if(setter.matches()){
                accessModifier = setter.group(2).equals("+")? "public" : "private";
                name = setter.group(3);
                classname = setter.group(1).isEmpty()?currentClassName:setter.group(1);
                parameter = setter.group(4);
                returnType = setter.group(5);
                add = "    " + accessModifier  + " " + returnType + " " + "set" + name + parameter + " {";
                class_temp.add(add);
                name = Character.toLowerCase(setter.group(3).charAt(0))+setter.group(3).substring(1);
                add = "        "+ "this." + name + " = " + name + ";";
                class_temp.add(add);
                class_temp.add("    "+"}");
                mapping.get(classname).addAll(class_temp);
            } else if(match_method.matches()){
                accessModifier = match_method.group(2).equals("+")? "public" : "private";
                returnType = match_method.group(5).isEmpty()?"void":match_method.group(5);
                name = match_method.group(3);
                classname = match_method.group(1).isEmpty()?currentClassName:match_method.group(1);
                parameter = match_method.group(4);
                add = "    " + accessModifier + " " + returnType + " " + name + parameter +" "+ "{"+returnMethod.get(returnType)+";}";
                class_temp.add(add);
                mapping.get(classname).addAll(class_temp);
            } else if(match_attribute.matches()){
                accessModifier = match_attribute.group(2).equals("+")? "public" : "private";
                returnType = match_attribute.group(3);
                name = match_attribute.group(4);
                classname = match_attribute.group(1).isEmpty()?currentClassName:match_attribute.group(1);
                add = "    " + accessModifier +" "+ returnType+" "+name+";";
                class_temp.add(add);
                mapping.get(classname).addAll(class_temp);
            }
            class_temp.clear();
        }
            reader.close();
            MemberParse.write(classcontent);
        }
    }
    
    class MemberParse {
        public static void write(ArrayList<ArrayList <String>> classcontent) throws IOException {
            for (ArrayList<String> str : classcontent) {
                String className = str.get(0).split("\\s+")[2];
                for (String c : str) {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(className+ ".java", true))) {
                        writer.write(c + "\n");
                    }
                }
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(className + ".java", true))) {
                    writer.write("}");
                }
            }
        }
    }
