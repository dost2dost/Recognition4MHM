package ImageUtils;

import Models.Person;
import Services.service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by Dost Muhammad on 5/8/2018.
 */
public class CompareImage {

    public static String  compar(BufferedImage img){
        BufferedImage bf=null;

        ImageComparator imc=new ImageComparator();

        String name="";
        service obj=new service();
        List<Person> lst=obj.lstPersonAll();
        for(Person p:lst){
            bf=ImageUtil.decodeToImage(p.getFaceimage());
            int k=imc.getPixelDiffPercent(bf,img);
            System.out.println("kk"+k);
            if(k==0){
                name=p.getUsername();
                break;
            }

        }

        return name;
    }


}
