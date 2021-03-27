package basic_controller;

import constructor.Car;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class basic_inNum_control implements Initializable{

    @FXML
    private Button submit;

    @FXML
    private TextField carNum;

    @FXML
    private Label msg;
    
    
	public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {
		
	};
	
	public void submitAction(ActionEvent e3) {
		String carNumber = carNum.getText();

		if(carNumber.length()==7) {
				
			try { 
				//차 앞번호 자르기
				int first = Integer.parseInt(carNumber.substring(0, 2)); //0번째 인덱스부터 2개
				char middle = carNumber.charAt(2);
				int last = Integer.parseInt(carNumber.substring(3));
				
				carNum.getScene().getWindow().hide();
				
				Stage stage = new Stage(StageStyle.UTILITY);
				stage.initModality(Modality.WINDOW_MODAL); //현재 창 고정???
				stage.initOwner(carNum.getScene().getWindow());
				
				//차 있음으로 객체의 bool값 변경
				Car connect = Car.getInstance();
				connect.isCar = true;
				
				FXMLLoader loader = new FXMLLoader();

				loader.setLocation(getClass().getResource("/FXML/basic_park.fxml"));

				Parent root = (Parent) loader.load();
				Scene scene = new Scene(root);
				
				//차량번호 넘겨주기
				basic_park_control data = loader.getController();
				data.GetCarNum(carNumber);
				
				
				stage.setScene(scene);
				stage.setTitle("입차_자리선택");
				stage.setResizable(false);
				stage.show();
				
				//if이미 입차한 차량이라면 => 이미 주차된 차량입니다.
				
			}catch (Exception e) {
				msg.setText("잘못 된 차량번호입니다.");
			}
		}
		else {
			msg.setText("잘못 된 차량번호입니다.");
		}
	}
	
	
	
}
