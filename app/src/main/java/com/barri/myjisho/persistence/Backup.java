package com.barri.myjisho.persistence;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.barri.myjisho.R;
import com.barri.myjisho.views.MangaView;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.WriteMode;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Barri on 24/03/2016.
 */
public class Backup {

    private MangaView context;
    private final String inFileName = "/data/data/com.barri.myjisho/databases/";
    private String databaseName;
    private String access;

    public Backup(MangaView context) {

        //Not gonna publish this on github lol
        access = context.getResources().getString(R.string.access_token);

        this.context = context;

        Bundle bundle;
        try {
            bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return;
        }
        databaseName = bundle.getString("DATABASE");
    }

    public void backup() {
        Toast.makeText(context, "Inicio Backup", Toast.LENGTH_SHORT).show();

        final String accessToken = access;
        final String database = databaseName;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    DbxRequestConfig config = new DbxRequestConfig(
                            "dropbox/Aplicaciones/MyJisho", "es_ES");
                    DbxClientV2 client = new DbxClientV2(config, accessToken);

                    try (InputStream in = new FileInputStream(inFileName + database)) {
                        FileMetadata metadata = client.files().uploadBuilder("/" + database)
                                .withMode(WriteMode.OVERWRITE).uploadAndFinish(in);
                    } catch (IOException e) {
                        int a = 3;
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

    public void restore() {
        Toast.makeText(context, "Inicio Restauracion", Toast.LENGTH_SHORT).show();

        final String accessToken = access;
        final String database = databaseName;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    DbxRequestConfig config = new DbxRequestConfig(
                            "dropbox/Aplicaciones/MyJisho", "es_ES");
                    DbxClientV2 client = new DbxClientV2(config, accessToken);

                    try (OutputStream out = new FileOutputStream(inFileName + database)) {
                        client.files().download("/" + database).download(out);
                    } catch (IOException e) {
                        Toast.makeText(context, "Fallo al descargar fichero", Toast.LENGTH_SHORT).show();
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
                        context.refresh();
                    }
                });
            }
        });

        thread.start();
    }
}
