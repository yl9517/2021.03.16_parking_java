package basic_controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.xdevapi.PreparableStatement;

import DBconnect.DBhandle;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PopupControl;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class basic_outNum_control implements Initializable{

    @FXML
    private Button submit;

    @FXML
    private TextField carNum;

    @FXML
    private Label msg;
	
    private Connection connection;
    private DBhandle handler;
    private PreparedStatement pst;
    
	public void initialize(URL arg0,ResourceBundle arg1) {
		handler = new DBhandle();
		
	};
	
	
	public void submitAction(ActionEvent e3) {
		
		String carNumber = carNum.getText(); 
		
		PauseTransition pt = new PauseTransition();
		pt.setDuration(Duration.seconds(1));
		pt.setOnFinished(e2 -> {
			
			connection = handler.getConnnection();
			String sql = "select * FROM parking where carNum=?";
			
			try { 
				int first = Integer.parseInt(carNumber.substring(0, 2)); //0번째 인덱스부터 2개
				char middle = carNumber.charAt(2);
				int last = Integer.parseInt(carNumber.substring(3));
				
				
				pst = connection.prepareStatement(sql);
				pst.setString(1, carNumber); //입력한 차량번호가 db에 있으면 아래 실행
				
				ResultSet rs = pst.executeQuery();
				int count = 0;

				String floor= "";
				String seat= "";
				
				while(rs.next()) {	
					count++;
					
					floor=rs.getString("floor");
					seat=rs.getString("seat");
				}

				if(count==1) {	//차량번호가 있다면 다음 출력
					try {
						System.out.println(floor);
						carNum.getScene().getWindow().hide();
						
						Stage stage = new Stage();
						
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/FXML/basic_out.fxml"));
						
						Parent root = (Parent) loader.load();
						Scene scene = new Scene(root);
						
						//데이터 전송하기(차량번호)
						basic_out_control data = loader.getController();
						data.initData(carNumber,floor,seat);
						//현재 시간도 넘겨주기
						
						
						stage.setScene(scene);
						stage.setTitle("출차 정보");
						stage.setResizable(false);
						stage.show();
					}catch (Exception e) {
					}
				}
				else {//차량번호 없으면
					msg.setText("해당 차량은 주차되어있지 않습니다.");
				}
			}catch (Exception e) {
				msg.setText("잘못 된 차량번호입니다.");
			}
		});
		pt.play();
		
	}
	
}
