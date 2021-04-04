package constructor;

public class Login { //½Ì±ÛÅæ
	
	//±øÅë »ı¼ºÀÚ
	private Login() {
	}
	
	//°´Ã¼ »ı¼º
	private static Login instance = new Login();

	//°´Ã¼¸¦ ²ø¾î¿Ã¼ö ÀÖ´Â get
	public static Login getInstance() {
		return instance;
	}
	
	public boolean isLogin = false;
	public String isName = "¾øÀ½";
	public String isPower = "";
	
	
}
