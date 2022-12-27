package com.paymybuddy.transaction;

import com.paymybuddy.transaction.models.Account;
import com.paymybuddy.transaction.models.Transfer;
import com.paymybuddy.transaction.models.User;
import com.paymybuddy.transaction.repositories.TransferRepository;
import com.paymybuddy.transaction.repositories.UserRepository;
import java.util.Date;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class TransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(PasswordEncoder encoder,
							 UserRepository userRepository,
							 TransferRepository transferRepository) {

		return args -> {
			//--------------
			List<User> users = new ArrayList<>();
			List<Account> accounts = new ArrayList<>();
			String role = "ROLE_USER";
			String roleAdmin = "ROLE_USER,ROLE_ADMIN";



			List<String> firstnames = List.of("user", "admin", "Jean", "Anna", "Olivier", "Sophie", "Omar", "Greg");
			List<String> lastnames = List.of("user", "admin", "Gouvert", "Doter", "Cavaillon", "Martin", "Sy", "Roland");
			List<String> roles = List.of(role, roleAdmin, role, role, role, role, role, role);
			List<Double> balances = List.of(0.0, 0.0, 0.0, 50.0, 100.0, 45.0, 18.50, 73.65);
			for (int i =0; i < 8; i++) {
				User user = new User();
				user.setFirstName(firstnames.get(i));
				user.setLastName(lastnames.get(i));
				user.setRoles(roles.get(i));
				user.setUsername(firstnames.get(i).toLowerCase() + "." + lastnames.get(i).toLowerCase() + "@mymail.com");
				user.setPassword(encoder.encode(firstnames.get(i)));
				user.setAccount(new Account());
				user.getAccount().setBalance(balances.get(i));
				users.add(user);
			}
			try {
				users.forEach(user -> {
					User userFromDB = userRepository.save(user);
					accounts.add(userFromDB.getAccount());
				});
				System.out.println("Users Saved!");

			} catch (Exception e){
				System.out.println("Unable to save users: " + e.getMessage());
			}

			//Create some transfers :
			Transfer transfer1 = Transfer.builder()
					.accountSender(accounts.get(0))
					.accountReceiver(accounts.get(1))
					.date(new Date())
					.amount(1523.56)
					.description("Chaussure édition collector")
					.build();


			Transfer transfer2 = Transfer.builder()
					.accountSender(accounts.get(2))
					.accountReceiver(accounts.get(3))
					.date(new Date())
					.amount(6.60)
					.description("Tacos")
					.build();


			Transfer transfer3 = Transfer.builder()
					.accountSender(accounts.get(4))
					.accountReceiver(accounts.get(5))
					.date(new Date())
					.amount(50.00)
					.description("Resto")
					.build();

			Transfer transfer4 = Transfer.builder()
					.accountSender(accounts.get(0))
					.accountReceiver(accounts.get(3))
					.date(new Date())
					.amount(59.00)
					.description("Resto")
					.build();

			Transfer transfer5 = Transfer.builder()
					.accountSender(accounts.get(4))
					.accountReceiver(accounts.get(0))
					.date(new Date())
					.amount(120.00)
					.description("Resto")
					.build();

			List<Transfer> transfers = List.of(transfer1, transfer2, transfer3, transfer4, transfer5);
			try {
				transferRepository.saveAll(transfers);
				System.out.println("transfers Saved!");

			} catch (Exception e){
				System.out.println("Unable to save transfers: " + e.getMessage());
			}

		};
	}

}
