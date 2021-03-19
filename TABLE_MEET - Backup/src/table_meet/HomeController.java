package table_meet;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

public class HomeController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    @FXML
    private void handleClosePrimaryStage(ActionEvent event) {
    }
    
    @FXML
    private void handleSwitchAccount(ActionEvent event) {
    }

    @FXML
    private void handleSignOut(ActionEvent event) {
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


    
}
