package table_meet;

import static javafx.application.Application.launch;
import javafx.stage.Stage;


public class Test{ 
  
    public void start(Stage s) { 
        String countryCode = "sfsdfd,sđs,aaaa";
        int i=0; char[] tmp = new char[7];
        for(int j=0;j<countryCode.length();j++){
            if(countryCode.charAt(j)==','){ System.out.println("a");
                System.out.println(tmp); System.out.println("a");
                i=0;
            }
            if(countryCode.charAt(j)!=','){
                tmp[i] = countryCode.charAt(j);
            }
        }
    } 
  
    public static void main(String args[]) {
        launch(args); 
    } 
} 

