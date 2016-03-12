package cfh.com.shopkaro.dummy;

import java.util.UUID;

/**
 * Created by ammura on 24-02-2016.
 */
public class Service {
    public UUID ID;
    public String CATEGORYID;
    public String NAME;
    public UUID SELLERID;
    public Boolean ISACTIVE;
    public String DETAILS;
    public String TAG1;
    public String TAG2;
    public String TAG3;
    public String PLACE;
    public String CITY;
    public String STATE;
    public int PINCODE;
    public float PRICE;
    public double AVGRATING;
    public int NUMBEROFUSERS;

    public Service(){

    }

    public Service(String NAME, double AVGRATING, int NUMBEROFUSERS){
        this.NAME = NAME;
        this.AVGRATING = AVGRATING;
        this.NUMBEROFUSERS = NUMBEROFUSERS;
    }
}
