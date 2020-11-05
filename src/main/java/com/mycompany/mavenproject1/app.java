/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
//import net.arnx.jsonic.JSON;
/**
 *
 * @author Yusuke Watanabe <yusuke.watanabe.hx@nachi.com>
 */
public class app {
    public static void main(String[] args) {
        //System.out.println(args.toString());
        
        if(args.length > 0){
            int i = 1;
            for (String arg : args) {
                System.out.println(i++ + ":" + arg);
            }
        }
        
        Client client = ClientBuilder.newClient();
        final WebTarget target = client.target("https://zipcloud.ibsnet.co.jp/api/search");//?zipcode=0790177");
        final Invocation.Builder builder = target.request();
        
        final Form form = new Form();
        form.param("zipcode", "0790177");
        Response response = builder.post(Entity.form(form), Response.class);

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                String json = response.readEntity(String.class);
                System.out.println(json);

////                System.err.println(json);
//                Map map = (Map) JSON.decode(json);
//                String Cdstdt02 = map.get("Cdstdt02").toString();
//                String Cdstdt04 = map.get("Cdstdt04").toString();
//                System.err.println("…Equal? " + Cdstdt02 + ":" + Cdstdt04);
//                return !Cdstdt02.equals(Cdstdt04);
//        } else {
//            //200OK以外の時 todoしかるべき処理
//            System.err.println("…APIアクセス失敗！");
//            System.err.println(response.getStatus());
//            System.err.println(response.getEntity());
//            return false;
//        }
    }
        
        System.out.println("end");
        
    }
}
