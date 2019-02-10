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

public class chatclient
{
	final static BigInteger one = new BigInteger("1");
	KeyGenerator keyGenerator = null;
	SecretKey secretKey = null;
	Cipher cipher = null;
	

	public static void main(String args[]) throws Exception
	{
		Socket sk=new Socket("localhost",2000);
		BufferedReader sin=new BufferedReader(new InputStreamReader(sk.getInputStream()));
		PrintStream sout=new PrintStream(sk.getOutputStream());
		BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
		String s;
		String s1;
		Scanner in = new Scanner(System.in);
		BigInteger p = new BigInteger("23");
		BigInteger g = new BigInteger("5");
		System.out.println("Person A: enter your secret number now.");
		BigInteger a = new BigInteger(in.next());
		BigInteger ya = g.modPow(a, p);
		System.out.println("Person A sends to person B " + ya + ".");


			sout.println(ya);
			s=sin.readLine();
			BigInteger yb =new BigInteger(s);
			System.out.print("Server : "+s+"\n");
			BigInteger KA = yb.modPow(a,p);
			System.out.println("A takes "+yb+" raises it to the power "+a+" mod "+p);
			System.out.println("The Key A calculates is "+KA+".");
			s1=sin.readLine();
			BigInteger j =new BigInteger(s1);
			String u=KA.toString();
			if(u.equals(s1))
			{
				System.out.println("Secured Connection..Start sending data..!!");
					
			}
			else
			{
				System.out.println("UnSecured Connection");
  			}
		 sk.close();
		 sin.close();
		 sout.close();
 		stdin.close();
	}
	
}