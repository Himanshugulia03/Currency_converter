import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {
	private final static String api_key = "b3a6c5ae216366c33e12326c";
	private static final String api_url = "https://v6.exchangerate-api.com/v6/";

	public static String getdata(String sourceCrr, String targetCrr){
		String Api_url = api_url + api_key + "/latest/" + sourceCrr;
		StringBuilder content = new StringBuilder();
		try{
			URL url = new URL(Api_url);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			int responsecode = connection.getResponseCode();
			if (responsecode != 200) {
				throw new RuntimeException("errorcode: " + responsecode);
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputline;

			while ((inputline = in.readLine()) != null) {
				content.append(inputline);
			}
			in.close();
			connection.disconnect();
		}
		catch (Exception e){
			throw new RuntimeException("invalid source currency: "+sourceCrr);
		}
		return content.toString();
	}

	public static double parsedata(String response, String targetCrr){
		JSONObject json = new JSONObject(response);
		 JSONObject exchangeP = json.getJSONObject("conversion_rates");

		 if(!exchangeP.has(targetCrr)){
			 throw new RuntimeException("invalid target currency: "+ targetCrr);
		 }
          return exchangeP.getDouble(targetCrr);
	}

	public static void main(String[] args){
		String sourceCrr;
		String targetCrr;
		double amount;

		Scanner abc = new Scanner(System.in);

		System.out.println("enter source currency");
		sourceCrr = abc.nextLine().toUpperCase();

		System.out.println("enter target currency");
		targetCrr = abc.nextLine().toUpperCase();

		System.out.println("enter amount");
		amount = abc.nextDouble();

		String response = getdata(sourceCrr, targetCrr);

		double exchangeRate = parsedata(response, targetCrr);
		double convertedAmount = amount * exchangeRate;

		System.out.printf("conversion Rate: 1 %s = %f %s%n",sourceCrr,exchangeRate,targetCrr);
		System.out.printf("%f %s = %f %s",amount,sourceCrr,convertedAmount,targetCrr);
	}
}
