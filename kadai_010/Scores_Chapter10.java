package kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Scores_Chapter10 {
	public static void main(String[] args) {
	    Connection con = null;
	    Statement statement = null;
	    
	    try {
	    	// データベースに接続
	    	con = DriverManager.getConnection(
	    			"jdbc:mysql://localhost/challenge_java",
	    			"root",
	    			"yukihiro-1220");
	    	
	    	System.out.println("データベース接続成功:"+con);
	    	
	    	// SQLクエリを準備
            statement = con.createStatement();
            String sql = "UPDATE scores SET score_math=95,score_english=80 WHERE id = 5;";
            
            // SQLクエリを実行（DBMSに送信）
	    	System.out.println("レコード更新を実行します");
	    	int rowCnt = statement.executeUpdate(sql);
		    System.out.println(rowCnt + "件のレコードが更新されました");
		    
		    // SQLクエリを準備
		    sql = "SELECT * FROM scores ORDER BY score_math DESC,score_english DESC;";
		    
		    // SQLクエリを実行（DBMSに送信）
		    ResultSet table = statement.executeQuery(sql);
		    int scoreMath = 0; 
		    System.out.println("数学・英語の点数が高い順に並べ替えました");
		    while(table.next()) {
		    	int id = table.getInt("id");
		    	String name = table.getString("name");
		    	int scoreMath2 = table.getInt("score_math");
		    	int scoreEn = table.getInt("score_english");
		    	System.out.println(table.getRow()+"件目：生徒ID="+id+"／氏名="+name+"／数学="+scoreMath2+"／英語="+scoreEn);
		    }
	    }
	    catch(SQLException e) {
            System.out.println("エラー発生：" + e.getMessage());
            }
	    finally {
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