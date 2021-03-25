package manage_controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class manage_income_control implements Initializable{

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void day_chartAction(ActionEvent e) {
		try {
			Stage stage = new Stage(); //스테이지 생성
		
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/manage_income_dayChart.fxml"));
		
	
			Scene scene = new Scene(root);
			
			stage.setScene(scene);
			stage.setTitle("일별 수입 차트");
			stage.setResizable(false);
			stage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void month_chartAction(ActionEvent e) {
		try {
			Stage stage = new Stage(); //스테이지 생성
		
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/manage_income_monthChart.fxml"));
		
	
			Scene scene = new Scene(root);
			
			stage.setScene(scene);
			stage.setTitle("월별 수입 차트");
			stage.setResizable(false);
			stage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
