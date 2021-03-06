package com.sayhellostream;

import com.sayhellostream.domain.Company;
import com.sayhellostream.domain.Contact;
import com.sayhellostream.domain.PaymentType;
import com.sayhellostream.domain.UserRoleType;
import com.sayhellostream.repo.CompanyRepo;
import com.sayhellostream.repo.TextMessageRepo;
import com.sayhellostream.repo.UserRepo;
import com.sayhellostream.domain.AppUser;
import com.sayhellostream.domain.LessonDay;
import com.sayhellostream.domain.LessonType;
import com.sayhellostream.domain.Payment;
import com.sayhellostream.domain.TextMessage;
import com.sayhellostream.repo.ContactRepo;
import com.sayhellostream.repo.GuardianRepo;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
public class SayHelloStreamApplication {

	@Autowired
	CompanyRepo companyRepo;

	@Autowired
	ContactRepo contactRepo;

	@Autowired
	GuardianRepo guardianRepo;

	@Autowired
	TextMessageRepo textMessageRepo;

	@Autowired
	UserRepo userRepo;

	public static void main(String[] args) {
		SpringApplication.run( SayHelloStreamApplication.class, args);
	}

	@PostConstruct
	@Transactional
	public void loadData() {

		Company company = new Company();
		company.setName( "Houston Voice and Piano" );
		companyRepo.save( company );
		loadDataForCompany( company );


		createUser( "dan", "dantorrey", "Dan", "Torrey", "danielowentorrey@gmail.com", company );

		Company company2 = new Company();
		company2.setName( "Company 2" );
		companyRepo.save( company2 );
//        loadDataForCompany( company2 );
		createUser( "sara", "saratorrey", "Sara", "Torrey", "sara.moravej@gmail.com", company );
		createUser( "owen", "owentorrey", "Oweny", "Torrey", "owen.torrey@gmail.com", company );


		TextMessage message = new TextMessage();
		message.setFirstName( "Sara" );
		message.setLastName ( "Torrey" );
		message.setBody( "Test scheduled message" );
		message.setWasSent( true );
		message.setSendDate( DateTime.now().minusDays( 1 ) );
		textMessageRepo.save( message );

		message = new TextMessage();
		message.setBody( "Test sent message" );
		message.setFirstName( "Dan" );
		message.setLastName ( "Torrey" );
		message.setWasSent( true );
		message.setSendDate( DateTime.now().minusDays( 1 ) );
		textMessageRepo.save( message );

		message = new TextMessage();
		message.setBody( "Test sent message2" );
		message.setFirstName( "Dan" );
		message.setLastName( "Torrey" );
		message.setWasSent( true );
		message.setSendDate( DateTime.now().minusDays( 1 ) );
		textMessageRepo.save( message );
	}

	private void createUser( String username,
							 String password,
							 String firstName,
							 String lastName,
							 String email,
							 Company company ) {

		AppUser user = new AppUser();
		user.setUsername( username );
		user.setFirstName( firstName );
		user.setLastName( lastName );
		user.setEmail( email );
		user.setCompany( company );
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = bCryptPasswordEncoder.encode( password );
		user.setPassword( encodedPassword );
		user.setRole( UserRoleType.ADMIN );
		userRepo.save( user );
	}

	private void loadDataForCompany( Company company ) {

		Contact contact = new Contact( "Dan", "Torrey", "Monday 5pm", company, "(832) 707-2323");
		contact.setLessonType(LessonType.VOICE );
		contact.setLessonDay(LessonDay.MONDAY );
		contact.setLessonTime(new org.joda.time.LocalTime(14, 0, 0, 0 ) );
		
		Payment payment = new Payment();
		payment.setDate(LocalDate.now() );
		payment.setAmount(Money.of(CurrencyUnit.USD, BigDecimal.TEN ) );
		payment.setType( PaymentType.CASH );
		payment.setContact(contact);
		contact.getPayments().add(payment );

		payment = new Payment();
		payment.setDate( LocalDate.now().minusDays( 90 ) );
		payment.setAmount( Money.of( CurrencyUnit.USD, BigDecimal.ONE ) );
		payment.setType( PaymentType.CHECK );
		payment.setContact(contact);
		contact.getPayments().add(payment );

		payment = new Payment();
		payment.setDate( LocalDate.now().minusDays( 90 ) );
		payment.setAmount( Money.of( CurrencyUnit.USD, new BigDecimal( "25" ) ) );
		payment.setType( PaymentType.CHECK );
		payment.setContact(contact);
		contact.getPayments().add(payment );

		contactRepo.save(contact);
		contactRepo.save(new Contact("Sara", "Torrey", "Monday 3pm", company, "(832) 707-2323") );
		contactRepo.save(new Contact("Nino", "Torrey", "Wednesday 4pm", company, "(832) 707-2323") );
		contactRepo.save(new Contact("Owen", "Torrey", "Monday 1pm", company, "(832) 707-2323") );
		contactRepo.save(new Contact("Cameron", "Torrey", "Monday 2pm", company, "(832) 707-2323") );

	}
}

