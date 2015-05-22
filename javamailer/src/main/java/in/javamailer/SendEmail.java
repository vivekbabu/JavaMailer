package in.javamailer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

	public void sendMail() throws IOException {
		File file = new File("e:\\email.properties");

		Properties userNamePwdProperties = new Properties();
		FileInputStream fis = new FileInputStream(file);
		userNamePwdProperties.load(fis);

		final String username = userNamePwdProperties
				.getProperty("mail.username");
		final String password = userNamePwdProperties
				.getProperty("mail.password");

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("to-user@abc.co"));
			message.setSubject("Automated Message");
			message.setText("Dear User,"
					+ "\n\n This is an automated message!");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws IOException {

		new SendEmail().sendMail();
	}
}