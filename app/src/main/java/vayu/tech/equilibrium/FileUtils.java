package vayu.tech.equilibrium;

import android.content.res.AssetManager;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by shomil on 7/23/17.
 */

public class FileUtils {

    public static String readFile(AssetManager assets, String path) {
        String json = null;
        try {
            InputStream is = assets.open(path);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

}
