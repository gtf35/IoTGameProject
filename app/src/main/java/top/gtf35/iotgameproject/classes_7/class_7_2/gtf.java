package top.gtf35.iotgameproject.classes_7.class_7_2;

public class gtf {
    public static void main(String[] args) {
       while (true){
           System.out.println(System.currentTimeMillis());
           try{
               Thread.sleep(1000);
           } catch (Exception e){
               e.printStackTrace();
           }
       }
    }
}
