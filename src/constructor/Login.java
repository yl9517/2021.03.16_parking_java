package constructor;

public class Login { //�̱���
	
	//���� ������
	private Login() {
	}
	
	//��ü ����
	private static Login instance = new Login();

	//��ü�� ����ü� �ִ� get
	public static Login getInstance() {
		return instance;
	}
	
	public boolean isLogin = false;
	public String isName = "����";
	public String isPower = "";
	
	
}
