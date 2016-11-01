package com.mralex6204.android.timaster;

import android.app.Application;

import com.mralex6204.android.timaster.db.Model;
import com.mralex6204.android.timaster.domain.DbRepository;
import com.mralex6204.android.timaster.models.Cliente;
import com.mralex6204.android.timaster.models.Configuration;
import com.mralex6204.android.timaster.models.TicketInfo;

/**
 * Created by oavera on 25/10/16.
 */

public class AndroidTiMasterApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        DbRepository dbRepository = DbRepository.getInstance(this);

        //CREATE AN INSTANCE OF EACH MODEL
        Model ClienteModel = new Cliente(),
              ConfigModel = new Configuration(),
              TicketModel = new TicketInfo();

        //REGISTER EACH MODEL HERE
        dbRepository.Models.add(ClienteModel);
        dbRepository.Models.add(ConfigModel);
        dbRepository.Models.add(TicketModel);

        //BUILD MODELS SYSTEM TABLES
        dbRepository.build();


    }

}
