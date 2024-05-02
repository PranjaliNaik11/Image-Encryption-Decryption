import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.net.*;
import java.util.*;
public class Enc
{
	KeyGenerator keyGenerator = null;
	SecretKey secretKey = null;
	Cipher cipher = null;
	public Enc()
	{
		try
		{
			secretKey = new SecretKeySpec("FName".getBytes(), "Blowfish");
			cipher = Cipher.getInstance("Blowfish");
		}
		catch (NoSuchPaddingException ex)
		{
			System.out.println(ex);
		}
		catch (NoSuchAlgorithmException ex)
		{
			System.out.println(ex);
		}
	}
	public static void main(String[] args) throws Exception
	{
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		Scanner sc = new Scanner(System.in);
		String optn;
		String fileToEncrypt = "a.jpg";
		String encryptedFile = "encryptedFile.jpg";
		String decryptedFile = "decryptedFile.jpg";
		String directoryPath = "E:";
		Enc encryptFile = new Enc();
		System.out.println("Starting Encryption...");
		encryptFile.encrypt(directoryPath + fileToEncrypt,
		                    directoryPath + encryptedFile);
		System.out.println("Encryption completed...");
		System.out.println("Send File to B ?[Y/N]..");
		optn=sc.nextLine();
		if(optn.equals("Y"))
		{
		Socket soc = new Socket("localhost", 5217);
		transferfileClient t = new transferfileClient(soc);
		t.displayMenu();
		}
	}
	private void encrypt(String srcPath, String destPath)
	{
		File rawFile = new File(srcPath);
		File encryptedFile = new File(destPath);
		InputStream inStream = null;
		OutputStream outStream = null;
		try
		{
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			inStream = new FileInputStream(rawFile);
			outStream = new FileOutputStream(encryptedFile);
			byte[] buffer = new byte[1024];
			int len;
			while ((len = inStream.read(buffer)) > 0)
			{
				outStream.write(cipher.update(buffer, 0, len));
				outStream.flush();
			}
			outStream.write(cipher.doFinal());
			inStream.close();
			outStream.close();
		}
		catch (IllegalBlockSizeException ex)
		{
			System.out.println(ex);
		}
		catch (BadPaddingException ex)
		{
			System.out.println(ex);
		}
		catch (InvalidKeyException ex)
		{
			System.out.println(ex);
		}
		catch (FileNotFoundException ex)
		{
			System.out.println(ex);
		}
		catch (IOException ex)
		{
			System.out.println(ex);
		}
	}
}
class transferfileClient
	{
		Socket ClientSoc;
		DataInputStream din;
		DataOutputStream dout;
		BufferedReader br;
		transferfileClient(Socket soc)
		{
			try
			{
				ClientSoc = soc;
				din = new DataInputStream(ClientSoc.getInputStream());
				dout = new DataOutputStream(ClientSoc.getOutputStream());
				br = new BufferedReader(new InputStreamReader(System.in));
			}
			catch (Exception ex)
			{
			}
		}
		void SendFile() throws Exception
		{
			String filename;
			System.out.print("Enter File Name :");
			filename = br.readLine();
			File f = new File(filename);
			if (!f.exists())
			{
				System.out.println("File not Exists...");
				dout.writeUTF("File not found");
				return;
			}
			dout.writeUTF(filename);
			String msgFromServer = din.readUTF();
			if (msgFromServer.compareTo("File Already Exists") == 0)
			{
				String Option;
				System.out.println("File Already Exists. Want to OverWrite (Y/N) ?");
				Option = br.readLine();
				if (Option == "Y")
				{
					dout.writeUTF("Y");
				}
				else
				{
					dout.writeUTF("N");
					return;
				}
			}
			System.out.println("Sending File ...");
			FileInputStream fin = new FileInputStream(f);
			int ch;
			do
			{
				ch = fin.read();
				dout.writeUTF(String.valueOf(ch));
			}
			while (ch != -1);
			fin.close();
			System.out.println(din.readUTF());
		}
		
		public void displayMenu() throws Exception
		{
			while (true)
			{
				System.out.println("***MENU***");
				System.out.println("1. Send File");
				System.out.println("2. Exit");
				System.out.print("\nEnter Choice :");
				int choice;
				choice = Integer.parseInt(br.readLine());
				if (choice == 1)
				{
					dout.writeUTF("SEND");
					SendFile();
				}
				else
				{
					dout.writeUTF("DISCONNECT");
					System.exit(1);
				}
			}
		}
	}
