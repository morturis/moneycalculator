package calculadora;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MoneyCalculator {
	public static void main(String[] args) throws Exception {
		MoneyCalculator money = new MoneyCalculator();
		money.execute();
	}
	
	private double amount;
	private double rate;
	private String currencyFrom;
	private String currencyTo;
	
	private void execute() throws Exception{
		input();
		proccess();
		output();
	}
	
	private void input() {
		System.out.println("Introduzca una cantidad inicial");
		Scanner scanner = new Scanner(System.in);
		amount = Double.parseDouble(scanner.next());
		System.out.println("Introduzca la divisa de origen");
		currencyFrom = scanner.next();
		System.out.println("Introduzca la divisa de origen");
		currencyTo= scanner.next();
		scanner.close();
	}
	
	private void proccess() throws Exception{
		rate = getExchangeRate(currencyFrom, currencyTo);
	}
	
	private void output() {
		System.out.println(amount + " " + currencyFrom + " equivalen a " + (amount*rate) + " " + currencyTo);
	}
	
	private static double getExchangeRate(String base, String to) throws Exception{
		URL url = new URL("http://api.fixer.io/latest?base="+base+"&symbols="+to);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		InputStreamReader input = new InputStreamReader(connection.getInputStream());
		try (BufferedReader reader = new BufferedReader(input)) {
			String line = reader.readLine();
			line = line.substring(line.indexOf(to)+5, line.indexOf("}"));
			return Double.parseDouble(line);
		}
	}
}