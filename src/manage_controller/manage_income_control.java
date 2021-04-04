package manage_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import com.mysql.cj.protocol.x.SyncFlushDeflaterOutputStream;

import DBconnect.DBhandle;
import constructor.parkBook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class manage_income_control implements Initializable{

	@FXML
	private TableColumn<parkBook,String> colM_year,colM_month,colM_day, colM_income,colY_income,colY_month;
	@FXML
	private TableView<parkBook> monthIncomeTable,yearIncomeTable;
	@FXML
	private Label month,beforeNextYear;

	@FXML
	private ImageView month_right,month_left;

	@FXML
	private Button day_chart;

	@FXML
	private Label all_income;
	private int allPrice=0;

	private ObservableList<parkBook> monthIncomeList = FXCollections.observableArrayList();



	@FXML
	private Label year;

	@FXML
	private ImageView year_right,year_left;

	@FXML
	private Button month_chart;
	private ObservableList<parkBook> yearIncomeList = FXCollections.observableArrayList();
	private int changeBtn =0; //초기값=0 왼쪽=1, 오른쪽=2
			//    private int mRight =0;
	//    private int yLeft =0;
	//    private int yRight =0;

	private Connection connection;
	private DBhandle handler;
	private PreparedStatement pst;
	private String bookMonth, bookDay,date;
	private int thisYear,thisMonth; 

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		handler = new DBhandle();

		//오늘 날짜
		SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM"); 
		Date time = new Date();
		date = format.format(time);

		thisYear = Integer.parseInt(date.substring(0,4)); //년
		thisMonth = Integer.parseInt(date.substring(5,7)); //월

		year.setText(String.valueOf(thisYear));
		month.setText(String.valueOf(thisMonth));
		beforeNextYear.setText(String.valueOf(thisYear));

		MonthShow();
		YearShow();

		month_left.setOnMouseClicked(e-> { //왼쪽
			changeBtn=1; 
			MonthShow(); 
		});
		month_right.setOnMouseClicked(e-> { //오른쪽
			changeBtn=2; 
			MonthShow(); 
		});
		year_left.setOnMouseClicked(e-> { //왼쪽
			changeBtn=3; 
			YearShow(); 
		});
		year_right.setOnMouseClicked(e-> { //오른쪽
			changeBtn=4; 
			YearShow(); 
		});
	}

	public void MonthShow() {
		connection = handler.getConnnection();

		monthIncomeList.clear();
		
		allPrice=0; //월별 총 수입
		
		//월 수입
		try {
			String sql = "SELECT * FROM parkbook WHERE finish=1 AND date LIKE ?";
			pst = connection.prepareStatement(sql);
			pst.setString(1, date+"%");
			
			if(changeBtn != 0) {
				if(changeBtn ==1) { //왼쪽 버튼 누르면
					thisMonth = Integer.parseInt(month.getText())-1; //현재월에서 1 빼기

					if(thisMonth==0) {
						thisYear=thisYear-1;
						thisMonth =12;
						beforeNextYear.setText(String.valueOf(thisYear));
					}
				}

				else if(changeBtn ==2) { //오른쪽 버튼 누르면
					thisMonth = Integer.parseInt(month.getText())+1; //현재월에서 1 더하기

					if(thisMonth==13) {
						thisYear=thisYear+1;
						thisMonth =1;
						beforeNextYear.setText(String.valueOf(thisYear));
					}

				}
				month.setText(String.valueOf(thisMonth));

				sql = "SELECT * FROM parkbook WHERE finish=1 AND date LIKE ? OR date LIKE ?";
				pst = connection.prepareStatement(sql);

				pst.setString(1, thisYear+"-0"+thisMonth+"-__");
				pst.setString(2, thisYear+"-"+thisMonth+"-__");
				changeBtn=0;
			}

			ResultSet rs = pst.executeQuery();

			while(rs.next()) { //리스트 저장
				bookMonth = rs.getString("date").substring(5,7);
				bookDay = rs.getString("date").substring(8,10);

				monthIncomeList.add(new parkBook(String.valueOf(thisYear),bookMonth,bookDay,rs.getInt("price")));
				allPrice+= rs.getInt("price"); //월 합계

			}

			//가격 콤마
			DecimalFormat formatter = new DecimalFormat("###,###");
			String all = (String) formatter.format(allPrice);
			all_income.setText(all);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//칼럼에 값넣기
		colM_year.setCellValueFactory(new PropertyValueFactory<>("year"));
		colM_month.setCellValueFactory(new PropertyValueFactory<>("month"));
		colM_day.setCellValueFactory(new PropertyValueFactory<>("day"));
		colM_income.setCellValueFactory(new PropertyValueFactory<>("price"));

		colM_year.setStyle(" -fx-alignment : center; ");
		colM_month.setStyle(" -fx-alignment : center; ");
		colM_day.setStyle(" -fx-alignment : center; ");
		colM_income.setStyle(" -fx-alignment : center-right; ");
		monthIncomeTable.setItems(monthIncomeList);
		
	}
	
		public void YearShow() {
			connection = handler.getConnnection();
			yearIncomeList.clear();

		//연 수입
			try {
				String sql = "SELECT * FROM parkbook WHERE finish=1 AND date LIKE ?";
				pst = connection.prepareStatement(sql);
				
				if(changeBtn != 0) {
					if(changeBtn ==3)  //왼쪽 버튼 누르면
						thisYear = Integer.parseInt(year.getText())-1; //현재월에서 1 빼기

					else if(changeBtn ==4)  //오른쪽 버튼 누르면
						thisYear = Integer.parseInt(year.getText())+1; //현재월에서 1 더하기
					
					year.setText(String.valueOf(thisYear));
				}

				pst.setString(1, thisYear+"%");
				ResultSet rs = pst.executeQuery();
				
				int[] monthsPrice = new int[12]; //월을 받을 변수배열

				while(rs.next()) { //리스트 저장
					bookMonth = rs.getString("date").substring(5,7);
					int monthValue = Integer.parseInt(bookMonth); //모든 월
					
					monthsPrice[monthValue-1]+=rs.getInt("price"); //배열[월-1] 안에 해당월의 총수입 (0번째 인덱스에 1월의 총수입)
				}
				
				for(int i=0; i<monthsPrice.length;i++){ //i = 월
					
					yearIncomeList.add(
							new parkBook(String.valueOf(thisYear),
							String.format("%02d", i+1),		// 2자리 숫자
							bookDay, monthsPrice[i])
					);
					yearIncomeList.get(i).getPrice();															
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}

		//컬럼에 값 넣기
		colY_month.setCellValueFactory(new PropertyValueFactory<>("month"));
		colY_income.setCellValueFactory(new PropertyValueFactory<>("price"));	

		colY_month.setStyle(" -fx-alignment : center; ");
		colY_income.setStyle(" -fx-alignment : center-right; ");

		yearIncomeTable.setItems(yearIncomeList);
	}




	public void day_chartAction(ActionEvent e) {
		try {
			Stage stage = new Stage(); //스테이지 생성

			FXMLLoader loader = new FXMLLoader();
			
			loader.setLocation(getClass().getResource("/FXML/manage_income_dayChart.fxml"));
			Parent root = (Parent) loader.load();
			Scene scene = new Scene(root);
			
			//정보 넘기기 (보고있는 월)
			manage_incomeDayChart_control connect = loader.getController();
			connect.SetIncomeChart(beforeNextYear.getText(), month.getText(),monthIncomeList);

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

			FXMLLoader loader = new FXMLLoader();

			loader.setLocation(getClass().getResource("/FXML/manage_income_monthChart.fxml"));
			Parent root = (Parent) loader.load();
			Scene scene = new Scene(root);
			
			//정보 넘기기 (보고있는 연도)
			manage_incomeMonthChart_control connect = loader.getController();
			connect.SetIncome(year.getText(), yearIncomeList);

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
