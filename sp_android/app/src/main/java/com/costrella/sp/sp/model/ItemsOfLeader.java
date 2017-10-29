package com.costrella.sp.sp.model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by mike on 2017-10-29.
 */
public class ItemsOfLeader extends RealmObject{

    RealmList<Leader> leaders;

    public RealmList<Leader> getLeaders() {
        return leaders;
    }

    public void setLeaders(RealmList<Leader> leaders) {
        this.leaders = leaders;
    }

}
