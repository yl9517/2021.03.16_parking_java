package basic_controller;

import java.io.IOException;
import java.lang.StackWalker.Option;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import com.mysql.cj.xdevapi.PreparableStatement;

import DBconnect.DBhandle;
import DBconnect.config;
import constructor.Login;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

public class basic_out_control implements Initializable{
  
    @FXML
    private TextField carNum;

    @FXML
    private Label car_floor,car_seat;

    @FXML
    private Label car_in,car_out,car_price,cancel;

    @FXML
    private Button pay;

    private Connection connection;
    private DBhandle handler;
    private PreparedStatement pst;
    
    public String time = "";
    private String saveOutDate;
    private String saveOutTime;
    
    public String car_inDay; //������¥(��ݰ��)
    public String car_inTime; //�����ð�(��ݰ��)
    public int resultPrice = 0; //������(db����)
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		handler = new DBhandle();
		
		show();
		
		//��Ҵ����� â�ݱ�
		cancel.setOnMouseClicked(event -> {
		cancel.getScene().getWindow().hide();}); 
		
	}
	public void initData(String num,String floor, String seat,String date) { //�����޾ƿ���
		carNum.setText(num);
		car_floor.setText(floor);
		car_seat.setText(seat);
		car_out.setText(date.substring(5)); //���� ����ð� MM-dd HH:hh
    	saveOutDate = date.substring(0,10); //�������� ��¥ YYYY-MM-dd
    	saveOutTime = date.substring(11); //�������� �ð� HH:hh
		
	}
	public void show() { //������ο��� ����,������� ����� �����ֱ�
		PauseTransition pt = new PauseTransition();
		pt.setDuration(Duration.seconds(0.1));
		
		pt.setOnFinished(e1 ->{
			connection = handler.getConnnection();
			String sql = "select * from parkbook where finish=0 and carNum=?";
			try {
				pst = connection.prepareStatement(sql);
				pst.setString(1, carNum.getText());

				ResultSet rs = pst.executeQuery();
				
				if(rs.next()) {
					//������¥(��ݰ��)
					car_inDay = rs.getString("date").substring(5); //mm-dd
					//�����ð�(��ݰ��)
					car_inTime = rs.getString("inTime"); //HH:hh
					
					
					//������ ������ �����ð�
					car_in.setText(car_inDay+" "+car_inTime);
				}
				//��� ��������
				car_price.setText(price(car_inDay,car_inTime,saveOutDate,saveOutTime));
				
			}catch (Exception e) {
				// TODO: handle exception
			}
		});
		pt.play();
	}
	
	@FXML
    public void payAction(ActionEvent event) { //�����ϱ� ������ ���
										//������Ȳ���� �� ���ֱ�, ������ο� �����ϱ�
		PauseTransition pt = new PauseTransition();
		pt.setDuration(Duration.seconds(1));
		
		pt.setOnFinished(e1 ->{
			connection = handler.getConnnection();
						
			try { //������Ȳ���� �� ���ֱ�
				String sql = "UPDATE parking SET carNum=NULL WHERE floor=? AND seat=?";
				pst = connection.prepareStatement(sql);
				pst.setString(1, car_floor.getText());
				pst.setString(2, car_seat.getText());
				
				pst.executeUpdate();
				
			}catch (Exception e) {
			}
			
			try { //������� ����
	    		Login login = Login.getInstance();
	    		
				String sql = "UPDATE parkbook SET date=?, outTime=?, price=?,staff=?,finish=? WHERE carNum=? AND finish=0";
				pst = connection.prepareStatement(sql);
				
				pst.setString(1, saveOutDate);
				pst.setString(2, saveOutTime);
				pst.setInt(3, resultPrice);
				pst.setString(4, login.isName);
				pst.setInt(5, 1);
				pst.setString(6, carNum.getText());
				
				pst.executeUpdate();
			}catch (Exception e) {
			}
		});
		pt.play();
    	pay.getScene().getWindow().hide();
    	
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("���� �˸�");
    	alert.setHeaderText("�˸�");
    	alert.setContentText("������ �Ϸ� �Ǿ����ϴ�.");
    	alert.showAndWait();
    	
    }
	
	public String price(String inday, String intime, String outday, String outtime) { //��� ���
		resultPrice = 0;
		//���ݽð� ( ���� 10�� ���� ���� 10�� ���������� 800�� )
		
		//���� ��¥�ð�
		//int inMonth = Integer.parseInt(car_inDay.substring(0,2)); //��
		int inDay = Integer.parseInt(inday.substring(3));	//��
		int inHH = Integer.parseInt(intime.substring(0,2));	//��
		int inMM = Integer.parseInt(intime.substring(3));	//��
		
		//���� ��¥�ð�
		//int outMonth = Integer.parseInt(saveOutDate.substring(5,7));
		int outDay = Integer.parseInt(outday.substring(8));
		int outHH = Integer.parseInt(outtime.substring(0,2));
		int outMM = Integer.parseInt(outtime.substring(3));
		
		//�ð� �����ϱ�!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!�ڡڡڡڡڡ� �Ϸ������ٰ� �ʸ������°�x
		//�Ϸ� �Ѿ����	1
		int dayPrice = 115200; //�������� �Ϸ� �������� 115,200�� //�Ϸ� 1440��  
			//�ӹ��� [��] //�����X
			int stayDay = outDay-inDay;
			if(inDay > outDay) { //�� �Ѿ�� 1�� ����
				stayDay = 1;
			}
			int resultDayTime = 0; //�� �ӹ����ϼ� �� �հ���
			
			
		//���� ���� 	//01~10�� : 800��(���� �̹� ��)			
			///�ӹ��� [��]
			if(inHH > outHH) { //���� �ð� �� ũ�� out�� 24�ð� �� ���ֱ� (23�ÿ� in - 2�� out) 
				outHH+=24;	//������ �Ϸ簡 �����ֱ� ����
				if(stayDay>=2) { // ��Ʋ�̻� �����ٸ� �Ϸ��̻� �����Ѱ�
					resultDayTime = (stayDay-1)*1440;
				}
			}else if(inHH < outHH){ //�����ð��� �� ũ��
				if(stayDay>=1) { //�Ϸ��̻� �����ٸ� �ϴ� 1440�� ������( �Ϸ� ���������¿��� �����ð��� �� Ŭ�� ����)
					resultDayTime = stayDay*1440;
				}
			}
			int stayHH = outHH-inHH; 
				
			if(stayHH ==0) { //�ӹ��� �ð��� 23�ð��̶��
				if(stayDay>=1)
					stayHH+=24; //�Ϸ� �����ֱ�
			}
			
			
			//�ӹ��� [��]
			if(inMM > outMM) { //���� ���� �� ũ�� out�� 60 �����ֱ� (20�� in - 10�� out)
				outMM +=60;    //������ �ѽð� ��������
				stayHH -= 1;	//���� 60������ ä���־����� 1�ð� ����
				
//				if(stayHH==-1)  {//�ӹ��� �ð��� 23�ð� �̻��̶�� (23�ð� �� '��'�� ���ٸ�)
//					stayHH+=24; //�Ϸ� �����ֱ�
//				}
			}
			if(stayHH ==0) { //�ӹ��� �ð��� 23�ð��̶��
				if(stayDay>=1)
					stayHH+=24; //�Ϸ� �����ֱ�
			}

			int stayMM = outMM-inMM; 
			
//					System.out.println(stayHH+"�ð� "+stayMM); //Ȯ��
			
			//���� ���� �ӹ����ð�(������ ���)
			int todayTime = (60*stayHH)+stayMM-10;
			
				//todayTime�� 0�̸� ��� 0
				if(todayTime <= 0 ) {
					todayTime = 0;
					if(stayDay>=1) { //�׷��� �Ϸ��̻��̸� 1440�� //�ϴ翡�� 1440�� ���ֱ�
						resultDayTime-=1440;
						todayTime = 1430; //10�� ���ֱ�
					}
				}
			
			int restPrice=0; // ������ �� ��ݰ��	
			if(todayTime%10>0) { //1~9���϶� 800��
				restPrice=800;
			}
			
		//���� �ð���
		int resultTime = resultDayTime+todayTime;
		
		//���� ��� 
		resultPrice = ((resultTime/10*800)+restPrice); // resultTime/10�� * 800��
		
		//���� �޸�
		DecimalFormat formatter = new DecimalFormat("###,###");
		String rep = (String) formatter.format(resultPrice);

//						System.out.println();
//						System.out.println("�ϴ� �ð� :"+resultDayTime);
//						System.out.println("���� �ð� :"+todayTime);
//						System.out.println("�� �ð� :"+resultTime);
//						System.out.println("���� ��� : "+resultPrice);
		
		return rep;
//		car_price.setText(rep)
		
			
	}
	
}
