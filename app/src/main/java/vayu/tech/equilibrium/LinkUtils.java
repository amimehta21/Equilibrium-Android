package vayu.tech.equilibrium;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LinkUtils {

    private static String generalURL = "http://10.0.0.226:8888/eq_mobileapp/service.php?q=";

    public static String buildURL(String type, HashMap<String, String> parameters) {
        String answer = generalURL;
        answer = answer + type;
        Iterator it = parameters.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            answer += ("&" + pair.getKey().toString() + "=" + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
        System.out.println("LinkUtils: Builded a URL: " + answer);
        return answer;
    }

}
