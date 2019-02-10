import java.net.*;
import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
public class chatserver
{
	final static BigInteger one = new BigInteger("1");
	KeyGenerator keyGenerator = null;
	SecretKey secretKey = null;
	Cipher cipher = null;
	public static void main(String args[]) throws Exception
	{
		ServerSocket ss=new ServerSocket(2000);
		Socket sk=ss.accept();
		BufferedReader cin=new BufferedReader(new InputStreamReader(sk.getInputStream()));
		PrintStream cout=new PrintStream(sk.getOutputStream());
		BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
		String s;
		Scanner in = new Scanner(System.in);
		BigInteger p = new BigInteger("23");
		BigInteger g = new BigInteger("5");
		System.out.println("Person B: enter your secret number now.");
		BigInteger b = new BigInteger(in.next());
		BigInteger yb = g.modPow(b, p);
		System.out.println("Person B sends to person A " + yb + ".");		

		s=cin.readLine();
		System.out.print("Client : "+s+"\n");
		//s=stdin.readLine();
		BigInteger ya=new BigInteger(s);
		cout.println(yb);
		BigInteger KB= ya.modPow(b,p);
		System.out.println("B takes "+ya+" raises it to the power "+b+" mod "+p);
		System.out.println("The Key B calculates is "+KB+".");
		cout.println(KB);

		ss.close();
 		sk.close();
 		cin.close();
		cout.close();
 		stdin.close();
	}
	
}