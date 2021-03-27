package manage_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import com.mysql.cj.Session;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.xdevapi.PreparableStatement;
import com.sun.javafx.scene.EnteredExitedHandler;

import DBconnect.DBhandle;
import basic_controller.basic_main_control;
import constructor.Login;
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
    
    private String worker_name;
    private String worker_power;
    private Button beforebtn;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		handler = new DBhandle();
		
	}
	public void Getbtn(Button btn) {
		beforebtn = btn;
	}
	
	public void SubmitAction(ActionEvent e) {
		//��й�ȣ�� ������ â ���� Ʋ���� �� ����
		PauseTransition pt = new PauseTransition();
		pt.setDuration(Duration.seconds(1)); //1�� ���� >> ��?
		pt.setOnFinished(e3 -> {
			
			connection = handler.getConnnection();
			String sql = "SELECT * FROM manage WHERE password=?";

			try {
				pst= connection.prepareStatement(sql);
				pst.setString(1, password.getText());
				//�н����� �Է¶��� ģ �Ͱ� ���� db�� ��� �������
				
				ResultSet rs = pst.executeQuery();
				
				int count=0; //rs �ʱⰪ
		
				while(rs.next()) { //���� ���� ���������� �ݺ� (��ó�� �ϳ�)
					count++;
					worker_name = rs.getString("name");
					worker_power = rs.getString("power");
				}
			
				if(count ==1) { //��й�ȣ�� �´ٸ� â ����
					try { 
						submit.getScene().getWindow().hide(); //����â �����
						beforebtn.getScene().getWindow().hide(); //���� â �����
						
						Stage manage = new Stage();
					
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/FXML/manage_main.fxml"));
						Parent root = (Parent) loader.load();
						Scene scene = new Scene(root);
						
						//������ â�� �����Ѱ��ֱ�(�ٹ����̸�)
						manage_main_control data = loader.getController();
						data.Work(worker_name);
						
						
						//login��ü�� ������ ����
						Login login = Login.getInstance();
						login.isLogin=true;
						login.isPower=worker_power;
						
						
						//��ٳ�¥,�ð� ���� DB�� ����
						SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
						SimpleDateFormat format2 = new SimpleDateFormat ( "HH:mm:ss");
						Date time1 = new Date();
						Date time2 = new Date();
						String startDay = format1.format(time1);
						String startTime = format2.format(time2);
						
						sql = "INSERT INTO work(startDay,startTime,worker)"+" values(?,?,?)";
						pst = connection.prepareStatement(sql);
						
						pst.setString(1, startDay);
						pst.setString(2, startTime);
						pst.setString(3, worker_name);
						
						pst.executeUpdate();
							
						
						//�������� ���̱�
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
