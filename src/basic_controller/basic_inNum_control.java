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
				//�� �չ�ȣ �ڸ���
				int first = Integer.parseInt(carNumber.substring(0, 2)); //0��° �ε������� 2��
				char middle = carNumber.charAt(2);
				int last = Integer.parseInt(carNumber.substring(3));
				
				carNum.getScene().getWindow().hide();
				
				Stage stage = new Stage(StageStyle.UTILITY);
				stage.initModality(Modality.WINDOW_MODAL); //���� â ����???
				stage.initOwner(carNum.getScene().getWindow());
				
				//�� �������� ��ü�� bool�� ����
				Car connect = Car.getInstance();
				connect.isCar = true;
				
				FXMLLoader loader = new FXMLLoader();

				loader.setLocation(getClass().getResource("/FXML/basic_park.fxml"));

				Parent root = (Parent) loader.load();
				Scene scene = new Scene(root);
				
				//������ȣ �Ѱ��ֱ�
				basic_park_control data = loader.getController();
				data.GetCarNum(carNumber);
				
				
				stage.setScene(scene);
				stage.setTitle("����_�ڸ�����");
				stage.setResizable(false);
				stage.show();
				
				//if�̹� ������ �����̶�� => �̹� ������ �����Դϴ�.
				
			}catch (Exception e) {
				msg.setText("�߸� �� ������ȣ�Դϴ�.");
			}
		}
		else {
			msg.setText("�߸� �� ������ȣ�Դϴ�.");
		}
	}
	
	
	
}
