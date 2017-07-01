
	public class Proprietario {
		
		// Singleton
		
		private static String name; 
		private static String surname;
		private static double cash;
			
		private static Proprietario proprietario;
		
		private Proprietario () {}; 
		
		public static Proprietario getRef () {
			return proprietario;
		}
		
		public static String getName() {
			return name;
		}

		public static void setName(String name) {
			Proprietario.name = name;
		}

		public static String getSurname() {
			return surname;
		}

		public static void setSurname (String surname ) {
			Proprietario.surname = surname;
		}
		
		public static void setCash ( double cash, Sponsor s) {
			Proprietario.cash = cash + s.getInvestment();
		}
		
		public static double getCash () {
			return cash;
		}
		
	}
