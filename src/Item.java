import java.io.Serializable;

/**
 *
 * @author Sergey
 */
public class Item implements Serializable{
    private String name ;
    private float buyPrice;
    private float priceOne;
    private int   count;
    private float murkUp;// my added value
    private float ky = 500;
    private float kx = 200;
    private int oyn = 50;
    private int oxn = 50;
    
    Item(String name, float priceOne, float buyPrice ){
        this.name = name;
        this.buyPrice = buyPrice;
        this.priceOne = priceOne;
    } 
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the buyPrice
     */
    public float getBuyPrice() {
        return buyPrice;
    }

    /**
     * @param buyPrice the buyPrice to set
     */
    public void setBuyPrice(float buyPrice) {
        this.buyPrice = buyPrice;
    }

    /**
     * @return the priceOne
     */
    public float getPriceOne() {
        return priceOne;
    }

    /**
     * @param priceOne the priceOne to set
     */
    public void setPriceOne(float priceOne) {
        this.priceOne = priceOne;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return the murkUp
     */
    public float getMurkUp() {
        return murkUp;
    }

    /**
     * @param murkUp the murkUp to set
     */
    public void setMurkUp(float murkUp) {
        this.murkUp = murkUp;
    }

    /**
     * @return the ky
     */
    public float getKy() {
        return ky;
    }

    /**
     * @param ky the ky to set
     */
    public void setKy(float ky) {
        this.ky = ky;
    }

    /**
     * @return the kx
     */
    public float getKx() {
        return kx;
    }

    /**
     * @param kx the kx to set
     */
    public void setKx(float kx) {
        this.kx = kx;
    }

    /**
     * @return the oyn
     */
    public int getOyn() {
        return oyn;
    }

    /**
     * @param oyn the oyn to set
     */
    public void setOyn(int oyn) {
        this.oyn = oyn;
    }

    /**
     * @return the oxn
     */
    public int getOxn() {
        return oxn;
    }

    /**
     * @param oxn the oxn to set
     */
    public void setOxn(int oxn) {
        this.oxn = oxn;
    }

}
