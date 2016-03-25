package com.barri.myjisho.persistence;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.barri.myjisho.R;
import com.barri.myjisho.views.MangaView;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.users.FullAccount;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Barri on 24/03/2016.
 */
public class Backup {

    private MangaView context;
    private final String inFileName = "/data/data/com.barri.myjisho/databases/";

    public Backup(MangaView context) {

        //Not gonna publish this on github lol
        String accessToken = context.getResources().getString(R.string.access_token);

        this.context = context;

        Bundle bundle;
        try {
            bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return;
        }
        String database = bundle.getString("DATABASE");

        connect(accessToken, database);
    }

    private void connect(final String accessToken, final String database) {
        Toast.makeText(context,"Inicio Backup",Toast.LENGTH_SHORT).show();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    DbxRequestConfig config = new DbxRequestConfig(
                            "dropbox/Aplicaciones/MyJisho", "es_ES");
                    DbxClientV2 client = new DbxClientV2(config, accessToken);

                    try (InputStream in = new FileInputStream(inFileName + database)) {
                        FileMetadata metadata = client.files().uploadBuilder("/" + database)
                                .uploadAndFinish(in);
                    } catch (IOException e) {
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "Fallo al subir fichero", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (DbxException e) {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Fallo al conectarse", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "420 Blaze it", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        thread.start();
    }


}
