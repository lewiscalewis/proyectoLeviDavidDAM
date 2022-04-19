package org.iesmurgi.proyectolevidaviddam.Controllers;

import org.iesmurgi.proyectolevidaviddam.Enviroment.CONSTANT;
import org.iesmurgi.proyectolevidaviddam.Middleware.OpenThread;
import org.iesmurgi.proyectolevidaviddam.models.ClientSocket;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class HomepageController {
    ClientSocket c = new ClientSocket();
    public void initialize() throws MalformedURLException, InterruptedException {

    }

    public void getResult() throws MalformedURLException, InterruptedException {
        String url = CONSTANT.URL.getUrl()+"/chatID";
        ArrayList<String[]> params = new ArrayList<>();
        params.add(new String[]{"username1", "test"});
        params.add(new String[]{"username2", "elias"});
        OpenThread<String> t = new OpenThread<>(url, params, "POST", String.class);
        c.setRoom(t.getResult());
        c.start();
    }
}
