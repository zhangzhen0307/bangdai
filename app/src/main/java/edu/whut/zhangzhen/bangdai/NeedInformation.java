package edu.whut.zhangzhen.bangdai;

import java.io.Serializable;

/**
 * Created by Acer on 2018/3/23.
 */

public class NeedInformation implements Serializable {
    private String receiverid;
    private String receiver;
    private String senderid;
    private String sender;
    private String title;
    private String information;
    private String location;
    private String date;
    private String deadline;
    public NeedInformation(){
    }

    public NeedInformation(String receiverid,String receiver,String senderid,String sender,String title,String information,String location,String date,String deadline){
        this.receiverid=receiverid;
        this.receiver=receiver;
        this.senderid=senderid;
        this.sender=sender;
        this.title=title;
        this.information=information;
        this.location=location;
        this.date=date;
        this.deadline=deadline;
    }

    public String getReceiverid(){return receiverid;}

    public void setReceiverid(String receiverid){this.receiverid=receiverid;}

    public String getReceiver(){return receiver;}

    public void setReceiver(String receiver){this.receiver=receiver;}

    public String getSenderid(){return senderid;}

    public void setSenderid(String senderid){this.senderid=senderid;}

    public String getSender(){
        return sender;
    }

    public void setSender(String sender){
        this.sender=sender;
    }

    public String getTitle(){return title;}

    public void setTitle(String title){this.title=title;}

    public String getInformation(){
        return information;
    }

    public void setInformation(String information){
        this.information=information;
    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location=location;
    }

    public String getDate(){return date;}

    public void setDate(String date){this.date=date;}

    public String getDeadline(){return deadline;}

    public void setDeadline(String deadline){this.deadline=deadline;}
}
