package Services;

import ImageUtils.ImageUtil;
import Models.Person;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.List;

/**
 * Created by Dost Muhammad on 5/8/2018.
 */
public class service {
    private  String strSource,strTarget;
    private Connection dbConnetion;
    private Statement statement;
    private ResultSet rs=null;
    int x=0;

    public  int saveImage(Image image,String username) {

        BufferedImage img= (BufferedImage) image;
        this.strSource= ImageUtil.encodeToString(img,"png");

        String sql="INSERT INTO public.\"userDetails\"(\n" +
                "\t username, faceimage)\n" +
                "\tVALUES ( '"+username+"', '"+this.strSource+"');";
        Statement statement;
        try {
            //ImageIO.write(webCam.getImage(), "PNG", new File("picture.png"));
            Connection dbConnetion= DAO.daoConnection.getConnection();
            System.out.println("Connection is estalished"+sql);
            if(dbConnetion!=null){
                statement=dbConnetion.createStatement();
                x=statement.executeUpdate(sql);
                System.out.print("record inserted"+x);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Snapshot taken from cam and stored at "+new File("").getAbsolutePath()+"\\picture.png");
        return x;
    }
    public java.util.List<Person> lstPersonAll() {
        List<Person> lst= new ArrayList();
//        BufferedImage img= (BufferedImage) image;
//        this.strSource= ImageUtil.encodeToString(img,"png");

        String sql="SELECT  username, email, faceid, faceimage\n" +
                "\tFROM public.\"userDetails\";";

        try {
            //ImageIO.write(webCam.getImage(), "PNG", new File("picture.png"));
             dbConnetion= DAO.daoConnection.getConnection();
            System.out.println("Connection is estalished"+sql);
            if(dbConnetion!=null){
                statement=dbConnetion.createStatement();
                rs=statement.executeQuery(sql);
                while(rs.next()){
                    lst.add(new Person(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
                }
                System.out.println("lstSize is "+lst.size()+"sql is "+sql);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Snapshot taken from cam and stored at "+new File("").getAbsolutePath()+"\\picture.png");
        return lst;
    }
}
