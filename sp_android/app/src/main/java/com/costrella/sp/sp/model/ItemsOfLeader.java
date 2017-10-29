package com.costrella.sp.sp.model;

import java.util.List;

import io.realm.RealmObject;

/**
 * Created by mike on 2017-10-29.
 */
public class ItemsOfLeader extends RealmObject{

    List<Leader> leaders;

    public List<Leader> getLeaders() {
        return leaders;
    }

    public void setLeaders(List<Leader> leaders) {
        this.leaders = leaders;
    }

}
