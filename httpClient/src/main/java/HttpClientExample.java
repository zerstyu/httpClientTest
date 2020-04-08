import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpClientExample {
    // one instance, reuse
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private static Map<Integer, String> urlMap = new TreeMap<>(Collections.reverseOrder());

    public static void main(String[] args) throws Exception {
        HttpClientExample obj = new HttpClientExample();
        try {
            obj.sendGet("http://www.daum.net");
            printSortedUrlMap();
        } finally {
            obj.close();
        }
    }

    private void close() throws IOException {
        httpClient.close();
    }

    private HttpEntity sendGet(String url) throws Exception {
        HttpGet request = new HttpGet(url);

        try (CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
            System.out.println(headers);

            try (BufferedReader br = new BufferedReader(new InputStreamReader((entity.getContent())))) {
                Boolean keepGoing = true;
                while (keepGoing) {
                    String currentLine = br.readLine();
                    if (currentLine == null) {
                        keepGoing = false;
                    } else {
                        UrlHtml(currentLine);
                    }
                }
            } catch (Exception e) {
                System.out.println("Exception" + e);
            }

            return entity;
        }
    }

    private static void printSortedUrlMap() {
        int count = 0;
        for (Iterator i = urlMap.keySet().iterator(); i.hasNext(); ) {
            if (count == 10) break;
            Integer key = (Integer) i.next();
            String value = urlMap.get(key);
            System.out.println(key + " = " + value);
            count++;
        }
    }

    private static void UrlHtml(String str) {
        String regex = "(http|https|ftp)://[^\\s^\\.]+(\\.[^\\s^\\.^\"^\']+)*";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);

        Set<String> urlSet = new HashSet<>();
        if (m.find()) {
            urlSet.add(m.group(0));
        }

        for (String url : urlSet) {
            urlMap.put(url.length(), url);
        }
    }
}
