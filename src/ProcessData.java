import java.io.*;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessData {
    public static void main(String[] args) {
        //String pattern = "\"(Spain|Italy|Germany|France)\"";
        //Pattern r = Pattern.compile(pattern);
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("src/airports.dat"));
            String read = null;
            while ((read = in.readLine()) != null) {
                String[] splited = read.split(",");
                if (splited[3].equals("\"Spain\"")|| splited[3].equals("\"Italy\"") || splited[3].equals("\"France\"") || splited[3].equals("\"Germany\""))
                {
                    //FIXME Still need to implement the node adding.
                    //Use the openflights ID value for the airport identifier.
                    System.out.println(splited[1]+" "+splited[0]);
                }
            }
            in = new BufferedReader(new FileReader("src/routes.dat"));
            while ((read = in.readLine()) != null) {
                String[] splited = read.split(",");
                //FIXME Still need to check if origin and arrival nodes exist and create the edge.
                //Use the openflights ID value.
                System.out.println("Origin: "+splited[3]+", Arrival: "+splited[5]);
            }
        } catch (IOException e) {
            System.out.println("There was a problem: " + e);
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (Exception e) {
            }
        }
    }
}
