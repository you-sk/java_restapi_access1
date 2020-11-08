# java_restapi_access1

Web APIにPOSTでリクエストして結果をDBに格納する実装例(JDK8)

## 概要

* mvn packageで実行形式jarを作成
* 実行はjava -jar 実行形式.jar 、第1引数をパラメータとして受け取ってPOST
* 作成されたjarと同じフォルダにあるsqlite3 DBファイルに結果を格納


## 補足

* etc/ai_analysis_log.create.sql　→　テーブル作成SQL
* etc/express_responce.js　→　WebAPIをExpressで簡易作成したもの(POSTでJSONを返す：たまにエラーも)
