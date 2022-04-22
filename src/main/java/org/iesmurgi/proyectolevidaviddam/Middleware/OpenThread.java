package org.iesmurgi.proyectolevidaviddam.Middleware;

import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class OpenThread<T> extends Thread implements Callable<T> {

    private String URL;
    private ArrayList<String[]> params;
    private T res;
    private Class<T> type;
    private String method;

    Requester<T> req;

    public OpenThread(String URL, ArrayList<String[]> params, String method, Class<T> type) throws MalformedURLException {
        this.URL = URL;
        this.params = params;
        this.method = method;
        this.type = type;
    }

    public void setParams(ArrayList<String[]> params) {
        this.params = params;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

    public void mainThread() throws InterruptedException, MalformedURLException {

        // Crear un objeto de grupo de subprocesos
        ExecutorService pool = Executors.newCachedThreadPool();

        // Crear una tarea con un valor de retorno
        boolean b = true;
        // Ejecuta la tarea y obtén el objeto Future
        Future<T> future = pool.submit((Callable<T>) this);

        // Obtener el valor de retorno de la tarea del objeto Future
        while (b) {
            // Puede usar el método isDone () para consultar si se ha completado el Futuro. Después de completar la tarea, puede llamar al método get () para obtener el resultado
            // Nota: si el método get se llama directamente sin juzgar, en este momento si el hilo no se completa, get se bloqueará hasta que el resultado esté listo
            if (future.isDone()) {
                try {
                    res = future.get();
                    System.out.println("Thread result: "+ res);
                    b = false;
                } catch (Exception e) {

                    e.printStackTrace();
                }

                // Cerrar el grupo de hilos
                pool.shutdown();
            }
        }
    }

    public T getResult() throws MalformedURLException, InterruptedException {
        mainThread();
        return res;
    }

    @Override
    public T call() {
        T response = null;
        try{
            if(method.toLowerCase(Locale.ROOT).equals("post")){
                req = new Requester<T>(URL, Requester.Method.POST, type);
            }else{
                req = new Requester<T>(URL, Requester.Method.GET, type);
            }

            for(String[] s : params){
                req.addParam(s[0], s[1]);;
            }
            response = req.execute();
        }catch (IOException e){
            e.printStackTrace();
        }
        return response;
    }
}
