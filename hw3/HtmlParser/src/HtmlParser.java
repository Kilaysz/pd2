import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.text.DecimalFormat;
public class HtmlParser {
    public static void main(String[] args) throws IOException {
        int mode = Integer.parseInt(args[0]);
        if(mode == 0){
        try {
            Document doc = Jsoup.connect("https://pd2-hw3.netdb.csie.ncku.edu.tw/").get();
            Elements stock_Name = doc.getElementsByTag("th");
            Elements stock_price = doc.getElementsByTag("td");
            boolean first_item = true;
            File file = new File("data.csv");
            long fileSize = file.exists()?Files.size(Paths.get("data.csv")):0;
            if(doc.title().equals("day1")){
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.csv", true))) {
                    if(fileSize!=0){writer.newLine();}
                    for (Element name : stock_Name) {
                        if(!first_item){
                            writer.append(" "); 
                        } else {
                            first_item = false;
                        }   
                        writer.append(name.text()); 
                    }
                        first_item = true;
                        writer.append("\n"); 
                    for(Element price : stock_price){
                        if(!first_item){
                            writer.append(" "); 
                        } else {
                            first_item = false;
                        }   
                        writer.append(price.text()); 
                    }
                    writer.close();
                }
            } else if(fileSize!=0){ 
                    first_item = true;
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.csv", true))) {
                        writer.append("\n"); 
                        for(Element price : stock_price){
                            if(!first_item){
                                writer.append(" "); 
                            } else {
                                first_item = false;
                            }   
                            writer.append(price.text()); 
                        }
                        writer.close();
                }
        }
            } catch (IOException e) {
                e.printStackTrace();
            }

    } else if(mode == 1){
        int task = Integer.parseInt(args[1]);
        File file = new File("output.csv");
        long fileSize = file.exists()?Files.size(Paths.get("output.csv")):0;
        if(task==0){
            try (BufferedReader reader = new BufferedReader(new FileReader("data.csv"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.csv"))) {
            String line;
            boolean firstLine = true;
            int i = 1;
            while (i<=31) {
                line = reader.readLine();
                if(line == null){break;}
                if (!firstLine) {
                    writer.append("\n");
                } else {
                    firstLine = false;
                }
               line = line.replaceAll("\\s", ",");
               writer.append(line);
               i++;
           }
           writer.close();
           reader.close();
       } catch (IOException e) {
           e.printStackTrace();
       }
        } else if(task == 1){
            String stock_name_task = args[2];
            int start = Integer.parseInt(args[3]);
            int end = Integer.parseInt(args[4]);
            double[] container = new double[end-start+1];
            try (BufferedReader reader = new BufferedReader(new FileReader("data.csv"))){
                String line;
                int k = 0;
                int index = 0; 
                for(int i = 0;i<=30;i++){
                    line = reader.readLine();
                    if(line == null){break;}
                    if(i>end){break;}
                    if(i==0){
                        String[] temptask  = line.split("\\s");
                        index = Arrays.asList(temptask).indexOf(stock_name_task);
                    }
                    if(i>=start&&i<=end){
                        String stock_task1 = line.split("\\s")[index];
                        container[k] = Double.parseDouble(stock_task1); 
                        k++;
                    }
                }
            reader.close();
       } catch (IOException e) {
           e.printStackTrace();
       }
       int j = 0;
       double sum = container[0]+container[1]+container[2]+container[3];
       double temp = 0.00d;
       double avg = 0.00d;
       DecimalFormat df = new DecimalFormat("0.0#");
       while(j<end-start-3){
            sum = sum - temp;
            sum += container[j+4];
            avg = sum/5;
            try(BufferedWriter writer = new BufferedWriter(new FileWriter("output.csv", true))){
                if(j==0){
                    if(fileSize!=0){writer.newLine();}
                    writer.append(stock_name_task+","+start+","+end+"\n");
                    writer.append(df.format(avg));
                } else {
                    writer.append(","+df.format(avg));
                }
                writer.close();
            }
                temp = container[j];
                j++;
        }
    } else if(task == 2){
        String stock_name_task = args[2];
        int start = Integer.parseInt(args[3]);
        int end = Integer.parseInt(args[4]);
        double[] container = new double[end-start+1];
        try (BufferedReader reader = new BufferedReader(new FileReader("data.csv"))){
            String line;
            int k = 0;
            int index = 0; 
            for(int i=0;i<=30;i++){
                line = reader.readLine();
                if(line == null){break;}
                if(i>end){break;}
                if(i==0){
                    String[] temptask  = line.split("\\s");
                    index = Arrays.asList(temptask).indexOf(stock_name_task);
                }
                if(i>=start&&i<=end){
                    String stock_task1 = line.split("\\s")[index];
                    container[k] = Double.parseDouble(stock_task1); 
                    k++;
                }
            }
        reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("output.csv", true))){
            if(fileSize!=0){writer.newLine();}
            writer.append(stock_name_task+","+start+","+end+"\n");
            double s = calculate.standardDeviation(container);
            DecimalFormat df = new DecimalFormat("0.0#");
            writer.append(df.format(s));
            writer.close();
        }
    } else if(task == 3){
        int start = Integer.parseInt(args[3]);
        int end = Integer.parseInt(args[4]);
        Map<String, Double> stockStdDevs = new HashMap<>();
        String[] stockNames = null;
        double[][] stockPrices = null;

        try (BufferedReader br = new BufferedReader(new FileReader("data.csv"))) {
            String line;
            int lineCount = 0;
            int k = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\\s");
                if (lineCount == 0) {
                    stockNames = values;
                    stockPrices = new double[stockNames.length][];
                    for (int i = 0; i < stockNames.length; i++) {
                        stockPrices[i] = new double[end-start+1];
                    }
                } else if(lineCount>=start&&lineCount<=end){
                    for (int i = 0; i < stockNames.length; i++) {
                        stockPrices[i][k] = Double.parseDouble(values[i]);
                    }
                    k++;
                }
                lineCount++;
            }
            double[] entryvalue = new double[stockNames.length];
            for (int i = 0; i < stockNames.length; i++) {
                double stdDev = calculate.standardDeviation(stockPrices[i]);
                stockStdDevs.put(stockNames[i], stdDev);
                entryvalue[i]=stdDev;
            }
            Arrays.sort(entryvalue);
            String[] keys = new String[3];
            int l = 0;
            for(int i=entryvalue.length-1;i>=entryvalue.length-3;i--){
                for (Map.Entry<String, Double> entry : stockStdDevs.entrySet()) {
                    if (entry.getValue().equals(entryvalue[i])) {
                        keys[l] = entry.getKey();
                        l++;
                        break;
                    }
                }
            }
            try(BufferedWriter writer = new BufferedWriter(new FileWriter("output.csv", true))){
                if(fileSize!=0){writer.newLine();}
                writer.append(keys[0]+","+keys[1]+","+keys[2]+","+start+","+end+"\n");
                DecimalFormat df = new DecimalFormat("0.##");
                writer.append(df.format(entryvalue[entryvalue.length-1])+","+df.format(entryvalue[entryvalue.length-2])+","+df.format(entryvalue[entryvalue.length-3]));
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    } else if(task == 4){
        String stock_name_task = args[2];
        double start = Double.parseDouble(args[3]);
        double end = Double.parseDouble(args[4]);
        double t_bar = (end+start)/2;
        double[] container = new double[(int)end-(int)start+1];
        try (BufferedReader reader = new BufferedReader(new FileReader("data.csv"))){
            String line;
            int k = 0;
            int index = 0; 
            for(int i = 0;i<=30;i++){
                line = reader.readLine();
                if(line == null){break;}
                if(i>end){break;}
                if(i==0){
                    String[] temptask  = line.split("\\s");
                    index = Arrays.asList(temptask).indexOf(stock_name_task);
                }
                if(i>=start&&i<=end){
                    String stock_task1 = line.split("\\s")[index];
                    container[k] = Double.parseDouble(stock_task1); 
                    k++;
                }
            }
        reader.close();
   } catch (IOException e) {
       e.printStackTrace();
   }
    double sum = 0.00d;
    for(double j:container){
        sum += j; 
    }
    double Y_bar = sum/(end-start+1);
    double numerator = 0.00d;
    double denominator = 0.00d;
    double t = start;
    for(double j:container){
        numerator += (t-t_bar)*(j-Y_bar);
        denominator += (t-t_bar)*(t-t_bar);
        t=t+1.0000d;
    }
    double b1 = numerator/denominator;
    double b0 = Y_bar-(b1*t_bar);
    try(BufferedWriter writer = new BufferedWriter(new FileWriter("output.csv", true))){
        if(fileSize!=0){writer.newLine();}
        writer.append(stock_name_task+","+(int)start+","+(int)end+"\n");
        DecimalFormat df = new DecimalFormat("0.0#");
        writer.append(df.format(b1)+","+df.format(b0));
        writer.close();
    }

    }
    
    }
}
}

class calculate {
    public static double standardDeviation(double[] container){
        double sum = 0.00d;
        for(double member:container){
            sum += member;
        }
        double avg = sum/(container.length);
        sum = 0.00d;
        for(double member : container){
            sum += (member-avg)*(member-avg);
        }
        double s = sum/(container.length-1);
        double guess = s/2;
        double error = 1e-8; // Desired precision
        double check = guess*guess-s;
        if(check<0.0000d){
            check *= -1;
        }
        while (check > error) {
            guess = (guess + s / guess) / 2;
            check = guess*guess-s;
            if(check<0.0000d){
                check *= -1;
            }
        }
        return guess;
    }
}