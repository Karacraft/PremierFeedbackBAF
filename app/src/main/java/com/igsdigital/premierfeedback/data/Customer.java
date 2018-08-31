package com.igsdigital.premierfeedback.data;

/**
 * Created by       duke / Kara Craft
 * For Project      Customer Feed Back
 * Dated            Jan 31 2016
 * File Name
 * Comments
 */
public class Customer
{

    private Integer _id;                        //Unique Row ID
    private String customer_contactnumber1;     //Contact number
    private String customer_name;         //Customer Name
    private String customer_email;          //Customer email
    private String customer_accountnumber;      //Customer Account Number

    //----------------Constructor------//
    public Customer(){}

    public Customer(Integer _id, String  customer_contactnumber1, String customer_name, String customer_email, String customer_accountnumber)
    {
        this._id = _id;
        this.customer_contactnumber1 = customer_contactnumber1;
        this.customer_name = customer_name;
        this.customer_email = customer_email;
        this.customer_accountnumber = customer_accountnumber;
    }
    //----------------------------------Getters-----------------------------//
    public Integer get_id()
    {
        return _id;
    }

    public String getCustomer_contactnumber1()
    {
        return customer_contactnumber1;
    }

    public String getCustomer_name()
    {
        return customer_name;
    }

    public String getCustomer_email()
    {
        return customer_email;
    }

    public String  getCustomer_accountnumber() { return customer_accountnumber; }

    //----------------------------------Setters-----------------------------//=
    public void set_id(Integer _id)
    {
        this._id = _id;
    }

    public void setCustomer_contactnumber1(String customer_contactnumber1)
    {
        this.customer_contactnumber1 = customer_contactnumber1;
    }

    public void setCustomer_name(String  customer_name)
    {
        this.customer_name = customer_name;
    }

    public void setCustomer_email(String  customer_email)
    {
        this.customer_email = customer_email;
    }

    public void setCustomer_accountnumber(String customer_accountnumber) { this.customer_accountnumber = customer_accountnumber;  }

    //----------------------------------Overrides-----------------------------//
    @Override
    public String toString()
    {
        return "Customer{ " +
                "_id =" + _id +
                ", customer_contactnumber1 = " + getCustomer_contactnumber1() +
                ", customer_name = " + getCustomer_name() +
                ", customer_email = " + getCustomer_email() +
                ", customer_accountnumber = " + getCustomer_accountnumber() +
                '}';
    }
}
