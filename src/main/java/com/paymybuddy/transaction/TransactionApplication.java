package com.paymybuddy.transaction;

import com.paymybuddy.transaction.models.Account;
import com.paymybuddy.transaction.models.Friendship;
import com.paymybuddy.transaction.models.Transfer;
import com.paymybuddy.transaction.models.User;
import com.paymybuddy.transaction.repositories.FriendshipRepository;
import com.paymybuddy.transaction.repositories.TransferRepository;
import com.paymybuddy.transaction.repositories.UserRepository;
import com.paymybuddy.transaction.services.ITransferService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TransactionApplication {

	@Autowired
	FriendshipRepository friendshipRepository;
	@Autowired
	ITransferService transferService;
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
			List<User> usersFromDB = new ArrayList<>();
			List<Account> accounts = new ArrayList<>();
			String role = "ROLE_USER";
			String roleAdmin = "ROLE_USER,ROLE_ADMIN";



			List<String> firstnames = List.of("user", "admin", "Jean", "Anna", "Olivier", "Sophie", "Omar", "Greg");
			List<String> lastnames = List.of("user", "admin", "Gouvert", "Doter", "Cavaillon", "Martin", "Sy", "Roland");
			List<String> roles = List.of(role, roleAdmin, role, role, role, role, role, role);
			List<Double> balances = List.of(3000d, 500d, 789d, 50.0, 100.0, 45.0, 0d, 73.65);
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
					usersFromDB.add(userFromDB);
					accounts.add(userFromDB.getAccount());
				});
				System.out.println("Users Saved!");

			} catch (Exception e){
				System.out.println("Unable to save users: " + e.getMessage());
			}

			//Add Buddies to users
			List<Friendship> friendShips = List.of(
					new Friendship(usersFromDB.get(0), usersFromDB.get(1)),
					new Friendship(usersFromDB.get(0), usersFromDB.get(3)),
					new Friendship(usersFromDB.get(1), usersFromDB.get(2)),
					new Friendship(usersFromDB.get(4), usersFromDB.get(5)),
					new Friendship(usersFromDB.get(4), usersFromDB.get(0))
			);
			try {
				userRepository.saveAll(usersFromDB);
				friendshipRepository.saveAll(friendShips);
				System.out.println("Users's buddies Saved!");

			} catch (Exception e){
				System.out.println("Unable to save users's buddies: " + e.getMessage());
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
				transfers.forEach(transferService::executeTransfer);
				System.out.println("transfers Saved!");
			} catch (Exception e){
				System.out.println("Unable to save transfers: " + e.getMessage());
			}

		};
	}

}
