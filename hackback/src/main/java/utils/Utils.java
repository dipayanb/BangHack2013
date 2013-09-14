package utils;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Winash
 */
public class Utils {

    public static String[] doTranslate(String company, String address) throws Exception {
        Translate.setClientId("stromrage");
        Translate.setClientSecret("o61cLmH+qDdVJ3b41EjLF2MoSeTcgeKjpW9AskKlHTo=");

        String[] texts = {company, address};
        String[] translatedTexts = Translate.execute(texts, Language.ENGLISH, Language.HINDI);
        return translatedTexts;
    }


    public static String makeGetRequest(String uri) throws Exception {
        URL url = new URL(uri);
        URLConnection connection = url.openConnection();
        String line;
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        return builder.toString();

    }


}
