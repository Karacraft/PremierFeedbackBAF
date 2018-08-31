package com.igsdigital.premierfeedback.data;

/**
 * Created by       duke / Kara Craft
 * For Project      Customer Feed Back
 * Dated            Jan 31 2016
 * File Name
 * Comments
 */
public class Feedback
{

    private Integer _id;
    private String created_at;
    private Integer happy;
    private Integer indifferent;
    private Integer angry;
    private Integer staffattitude;
    private Integer lackofawareness;
    private Integer staffunavailability;
    private Integer systemdowntime;
    private Integer transactiontime;
//    private String comments;
    private String customerName;        //The Bank User
    private String customerAccountNumber;   //Account Number
    private String username;    //The Priority Customer

    //----------------------------------Constructors-----------------------------//
    public Feedback(){}

    public Feedback(Integer _id
            , String created_at
            , Integer happy
            , Integer indifferent
            , Integer angry
            , Integer staffattitude
            , Integer lackofawareness
            , Integer staffunavailability
            , Integer systemdowntime
            , Integer transactiontime
//            , String comments
            , String customerName
            , String customerAccountNumber
            , String username)
    {
        this._id = _id;
        this.created_at = created_at;
        this.happy = happy;
        this.indifferent = indifferent;
        this.angry = angry;
        this.staffattitude = staffattitude;
        this.lackofawareness = lackofawareness;
        this.staffunavailability = staffunavailability;
        this.systemdowntime = systemdowntime;
        this.transactiontime = transactiontime;
//        this.comments = comments;
        this.customerName = customerName;
        this.customerAccountNumber = customerAccountNumber;
        this.username = username;
    }
    //----------------------------------Getters-----------------------------//
    public Integer get_id()
    {
        return _id;
    }

    public String getCreated_at()
    {
        return created_at;
    }

    public Integer getHappy()
    {
        return happy;
    }

    public Integer getIndifferent()
    {
        return indifferent;
    }

    public Integer getAngry()
    {
        return angry;
    }

    public Integer getStaffattitude()
    {
        return staffattitude;
    }

    public Integer getLackofawareness()
    {
        return lackofawareness;
    }

    public Integer getStaffunavailability()
    {
        return staffunavailability;
    }

    public Integer getSystemdowntime()
    {
        return systemdowntime;
    }

    public Integer getTransactiontime()
    {
        return transactiontime;
    }

//    public String getComments()
//    {
//        return comments;
//    }

    public String getCustomerName()
    {
        return customerName;
    }

    public String getCustomerAccountNumber() { return customerAccountNumber;}

    public String getUsername()
    {
        return username;
    }
    //----------------------------------Setters-----------------------------//
    public void set_id(Integer _id)
    {
        this._id = _id;
    }

    public void setCreated_at(String created_at)
    {
        this.created_at = created_at;
    }

    public void setHappy(Integer happy)
    {
        this.happy = happy;
    }

    public void setIndifferent(Integer indifferent)
    {
        this.indifferent = indifferent;
    }

    public void setAngry(Integer angry)
    {
        this.angry = angry;
    }

    public void setStaffattitude(Integer staffattitude)
    {
        this.staffattitude = staffattitude;
    }

    public void setLackofawareness(Integer lackofawareness)
    {
        this.lackofawareness = lackofawareness;
    }

    public void setStaffunavailability(Integer staffunavailability)
    {
        this.staffunavailability = staffunavailability;
    }

    public void setSystemdowntime(Integer systemdowntime)
    {
        this.systemdowntime = systemdowntime;
    }

    public void setTransactiontime(Integer transactiontime)
    {
        this.transactiontime = transactiontime;
    }

//    public void setComments(String comments)
//    {
//        this.comments = comments;
//    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public void setCustomerAccountNumber(String customerAccountNumber)
    {
        this.customerAccountNumber = customerAccountNumber;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    //----------------------------------Overrides-----------------------------//

    @Override
    public String toString()
    {
        return "Feedback{ " +
                "_id = " + _id +
                ", created_at ='" + created_at + '\'' +
                ", happy = " + happy +
                ", indifferent = " + indifferent +
                ", angry = " + angry +
                ", staff attitude = " + staffattitude +
                ", lack of awareness = " + lackofawareness +
                ", staff unavailability = " + staffunavailability +
                ", system downtime = " + systemdowntime +
                ", transaction time = " + transactiontime +
//                ", comments ='" + comments + '\'' +
                ", customer Name = " + customerName +
                ", Account Number = " + customerAccountNumber +
                ", username = " + username +
                '}';
    }
}
