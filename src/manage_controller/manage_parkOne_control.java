package manage_controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class manage_parkOne_control implements Initializable {


    @FXML
    private Button close;

    @FXML
    private TextField carNum;

    @FXML
    private Label carIn;

    @FXML
    private Label price;

  
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	public void show(String carNumber,String date, int money) {
		carNum.setText(carNumber);
		carIn.setText(date);
		price.setText(String.valueOf(money));
	
	}
	public void closeAction(ActionEvent event) {
		close.getScene().getWindow().hide();
	 }
}
