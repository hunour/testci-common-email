package org.apache.commons.mail;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EmailTest {
	private EmailConcrete email;
	
	private static String[] TEST_EMAILS = {"ab@bc.com", "a.b@c.org", "abcdefghijklmnopqrst@abcdefghijklmnopqrst.com.bd"};
	private static String[] BAD_TEST_EMAILS = {};
	
	private static String goodHeaderName = "headerName";
	private static String goodHeaderValue = "some header value";
	
	@Before
	public void setUpEmailTest() throws Exception{
		email = new EmailConcrete();
	}
	
	@After
	public void tearDownEmailTest() throws Exception{}
	
	//testing addBcc(String... emails) with good input
		@Test
		public void testAddBcc() throws Exception{
			email.addBcc(TEST_EMAILS);
			assertEquals(3, email.getBccAddresses().size());	
		}
	
		//test addCc(String email)
		@Test
		public void testAddCc() throws Exception{
			email.addCc(TEST_EMAILS[1]);
			assertNotNull(email.getCcAddresses());
			assertEquals(1, email.getCcAddresses().size());	
		}
		
		//test addHeader(String name, String value)
		@Test
		public void testAddHeader() throws Exception{
			email.addHeader(this.goodHeaderName, this.goodHeaderValue);
		}
		
		@Test (expected = IllegalArgumentException.class)
		public void testAddHeaderNullVal() throws Exception{
			String name = "TestHeader";
			email.addHeader(name, null);
		}
		
		@Test (expected = IllegalArgumentException.class)
		public void testAddHeaderNullName() throws Exception{
			String value = "1";
			email.addHeader(null, value);
		}
		
		//test addReplyTo(String email, String name)
		@Test
		public void testAddReplyTo() throws Exception
		{
			email.addReplyTo(TEST_EMAILS[2], "SOME_NAME");
			assertNotNull(email.getReplyToAddresses());
		}
		//test buildMimeMessage()
		@Test 
		public void testBuildMimeMessage() throws Exception
		{
			String subject = "Message Subject";
			email.setSubject(subject);
			assertEquals(subject, email.getSubject());
			
			String contentType = "Content Type";
			email.updateContentType(contentType);
			assertNotNull(contentType);
			
			String fromEmail = TEST_EMAILS[1];
			email.setFrom(fromEmail);
			assertEquals(fromEmail, email.getFromAddress().toString());
			
			String [] toEmails = TEST_EMAILS;
			email.addTo(toEmails);
			assertEquals(3, email.getToAddresses().size());
			
			String reply_To = TEST_EMAILS[2];
			email.addReplyTo(reply_To);
			assertEquals(1, email.getReplyToAddresses().size());
			
			
			String [] bcc = TEST_EMAILS;
			email.addBcc(bcc);
			assertEquals(3, email.getBccAddresses().size());
			
			email.addHeader(this.goodHeaderName, this.goodHeaderValue);
			
			email.setCharset("UTF-8");
			
			String host = "Hostname";
			email.setHostName(host);
			assertEquals(host, email.getHostName());
			
			email.buildMimeMessage();
			email.buildMimeMessage();
		}
	
	@Test  (expected = IllegalStateException.class)
	public void testBuildMimeMessageTwice() throws Exception
	{
		String host = "hostName";
		email.setHostName(host);
		email.setFrom(TEST_EMAILS[1]);
		email.addReplyTo(TEST_EMAILS[2]);
		email.setTo(email.getReplyToAddresses());
		email.buildMimeMessage();
		email.buildMimeMessage();
	}
	
	@Test (expected = EmailException.class)
	public void testBuildMimeMessageNullFrom() throws Exception
	{
		String host = "hostName";
		email.setHostName(host);
		email.addReplyTo(TEST_EMAILS[2]);
		email.setTo(email.getReplyToAddresses());
		email.buildMimeMessage();
	}
	@Test (expected = EmailException.class)
	public void testBuildMimeMessageNullHostname() throws Exception
	{
		//String host = "hostName";
		//email.setHostName(host);
		email.setFrom(TEST_EMAILS[1]);
		email.addReplyTo(TEST_EMAILS[2]);
		email.setTo(email.getReplyToAddresses());
		email.buildMimeMessage();
	}
	
	@Test (expected = EmailException.class)
	public void testBuildMimeMessageNullTo() throws Exception{
		String hostname = "HostName";
		email.setHostName(hostname);
		email.setFrom(TEST_EMAILS[1]);
		email.addReplyTo(TEST_EMAILS[2]);
		email.buildMimeMessage();
	}	
	
	@Test
	public void testGetHostName() throws Exception
	{
		String host = "Hostname";
		email.setHostName(host);
		assertEquals(host, email.getHostName());
	}
	
	
	@Test (expected = EmailException.class)
	public void testGetHostNameNull() throws EmailException
	{
		email.setHostName(null);
		assertNotNull(email.getHostName());
	}
	
	@Test
	public void testGetMailSession() throws Exception
	{
		String host = "Hostname";
		email.setHostName(host);
		email.setBounceAddress(TEST_EMAILS[2]);
		email.setAuthentication("some_username", "some_password");
		assertEquals(host, email.getHostName());
		assertNotNull(email.getBccAddresses());
		assertNotNull(email.getMailSession());
	}
	
	@Test
	public void testGetSentDate() throws Exception
	{
		Date date = new Date(123);
		email.setSentDate(date);
		assertEquals(date, email.getSentDate());
		assertNotNull(email.getSentDate());
	}
	
	@Test
	public void testGetSocketConnectonTimeout() throws Exception
	{
		int socketConTimeout = 123;
		email.setSocketConnectionTimeout(socketConTimeout);
		assertEquals(socketConTimeout, email.getSocketConnectionTimeout());
		assertNotNull(email.getSocketConnectionTimeout());
	}
	
	@Test
	public void testSetFrom() throws Exception
	{
		String fromEmail = TEST_EMAILS[1];
		email.setFrom(fromEmail);
		assertEquals(fromEmail, email.getFromAddress().toString());
	}
	
}
