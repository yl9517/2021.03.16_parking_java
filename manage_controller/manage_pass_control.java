package manage_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.util.Duration;

public class manage_pass_control implements Initializable{

    @FXML
    private Button submit;

    @FXML
    private PasswordField password;

    @FXML
    private Label wrong;
    
    private Connection connection;
    private DBhandle handler;
    private PreparedStatement pst;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		handler = new DBhandle();
	}

	public void submitAction(ActionEvent e) {
		//비밀번호가 맞으면 창 띄우기 틀리면 말 띄우기
		PauseTransition pt = new PauseTransition();
		pt.setDuration(Duration.seconds(1)); //1초 정지 >> 왜?
		pt.setOnFinished(e3 -> {
			connection = handler.getConnnection();
			String sql = "SELECT * FROM park WHERE password=?";
			try {
				pst= connection.prepareStatement(sql);
				pst.setString(1, password.getText());
				//패스워드 입력란에 친 것과 같은 db를 모두 끌어오기
				
				ResultSet rs = pst.executeQuery();
				
				int count=0;
				
				while(rs.next()) { //다음 값이 있을때까지 반복
					count++;
			}
				if(count ==1) { //비밀번호가 맞다면 창 띄우기
					try {
						submit.getScene().getWindow().hide(); //현재창 숨기기
						Stage manage = new Stage();
					
						Parent root = FXMLLoader.load(getClass().getResource("/FXML/manage_main.fxml"));

						Scene scene = new Scene(root);
					
						manage.setScene(scene);
						manage.setTitle("관리자메인");
						manage.setResizable(false);
						manage.show();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					wrong.setText("비밀번호가 틀렸습니다.");
				}
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		pt.play();
	}


}
