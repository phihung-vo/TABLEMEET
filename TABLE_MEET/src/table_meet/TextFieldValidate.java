package table_meet;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Label;

public class TextFieldValidate {
    
    public static boolean isTextFieldNotEmpty(JFXTextField tf){
        boolean tmp = false;
        if(tf.getText().length()!=0 || !tf.getText().isEmpty()){
            tmp = true;
        }
        return tmp;
    }
    
    public static boolean isTextFieldNotEmptyNote(JFXTextField tf, Label lb, String errorMessage){
        boolean tmp = true;
        String msg = null;
        tf.getStyleClass().remove("error");
        if(!isTextFieldNotEmpty(tf)){
            tmp = false;
            msg = errorMessage;
            tf.getStyleClass().add("error");
        }
        lb.setText(msg);
        return tmp;
    }
    
    public static boolean isPasswordNotEmpty(JFXPasswordField pf){
        boolean tmp = false;
        if(pf.getText().length()!=0 || !pf.getText().isEmpty()){
            tmp = true;
        }
        return tmp;
    }
    
    public static boolean isPasswordNotEmptyNote(JFXPasswordField pf, Label lb, String errorMessage){
        boolean tmp = true;
        String msg = null;
        pf.getStyleClass().remove("error");
        if(!isPasswordNotEmpty(pf)){
            tmp = false;
            msg = errorMessage;
            pf.getStyleClass().add("error");
        }
        lb.setText(msg);
        return tmp;
    }
    
    public static boolean isValidEmail(JFXTextField tf){
        boolean tmp = false;
        String pattern = "\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        if(tf.getText().matches(pattern)){
            tmp = true;
        }
        return tmp;
    }
    
    public static boolean isValidEmailNote(JFXTextField tf, Label lb, String errorMessage){
        boolean tmp = true;
        String msg = null;
        tf.getStyleClass().remove("error");
        if(!isValidEmail(tf)){
            tmp = false;
            msg = errorMessage;
            tf.getStyleClass().add("error");
        }
        lb.setText(msg);
        return tmp;
    }
    
    public static boolean isPasswordMatched(JFXPasswordField pf1, JFXPasswordField pf2){
        boolean tmp = false;
        if(pf1.getText().equals(pf2.getText())){
            tmp = true;
        }
        return tmp;
    }
    
    public static boolean isPasswordMatchedNote(JFXPasswordField pf1, JFXPasswordField pf2, Label lb, String errorMessage){
        boolean tmp = true;
        String msg = null;
        pf2.getStyleClass().remove("error");
        if(!isPasswordMatched(pf1, pf2)){
            tmp = false;
            msg = errorMessage;
            pf2.getStyleClass().add("error");
        }
        lb.setText(msg);
        return tmp;
    }
    
    public static boolean isBirthdayNotEmpty(JFXDatePicker dpk){
        boolean tmp = false;
        if(dpk.getValue()!=null){
            tmp = true;
        }
        return tmp;
    }
    
    public static boolean isBirthdayNotEmptyNote(JFXDatePicker dpk, Label lb, String errorMessage){
        boolean tmp = true;
        String msg = null;
        dpk.getStyleClass().remove("error");
        if(!isBirthdayNotEmpty(dpk)){
            tmp = false;
            msg = errorMessage;
            dpk.getStyleClass().add("error");
        }
        lb.setText(msg);
        return tmp;
    }
    
}