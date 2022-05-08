package src.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import src.server.model.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonHandler {
    public static boolean checkUser(User user_) throws FileNotFoundException {

        File jsonList = new File(System.getProperty("user.dir") + "\\userData.json");
        Type REVIEW_TYPE = new TypeToken<List<User>>() {}.getType();
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(jsonList));
        List<User> data = gson.fromJson(reader, REVIEW_TYPE);
        if (data != null) {
            return data.contains(user_);
        } else return false;
    }

    public static boolean usernameTaken(User user_) throws FileNotFoundException {

        File jsonList = new File(System.getProperty("user.dir") + "\\userData.json");
        Type REVIEW_TYPE = new TypeToken<List<User>>() {}.getType();
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(jsonList));
        List<User> data = gson.fromJson(reader, REVIEW_TYPE);
        for (User user: data){
            if (user.getUsername().equals(user_.getUsername())){
                return true;
            }
        }
        return false;
    }

    public static boolean saveUser(User user_) throws IOException {
        if (!usernameTaken(user_)){
            File jsonList = new File(System.getProperty("user.dir") + "\\userData.json");
            Type USER_TYPE = new TypeToken<List<User>>() {}.getType();
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(jsonList));
            List<User> data = gson.fromJson(reader, USER_TYPE);
            data.add(user_);
            FileWriter writer = new FileWriter(System.getProperty("user.dir") + "\\userData.json");
            gson.toJson(data, writer);
            writer.flush();
            writer.close();
            return false;
        } else {
            return true;
        }

    }
}
