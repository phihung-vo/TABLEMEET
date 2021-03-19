package table_meet;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SignUpController implements Initializable {
    //SQL Init for SIGNUP
    private final Connection cn = dba.DBConnection.table_meetConnection();
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private Label lbEmailError;
    @FXML
    private JFXDatePicker dpkBirthday;
    @FXML
    private Label lbBirthdayError;
    @FXML
    private JFXTextField txtFirstName;
    @FXML
    private Label lbFirstnameError;
    @FXML
    private JFXTextField txtLastName;
    @FXML
    private JFXTextField txtPhoneNo;
    @FXML
    private JFXTextField txtAddress;
    @FXML
    private JFXPasswordField txtPassword1;
    @FXML
    private JFXPasswordField txtPassword2;
    @FXML
    private Label lbPassword1Error;
    @FXML
    private Label lbPassword2Error;
    @FXML
    private JFXButton btnSignUp;            
    @FXML
    private JFXButton btnBackSignIn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    @FXML
    public void handleClosePrimaryStage(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void clickFacebook(MouseEvent event) throws URISyntaxException, IOException {
        String url = "https://www.facebook.com";
        java.awt.Desktop.getDesktop().browse(new URI(url));
    }

    @FXML
    private void clickInstagram(MouseEvent event) throws URISyntaxException, IOException {
        String url = "https://www.instagram.com";
        java.awt.Desktop.getDesktop().browse(new URI(url));
    }

    @FXML
    private void handleBackSignIn(ActionEvent event) throws IOException{
        btnBackSignIn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
        Scene scene = new Scene(root);
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
    }
    
    @FXML
    private void handleSignUp(ActionEvent event) throws IOException {

        if (signUpRegister() == true) {
            if(TextFieldValidate.isValidEmail(txtEmail)&&TextFieldValidate.isPasswordMatched(txtPassword1, txtPassword2)&&TextFieldValidate.isBirthdayNotEmpty(dpkBirthday)&&TextFieldValidate.isTextFieldNotEmpty(txtFirstName)){
                
                String sql = "insert into account values(?, ?)";

                try {
                    ps = cn.prepareStatement(sql);
                    ps.setString(1, txtEmail.getText());
                    ps.setString(2, txtPassword1.getText());
                    ps.execute();
                } catch (SQLException e) {
                    errorDialog("Insert Account Unsuccessfully", null, null);
                    Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, e);
                }

                String sql2 = "insert into account_detail(email, birthday, firstname, lastname, phoneno, address, accountcreatedate) values(?,?,?,?,?,?,?)";
                DateFormat dateFormatID = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date today = new Date();

                try {
                    ps = cn.prepareStatement(sql2);
                    ps.setString(1, txtEmail.getText());
                    ps.setString(2, dpkBirthday.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    ps.setString(3, txtFirstName.getText());
                    ps.setString(4, txtLastName.getText());
                    ps.setString(5, txtPhoneNo.getText());
                    ps.setString(6, txtAddress.getText());
                    ps.setString(7, dateFormatID.format(today));
                    ps.execute();
                    
                    confirmDialog("Sign Up successfully. Back to Sign In window?", null, null);
                    
                } catch (SQLException e) {
                    errorDialog("Insert Account_Detail Unsuccessful", null, null);
                    Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, e);
                }
            }  
        }
    }

    public boolean signUpRegister() {

        boolean emailNotEmpty = TextFieldValidate.isTextFieldNotEmpty(txtEmail);
        boolean pass1NotEmpty = TextFieldValidate.isPasswordNotEmpty(txtPassword1);
        boolean pass2NotEmpty = TextFieldValidate.isPasswordNotEmpty(txtPassword2);
        boolean birthNotEmpty = TextFieldValidate.isBirthdayNotEmpty(dpkBirthday);
        boolean fnameNotEmpty = TextFieldValidate.isTextFieldNotEmpty(txtFirstName);
        boolean isValidMail = TextFieldValidate.isValidEmail(txtEmail);
        boolean isValidPassword = TextFieldValidate.isPasswordMatched(txtPassword1, txtPassword2);

        boolean tmp = false;

        if (!emailNotEmpty && !pass1NotEmpty && !pass2NotEmpty && !birthNotEmpty && !fnameNotEmpty) {
            TextFieldValidate.isTextFieldNotEmptyNote(txtEmail, lbEmailError, "Email not empty!");
            TextFieldValidate.isPasswordNotEmptyNote(txtPassword1, lbPassword1Error, "Pass not empty!");
            TextFieldValidate.isPasswordNotEmptyNote(txtPassword2, lbPassword2Error, "Not empty!");
            TextFieldValidate.isBirthdayNotEmptyNote(dpkBirthday, lbBirthdayError, "Birth not empty!");
            TextFieldValidate.isTextFieldNotEmptyNote(txtFirstName, lbFirstnameError, "Firstname not empty!");
            tmp = false;
        }
        if (emailNotEmpty) {
            if (!isValidMail) {
                lbEmailError.setText("Invalid Email");
                tmp = false;
            } else {
                lbEmailError.setText(null);
                txtEmail.getText();
                tmp = true;
            }
        }
        if (pass1NotEmpty && pass2NotEmpty) {
            if (!isValidPassword) {
                lbPassword1Error.setText("Password not matched");
                lbPassword2Error.setText(null);
                tmp = false;
            } else {
                lbPassword1Error.setText(null);
                lbPassword2Error.setText(null);
                tmp = true;
            }
        }
        if (birthNotEmpty) {
            lbBirthdayError.setText(null);
            tmp = true;
        }
        if (fnameNotEmpty) {
            lbFirstnameError.setText(null);
            tmp = true;
        }
        return tmp;
    }

    private void errorDialog(String info, String header, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(info);
        alert.setHeaderText(header);
        alert.showAndWait();
    }

    private void confirmDialog(String info, String header, String title) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(info);
        alert.setHeaderText(header);
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get()==ButtonType.OK){
            btnSignUp.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
            Scene scene = new Scene(root);
            Stage window = new Stage();
            window.setScene(scene);
            window.show();
        }else{
            alert.close();
            txtEmail.setText(null);
            txtPassword1.setText(null);
            txtPassword2.setText(null);
            dpkBirthday.setValue(null);
            txtFirstName.setText(null);
            txtLastName.setText(null);
            txtPhoneNo.setText(null);
            txtAddress.setText(null);
        }
    }
    
    public void goToSignIn(ActionEvent event) throws IOException{
        btnSignUp.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
        Scene scene = new Scene(root);
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
    }
}