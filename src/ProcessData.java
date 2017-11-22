import java.io.*;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessData {
    public static void main(String[] args) {
        String pattern = "\"(Spain|Italy|Germany|France)\"";

        Pattern r = Pattern.compile(pattern);

        try {
            FileInputStream fstream = new FileInputStream("airports.dat");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            FileOutputStream outputStream = new FileOutputStream("processd.dat");
            String strLine;

            while ((strLine = br.readLine()) != null)   {
                // Print the content on the console
                Matcher m = r.matcher(strLine);
                if(m.find() && m.groupCount() != 0){
                    System.out.println(strLine);
                    outputStream.write((strLine+"\n").getBytes(Charset.forName("UTF-8")));
                }
//                System.out.println (strLine);
            }

        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
