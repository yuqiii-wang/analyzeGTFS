import com.google.transit.realtime.GtfsRealtime;

import apiKey.apiKey; // you can define your own api key here
import proxySettings.proxySettings; // you can set your proxy here

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.Proxy;
import java.net.InetSocketAddress;

public class analyzeGTFS {
    public static void main(String[] args) {
        HttpURLConnection connection = null;
        InputStream inStr = null;
        proxySettings prxySet = new proxySettings();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(prxySet.strHost, prxySet.intPort));
        apiKey myApiKey = new apiKey();
        try{
            URL urlLink = new URL("https://api.transport.nsw.gov.au/v1/gtfs/vehiclepos/nswtrains");
            connection = (HttpURLConnection) urlLink.openConnection(proxy);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Authorization", myApiKey.str);
            connection.connect();
            inStr = connection.getInputStream();
            GtfsRealtime.FeedMessage msg = GtfsRealtime.FeedMessage.parseFrom(inStr);
            System.out.println(msg);
        }
        catch (IOException err) {
            err.printStackTrace();
        }
    }
}
