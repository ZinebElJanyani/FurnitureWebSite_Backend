package ma.org.comfybackend.security.DTO;

import ma.org.comfybackend.security.Enumerations.CommandState;
import ma.org.comfybackend.security.Enumerations.PaymentMethod;

import java.sql.Date;

public class CommandDTO {
    String ref;
    Date date;

    String phone;
    String email;
    String name;
    PaymentMethod paymentMethod;

    CommandState commandState;

    Date deliveryDate;

    boolean withAssembly;
    float assemblyPrice;

    float totalPrice;
    float deliveryPrice;
    float couponDiscount;

    public String getRef() {
        return ref;
    }

    public Date getDate() {
        return date;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public CommandState getCommandState() {
        return commandState;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public boolean isWithAssembly() {
        return withAssembly;
    }

    public float getAssemblyPrice() {
        return assemblyPrice;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public float getDeliveryPrice() {
        return deliveryPrice;
    }

    public float getCouponDiscount() {
        return couponDiscount;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setCommandState(CommandState commandState) {
        this.commandState = commandState;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setWithAssembly(boolean withAssembly) {
        this.withAssembly = withAssembly;
    }

    public void setAssemblyPrice(float assemblyPrice) {
        this.assemblyPrice = assemblyPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setDeliveryPrice(float deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public void setCouponDiscount(float couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
