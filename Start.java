
public class Start {
	
	private Leaf l1, l2, l3, l4;
	private Package p1, p2;
	
	public Start () {
		l1 = new Pallone("ProSoccer", 20, true);
		l2 = new Completo("Marchisio", 20, 28);
		l3 = new BigliettoPartita("Fiorentina - Inter", 30, "Artemio Franchi", "13 Gennaio 2015", "20:45");
		l4 = new Pallone("BrasialianFootball", 35, true);
		p1 = new Package("Calcio1");
		p2 = new Package("Calcio2");
		addToPack();
		addToWarehouse();
		addSupporterToDatabase();
		addSupporterToBank();
		addDescription();
	}
	
	private void addToPack () {
		p1.add(l1);
		p1.add(l3);
		p2.add(l2);
		p2.add(l4);
	}
	
	private void addToWarehouse () {
		Warehouse w = Warehouse.getRef();
		w.add(l1);
		w.add(l2);
		w.add(l3);
		w.add(l4);
		w.add(p1);
		w.add(p2);
	}
	
	private void addSupporterToDatabase() {
		Supporter s = new ConcreteSupporter("Marco", "Bianchi", 17, "username", "password");
		DatabaseSupporters db = DatabaseSupporters.getRef();
		db.addDb(s);
		Supporter s1 = new ConcreteSupporter("Alessio", "Verdi", 76, "user", "pass");
		db.addDb(s1);
	}
	
	private void addSupporterToBank() {
		ClientBank client1 = new ConcreteClientBank(00000, 00000, 1000);
		ClientBank client2 = new ConcreteClientBank(11111, 11111, 2000);
		Bank bank = Bank.getRef();
		bank.add(client1);
		bank.add(client2);
	}
	
	private void addDescription() {
		
		l1.setDescription("Nuovissimo pallone adatto a tutte le superfici.");
		l2.setDescription("Ecco il completo del centrocampista Claudio Marchisio"+
							" disponibile sia in versione casa che traferta.");
		l3.setDescription("Allo stadio Artemio Franchi l'Inter cerca i tre punti dopo un avvio di stagione difficile."+
							"Calcio d'inizio Domenica 15 Gennaio alle ore 20:45. ");
		l4.setDescription("Ecco il BrasilianFootball, il pallone decorato coi colori del paese"+
							"che vive per il calcio");
		p1.setDescription("Caricati facendo due tiri con ProSoccer prima di Fiorentina-Inter!");
		p2.setDescription("Vesti i panni di Marchisio e gioca a ritmo brasialiano con FootballBrasil!");
						
	}
	
	public static void main ( String args[] ) {
		new Start();
		new FrameLogin();
	}
}
