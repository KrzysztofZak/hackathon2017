package com.costrella.sp.sp.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mike on 2017-10-29.
 */
public class Leader extends RealmObject {

    @PrimaryKey
    int id;

    String name;

}
