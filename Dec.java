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
public class Dec
{
	KeyGenerator keyGenerator = null;
	SecretKey secretKey = null;
	Cipher cipher = null;
	public Dec()
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
		ServerSocket soc = new ServerSocket(5217);
		//System.out.println("FTP Server Started on Port Number 5217");
		while (true)
		{
			System.out.println("Waiting for Connection ...");
			transferfile t = new transferfile(soc.accept());
		}
		
	}
	public void Decrypt(String srcPath, String destPath)
	{
		File encryptedFile = new File(srcPath);
		File DecryptedFile = new File(destPath);
		InputStream inStream = null;
		OutputStream outStream = null;
		try
		{
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			inStream = new FileInputStream(encryptedFile);
			outStream = new FileOutputStream(DecryptedFile);
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
class transferfile extends Thread
{
	Socket ClientSoc;
	DataInputStream din;
	DataOutputStream dout;
	transferfile(Socket soc)
	{
		try
		{
			ClientSoc = soc;
			din = new DataInputStream(ClientSoc.getInputStream());
			dout = new DataOutputStream(ClientSoc.getOutputStream());
			System.out.println("FTP Client Connected ...");
			start();
		}
		catch (Exception ex)
			{		}
	}
	void ReceiveFile() throws Exception
	{
		String filename = din.readUTF();
		if (filename.compareTo("File not found") == 0)
		{
			return;
		}
		File f = new File(filename);
		String option;
		if (f.exists())
		{
			dout.writeUTF("File Already Exists");
			option = din.readUTF();
		}
		else
		{
			dout.writeUTF("SendFile");
			option = "Y";
		}
		if (option.compareTo("Y") == 0)
		{
			FileOutputStream fout = new FileOutputStream(f);
			int ch;
			String temp;
			do
			{
				temp = din.readUTF();
				ch = Integer.parseInt(temp);
				if (ch != -1)
				{
					fout.write(ch);
				}
			}
			while (ch != -1);
			fout.close();
			dout.writeUTF("File Send Successfully");
		}
		else
		{
			return;
		}
	}
	public void run()
	{
		while (true)
		{
			try
			{
				System.out.println("Waiting for Command ...");
				String Command = din.readUTF();
				if (Command.compareTo("SEND") == 0)
				{
					System.out.println("\tSEND Command Receiced ...");
					ReceiveFile();
					String fileToEncrypt = "a.jpg";
					String encryptedFile = "encryptedFile.jpg";
					String DecryptedFile = "DecryptedFile.jpg";
					String directoryPath = "E:/ntal/";
					Dec encryptFile = new Dec();
					System.out.println("Starting Decryption...");
					encryptFile.Decrypt(directoryPath + encryptedFile,
		                    directoryPath + DecryptedFile);
					System.out.println("Decryption completed...");
					continue;
				}
				else if (Command.compareTo("DISCONNECT") == 0)
				{
					System.out.println("\tDisconnect Command Received ...");
					System.exit(1);
				}
			}
			catch (Exception ex)
				{	}
		}
	}
}

