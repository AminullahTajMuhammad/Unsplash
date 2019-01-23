package unsplash.com.unsplash;

import android.content.Context;
import android.content.SharedPreferences;

public class PermissionUtil {
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    PermissionUtil(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.permission_storage),Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


    }

    public void updatePermissionPrefrence(String permission) {
        switch (permission) {
            case "storage": {
                editor.putBoolean(context.getString(R.string.permission_storage),true);
                editor.commit();
            }
        }
    }

    public boolean checkPermissionPrefernece(String permission) {

        boolean isShow = false;

        switch (permission) {
            case "storage" :
                isShow = sharedPreferences.getBoolean(context.getString(R.string.permission_storage),false);
                break;
        }


        return isShow;
    }

}
