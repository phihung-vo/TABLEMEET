package table_meet;

import com.jfoenix.controls.JFXButton;
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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
//import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
//import sun.plugin2.message.transport.Transport;

public class SignInController implements Initializable {
    //SQL Init for SIGNIN
    private final Connection cn = dba.DBConnection.table_meetConnection();
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXButton btnSignIn;
    @FXML
    private JFXButton btnSignUp;
    @FXML
    private JFXButton btnForgotPassword;
    @FXML
    private Label lbInvalid;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    @FXML
    public void handleClosePrimaryStage(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

//    private void errorDialog(String info, String header, String title) {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setContentText(info);
//        alert.setHeaderText(header);
//        alert.showAndWait();
//    }

//    private void warningDialog(String info, String header, String title) {
//        Alert alert = new Alert(Alert.AlertType.WARNING);
//        alert.setContentText(info);
//        alert.setHeaderText(header);
//        alert.showAndWait();
//    }

    private void changePasswordDialog() throws IOException {

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Reset Password");
        dialog.setHeaderText("Update your new password");

        ButtonType changeButton = new ButtonType("Change", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(changeButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 50, 10, 10));

        JFXTextField jfEmail = new JFXTextField();
        jfEmail.setMinWidth(250);
        jfEmail.setPromptText("Your Email");
        JFXPasswordField pfPassword = new JFXPasswordField();
        pfPassword.setPromptText("New Password");
        JFXPasswordField pfPasswordConfirm = new JFXPasswordField();
        pfPasswordConfirm.setPromptText("Confirm New Password");

        grid.add(new Label("Email:"), 0, 0);
        grid.add(jfEmail, 1, 0);
        grid.add(new Label("New Password:"), 0, 1);
        grid.add(pfPassword, 1, 1);
        grid.add(new Label("Confirm New Password:"), 0, 2);
        grid.add(pfPasswordConfirm, 1, 2);
        ButtonType loginButtonType;

//        Node loginButton = dialog.getDialogPane().lookupButton(changeButton);
//        loginButton.setDisable(true);
//        jfEmail.textProperty().addListener((observable, oldValue, newValue) -> {
//            loginButton.setDisable(newValue.trim().isEmpty());
//        });
        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> jfEmail.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == changeButton) {
                return new Pair<>(jfEmail.getText(), pfPassword.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
        });

    }

    public void goToHome(ActionEvent event) throws IOException {
        btnSignIn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Scene scene = new Scene(root);
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
    }

    public boolean validSignIn(ActionEvent event) throws IOException {                       
        
        boolean valid = false,
        emailNotEmpty = TextFieldValidate.isTextFieldNotEmpty(txtEmail),
        passNotEmpty  = TextFieldValidate.isPasswordNotEmpty(txtPassword),
        isValidMail = TextFieldValidate.isValidEmail(txtEmail);                
        
        if(!emailNotEmpty || !passNotEmpty){
            
            lbInvalid.setText("Email and Password must not be empty!");
            valid = false;
        }else{
            if(!isValidMail){
                lbInvalid.setText(null);
                lbInvalid.setText("Your email is not the right format!");
                valid = false;
            }else{
                lbInvalid.setText(null);
                String sql = "SELECT EMAIL, PASSWORD FROM ACCOUNT_DETAIL WHERE EMAIL = ? AND PASSWORD = ?";
                try {
                    ps = cn.prepareStatement(sql);
                    ps.setString(1, txtEmail.getText());
                    ps.setString(2, txtPassword.getText());
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        System.out.println("Sign In Right Info");
                        valid = true;
                    } else {
                        System.out.println("Sign In Wrong Info");                        
                        lbInvalid.setText("Your email or password is incorrect. Retry!");
                        valid = false;
                    }
                } catch (SQLException e) {
                    System.out.println("Khong the tien hanh kiem tra tai khoan");
                    Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }                       
        return valid;
    }

    @FXML
    public void handleSignIn(ActionEvent event) throws IOException {
        if(validSignIn(event)){ goToHome(event); }
    }

    @FXML
    public void handleGoToSignUp(ActionEvent event) throws IOException {
        btnSignUp.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        Scene scene = new Scene(root);
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
    }
    
    @FXML
    private void handleForgotPassword(ActionEvent event) throws IOException {        
        changePasswordDialog();
    }

    @FXML
    private void handleClickFacebook(MouseEvent event) throws URISyntaxException, IOException {
        String url = "https://www.facebook.com";
        java.awt.Desktop.getDesktop().browse(new URI(url));
    }

    @FXML
    private void handleClickInstagram(MouseEvent event) throws URISyntaxException, IOException {
        String url = "https://www.instagram.com";
        java.awt.Desktop.getDesktop().browse(new URI(url));
    }
}




//----------------------------
//    public static void sendMail(String recepient) throws Exception {
//        System.out.println("Preparing to send email");
//        Properties properties = new Properties();
//
//        //Enable authentication
//        properties.put("mail.smtp.auth", "true");
//        //Set TLS encryption enabled
//        properties.put("mail.smtp.starttls.enable", "true");
//        //Set SMTP host
//        properties.put("mail.smtp.host", "smtp.gmail.com");
//        //Set smtp port
//        properties.put("mail.smtp.port", "587");
//
//        //Your gmail address
//        String myAccountEmail = "xxxxxxxxx@gmail.com";
//        //Your gmail password
//        String password = "xxxxxxxx";
//
//        //Create a session with account credentials
//        Session session;
//        session = Session.getInstance(properties, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(myAccountEmail, password);
//            }
//        });
//
//        //Prepare email message
//        Message message = prepareMessage(session, myAccountEmail, recepient);
//
//        //Send mail
//        Transport.send(message);
//        System.out.println("Message sent successfully");
//    }

//    private static Message prepareMessage(Session session, String myAccountEmail, String recepient) {
//        try {
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(myAccountEmail));
//            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
//            message.setSubject("My First Email from Java App");
//            String htmlCode = "<h1> WE LOVE JAVA </h1> <br/> <h2><b>Next Line </b></h2>";
//            message.setContent(htmlCode, "text/html");
//            return message;
//        } catch (Exception ex) {
//            Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }