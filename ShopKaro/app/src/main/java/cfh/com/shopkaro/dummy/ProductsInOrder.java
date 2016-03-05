package cfh.com.shopkaro.dummy;

/**
 * Created by Pooja on 3/5/2016.
 */
public class ProductsInOrder {
    private  String PRODUCTID;
    private String BUYERID;
    private int QUANTITY;
    private Double Price;

    public String getPRODUCTID() {
        return PRODUCTID;
    }

    public void setPRODUCTID(String PRODUCTID) {
        this.PRODUCTID = PRODUCTID;
    }

    public String getBUYERID() {
        return BUYERID;
    }

    public void setBUYERID(String BUYERID) {
        this.BUYERID = BUYERID;
    }

    public int getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(int QUANTITY) {
        this.QUANTITY = QUANTITY;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }
}
