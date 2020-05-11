package com.example.birdsystem.model;

public class Bird {
    private String birdName;
    //鸟类栖息地
    private int birdHabitat;
    //鸟类生活习惯
    private int birdLifestyle;
    //鸟类居留类型
    private int birdResidence;
    //鸟类详细信息
    private String birdDetails;
    //链接
    private String birdUrl;
    //存放鸟类图片链接
    private byte[] birdImg;

    public  Bird(){}
    public Bird(String birdName,int habitat,int lifestyle,int residence,String details,String birdUrl,byte[] birdImg){
        this.birdName=birdName;
        this.birdHabitat=habitat;
        this.birdLifestyle=lifestyle;
        this.birdResidence=residence;
        this.birdDetails=details;
        this.birdUrl=birdUrl;
        this.birdImg=birdImg;
    }

    /*
    鸟类信息接口
     */

    public String getBirdName(){
        return birdName;
    }

    public int getBirdHabitat(){
        return birdHabitat;
    }

    public int getBirdLifestyle(){
        return birdLifestyle;
    }

    public int getBirdResidence(){
        return birdResidence;
    }

    public String getBirdDetails(){
        return birdDetails;
    }

    public String getBirdUrl(){
        return birdUrl;
    }

    public byte[] getBirdImg(){
        return birdImg;
    }


    /*
    修改鸟类信息
     */
    public void setBirdHabitat(int birdHabitat) {
        this.birdHabitat = birdHabitat;
    }

    public void setBirdLifestyle(int birdLifestyle) {
        this.birdLifestyle = birdLifestyle;
    }

    public void setBirdResidence(int birdResidence) {
        this.birdResidence = birdResidence;
    }

    public void setBirdDetails(String birdDetails) {
        this.birdDetails = birdDetails;
    }

    public void setBirdName(String birdName) {
        this.birdName = birdName;
    }

    public void setBirdUrl(String birdUrl) {
        this.birdUrl = birdUrl;
    }

    public void setBirdImg(byte[] birdImg) {
        this.birdImg = birdImg;
    }
}
