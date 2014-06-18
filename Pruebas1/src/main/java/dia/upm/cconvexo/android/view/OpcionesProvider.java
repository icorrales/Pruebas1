package dia.upm.cconvexo.android.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import dia.upm.cconvexo.R;
import dia.upm.cconvexo.android.gestores.GestorConfiguracion;

/**
 * Created by ivan on 16/03/14.
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class OpcionesProvider extends ActionProvider implements MenuItem.OnMenuItemClickListener {

    Context mcontext = null;

    public OpcionesProvider(Context context) {

        super(context);
        mcontext = context;
    }




    @Override
    public View onCreateActionView() {

/*
        ImageView imageView = new ImageView(mcontext);
        imageView.setImageResource(R.drawable.ic_action_share);


        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        View view = layoutInflater.inflate(R.layout.opcionesprovider, null);
*/


        return null;

    }

    @Override
    public boolean hasSubMenu(){
        return true;
    }

    @Override
    public void onPrepareSubMenu(SubMenu subMenu){
        subMenu.clear();

        subMenu.add(SubMenu.NONE,R.string.directo,SubMenu.NONE,R.string.directo ).setOnMenuItemClickListener(this);
        subMenu.add(SubMenu.NONE,R.string.retardo,SubMenu.NONE,R.string.retardo).setOnMenuItemClickListener(this);
        subMenu.add(SubMenu.NONE,R.string.paso_paso,SubMenu.NONE,R.string.paso_paso).setOnMenuItemClickListener(this);
    }


    public boolean onMenuItemClick(MenuItem item){
        Log.d(this.getClass().getName(),"OnMenuItemClick" + item.toString());
        GestorConfiguracion.getInstancia().setTipoEjecucion(item.getItemId());
        // Toast.makeText(mcontext, mcontext.getString(item.getItemId()), Toast.LENGTH_SHORT).show();

        return true;
    }
}
