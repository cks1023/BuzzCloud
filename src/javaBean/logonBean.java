
package javaBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Random;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

public class logonBean {
	
	private static Connection conn = connectMysql.connectMysql();
	private static memberDataBean instance = new memberDataBean();

	public static memberDataBean getInstance() {
		return instance;
	}

	public logonBean() {
	}

	/*
	 * public memberDataBean makeMember(int id, String name, String mail,
	 * String pw, String idType, Timestamp reg_data){
	 * 
	 * return new memberDataBean(id,mail, name, pw, idType, reg_data); }
	 */

	private Connection getConnection() throws Exception {
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource) envCtx.lookup("jdbc/basicjsp");
		return ds.getConnection();
	}

	public boolean confirmAttribute(Object val, String att)
			throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isDuplicated = false;
		try {

			switch (att) {

				case "Id" :
					ps = conn.prepareStatement(
							"select Id from UserMapping where Id = ?");
					ps.setInt(1, (int) val);
					break;

				case "Email" :
				case "Nickname" :
					ps = conn.prepareStatement("select "
							+ att
							+ " from User where "
							+ att + " = ?");

					ps.setString(1, (String) val);
					break;

				default :

			}

			rs = ps.executeQuery();
			// 중복아이디가 있는지 결과를 저장
			if (rs.next())
				isDuplicated = true;
			else
				isDuplicated = false;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException ex) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
	
		}

		return isDuplicated;
	}

	/*
	 * 1.테이블이 꽉 찼는지 검사 2. 유저매핑 테이블에 중복여부 검사후 추ga 3. 유저테이블에 최종적으로 추가
	 */
	@SuppressWarnings("resource")
	public boolean joinMember(memberDataBean member, HttpServletRequest request) throws SQLException {

		ResultSet rs = null;
		// Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			// conn = getConnection();

			// user매핑테이블 추가
			while (true) {

				Statement stmt = conn.createStatement();

				rs = stmt.executeQuery(
						"select count(*) as size from UserMapping");
				rs.next();
				if (rs.getLong("size") > 99999999L)
					throw new SQLException(
							"UserMapping테이블이 꽉 찼습니다.");

				int tempID = (new Random()).nextInt(90000000)
						+ 10000000;
				if(confirmAttribute(member.getEmail(), "Email") == false){
					request.setAttribute("duplicatedMail", true);
					throw new SQLException(member.getEmail() + "메일이 이미 존재합니다.");
				} 
					
				if (confirmAttribute(tempID, "Id") == false ) {
					member.setId(tempID);

					pstmt = conn.prepareStatement(
							"insert into UserMapping values (?,?)");
					pstmt.setInt(1, member.getId());
					pstmt.setString(2, member.getEmail());
					pstmt.executeUpdate();
					break;
				}
			}

			// 유저테이블 검사 및 추가
			if (confirmAttribute(member.getId(),
					String.valueOf(member.getId()))) {
				throw new SQLException("User테이블의 ID가 이미 존재합니다");
			}

			pstmt = conn.prepareStatement(
					"insert into User values (?,?,?,?,?)");
			pstmt.setInt(1, member.getId());
			pstmt.setString(2, member.getNickname());
			pstmt.setString(3, member.getPassword());
			pstmt.setString(4, member.getIdType());
			pstmt.setTimestamp(5, member.getReg_date());

			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
		}

		return true;
	}

}
