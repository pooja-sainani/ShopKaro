package cfh.com.shopkaro.dummy;

import java.util.UUID;

/**
 * Created by ammura on 02-03-2016.
 */
public class Product {
    public UUID ID;
    public String CATEGORYID;
    public String NAME;
    public UUID SELLERID;
    public Boolean ISACTIVE;
    public String DETAILS;
    public String TAG1;
    public String TAG2;
    public String TAG3;
    public float PRICE;
    public int THRESHOLDQUANTITY;
    public int QUANTITYSOLD;
    public int QUANTITYAVAILABLE;
    public double AVGRATING;

    public  Product(){

    }

    public Product(String NAME, double AVGRATING, int QUANTITYAVAILABLE, int QUANTITYSOLD){
        this.NAME = NAME;
        this.AVGRATING = AVGRATING;
        this.QUANTITYAVAILABLE = QUANTITYAVAILABLE;
        this.QUANTITYSOLD = QUANTITYSOLD;
    }
}
