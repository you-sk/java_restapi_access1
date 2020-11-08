package com.mycompany.mavenproject1;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mycompany.mavenproject1.entities.ApiResponce;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

/**
 * WebAPIにPOSTでアクセスして結果をDBに格納する実装例
 * @author Yusuke Watanabe
 */
public class app {

    public static void main(String[] args) {

        String imagePath = null;
        if (args.length > 0) {
            for (String arg : args) {
                //第1引数をimagePathと解釈する
                imagePath = arg;
                break;
            }
        }

        if (imagePath == null) {
            System.out.println("[警告]image_path指定なし");
            imagePath = "/image/d03f1d36ca69348c51aa/c413eac329e1c0d03/test.jpg";   //test用
        }
        //WebAPIにPOSTでアクセス
        ApiResponce res = getRes(imagePath);
        res.imagePath = imagePath;
        //結果をDBへ格納
        insertDb(res);
//        System.out.println(res.message);
    }

    private static ApiResponce getRes(String imagePath) {
        //POSTリクエストの準備
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://example.com/");
        target = client.target("http://localhost:3000/api");    //test用
        final Invocation.Builder builder = target.request();
        final Form form = new Form();
        form.param("image_path", imagePath);

        //レスポンスを取得
        int requestTimestamp = (int) (System.currentTimeMillis() / 1000L);
        Response response = builder.post(Entity.form(form), Response.class);
        int responseTimestamp = (int) (System.currentTimeMillis() / 1000L);

        ApiResponce res = new ApiResponce();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            //レスポンスJSONをパース
            String json = response.readEntity(String.class);
            System.out.println(json);

            try {
                Gson gson = new Gson();
                res = gson.fromJson(json, ApiResponce.class);
            } catch (JsonSyntaxException e) {
                //パース出来なかったときは失敗扱い
                res.success = false;
                res.message = e.getMessage();
            }
        } else {
            res.success = false;
            res.message = "status:" + response.getStatus();
        }
        //リクエスト、レスポンス時刻をセット
        res.requestTimestamp = requestTimestamp;
        res.responseTimestamp = responseTimestamp;

        return res;
    }

    //DB格納（SQLITE3使用)
    private static void insertDb(ApiResponce res) {
        if (res == null) {
            return;
        }
        if (res.success == null) {
            return;
        }

        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:sampledb.sqlite3");
            connection.setAutoCommit(false);

            //insert文の構築
            String insertSql = "Insert into ai_analysis_log(image_path,success,message"
                    + ",class,confidence"
                    + ",request_timestamp,response_timestamp)"
                    + " values"
                    + " (?,?,?,?,?,?,?)";
            //パラメータをセット
            PreparedStatement ps = connection.prepareStatement(insertSql);
            ps.setString(1, res.imagePath);
            ps.setString(2, res.success ? "True" : "False");
            ps.setString(3, res.message);
            //estimatedDataおよび値がnullの可能性があるため制御
            if (res.estimatedData != null) {
                if (res.estimatedData.classInt != null) {
                    ps.setInt(4, res.estimatedData.classInt);
                }
                if (res.estimatedData.confidence != null) {
                    ps.setBigDecimal(5, res.estimatedData.confidence);
                }
            }
            ps.setInt(6, res.requestTimestamp);
            ps.setInt(7, res.responseTimestamp);

            //insert実行＆commit
            ps.executeUpdate();
            connection.commit();

        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
