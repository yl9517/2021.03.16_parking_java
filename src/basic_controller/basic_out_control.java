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
    
    public String car_inDay; //입차날짜(요금계산)
    public String car_inTime; //입차시간(요금계산)
    public int resultPrice = 0; //결과요금(db저장)
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		handler = new DBhandle();
		
		show();
		
		//취소누르면 창닫기
		cancel.setOnMouseClicked(event -> {
		cancel.getScene().getWindow().hide();}); 
		
	}
	public void initData(String num,String floor, String seat,String date) { //정보받아오기
		carNum.setText(num);
		car_floor.setText(floor);
		car_seat.setText(seat);
		car_out.setText(date.substring(5)); //고객용 현재시간 MM-dd HH:hh
    	saveOutDate = date.substring(0,10); //장부저장용 날짜 YYYY-MM-dd
    	saveOutTime = date.substring(11); //장부저장용 시간 HH:hh
		
	}
	public void show() { //주차장부에서 입차,요금정보 끌어와 보여주기
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
					//입차날짜(요금계산)
					car_inDay = rs.getString("date").substring(5); //mm-dd
					//입차시간(요금계산)
					car_inTime = rs.getString("inTime"); //HH:hh
					
					
					//고객에게 보여질 입차시간
					car_in.setText(car_inDay+" "+car_inTime);
				}
				//요금 가져오기
				car_price.setText(price(car_inDay,car_inTime,saveOutDate,saveOutTime));
				
			}catch (Exception e) {
				// TODO: handle exception
			}
		});
		pt.play();
	}
	
	@FXML
    public void payAction(ActionEvent event) { //결제하기 눌렀을 경우
										//주차현황에서 값 없애기, 주차장부에 저장하기
		PauseTransition pt = new PauseTransition();
		pt.setDuration(Duration.seconds(1));
		
		pt.setOnFinished(e1 ->{
			connection = handler.getConnnection();
						
			try { //주차현황에서 값 없애기
				String sql = "UPDATE parking SET carNum=NULL WHERE floor=? AND seat=?";
				pst = connection.prepareStatement(sql);
				pst.setString(1, car_floor.getText());
				pst.setString(2, car_seat.getText());
				
				pst.executeUpdate();
				
			}catch (Exception e) {
			}
			
			try { //주차장부 저장
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
    	alert.setTitle("출차 알림");
    	alert.setHeaderText("알림");
    	alert.setContentText("출차가 완료 되었습니다.");
    	alert.showAndWait();
    	
    }
	
	public String price(String inday, String intime, String outday, String outtime) { //요금 계산
		resultPrice = 0;
		//가격시간 ( 최초 10분 무료 이후 10분 지날때마다 800원 )
		
		//입차 날짜시간
		//int inMonth = Integer.parseInt(car_inDay.substring(0,2)); //월
		int inDay = Integer.parseInt(inday.substring(3));	//일
		int inHH = Integer.parseInt(intime.substring(0,2));	//시
		int inMM = Integer.parseInt(intime.substring(3));	//분
		
		//출차 날짜시간
		//int outMonth = Integer.parseInt(saveOutDate.substring(5,7));
		int outDay = Integer.parseInt(outday.substring(8));
		int outHH = Integer.parseInt(outtime.substring(0,2));
		int outMM = Integer.parseInt(outtime.substring(3));
		
		//시간 수정하기!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!★★★★★★ 하루지났다고 십만원무는거x
		//하루 넘어갔을때	1
		int dayPrice = 115200; //최조제외 하루 주차가격 115,200원 //하루 1440분  
			//머무른 [일] //월계산X
			int stayDay = outDay-inDay;
			if(inDay > outDay) { //월 넘어가면 1로 통일
				stayDay = 1;
			}
			int resultDayTime = 0; //총 머무른일수 총 합가격
			
			
		//당일 가격 	//01~10분 : 800원(무료 이미 뺌)			
			///머무른 [시]
			if(inHH > outHH) { //들어온 시가 더 크면 out에 24시간 더 해주기 (23시에 in - 2시 out) 
				outHH+=24;	//무조건 하루가 지나있기 때문
				if(stayDay>=2) { // 이틀이상 지났다면 하루이상 주차한것
					resultDayTime = (stayDay-1)*1440;
				}
			}else if(inHH < outHH){ //나간시간이 더 크면
				if(stayDay>=1) { //하루이상 지났다면 일당 1440분 더해줌( 하루 안지난상태에서 나간시간이 더 클수 없음)
					resultDayTime = stayDay*1440;
				}
			}
			int stayHH = outHH-inHH; 
				
			if(stayHH ==0) { //머무른 시간이 23시간이라면
				if(stayDay>=1)
					stayHH+=24; //하루 더해주기
			}
			
			
			//머무른 [분]
			if(inMM > outMM) { //들어온 분이 더 크면 out에 60 더해주기 (20분 in - 10분 out)
				outMM +=60;    //무조건 한시간 지나있음
				stayHH -= 1;	//위에 60분으로 채워주었으니 1시간 빼기
				
//				if(stayHH==-1)  {//머무른 시간이 23시간 이상이라면 (23시간 후 '시'가 같다면)
//					stayHH+=24; //하루 더해주기
//				}
			}
			if(stayHH ==0) { //머무른 시간이 23시간이라면
				if(stayDay>=1)
					stayHH+=24; //하루 더해주기
			}

			int stayMM = outMM-inMM; 
			
//					System.out.println(stayHH+"시간 "+stayMM); //확인
			
			//최종 당일 머무른시간(분으로 계산)
			int todayTime = (60*stayHH)+stayMM-10;
			
				//todayTime이 0이면 결과 0
				if(todayTime <= 0 ) {
					todayTime = 0;
					if(stayDay>=1) { //그러나 하루이상이면 1440분 //일당에서 1440분 뺴주기
						resultDayTime-=1440;
						todayTime = 1430; //10분 빼주기
					}
				}
			
			int restPrice=0; // 마지막 분 요금계산	
			if(todayTime%10>0) { //1~9분일때 800원
				restPrice=800;
			}
			
		//최종 시간분
		int resultTime = resultDayTime+todayTime;
		
		//최종 요금 
		resultPrice = ((resultTime/10*800)+restPrice); // resultTime/10분 * 800원
		
		//가격 콤마
		DecimalFormat formatter = new DecimalFormat("###,###");
		String rep = (String) formatter.format(resultPrice);

//						System.out.println();
//						System.out.println("일당 시간 :"+resultDayTime);
//						System.out.println("당일 시간 :"+todayTime);
//						System.out.println("총 시간 :"+resultTime);
//						System.out.println("최종 요금 : "+resultPrice);
		
		return rep;
//		car_price.setText(rep)
		
			
	}
	
}
