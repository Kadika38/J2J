import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.github.kadika38.JSONBucket;

public class App {

    public static void main(String[] args) {
        /* JSONBucket testBucket2 = new JSONBucket(getJson());
        testBucket2.print(); */

        JSONBucket testBucket3 = JSONBucket.build(getJson());
        testBucket3.print();
    }

    public static String getJson() {
        String url = "https://api.nasa.gov/neo/rest/v1/feed?start_date=2015-09-07&end_date=2015-09-08&api_key=erINDupuY35TifPnTrNbzXgNhcoqE0nBPrMDvloc";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .GET()
            .header("Content-Type", "application/json")
            .uri(URI.create(url))
            .build(); 
        
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "{}";
        }
        
    }
}
