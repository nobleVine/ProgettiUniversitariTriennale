
public class VisitorTest {

public static void main( String args[] ) throws Exception {
		
		Pallone p1 = new Pallone("FootballStreet", 19.90, false);
		Completo c = new Completo("Marchisio", 59.90, 29);
		Package p = new Package("Calcio1");
		Package p2 = new Package("Calcio2");
		System.out.println("Pacchetto: " + p.getNameItem());
		
		p2.add(c);
		p2.add(c);
		
		p.add(p1);
		p.add(c); 
		p.add(p2);
		
		System.out.println("Prezzo del pacchetto: " + p.getPrice() + " €");
		System.out.println("Dimensione pacchetto: " + p.listSize());
		p.getNameItem();
		System.out.println();
		
		System.out.println("Prezzo del pacchetto: " + p.getPrice() + " €");
		System.out.println("Dimensione pacchetto: " + p.listSize());
		System.out.println();
		
		VisitorItem pa = new VisitorPrintItem(); // ConcreteVisitor1
		p.accept(pa);
		p1.accept(pa);
		
		System.out.println();
		
		Item myLeaf = new Leaf("PalloneProS", 50); // ConcreteVisitor2
		Item myLeaf1 = new Leaf("PallonPro2", 60);
		Item myLeaf2 = new Leaf("PallonePro3", 70);
		VisitorMaxLeaf myVisitor = new VisitorMaxLeaf();
		myLeaf.accept(myVisitor);
		
		Item myPackage = new Package("TestLeaf");
		myPackage.add(myLeaf);
		myPackage.add(myLeaf1);
		myPackage.add(myLeaf2);
		myPackage.accept(myVisitor);
		System.out.println("La foglia di costo maggiore è: " + myVisitor.getMax() + " €");
		
		System.out.println();
		
		VisitorMinLeaf myVisitor2 = new VisitorMinLeaf(); // ConcreteVisitor3
		myLeaf.accept(myVisitor2);
		System.out.println("La foglia di costo minimo è: " + myVisitor2.getMin() + " €");
	}
	
}
