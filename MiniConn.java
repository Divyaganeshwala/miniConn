package MiniConn;
import java.util.*;

class Ex extends Exception {
	public Ex(String message) {
		super(message);
	}
}

public class MiniConn {

	// static things
	static Scanner sc = new Scanner(System.in);
	static HashMap<String, PriorityQueue<FreelancerNode>> connect = new HashMap<>();
	static ArrayList<String> arrSkill;

	// freeeLancerNode -> freeLancer Acc
	static class FreelancerNode implements Comparable<FreelancerNode> {

		String password;
		String name;
		double age;
		String portfolioLink;
		double yearsOfExp;
		double balance;

		public FreelancerNode(String password, String name, double age, String portfolioLink, double yearsOfExp) {

			this.password = password;
			this.name = name;
			this.age = age;
			this.portfolioLink = portfolioLink;
			this.yearsOfExp = yearsOfExp;
		}

		//T.C.: 0(1)
		public int compareTo(FreelancerNode other) {
			return Double.compare(other.yearsOfExp, this.yearsOfExp);
		}
	}

	// CompanyNode -> company Acc

	static class HireRecord {
		String nameFl;
		String skillOption;
		double term;
		double stipend;

		HireRecord(String nameF, String skillOption, double term, double stipend) {
			this.nameFl = nameF;
			this.skillOption = skillOption;
			this.term = term;
			this.stipend = stipend;

		}
	}

	static class CompanyNode {

		String password;
		String name;
		String skillOpt;
		ArrayList<HireRecord> record;

		public CompanyNode(String password, String name, String skillOpt) {

			this.password = password;
			this.name = name;
			this.skillOpt = skillOpt;
			this.record = new ArrayList<>();
		}
	}

	// skillTree -> hardCoded BST creation + methods of BST(searchAsPerSkill,
	// addNewSkill)
	static class SkillTree {

		static class SkillNode {

			SkillNode left;
			SkillNode right;

			String keySkill;
			
			HashMap<String, FreelancerNode> hmFreelancer = new HashMap<>();
			HashMap<String, CompanyNode> hmCompany = new HashMap<>();

			public SkillNode(String keySkill) {

				this.left = null;
				this.right = null;

				this.keySkill = keySkill;
			}
		}

		public static SkillNode root;

		// skillTree creation
		// methods
		
		//T.C: 0(1)
		public static boolean isEmpty() {
			return root == null;
		}

		static double val= 0;
		public static double Exceptions() {

			try {
				val = sc.nextDouble();
				// Mark as valid to exit the loop
			} catch (InputMismatchException e) {
				sc.nextLine(); // Consume the invalid input
				try {
					throw new Ex("Invalid input. Please enter a valid  integer.");
				} catch (Ex ex) {
					System.out.println(ex.getMessage());
					
					Exceptions();
				}
			}
			return val;
			
		}
		//T.C: 0(logn)
		public static void createSkillTreeUtil(String keySkill) {

			// addSkill to HashMap-> connect
			PriorityQueue<FreelancerNode> pq = new PriorityQueue<>();
			connect.put(keySkill, pq);

			// createNode
			SkillNode newNode = new SkillNode(keySkill);

			// if root isEmpty
			if (isEmpty()) {
				root = newNode;
				return;
			}
			SkillNode curr = root;

			// add skill to skillTree
			for (;;) {

				if (newNode.keySkill.compareTo(curr.keySkill) < 0) {

					// goLeft
					if (curr.left == null) {
						curr.left = newNode;
						break;
					} else {
						curr = curr.left;
					}
				} else {

					// goRight
					if (curr.right == null) {
						curr.right = newNode;
						break;
					} else {
						curr = curr.right;
					}
				}
			}
		}

		public static void createSkillTree() {

			arrSkill = new ArrayList<>();

			// Adding skills directly to the ArrayList
			arrSkill.add("web development");
			arrSkill.add("graphic design");
			arrSkill.add("content writing");
			arrSkill.add("digital marketing");
			arrSkill.add("mobile app development");
			arrSkill.add("video editing");
			arrSkill.add("data entry");
			arrSkill.add("seo (search engine optimization)");
			arrSkill.add("social media management");
			arrSkill.add("translation services");

			// Printing the list of skills
			for (String skill : arrSkill) {
				createSkillTreeUtil(skill);

			}
		}

		// search 'x' skillNode
		//T.C: 0(logn)
		public static SkillNode searchSkillNode(String skill) {

			// add base case to verify if the skill exists
			// if BST isEmpty
			if (isEmpty()) {
				return null;
			}
			SkillNode curr = root;

			// binary search skillTree
			for (;;) {

				if (curr == null) {
					break;
				}
          
				if (curr.keySkill.equalsIgnoreCase(skill)) {// viola
					break;
				} else if (skill.compareTo(curr.keySkill) < 0) {
					// goLeft
					curr = curr.left;
				} else if (skill.compareTo(curr.keySkill) > 0) {
					// goRight
					curr = curr.right;
				}
			}
			return curr;
		}
	}

//DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD
	// (freelancer & company) newNode creation +
	// additn to resp. skillNode hashmap
	// + addition to connect hm's pq (for freelancer)
	static class FreelancerAccounts extends SkillTree {

		// freelancer Acc
		public static void createFreelancerNode() {

			System.out.println("AVAILABLE SKILLS: ");
			for (int i = 0; i < arrSkill.size(); i++) {
				System.out.println((i + 1) + ")" + arrSkill.get(i));
			}
			
			// INPUT Freelancer details
			sc.nextLine();
			System.out.println("Which keySkill?: ");
			String skillOption = sc.nextLine();
			
			skillOption=skillOption.toLowerCase();

			// find skillNode in BST
			SkillNode skillNode = searchSkillNode(skillOption);
			if (skillNode == null) {
				System.out.println("This skill does not exist.");
				return;
			}
			
			sc.nextLine();
			System.out.println("Create new password: ");
			String password = sc.nextLine();

			// CORNER CASE
			while (skillNode.hmFreelancer.containsKey(password)) {
				
				sc.nextLine();
				System.out.println("THIS password has already been used." + "\nPlease enter another password:");
				password = sc.nextLine();

			}

			sc.nextLine();
			System.out.println("Enter name: ");
			String name = sc.nextLine();
			name=name.toLowerCase();

			System.out.println("Enter Age: ");
			double age = Exceptions();
			sc.nextLine();
			
			System.out.println("Enter portfolioLink: ");
			String portfolioLink = sc.nextLine();
			portfolioLink=portfolioLink.toLowerCase();

			System.out.print("Enter Year of Experience ");
			double yearsOfExp = Exceptions();

			// create freeLancerNode -> account in freelancerhm of BST node
			FreelancerNode newNode = new FreelancerNode(password, name, age, portfolioLink, yearsOfExp);

			// add freelancerNode (account) to freelancer hashmap
			skillNode.hmFreelancer.put(password, newNode);

			// add freeleancerNode to priorityQueue
			connect.get(skillOption).add(newNode);

			System.out.println("Congratulations!! New account created :)");
		}

		// freelancer methods POST LOGIN ->>>>>
		public static void displayProfile() {

			// display String password, String name, int age,String portfolioLink, double
			// yearsOfExp
			System.out.println("Name: " + freelancerNode.name + "\nAge: " + freelancerNode.age + "\nportfolioLink: "
					+ freelancerNode.portfolioLink + "\nyearsOfExp: " + freelancerNode.yearsOfExp);

		}

		public static void updateProfile() {

			// display acc details

			System.out.println("Which fields would you like to update?: " + "\n1.Name" + "\n2.Age" + "\n3.PortfolioLink"
					+ "\n4.YearsOfExp" + "\n5.Password");

			int choice = sc.nextInt();

			switch (choice) {

			case 1:
				sc.nextLine();
				System.out.print("Enter new name: ");
				freelancerNode.name = sc.nextLine();
				freelancerNode.name = freelancerNode.name.toLowerCase();

				break;

			case 2:
				System.out.print("Enter new age:");
				freelancerNode.age = Exceptions();
				break;

			case 3:
				sc.nextLine();
				System.out.print("Enter new portfolioLink:");
				freelancerNode.portfolioLink = sc.nextLine();
				freelancerNode.portfolioLink=freelancerNode.portfolioLink.toLowerCase();
				break;

			case 4:
				System.out.print("Enter new yearsOfExperience:");
				freelancerNode.yearsOfExp = Exceptions();
				break;

			case 5:
				sc.nextLine();
				System.out.print("Enter current password: ");
				String currentPassword = sc.nextLine();

				if (!currentPassword.equals(freelancerNode.password)) {
					System.out.println("Wrong password");
					System.out.println("Updation failed");
					break;
				}
				sc.nextLine();
				System.out.print("Enter new password:");
				freelancerNode.password = sc.nextLine();
				break;

			default:
				System.out.println("INVALID CHOICE");
				break;
			}
		}

		public static void displayBalance() {

			System.out.println("Current account balance: " + freelancerNode.balance);

		}

		public static void withdrawCash() {

			System.out.println("Enter amount to be withdrawn: ");
			double amount = Exceptions();

			if (amount > freelancerNode.balance) {
				System.out.println("Maximum amount you can withdraw Rs: " + freelancerNode.balance);
				System.out.println("Withdrawal failed");
				return;
			}

			freelancerNode.balance -= amount;
			displayBalance();
			System.out.println("Withdrawal successful");

		}

		public static void accountBalance() {

			int repeat = 0;
			do {

				System.out.println("Enter the option you would like to perform: " + "\n1.Display account balance"
						+ "\n2.withdraw cash");

				int choice = sc.nextInt();

				switch (choice) {

				case 1:
					displayBalance();
					break;

				case 2:
					withdrawCash();
					break;
				}

				System.out.println("repeat <display bal./ withdraw cash> ?: <1/0>");
				repeat = sc.nextInt();

			} while (repeat == 1);

		}

		public static void deleteAccount(String skillOption) {

			// find skillNode in BST
			SkillNode skillNode = searchSkillNode(skillOption);
			String password = freelancerNode.password;

			// Delete from hmFreelancer
			FreelancerNode deletedToBe = skillNode.hmFreelancer.remove(password);

			Stack<FreelancerNode> stack = new Stack<>();
			while (connect.get(skillOption).peek().password != deletedToBe.password) {
				stack.push(connect.get(skillOption).remove());
			}

			// Reqd. freelancerNode found->> and deleted|| removed
			connect.get(skillOption).remove();

			// Refill the connect pq
			while (!stack.isEmpty()) {
				connect.get(skillOption).add(stack.pop());
			}
		}

		static FreelancerNode freelancerNode;

		public static void login() {

			System.out.println("AVAILABLE SKILLS: ");
			for (int i = 0; i < arrSkill.size(); i++) {
				System.out.println((i + 1) + ")" + arrSkill.get(i));
			}
			sc.nextLine();
			System.out.println("Enter your skill?: ");
			String skillOption = sc.nextLine();
			skillOption=skillOption.toLowerCase();

			// find skillNode in BST
			SkillNode skillNode = searchSkillNode(skillOption);
			if (skillNode == null) {
				System.out.println("This skill does not exist.");
				return;
			}

			System.out.println("Enter account password: ");
			String password = sc.nextLine();
			if (!skillNode.hmFreelancer.containsKey(password)) {
				System.out.println("This account does not exist");
				return;
			}

			// get freelancerNode from freelancerhm
			freelancerNode = skillNode.hmFreelancer.get(password);

			int repeat = 0;
			boolean deleted = false;

			do {

				System.out.println("Enter the option you would like to perform: " + "\n1.Display profile"
						+ "\n2.Update profile" + "\n3.Manage balance account" + "\n4.Delete account");

				int Choice = sc.nextInt();

				switch (Choice) {

				case 1:
					FreelancerAccounts.displayProfile();
					break;

				case 2:
					FreelancerAccounts.updateProfile();
					break;

				case 3:
					FreelancerAccounts.accountBalance();
					break;

				case 4:
					FreelancerAccounts.deleteAccount(skillOption);
					deleted = true;
					break;

				default:
					System.out.println("INVALID OPTION");
					break;
				}

				if (deleted == true) {
					break;
				}
				System.out.println("repeat <Post Login choices >? <1/0>");
				repeat = sc.nextInt();

			} while (repeat == 1);

		}

	}

//DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD	
//VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV	
	static class CompanyAccounts extends SkillTree {

		// Create for first time
		public static void createCompanyNode() {

			sc.nextLine();
			System.out.println("Enter Company name: ");
			String name = sc.nextLine();
			name=name.toLowerCase();

			System.out.println("Available skills: ");
			for (int i = 0; i < arrSkill.size(); i++) {
				System.out.println((i + 1) + ")" + arrSkill.get(i));
			}
			System.out.println("Which keySkill?: ");
			String skillOption = sc.nextLine();
			skillOption=skillOption.toLowerCase();

			SkillNode skillNode = searchSkillNode(skillOption);

			// SKILL VALIDATION
			if (skillNode == null) {
				System.out.println("This skill does not exist.");
				return;
			}

			System.out.println("Create a password: ");
			String password = sc.nextLine();

			// PASSWORD VALIDATION
			while (skillNode.hmCompany.containsKey(password)) {

				System.out.println("This password has already been used! Pls enter another password");
				password = sc.nextLine();
			}

			// create node
			CompanyNode companyNode = new CompanyNode(password, name, skillOption);

			// skillNode.hmfreelancer.put(password, newNode);
			skillNode.hmCompany.put(password, companyNode);

			System.out.println("Congratulations!!! New account created :)");
		}

		// postLogin company methods
		// 1.UPDATE PROFILE
		public static void updateCompanyProf() {

			System.out.println("Which fields would you like to update?: " + "\n1.Name" + "\n2.Password");

			int choice = sc.nextInt();

			switch (choice) {

			case 1:
				sc.nextLine();
				System.out.print("Enter new name: ");
				companyNode.name = sc.nextLine();
				companyNode.name=companyNode.name.toLowerCase();
				break;

			case 2:

				sc.nextLine();
				System.out.print("Enter current password: ");
				String currentPassword = sc.nextLine();

				if (!currentPassword.equals(companyNode.password)) {
					System.out.println("Wrong password");
					System.out.println("Updation failed");
					break;
				}

				System.out.print("Enter new password:");
				companyNode.password = sc.nextLine();
				break;

			default:
				System.out.println("INVALID CHOICE");
				break;
			}
		}

		// 2.FIND FREELANCER
		public static void findFreelancer() {

			Stack<FreelancerNode> rejectedStack = new Stack<>();
			PriorityQueue<FreelancerNode> pq = connect.get(companyNode.skillOpt);

			int happy = 0;
			while (happy != 1) {

				if (pq.isEmpty()) {
					System.out.println("No freelancer available. Please try again later.");

					while (!rejectedStack.isEmpty()) {

						// add back to priorityQueue in connect
						pq.add(rejectedStack.pop());
					}
					return;
				}

				FreelancerNode freelancerNode = pq.remove();

				// display fl profile
				System.out.println("You will be interviewing: ");
				System.out.println("Name: " + freelancerNode.name + "\nAge: " + freelancerNode.age + "\nportfolioLink: "
						+ freelancerNode.portfolioLink + "\nyearsOfExp: " + freelancerNode.yearsOfExp);

				// satisfied
				System.out.println("confirm this freelancer?: Enter 1 for 'YES', else enter 0:");
				happy = sc.nextInt();

				if (happy != 1) {
					rejectedStack.push(freelancerNode);
					continue;
				}

				if (happy == 1) {

					while (!rejectedStack.isEmpty()) {

						// add back to priorityQueue in connect
						pq.add(rejectedStack.pop());
					}
					System.out.println("Enter term of agreement in years: ");
					double term = Exceptions();

					System.out.println("Term is over! Enter amount to be paid: ");
					double stipend = Exceptions();

					freelancerNode.balance += stipend;
					freelancerNode.yearsOfExp += term;

					// hirerecord add fl
					HireRecord hr = new HireRecord(freelancerNode.name, companyNode.skillOpt, term, stipend);
					companyNode.record.add(hr);

					// find confirmed freelancerNode in BST-> hm
					SkillNode skillNode = searchSkillNode(companyNode.skillOpt);
					FreelancerNode hmFreelancerNode = skillNode.hmFreelancer.get(freelancerNode.password);

					// update freelancerNode details
					//hmFreelancerNode.balance += stipend;
					//hmFreelancerNode.yearsOfExp += term;

					// >> add freelancer back to pq
					connect.get(companyNode.skillOpt).add(freelancerNode);

					break;

				}
			}
		}

		// 3.DELETE COMPANY ACCOUNT
		public static void deleteCompanyAccount() {

			// delete from BST
			SkillNode skillNode = searchSkillNode(companyNode.skillOpt);
			skillNode.hmCompany.remove(companyNode.password);

			System.out.println("Account deleted");
		}

		// 4.DISPLAY COMPANY PROFILE

		public static void displayCompanyProfile() {

			System.out.println("PROFILE");
			System.out.println("Name: " + companyNode.name);
			System.out.println("Skill: " + companyNode.skillOpt);
			System.out.println("Freelancers employed till now: ");

			for (HireRecord hr : companyNode.record) {

				if (hr == null) {
					System.out.println("No freelancers have been hired yet ");
					return;
				}
				System.out.println("FreeLancer: " + hr.nameFl + " was hired for skill: " + hr.skillOption + "\nTerm: "
						+ hr.term + "\nPayment: " + hr.stipend);
			}
		}

		// LOGIN
		// static companyNode created for the particular company. changes with every
		// login.
		static CompanyNode companyNode;

		public static void loginCompany() {

			sc.nextLine();
			System.out.println("Enter skill: ");
			String skillOption = sc.nextLine();
			skillOption=skillOption.toLowerCase();
			
			SkillNode skillNode = searchSkillNode(skillOption);

			// SKILL VALIDATION
			if (skillNode == null) {
				System.out.println("Skill is not present. Pls request Admin to update this skill");
				return;
			}

			System.out.println("Enter password: ");
			String password = sc.nextLine();

			// PASSWORD VALIDATION
			int trial = 0;
			while (!skillNode.hmCompany.containsKey(password)) {
				System.out.println("Incorrect password!Pls re-enter password");
				password = sc.nextLine();

				trial++;
				if (trial == 3) {
					System.out.println("Login failed");
					return;
				}
			}

			int repeat = 0;
			boolean deleted = false;

			companyNode = skillNode.hmCompany.get(password);
			// CORRECT PASSWORD
			do {
				System.out.println("Enter the operation number you would like to perform:" + "\n1.Update profile"
						+ "\n2.Find freelancer" + "\n3.Delete account" + "\n4.Display Profile");

				int choice = sc.nextInt();

				switch (choice) {

				case 1:
					updateCompanyProf();
					break;
				case 2:
					findFreelancer();
					break;
				case 3:
					deleteCompanyAccount();
					deleted = true;
					break;

				case 4:
					displayCompanyProfile();
				}

				if (deleted == true) {
					break;
				}
				System.out.println("repeat <postLogin choice>: <1/0> ?: ");
				repeat = sc.nextInt();

			} while (repeat == 1);

		}

	}

	// VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV

	static class AdminAccount extends SkillTree {
		// RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
		public static void addNewSkill() {
			
			System.out.println("Available Skills");
			for (int i = 0; i < arrSkill.size(); i++) {
				System.out.println((i + 1) + ")" + arrSkill.get(i));
			}
			sc.nextLine();
			System.out.println("Enter new skill to add :");
			
			String newSkill = sc.nextLine();
			newSkill=newSkill.toLowerCase();

			// CHECK FOR UNIQUE SKILL
			if (connect.containsKey(newSkill)) {
				System.out.println("This skill already exists in the dataBase");
				return;
			}

			createSkillTreeUtil(newSkill);
			arrSkill.add(newSkill);
			System.out.println("New skill ADDED to the dataBase");

		}

		// RUN ANALYTICS
		public static void percOfSkills() {

			int totalFreelancers = 0;

			for (String skill : connect.keySet()) {

				PriorityQueue<FreelancerNode> freelancerPQ = connect.get(skill);
				int numFreelancers = freelancerPQ.size();

				totalFreelancers += numFreelancers;

			}

			System.out.println("Total Number of Freelancers: " + totalFreelancers);

			for (String skill : connect.keySet()) {

				PriorityQueue<FreelancerNode> freelancers = connect.get(skill);
				int numFreelancers = freelancers.size();

				System.out.print("% Freelancer having skill " + skill + " ");
				float percentage = ((float) numFreelancers / totalFreelancers) * 100;
				System.out.printf("%.2f", percentage);

				System.out.println();
			}
		}
		
		
		public static SkillNode isInOrder(SkillNode node) {
			while (node.left != null) {
				node = node.left;
			}
			return node;
		}
		
		public static SkillNode delete(String skill, SkillNode node) {
			if (node == null) {
				System.out.println(skill + " not present in database");
				return null;
			}

			if (skill.compareTo(node.keySkill) < 0) {
				node.left = delete(skill, node.left);
			} else if (skill.compareTo(node.keySkill) > 0) {
				node.right = delete(skill, node.right);
			} else {
				if (node.left == null) {
					return node.right;
				}
				if (node.right == null) {
					return node.left;
				}
				SkillNode isNode = isInOrder(node.right);
				node.keySkill = isNode.keySkill;
				node.hmFreelancer = isNode.hmFreelancer;
				node.hmCompany = isNode.hmCompany;
				connect.remove(skill);
				node.right = delete(isNode.keySkill, node.right);
			}
			return node;
		}

		public static void deleteSkill() {
			sc.nextLine();
			System.out.println("Enter Skill to be deleted: ");
			String skill = sc.nextLine();
			skill=skill.toLowerCase();
			root = delete(skill, root);

			if (root == null) {
				return;
			}

			if(connect.containsKey(skill)) {
				connect.remove(skill);
			}
			
			if(arrSkill.contains(String.valueOf(skill))) {
				arrSkill.remove(String.valueOf(skill));
				
				System.out.println("Deletion completed");
				return;
			}
			
		}

	

	}

	public static class Main {
		public static void admin() {

			sc.nextLine();
			System.out.println("Enter password: ");
			String password = sc.nextLine();

			if (!password.equals("xyz")) {
				System.out.println("INVALID!!! HACKER ALERT!!! ");
				return;
			}

			int repeat = 0;

			do {

				System.out.println("Operations:" + "\n1.Add new skill" + "\n2.Run Analytics"+
				"\n3.delete skill"
						+ "\nEnter the operation number you would like to perform: ");

				int operation = sc.nextInt();

				switch (operation) {
				case 1:
					// addNewSkill
					AdminAccount.addNewSkill();
					break;

				case 2:
					// run Analytics
					AdminAccount.percOfSkills();
					break;
				case 3:
					AdminAccount.deleteSkill();
					break;
				}
				System.out.println("repeat <admin Operations>? <1/0>: ");
				repeat = sc.nextInt();

			} while (repeat == 1);
		}

		public static void company() {

			int repeat = 0;
			do {
				System.out.println("Menu: ");
				System.out.println("1.Create Account ");
				System.out.println("2.Login ");
				System.out.println("Enter choice: ");
				int choice = sc.nextInt();

				switch (choice) {
				case 1:
					CompanyAccounts.createCompanyNode();
					break;
				case 2:
					CompanyAccounts.loginCompany();
					break;
				default:
					System.out.println("Invalid option! Pls choose the correct option.");
					break;
				}
				System.out.println("repeat <create/ login> ?<0/1> ?: ");
				repeat = sc.nextInt();
			} while (repeat == 1);

		}

		public static void freelancer() {

			int repeat = 0;
			do {
				System.out.println(
						"Enter the operation number you would like to perform:" + "\n1.Create account" + "\n2.Login");

				int optFreelancer = sc.nextInt();

				switch (optFreelancer) {

				case 1:
					FreelancerAccounts.createFreelancerNode();
					break;

				case 2:
					FreelancerAccounts.login();
					break;

				default:

					System.out.println("INVALID CHOICE");
					break;
				}

				System.out.println("repeat <create Account/ Login>? <1/0>");
				repeat = sc.nextInt();

			} while (repeat == 1);

		}

		public static void main(String[] args) {
			// TODO Auto-generated method stub
			SkillTree.createSkillTree();

			int repeat = 0;
			do {

				System.out.println("Enter choice: " + "\n1.Admin" + "\n2.Company" + "\n3.Freelancer");
				int choice = sc.nextInt();

				switch (choice) {

				case 1:
					admin();
					break;
				case 2:
					company();
					break;
				case 3:
					freelancer();
					break;

				}

				System.out.println("repeat <admin/company/freelancer>? <1/0>");
				repeat = sc.nextInt();
			} while (repeat == 1);
		}
	}

}
/*Enter choice: 
1.Admin
2.Company
3.Freelancer

Enter the operation number you would like to perform:
1.Create account
2.Login
1
AVAILABLE SKILLS: 
1)web development
2)graphic design
3)content writing
4)digital marketing
5)mobile app development
6)video editing
7)data entry
8)seo (search engine optimization)
9)social media management
10)translation services
Which keySkill?: 
web development

Create new password: 
1

Enter name: 
1
Enter Age: 
1
Enter portfolioLink: 
1
Enter Year of Experience 1
Congratulations!! New account created :)
repeat <create Account/ Login>? <1/0>
1
Enter the operation number you would like to perform:
1.Create account
2.Login
1
AVAILABLE SKILLS: 
1)web development
2)graphic design
3)content writing
4)digital marketing
5)mobile app development
6)video editing
7)data entry
8)seo (search engine optimization)
9)social media management
10)translation services
Which keySkill?: 
web development

Create new password: 
2

Enter name: 
2
Enter Age: 
2
Enter portfolioLink: 
2
Enter Year of Experience 2
Congratulations!! New account created :)
repeat <create Account/ Login>? <1/0>
1
Enter the operation number you would like to perform:
1.Create account
2.Login
0
INVALID CHOICE
repeat <create Account/ Login>? <1/0>
0
repeat <admin/company/freelancer>? <1/0>
1
Enter choice: 
1.Admin
2.Company
3.Freelancer
2
Menu: 
1.Create Account 
2.Login 
Enter choice: 
1
Enter Company name: 
db
Available skills: 
1)web development
2)graphic design
3)content writing
4)digital marketing
5)mobile app development
6)video editing
7)data entry
8)seo (search engine optimization)
9)social media management
10)translation services
Which keySkill?: 
web development
Create a password: 
1
Congratulations!!! New account created :)
repeat <create/ login> ?<0/1> ?: 
1
Menu: 
1.Create Account 
2.Login 
Enter choice: 
2
Enter skill: 
web development
Enter password: 
1
Enter the operation number you would like to perform:
1.Update profile
2.Find freelancer
3.Delete account
4.Display Profile
2
You will be interviewing: 
Name: 2
Age: 2.0
portfolioLink: 2
yearsOfExp: 2.0
confirm this freelancer?: Enter 1 for 'YES', else enter 0:
0
You will be interviewing: 
Name: 1
Age: 1.0
portfolioLink: 1
yearsOfExp: 1.0
confirm this freelancer?: Enter 1 for 'YES', else enter 0:
0
No freelancer available. Please try again later.
repeat <postLogin choice>: <1/0> ?: 
1
Enter the operation number you would like to perform:
1.Update profile
2.Find freelancer
3.Delete account
4.Display Profile
2
You will be interviewing: 
Name: 2
Age: 2.0
portfolioLink: 2
yearsOfExp: 2.0
confirm this freelancer?: Enter 1 for 'YES', else enter 0:
1
Enter term of agreement in years: 
3
Term is over! Enter amount to be paid: 
5000
repeat <postLogin choice>: <1/0> ?: 
1
Enter the operation number you would like to perform:
1.Update profile
2.Find freelancer
3.Delete account
4.Display Profile
1
Which fields would you like to update?: 
1.Name
2.Password
1
Enter new name: d
repeat <postLogin choice>: <1/0> ?: 
1
Enter the operation number you would like to perform:
1.Update profile
2.Find freelancer
3.Delete account
4.Display Profile
4
PROFILE
Name: d
Skill: web development
Freelancers employed till now: 
FreeLancer: 2 was hired for skill: web development
Term: 3.0
Payment: 5000.0
repeat <postLogin choice>: <1/0> ?: 
1
Enter the operation number you would like to perform:
1.Update profile
2.Find freelancer
3.Delete account
4.Display Profile
3
Account deleted
repeat <create/ login> ?<0/1> ?: 
1
Menu: 
1.Create Account 
2.Login 
Enter choice: 
2
Enter skill: 
web development
Enter password: 
1
Incorrect password!Pls re-enter password
1
Incorrect password!Pls re-enter password
1
Incorrect password!Pls re-enter password
1
Login failed
repeat <create/ login> ?<0/1> ?: 
0
repeat <admin/company/freelancer>? <1/0>
1
Enter choice: 
1.Admin
2.Company
3.Freelancer
1
Enter password: 
xyz
Operations:
1.Add new skill
2.Run Analytics
3.delete skill
Enter the operation number you would like to perform: 
1
Available Skills
1)web development
2)graphic design
3)content writing
4)digital marketing
5)mobile app development
6)video editing
7)data entry
8)seo (search engine optimization)
9)social media management
10)translation services
Enter new skill to add :
golfing
New skill ADDED to the dataBase
repeat <admin Operations>? <1/0>: 
1
Operations:
1.Add new skill
2.Run Analytics
3.delete skill
Enter the operation number you would like to perform: 
2
Total Number of Freelancers: 2
% Freelancer having skill digital marketing 0.00
% Freelancer having skill content writing 0.00
% Freelancer having skill graphic design 0.00
% Freelancer having skill video editing 0.00
% Freelancer having skill social media management 0.00
% Freelancer having skill web development 100.00
% Freelancer having skill data entry 0.00
% Freelancer having skill translation services 0.00
% Freelancer having skill golfing 0.00
% Freelancer having skill mobile app development 0.00
% Freelancer having skill seo (search engine optimization) 0.00
repeat <admin Operations>? <1/0>: 
1
Operations:
1.Add new skill
2.Run Analytics
3.delete skill
Enter the operation number you would like to perform: 
3
Enter Skill to be deleted: 
golfing
Deletion completed
repeat <admin Operations>? <1/0>: 
1
Operations:
1.Add new skill
2.Run Analytics
3.delete skill
Enter the operation number you would like to perform: 
2
Total Number of Freelancers: 2
% Freelancer having skill digital marketing 0.00
% Freelancer having skill content writing 0.00
% Freelancer having skill graphic design 0.00
% Freelancer having skill video editing 0.00
% Freelancer having skill social media management 0.00
% Freelancer having skill web development 100.00
% Freelancer having skill data entry 0.00
% Freelancer having skill translation services 0.00
% Freelancer having skill mobile app development 0.00
% Freelancer having skill seo (search engine optimization) 0.00
repeat <admin Operations>? <1/0>: 
0
repeat <admin/company/freelancer>? <1/0>
1
Enter choice: 
1.Admin
2.Company
3.Freelancer
3
Enter the operation number you would like to perform:
1.Create account
2.Login
2
AVAILABLE SKILLS: 
1)web development
2)graphic design
3)content writing
4)digital marketing
5)mobile app development
6)video editing
7)data entry
8)seo (search engine optimization)
9)social media management
10)translation services
Enter your skill?: 
web development
Enter account password: 
1
Enter the option you would like to perform: 
1.Display profile
2.Update profile
3.Manage balance account
4.Delete account
1
Name: 1
Age: 1.0
portfolioLink: 1
yearsOfExp: 1.0
repeat <Post Login choices >? <1/0>
3
repeat <create Account/ Login>? <1/0>
1
Enter the operation number you would like to perform:
1.Create account
2.Login
2
AVAILABLE SKILLS: 
1)web development
2)graphic design
3)content writing
4)digital marketing
5)mobile app development
6)video editing
7)data entry
8)seo (search engine optimization)
9)social media management
10)translation services
Enter your skill?: 
web development
Enter account password: 
2
Enter the option you would like to perform: 
1.Display profile
2.Update profile
3.Manage balance account
4.Delete account
1
Name: 2
Age: 2.0
portfolioLink: 2
yearsOfExp: 8.0
repeat <Post Login choices >? <1/0>
1
Enter the option you would like to perform: 
1.Display profile
2.Update profile
3.Manage balance account
4.Delete account
3
Enter the option you would like to perform: 
1.Display account balance
2.withdraw cash
1
Current account balance: 10000.0
repeat <display bal./ withdraw cash> ?: <1/0>
0
repeat <Post Login choices >? <1/0>
1
Enter the option you would like to perform: 
1.Display profile
2.Update profile
3.Manage balance account
4.Delete account
4
repeat <create Account/ Login>? <1/0>
1
Enter the operation number you would like to perform:
1.Create account
2.Login
1
AVAILABLE SKILLS: 
1)web development
2)graphic design
3)content writing
4)digital marketing
5)mobile app development
6)video editing
7)data entry
8)seo (search engine optimization)
9)social media management
10)translation services
Which keySkill?: 
web development

Create new password: 
2

Enter name: 
2
Enter Age: 
2
Enter portfolioLink: 
2
Enter Year of Experience 2
Congratulations!! New account created :)
repeat <create Account/ Login>? <1/0>
1 
Enter the operation number you would like to perform:
1.Create account
2.Login
*/