/**********************************************************************************************/
/**********************************************************************************************/
/************                               OperationNumber                        ************/
/************                               ---------------                        ************/
/************         1.Send SMS                                                   ************/
/************		  2.Send Status	                   							   ************/
/************ 		  3.ChangePassword											   ************/
/************		  4.ForgetPassword											   ************/
/************ 		  5.Balance                                                    ************/
/************ 		  6.Active Sender                                              ************/
/************ 		  7.Check Sender                                               ************/
/************ 		  8.Add Sender                                                 ************/
/**********************************************************************************************/
/**********************************************************************************************/

package loan.system.general;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
public class Mobily {
	private String msg="";
	private String balance="";
	static char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

//Send SMS Method
//userName : «”„ «·„” Œœ„ „‰ „Ê»«Ì·Ì
//password : «·»«”Ê—œ  „‰ „Ê»«Ì·Ì

//sender : «”„ «·„—”· «·–Ì ”ÌŸÂ— ⁄‰œ «—”«· «·—”«·Â° ÊÌ „  ‘›Ì—Â ≈·Ï  »‘ﬂ·  ·ﬁ«∆Ì ≈·Ï ‰Ê⁄ «· ‘›Ì— (urlencode)

//message : 							SendSMS «—”«· «·—”«·Â »’Ì€… «·ÌÊ‰ÌﬂÊœ Ì „  ÕÊÌ· «·—”«·… œ«Œ· «·›‰ﬂ‘‰ 
/*										«·—”«·Â «·⁄—»ÌÂ  70 Õ—›
										«·—”«·Â «·«‰Ã·Ì“ÌÂ 160 Õ—›
										›Ì Õ«· «—”«· «ﬂÀ— „‰ —”«·Â ⁄—»ÌÂ ›«‰ «·—”«·Â «·Ê«ÕœÂ  Õ”» 67
										Ê«·—”«·Â «·«‰Ã·Ì“Ì 158
*/

//numbers : 							ÌÃ» ﬂ «»… «·—ﬁ„ »«·’Ì€… «·œÊ·Ì… „À· 96650555555 Ê⁄‰œ «·≈—”«· ≈·Ï √ﬂÀ— „‰ —ﬁ„ ÌÃ» Ê÷⁄ «·›«’·… (,) ÊÂÌ «· Ì ⁄‰œ Õ—› «·Ê«Ê »Ì‰ ﬂ· —ﬁ„Ì‰ 
/*										·« ÌÊÃœ ⁄œœ „Õœœ „‰ «·√—ﬁ«„ «· Ì Ì„ﬂ‰ﬂ «·≈—”«· ·Â« ›Ì Õ«·  „ «·≈—”«· „‰ Œ·«· »Ê«»… fsockpoen  √Ê »Ê«»… CURL°
										Ê·ﬂ‰ ›Ì Õ«·  „ «·≈—”«· „‰ Œ·«· »Ê«»… fOpen ° ›≈‰Â Ì„ﬂ‰ﬂ «·≈—”«· ≈·Ï 120 —ﬁ„ ›ﬁÿ ›Ì ﬂ· ⁄„·Ì… ≈—”«·
*/
// ··≈ÿ·«⁄ ⁄·Ï  ‰ «∆Ã «·»Ê«»… ° Ì—ÃÏ «·—ÃÊ⁄ ≈·Ï „·› ≈ﬁ—«‰Ì «·„ÊÃÊœ „⁄ «·„·›« 
	public void sendMessage(String userName,String password,String sender,String message,String numbers){
		    String para ="mobile=" + userName + "&password=" + password + "&numbers=" + numbers+ "&sender=" + sender + "&msg=" + convertUnicode(message) + "&applicationType=24";
	        sendURL("http://www.mobily.ws/api/msgSend.php",para,1);
	        System.out.println(getMessage());
	}


//Check send Status method
// ··≈ÿ·«⁄ ⁄·Ï  ‰ «∆Ã «·»Ê«»… ° Ì—ÃÏ «·—ÃÊ⁄ ≈·Ï „·› ≈ﬁ—«‰Ì «·„ÊÃÊœ „⁄ «·„·›« 
	public void sendStatus(){
		sendURL("http://www.mobily.ws/api/sendStatus.php","",2);
	}

//Change Password method
//userName : «”„ «·„” Œœ„ ·Õ”«»ﬂ ›Ì „Êﬁ⁄ „Ê»«Ì·Ì
//password : ﬂ·„… «·„—Ê— «·ﬁœÌ„Â ·Õ”«»ﬂ ›Ì „Êﬁ⁄ „Ê»«Ì·Ì
//newPassword : ﬂ·„… «·„—Ê— «·ÃœÌœ ·Õ”«»ﬂ ›Ì „Êﬁ⁄ „Ê»«Ì·Ì
// ··≈ÿ·«⁄ ⁄·Ï  ‰ «∆Ã «·»Ê«»… ° Ì—ÃÏ «·—ÃÊ⁄ ≈·Ï „·› ≈ﬁ—«‰Ì «·„ÊÃÊœ „⁄ «·„·›« 
	public void changePassword(String userName,String password,String newPassword){
		String para="mobile="+userName+"&password="+password+"&newPassword="+newPassword;
		sendURL("http://www.mobily.ws/api/changePassword.php",para,3);
	}


//Forget Password Method 
//userName : «”„ «·„” Œœ„ ·Õ”«»ﬂ ›Ì „Êﬁ⁄ „Ê»«Ì·Ì
//type : œ«·…  ÕœÌœ „ﬂ«‰ ≈—”«· ﬂ·„… «·„—Ê—
//1: ≈—”«· ﬂ·„… «·„—Ê— ⁄·Ï «·ÃÊ«·
//2: ≈—”«· ﬂ·„… «·„—Ê— ⁄·Ï «·≈Ì„Ì·° ÊÌÃ»  Ê›— «·≈Ì„Ì· »«·‘ﬂ· «·’ÕÌÕ ÷„‰ «·„⁄·Ê„«  «·‘Œ’ÌÂ ›Ì «·„Êﬁ⁄
// ··≈ÿ·«⁄ ⁄·Ï  ‰ «∆Ã «·»Ê«»… ° Ì—ÃÏ «·—ÃÊ⁄ ≈·Ï „·› ≈ﬁ—«‰Ì «·„ÊÃÊœ „⁄ «·„·›« 
	public void forgetPasswrd(String userName,String type){
		String para="mobile="+userName+"&type="+type;
		sendURL("http://www.mobily.ws/api/forgetPassword.php",para,4);
	}


//check balance method
//userName : «”„ «·„” Œœ„ ·Õ”«»ﬂ ›Ì „Êﬁ⁄ „Ê»«Ì·Ì
//password : ﬂ·„… «·„—Ê— ·Õ”«»ﬂ ›Ì „Êﬁ⁄ „Ê»«Ì·Ì
// ··≈ÿ·«⁄ ⁄·Ï  ‰ «∆Ã «·»Ê«»… ° Ì—ÃÏ «·—ÃÊ⁄ ≈·Ï „·› ≈ﬁ—«‰Ì «·„ÊÃÊœ „⁄ «·„·›« 
	public String checkBalance(String userName,String password){
		String para="mobile="+userName+"&password="+password;
		sendURL("http://www.mobily.ws/api/balance.php",para,5);
		return balance;
	}


//Active Sender Method
//userName : «”„ «·„” Œœ„ „‰ „Ê»«Ì·Ì
//Password : «·»«”Ê—œ  „‰ „Ê»«Ì·Ì
//senderID : «·ﬁÌ„… «·⁄œœÌÂ «·‰« ÃÂ „‰ ⁄„·Ì… ÿ·»  ›⁄Ì· —ﬁ„ «·ÃÊ«· ﬂ≈”„ „—”·° Ê»œÊ‰ «·—„“ (#)° Êﬂ„À«· ›≈‰ «·—ﬁ„ #110 ÌÃ» ≈—”«·Â ⁄·Ï «·‘ﬂ· 110
//activeKey : ﬂÊœ «· ›⁄Ì· «·–Ì  „ ≈” ·«„Â ⁄·Ï «·ÃÊ«·
// ··≈ÿ·«⁄ ⁄·Ï  ‰ «∆Ã «·»Ê«»… ° Ì—ÃÏ «·—ÃÊ⁄ ≈·Ï „·› ≈ﬁ—«‰Ì «·„ÊÃÊœ „⁄ «·„·›« 
	public void ActiveSender(String userName,String Password,String senderID,String activeKey){
		String para="mobile="+userName+"&password="+Password+"&senderId="+senderID+"&activeKey="+activeKey;
		sendURL("http://www.mobily.ws/api/activeSender.php",para,6);
	}

//Check Sender Method
//userName : «”„ «·„” Œœ„ „‰ „Ê»«Ì·Ì
//password : «·»«”Ê—œ  „‰ „Ê»«Ì·Ì
//senderID : «·ﬁÌ„… «·⁄œœÌÂ «·‰« ÃÂ „‰ ⁄„·Ì… ÿ·»  ›⁄Ì· —ﬁ„ «·ÃÊ«· ﬂ≈”„ „—”·° Ê»œÊ‰ «·—„“ (#)° Êﬂ„À«· ›≈‰ «·—ﬁ„ #110 ÌÃ» ≈—”«·Â ⁄·Ï «·‘ﬂ· 110
// ··≈ÿ·«⁄ ⁄·Ï  ‰ «∆Ã «·»Ê«»… ° Ì—ÃÏ «·—ÃÊ⁄ ≈·Ï „·› ≈ﬁ—«‰Ì «·„ÊÃÊœ „⁄ «·„·›« 
	public void checkSender(String userName,String password,String senderID){
		String para="mobile="+userName+"&password="+password+"&senderId="+senderID;
		sendURL("http://www.mobily.ws/api/checkSender.php",para,7);
	}
	public String getMessage(){
		return msg;
	}
	//**********************************************************************************************//
	//*********************************							************************************//
	//*********************************conver to unicode Methods************************************//
	//********************************* 						************************************//
	//**********************************************************************************************//
	
	public static String convertUnicode(String a) {
		int bufSize = 16;
		byte[] buffer = new byte[bufSize];
		String s = null;
		try {
			buffer=a.getBytes();
			s = bytesToHex(buffer,0,buffer.length);
			System.out.println("Hex: "+s);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return s;
	}
	public static String bytesToHex(byte[] b, int off, int len) {
		StringBuffer buf = new StringBuffer();
		for (int j=0; j<len; j++)
			buf.append(byteToHex(b[off+j]));
			return buf.toString();
	}
	public static String byteToHex(byte b) {
		char[] a = { hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f] };
		return forDigits(new String(a));
	}
	public static String forDigits(String val){
		switch (val.length() ){
			case 1:return "000"+val;
			case 2:return "00"+val;
			case 3:return "0"+val;
			case 4:return ""+val;
			default:return val;
		}
	}
	//**********************************************************************************************//
	//**********************************************************************************************//
	//**********************************************************************************************//
	public void selectedMessage(int value,int operationNumber){
		switch(operationNumber){
			case 1:switch(value){
						case 1:msg= " „  «·⁄„·Ì… »‰Ã«Õ";break;
						case 2:msg="≈‰ —’Ìœﬂ ·œÏ „Ê»«Ì·Ì ﬁœ ≈‰ ÂÏ Ê·„ Ì⁄œ »Â √Ì —”«∆·. (·Õ· «·„‘ﬂ·… ﬁ„ »‘Õ‰ —’Ìœﬂ „‰ «·—”«∆· ·œÏ „Ê»«Ì·Ì. ·‘Õ‰ —’Ìœﬂ ≈ »⁄  ⁄·Ì„«  ‘Õ‰ «·—’Ìœ";break;
						case 3:msg="≈‰ —’Ìœﬂ «·Õ«·Ì ·« Ìﬂ›Ì ·≈ „«„ ⁄„·Ì… «·≈—”«·. (·Õ· «·„‘ﬂ·… ﬁ„ »‘Õ‰ —’Ìœﬂ „‰ «·—”«∆· ·œÏ „Ê»«Ì·Ì. ·‘Õ‰ —’Ìœﬂ ≈ »⁄  ⁄·Ì„«  ‘Õ‰ «·—’Ìœ";break;
						case 4:msg="≈‰ ≈”„ «·„” Œœ„ «·–Ì ≈” Œœ„ Â ··œŒÊ· ≈·Ï Õ”«» «·—”«∆· €Ì— ’ÕÌÕ ( √ﬂœ „‰ √‰ ≈”„ «·„” Œœ„ «·–Ì ≈” Œœ„ Â ÂÊ ‰›”Â «·–Ì  ” Œœ„Â ⁄‰œ œŒÊ·ﬂ ≈·Ï „Êﬁ⁄ „Ê»«Ì·Ì).";break;
						case 5:msg="Â‰«ﬂ Œÿ√ ›Ì ﬂ·„… «·„—Ê— ( √ﬂœ „‰ √‰ ﬂ·„… «·„—Ê— «· Ì  „ ≈” Œœ«„Â« ÂÌ ‰›”Â« «· Ì  ” Œœ„Â« ⁄‰œ œŒÊ·ﬂ „Êﬁ⁄ „Ê»«Ì·Ì,≈–« ‰”Ì  ﬂ·„… «·„—Ê— ≈÷€ÿ ⁄·Ï —«»ÿ ‰”Ì  ﬂ·„… «·„—Ê— · ’·ﬂ —”«·… ⁄·Ï ÃÊ«·ﬂ »—ﬁ„ «·„—Ê— «·Œ«’ »ﬂ";break;
						case 6:msg="≈‰ ’›Õ… «·≈—”«· ·« ÃÌ» ›Ì «·Êﬁ  «·Õ«·Ì (ﬁœ ÌﬂÊ‰ Â‰«ﬂ ÿ·» ﬂ»Ì— ⁄·Ï «·’›Õ… √Ê  Êﬁ› „ƒﬁ  ··’›Õ… ›ﬁÿ Õ«Ê· „—… √Œ—Ï √Ê  Ê«’· „⁄ «·œ⁄„ «·›‰Ì ≈–« ≈” „— «·Œÿ√";break;
						case 12:msg="≈‰ Õ”«»ﬂ »Õ«Ã… ≈·Ï  ÕœÌÀ Ì—ÃÏ „—«Ã⁄… «·œ⁄„ «·›‰Ì";break;
						case 13:msg="≈‰ ≈”„ «·„—”· «·–Ì ≈” Œœ„ Â ›Ì Â–Â «·—”«·… ·„ Ì „ ﬁ»Ê·Â. (Ì—ÃÏ ≈—”«· «·—”«·… »≈”„ „—”· ¬Œ— √Ê  ⁄—Ì› ≈”„ «·„—”· ·œÏ „Ê»«Ì·Ì";break;
						case 14:msg="≈‰ ≈”„ «·„—”· «·–Ì ≈” Œœ„ Â €Ì— „⁄—› ·œÏ „Ê»«Ì·Ì. (Ì„ﬂ‰ﬂ  ⁄—Ì› ≈”„ «·„—”· „‰ Œ·«· ’›Õ… ≈÷«›… ≈”„ „—”·";break;
						case 15:msg="ÌÊÃœ —ﬁ„ ÃÊ«· Œ«ÿ∆ ›Ì «·√—ﬁ«„ «· Ì ﬁ„  »«·≈—”«· ·Â«. ( √ﬂœ „‰ ’Õ… «·√—ﬁ«„ «· Ì  —Ìœ «·≈—”«· ·Â« Ê√‰Â« »«·’Ì€… «·œÊ·Ì…";break;
						case 16:msg="«·—”«·… «· Ì ﬁ„  »≈—”«·Â« ·«  Õ ÊÌ ⁄·Ï ≈”„ „—”·. (√œŒ· ≈”„ „—”· ⁄‰œ ≈—”«·ﬂ «·—”«·…";break;
						case 17:msg="„ Ì „ «—”«· ‰’ «·—”«·…. «·—Ã«¡ «· √ﬂœ „‰ «—”«· ‰’ «·—”«·… Ê«· √ﬂœ „‰  ÕÊÌ· «·—”«·… «·Ï ÌÊ‰Ì ﬂÊœ («·—Ã«¡ «· √ﬂœ „‰ «” Œœ«„ «·œ«·…";break;
						case -1:msg="·„ Ì „ «· Ê«’· „⁄ Œ«œ„ (Server) «·≈—”«· „Ê»«Ì·Ì »‰Ã«Õ. (ﬁœ ÌﬂÊ‰ Â‰«ﬂ „Õ«Ê·«  ≈—”«· ﬂÀÌ—…  „  „⁄« , √Ê ﬁœ ÌﬂÊ‰ Â‰«ﬂ ⁄ÿ· „ƒﬁ  ÿ—√ ⁄·Ï «·Œ«œ„ ≈–« ≈” „—  «·„‘ﬂ·… Ì—ÃÏ «· Ê«’· „⁄ «·œ⁄„ «·›‰Ì)";break;
						case -2:msg="·„ Ì „ «·—»ÿ „⁄ ﬁ«⁄œ… «·»Ì«‰«  (Database) «· Ì  Õ ÊÌ ⁄·Ï Õ”«»ﬂ Ê»Ì«‰« ﬂ ·œÏ „Ê»«Ì·Ì. (ﬁœ ÌﬂÊ‰ Â‰«ﬂ „Õ«Ê·«  ≈—”«· ﬂÀÌ—…  „  „⁄« , √Ê ﬁœ ÌﬂÊ‰ Â‰«ﬂ ⁄ÿ· „ƒﬁ  ÿ—√ ⁄·Ï «·Œ«œ„ ≈–« ≈” „—  «·„‘ﬂ·… Ì—ÃÏ «· Ê«’· „⁄ «·œ⁄„ «·›‰Ì)";break;
						default:msg="";break;
				   }break;
			case 2:switch(value){
						case 1:msg= "Ì„ﬂ‰ﬂ «·≈—”«· «·¬‰.";break;
						default:msg="«·≈—”«· „ Êﬁ› «·¬‰.";break;
				   }break;
			case 3:switch(value){
						case 1:msg= "—ﬁ„ «·ÃÊ«· €Ì— „ Ê›—.";break;
						case 2:msg="ﬂ·„Â «·„—Ê— «·ﬁœÌ„Â Œ«ÿ∆Â.";break;
						case 3:msg="⁄„·Ì…  €ÌÌ— ¬·„… «·„—Ê—  „  »‰Ã«Õ.";break;
						default:msg="";break;
				   }break;
			case 4:switch(value){
						case 1:msg= "—ﬁ„ «·ÃÊ«· €Ì— „ Ê›—..";break;
						case 2:msg="«·≈Ì„Ì· «·Œ«’ »«·Õ”«» €Ì— „ Ê›—.";break;
						case 3:msg=" „ ≈—”«· ¬·„… «·„—Ê— ⁄·Ï «·ÃÊ«· »‰Ã«Õ.";break;
						case 4:msg="—’Ìœﬂ €Ì— ¬«›Ì ·≈ „«„ ⁄„·Ì… «·≈—”«·.";break;
						case 5:msg=" „ ≈—”«· ¬·„… «·„—Ê— ⁄·Ï «·≈Ì„Ì· »‰Ã«Õ.";break;
						case 6:msg="«·≈Ì„Ì· «·Œ«’ »«·Õ”«» €Ì— ’ÕÌÕ.";break;
						case 7:msg="≈”„ «·Õ”«» «·„” Œœ„ €Ì— ’ÕÌÕ.";break;
						default:msg="";break;
				   }break;
			case 5:switch(value){
						case 1:msg= "—ﬁ„ «·ÃÊ«· €Ì— „ Ê›—.";break;
						case 2:msg= "¬·„… «·„—Ê— Œ«ÿ∆Â.";break;
						default:msg=balance;break;
				   }break;
			
			case 7:switch(value){
						case 0:msg= "«”„ «·„—”· €Ì— „›⁄·.";break;
						case 1:msg= "≈”„ «·„—”· „›⁄·.";break;
						case 2:msg="≈”„ «·„—”· „—›Ê÷ ( „ «œŒ«· ¬Êœ «· ›⁄Ì· ·Â–« «·„—”· 3 „—«  Œÿ√)";break;
						case 3:msg="—ﬁ„ «·ÃÊ«· €Ì— „ Ê›—.";break;
						case 4:msg="ﬂ·„Â «·„—Ê— Œ«ÿ∆Â.";break;
						default:msg="";break;
				   }break;
			}
	}
	public void sendURL(String URL,String parameters,int operationNumber){
		try {
	        URL url;
	        URLConnection urlConnection;
	        DataOutputStream outStream;
	        // Create connection
	        url = new URL(URL);
	        urlConnection = url.openConnection();
	        ((HttpURLConnection)urlConnection).setRequestMethod("POST");
	        urlConnection.setDoInput(true);
	        urlConnection.setDoOutput(true);
	        urlConnection.setUseCaches(false);
	        urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        urlConnection.setRequestProperty("Content-Length", ""+ parameters.length());
	        urlConnection.setRequestProperty("User-agent","Mozilla/4.0");
	        // Create I/O streams
	        outStream = new DataOutputStream(urlConnection.getOutputStream());
	        // Send request
	        outStream.writeBytes(parameters);
	        outStream.flush();
	        outStream.close();
	        // Get Response
	        BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
	        // - For debugging purposes only!
	        String buffer;
	        while((buffer = rd.readLine()) != null) {
	        	try{
	        		selectedMessage(Integer.parseInt(buffer),operationNumber);
	        	}catch(Exception ex){
	        		balance=buffer;
	        	}
	        }
	        // Close I/O streams
	        rd.close();
	        outStream.close();
	    }
	    catch(Exception ex) {
	        System.out.println("Exception cought:\n"+ ex.toString());
	    }
	}
	
	
}