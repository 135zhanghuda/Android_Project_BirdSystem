package bean;

public class Bird {
	 //��������
    private String birdName;
    //������Ϣ��
    private int birdHabitat;
    //��������ϰ��
    private int birdLifestyle;
    //�����������
    private int birdResidence;
    //������ϸ��Ϣ
    private String birdDetails;
    //����
    private String birdUrl;
    //�������ͼƬ����
    private byte[] birdImg;

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
    ������Ϣ�ӿ�
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
    �޸�������Ϣ
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
