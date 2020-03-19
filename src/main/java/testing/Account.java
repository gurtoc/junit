package testing;

public class Account {
    private boolean active;

    private Address defaultDeliveryAdress;

    public Account() {
        this.active = false;
    }

    public Account(Address defaultDeliveryAdress) {
        this.defaultDeliveryAdress = defaultDeliveryAdress;
        if(defaultDeliveryAdress !=null){
            activate();
        }else {
            this.active=false;
        }
    }

    public void activate(){
        this.active = true;
    }

    public boolean isActive() {
        return active;
    }

    public Address getDefaultDeliveryAdress() {
        return defaultDeliveryAdress;
    }

    public void setDefaultDeliveryAdress(Address defaultDeliveryAdress) {
        this.defaultDeliveryAdress = defaultDeliveryAdress;
    }
}
