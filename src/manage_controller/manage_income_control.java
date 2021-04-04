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
	private int changeBtn =0; //�ʱⰪ=0 ����=1, ������=2
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

		//���� ��¥
		SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM"); 
		Date time = new Date();
		date = format.format(time);

		thisYear = Integer.parseInt(date.substring(0,4)); //��
		thisMonth = Integer.parseInt(date.substring(5,7)); //��

		year.setText(String.valueOf(thisYear));
		month.setText(String.valueOf(thisMonth));
		beforeNextYear.setText(String.valueOf(thisYear));

		MonthShow();
		YearShow();

		month_left.setOnMouseClicked(e-> { //����
			changeBtn=1; 
			MonthShow(); 
		});
		month_right.setOnMouseClicked(e-> { //������
			changeBtn=2; 
			MonthShow(); 
		});
		year_left.setOnMouseClicked(e-> { //����
			changeBtn=3; 
			YearShow(); 
		});
		year_right.setOnMouseClicked(e-> { //������
			changeBtn=4; 
			YearShow(); 
		});
	}

	public void MonthShow() {
		connection = handler.getConnnection();

		monthIncomeList.clear();
		
		allPrice=0; //���� �� ����
		
		//�� ����
		try {
			String sql = "SELECT * FROM parkbook WHERE finish=1 AND date LIKE ?";
			pst = connection.prepareStatement(sql);
			pst.setString(1, date+"%");
			
			if(changeBtn != 0) {
				if(changeBtn ==1) { //���� ��ư ������
					thisMonth = Integer.parseInt(month.getText())-1; //��������� 1 ����

					if(thisMonth==0) {
						thisYear=thisYear-1;
						thisMonth =12;
						beforeNextYear.setText(String.valueOf(thisYear));
					}
				}

				else if(changeBtn ==2) { //������ ��ư ������
					thisMonth = Integer.parseInt(month.getText())+1; //��������� 1 ���ϱ�

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

			while(rs.next()) { //����Ʈ ����
				bookMonth = rs.getString("date").substring(5,7);
				bookDay = rs.getString("date").substring(8,10);

				monthIncomeList.add(new parkBook(String.valueOf(thisYear),bookMonth,bookDay,rs.getInt("price")));
				allPrice+= rs.getInt("price"); //�� �հ�

			}

			//���� �޸�
			DecimalFormat formatter = new DecimalFormat("###,###");
			String all = (String) formatter.format(allPrice);
			all_income.setText(all);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Į���� ���ֱ�
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

		//�� ����
			try {
				String sql = "SELECT * FROM parkbook WHERE finish=1 AND date LIKE ?";
				pst = connection.prepareStatement(sql);
				
				if(changeBtn != 0) {
					if(changeBtn ==3)  //���� ��ư ������
						thisYear = Integer.parseInt(year.getText())-1; //��������� 1 ����

					else if(changeBtn ==4)  //������ ��ư ������
						thisYear = Integer.parseInt(year.getText())+1; //��������� 1 ���ϱ�
					
					year.setText(String.valueOf(thisYear));
				}

				pst.setString(1, thisYear+"%");
				ResultSet rs = pst.executeQuery();
				
				int[] monthsPrice = new int[12]; //���� ���� �����迭

				while(rs.next()) { //����Ʈ ����
					bookMonth = rs.getString("date").substring(5,7);
					int monthValue = Integer.parseInt(bookMonth); //��� ��
					
					monthsPrice[monthValue-1]+=rs.getInt("price"); //�迭[��-1] �ȿ� �ش���� �Ѽ��� (0��° �ε����� 1���� �Ѽ���)
				}
				
				for(int i=0; i<monthsPrice.length;i++){ //i = ��
					
					yearIncomeList.add(
							new parkBook(String.valueOf(thisYear),
							String.format("%02d", i+1),		// 2�ڸ� ����
							bookDay, monthsPrice[i])
					);
					yearIncomeList.get(i).getPrice();															
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}

		//�÷��� �� �ֱ�
		colY_month.setCellValueFactory(new PropertyValueFactory<>("month"));
		colY_income.setCellValueFactory(new PropertyValueFactory<>("price"));	

		colY_month.setStyle(" -fx-alignment : center; ");
		colY_income.setStyle(" -fx-alignment : center-right; ");

		yearIncomeTable.setItems(yearIncomeList);
	}




	public void day_chartAction(ActionEvent e) {
		try {
			Stage stage = new Stage(); //�������� ����

			FXMLLoader loader = new FXMLLoader();
			
			loader.setLocation(getClass().getResource("/FXML/manage_income_dayChart.fxml"));
			Parent root = (Parent) loader.load();
			Scene scene = new Scene(root);
			
			//���� �ѱ�� (�����ִ� ��)
			manage_incomeDayChart_control connect = loader.getController();
			connect.SetIncomeChart(beforeNextYear.getText(), month.getText(),monthIncomeList);

			stage.setScene(scene);
			stage.setTitle("�Ϻ� ���� ��Ʈ");
			stage.setResizable(false);
			stage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void month_chartAction(ActionEvent e) {
		try {
			Stage stage = new Stage(); //�������� ����

			FXMLLoader loader = new FXMLLoader();

			loader.setLocation(getClass().getResource("/FXML/manage_income_monthChart.fxml"));
			Parent root = (Parent) loader.load();
			Scene scene = new Scene(root);
			
			//���� �ѱ�� (�����ִ� ����)
			manage_incomeMonthChart_control connect = loader.getController();
			connect.SetIncome(year.getText(), yearIncomeList);

			stage.setScene(scene);
			stage.setTitle("���� ���� ��Ʈ");
			stage.setResizable(false);
			stage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
