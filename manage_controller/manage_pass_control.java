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
		//��й�ȣ�� ������ â ���� Ʋ���� �� ����
		PauseTransition pt = new PauseTransition();
		pt.setDuration(Duration.seconds(1)); //1�� ���� >> ��?
		pt.setOnFinished(e3 -> {
			connection = handler.getConnnection();
			String sql = "SELECT * FROM park WHERE password=?";
			try {
				pst= connection.prepareStatement(sql);
				pst.setString(1, password.getText());
				//�н����� �Է¶��� ģ �Ͱ� ���� db�� ��� �������
				
				ResultSet rs = pst.executeQuery();
				
				int count=0;
				
				while(rs.next()) { //���� ���� ���������� �ݺ�
					count++;
			}
				if(count ==1) { //��й�ȣ�� �´ٸ� â ����
					try {
						submit.getScene().getWindow().hide(); //����â �����
						Stage manage = new Stage();
					
						Parent root = FXMLLoader.load(getClass().getResource("/FXML/manage_main.fxml"));

						Scene scene = new Scene(root);
					
						manage.setScene(scene);
						manage.setTitle("�����ڸ���");
						manage.setResizable(false);
						manage.show();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					wrong.setText("��й�ȣ�� Ʋ�Ƚ��ϴ�.");
				}
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		pt.play();
	}


}
