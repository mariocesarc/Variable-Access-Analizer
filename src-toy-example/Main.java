public class Main {

	int a;
	int b;
	int c;
	static int d;

	public void foo(){
		a = 10;
		b = 20;
	}

	public void bar(){
		boolean b1 = a>10;
		boolean b2 = b>20;
		boolean b3 = b>30;
		boolean b4 = b>40;
	}

	public void baz(){
		a = 30;
		a = 40;
		b = 50;
	}

	public static void main(String[] args) {

		for (int i = 0; i<10; i++){
			new Main();
		}

		Main m1 = new Main();
		m1.foo();
		m1.bar();
		m1.baz();

		Main m2 = new Main();
		m2.foo();
		m2.bar();
		m2.baz();	

		d = 10;
	}
}
