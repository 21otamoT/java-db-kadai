package kadai_007;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Posts_Chapter07 {

	public static void main(String[] args) {
		Connection con = null;
        PreparedStatement statement = null;

        // ユーザーリスト
        String[][] userList = {
            { "1003", "2023-02-08","昨日の夜は徹夜でした・・","13" },
            { "1002", "2023-02-08","お疲れ様です！","12" },
            { "1003", "2023-02-09","今日も頑張ります！","18" },
            { "1001", "2023-02-09","無理は禁物ですよ！","17" },
            { "1002", "2023-02-10","明日から連休ですね！","20" }
        };

        try {
            // データベースに接続
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost/challenge_java",
                "root",
                "yukihiro-1220"
            );

            System.out.println("データベース接続成功"+con);
            System.out.println("レコード追加を実行します");

            // SQLクエリを準備
            String sql = "INSERT IGNORE INTO posts (user_id, posted_at, post_content,likes) VALUES (?,?,?,?);";
            statement = con.prepareStatement(sql);
            
            // リストの1行目から順番に読み込む
            int rowCnt = 0;
            int count = 0;
            for( int i = 0; i < userList.length; i++ ) {
                // SQLクエリの「?」部分をリストのデータに置き換え
                statement.setString(1, userList[i][0]); // id
                statement.setString(2, userList[i][1]); // at
                statement.setString(3, userList[i][2]); //content
                statement.setString(4, userList[i][3]); //likes
                // SQLクエリを実行（DBMSに送信）
                rowCnt = statement.executeUpdate();
                count += rowCnt;
            }
            
            System.out.println( count + "件のレコードが追加されました");
            
            sql = "SELECT * From posts";
            ResultSet result = statement.executeQuery(sql);
            
            while(result.next()) {
            	int id = result.getInt("user_id");
            	int like = result.getInt("likes");
            	if(id == 1002 && like == 12) {
            		Date at = result.getDate("posted_at");
                	String post = result.getString("post_content");
            		System.out.println("ユーザーIDが"+id+"のレコードを検出しました");
            		System.out.println("1件目：投稿日時＝"+at+"/投稿内容＝"+post+"/いいね数＝"+like);
            	}
            	if(like == 20) {
            		Date at = result.getDate("posted_at");
                	String post = result.getString("post_content");
            		System.out.println("2件目：投稿日時＝"+at+"/投稿内容＝"+post+"/いいね数＝"+like);
            	}
            }
            
        } catch(SQLException e) {
            System.out.println("エラー発生：" + e.getMessage());
        } finally {
            // 使用したオブジェクトを解放
            if( statement != null ) {
                try { statement.close(); } catch(SQLException ignore) {}
            }
            if( con != null ) {
                try { con.close(); } catch(SQLException ignore) {}
            }
        }
	}

}