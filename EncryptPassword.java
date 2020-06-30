import java.security.SecureRandom;
import java.lang.Byte;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;
import java.util.Arrays;

public class EncryptPassword
{
	private static SecureRandom random = new SecureRandom();
	
	public static void main(String[] args) {
		byte[] salt = new byte[16];
		random.nextBytes(salt);

		byte[] pass1, pass2;

		String password, password2;
		Scanner input = new Scanner(System.in);
		System.out.print("Enter Password: ");
		password = input.next();
		pass1 = generateHash(password, salt);
		System.out.print("Authenticate Yourself (Enter your password again): ");
		password2 = input.next();
		pass2 = generateHash(password2, salt);
		if (Arrays.equals(pass1, pass2))
		{
			System.out.println("Authentication Successful.");
		}
		else
		{
			System.out.println("Authentication Failed.");
		}

		/*
		Here, we convert the byte arrays to strings and check if they are
		equal which works. After, we convert the strings back to byte
		arrays and chech again which works. This might be useful for how
		passwords are stored in a database.

		String pass1String = new String(pass1);
		String pass2String = new String(pass2);
		System.out.println(pass1String);
		System.out.println(pass2String);
		System.out.println(pass1String.equals(pass2String));

		byte[] pass1BackToByte = pass1String.getBytes();
		byte[] pass2BackToByte = pass2String.getBytes();
		System.out.println();
		System.out.println(pass1BackToByte);
		System.out.println(pass2BackToByte);
		System.out.println(Arrays.equals(pass1BackToByte, pass2BackToByte));
		*/
	}

	public static byte[] generateHash(String password, byte[] salt) {

		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			byte[] hash = factory.generateSecret(spec).getEncoded();
			return hash;
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (InvalidKeySpecException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}

