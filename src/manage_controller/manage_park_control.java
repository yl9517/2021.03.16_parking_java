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

import DBconnect.DBhandle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class manage_park_control implements Initializable{
	  @FXML
	    private Label time,unusePark;

	    @FXML
	    private Button main;

	    @FXML
	    private Button floorB1,floorB2,floorB3;

	    @FXML
	    private Label floor;

	    @FXML
	    private Button C1,C2,C3,C4,C5,A1,A2,A3,A4,B1,B2,B3,B4;

	    @FXML
	    private Label A1_carnum,A2_carnum,A3_carnum,A4_carnum,B1_carnum,B2_carnum,B3_carnum,B4_carnum, C1_carnum,C2_carnum,C3_carnum,C4_carnum,C5_carnum;

	    private Connection connection;	//db���ᰴü
		private DBhandle handler;
		private PreparedStatement pst;
	    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		handler = new DBhandle();
		
		SimpleDateFormat format1 = new SimpleDateFormat ("YYYY-MM-dd HH:mm");
		Date date = new Date();
		String nowtime = format1.format(date);
		time.setText(nowtime);
		
    	floor.setText("B1"); //�⺻ ��
    	CarShow();

    	floorB1.setOnAction( e1 -> {
    		floor.setText("B1");
			floorB1.setStyle("-fx-background-color:silver;");
			floorB2.setStyle("-fx-background-color:");
			floorB3.setStyle("-fx-background-color:");

	    	CarShow();
    	});
    	floorB2.setOnAction( e2 -> {
    		floor.setText("B2");
			floorB2.setStyle("-fx-background-color:silver;");
			floorB1.setStyle("-fx-background-color:");
			floorB3.setStyle("-fx-background-color:");
	    	CarShow();
    	});
    	floorB3.setOnAction( e3 -> {
    		floor.setText("B3");
			floorB3.setStyle("-fx-background-color:silver;");
			floorB2.setStyle("-fx-background-color:");
			floorB1.setStyle("-fx-background-color:");
	    	CarShow();
    	});
	}
	 public void CarShow() {
    	//��� �� �ʱ�ȭ
	    Label[] labels = {A1_carnum,A2_carnum,A3_carnum,A4_carnum,B1_carnum,B2_carnum,B3_carnum,B4_carnum, C1_carnum,C2_carnum,C3_carnum,C4_carnum,C5_carnum};
	    for(int i =0; i<labels.length; i++) {
	    	labels[i].setText("");
    	}
	    
	    try {
		    connection=handler.getConnnection();
		    String sql = "select * from parking WHERE floor=? AND carNum IS NOT NULL"; //�ش� ���� �����Ǿ��ִ��ڸ� ����
	    
			pst = connection.prepareStatement(sql);
			pst.setString(1, floor.getText());
			ResultSet rs = pst.executeQuery();

			int use = 0; //������� �ڸ� �ʱ�ȭ
			while(rs.next()) {
        		for(int i =0; i<labels.length; i++) {	//��ư���ڸ�ŭ i ������
    				if(labels[i].getId().substring(0,2).equals(rs.getString("seat"))) { //�����Ǿ������� �ؽ�Ʈ ǥ��
    					labels[i].setText(rs.getString("carNum"));
    					use++;
    				}
    			}
			}
			unusePark.setText(String.valueOf(13-use)); //���� ���� ��
    		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
	}
	 
	 
    public void mainAction(ActionEvent e1) {
    	main.getScene().getWindow().hide(); //����â �ݱ�
    	Stage main = new Stage(); //�������� ����
    	
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/manage_main.fxml"));
			
	    	Scene scene = new Scene(root);
	    	
	    	main.setScene(scene);
	    	main.setResizable(false);
	    	main.setTitle("�Ϲ� ����");
	    	main.show();
	    	
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
