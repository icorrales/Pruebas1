package dia.upm.cconvexo.android.gestores;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.content.Context;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import dia.upm.cconvexo.android.MainActivity;
import dia.upm.cconvexo.interfaces.IDelegatePaint;
import dia.upm.cconvexo.interfaces.IMessage;

/**
 * Created by ivan on 10/11/14.
 * GestorMensajes is a manager class that is used for sending messages to show and for translating messages and titles
 * to other languages.
 */
public class GestorMensajes {

    private static GestorMensajes instancia = null;
    private List<String> historicoMensajes = null;
    private List<IMessage> listaListener;

    // Context to translate messages through Resources function
    private Context c = null;

    /*
        Return list with all the messages queued.
     */
    public List<String> getHistoricoMensajes() {
        return historicoMensajes;
    }

    public void setHistoricoMensajes(List<String> historicoMensajes) {
        this.historicoMensajes = historicoMensajes;
    }

    /*
     Private constructor
     */
    private GestorMensajes()
    {
        historicoMensajes = new LinkedList<String>();
        listaListener = new LinkedList<IMessage>();
    }

    /*
       Method to implement singleton pattern
     */
    public static GestorMensajes getInstancia()
    {
        if (instancia == null)
        {
            instancia = new GestorMensajes();
        }
        return instancia;
    }

    /*
       Add message to message list and communicat it to listeners to be showed in android apps.
     */
    private void addMessage(String message)
    {

        historicoMensajes.add(message);
        for (Iterator<IMessage> iterator = listaListener.iterator(); iterator.hasNext();) {
            IMessage delegate = iterator.next();
            delegate.showMessage(message);

        }
    }

    /**
     * Add a message to messages list. This message is provided with a Resource Id.
     * @param id
     */
    public void addMessage(int id)
    {
        assert c != null;
        String message = c.getString(id);
        addMessage(message);
    }


        public void addListener (IMessage delegate)
    {
        assert delegate != null;
        listaListener.add(delegate);
    }

    public void removeListener(IMessage delegate)
    {
        assert delegate != null;
        listaListener.remove(delegate);
    }

    /*
      Return last message if found and remove it from list.
     */
    public String getLastMessage() {

        String mensaje = historicoMensajes.get(historicoMensajes.size()-1);
        historicoMensajes.remove(historicoMensajes.size() - 1);

        return mensaje;
    }

    public String getbeforeLastMessage() {

        return historicoMensajes.get(historicoMensajes.size()-2);
    }


    /*
      Return context set.
     */
    public Context getC() {
        return c;
    }

    /*
      Set new context in GestorMensajes
     */
    public void setC(Context c) {
        this.c = c;
    }


    public String getResourceString(int id){
        assert c != null && id != 0;
        return c.getString(id);

    }

}
