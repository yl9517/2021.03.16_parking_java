package constructor;

public class Login { //教臂沛
	
	//兵烹 积己磊
	private Login() {
	}
	
	//按眉 积己
	private static Login instance = new Login();

	//按眉甫 缠绢棵荐 乐绰 get
	public static Login getInstance() {
		return instance;
	}
	
	public boolean isLogin = false;
	public String isPower = "";
	
	
}
