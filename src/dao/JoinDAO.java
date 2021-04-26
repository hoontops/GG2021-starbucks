// DAO(Data Access Object)

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import model.JoinDTO;


public class JoinDAO {
	// 싱글톤
		private static JoinDAO instance = new JoinDAO();

		public static JoinDAO getInstance() {
			return instance;
		}

		// 컨넥션풀
		private Connection getConnection() throws Exception {
			Context init = new InitialContext();
			DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/orcl");
			return ds.getConnection();
		}

		// 회원가입
		public int insert(JoinDTO member) {
			int result = 0;
			
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				con = getConnection();
				
				String sql = "insert into member values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, member.getId());
				pstmt.setString(2, member.getM_pw());
				pstmt.setString(3, member.getM_nick());
				pstmt.setString(4, member.getM_name());
				pstmt.setString(5, member.getM_birth1());
				pstmt.setString(6, member.getM_birth2());
				pstmt.setString(7, member.getM_birth3());
				pstmt.setString(8, member.getM_phone1());
				pstmt.setString(9, member.getM_phone2());
				pstmt.setString(10, member.getM_phone3());
				pstmt.setString(11, member.getM_mail());
				pstmt.setString(12, member.getM_post());
				pstmt.setString(13, member.getM_adds());
				
				result = pstmt.executeUpdate();
				
				
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				if(pstmt!=null) try { pstmt.close();}catch(Exception e) {}
				if(con!=null) try { con.close();}catch(Exception e) {}
			}
			
			return result;
		}
		
		// 로그인 (인증)
		public int joinAuth(String id, String m_pw) {
			int result = 0;
			
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				con = getConnection();
				
				String sql = "select * from member where id=? and m_pw=?";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, m_pw);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					result = 1;		// 인증 성공
				} else {
					result = -1;	// 인증 실패
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				if(rs!=null) try { rs.close();}catch(Exception e) {}
				if(pstmt!=null) try { pstmt.close();}catch(Exception e) {}
				if(con!=null) try { con.close();}catch(Exception e) {}
			}
			
			return result;
		}
		
		// 회원 정보 구하기 1명 (수정폼)
		public JoinDTO getMember(String id) {
			JoinDTO member = new JoinDTO();
			
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				con = getConnection();
				
				String sql = "select * from member where id=?";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					member.setId(rs.getString("id"));
					member.setM_pw(rs.getString("m_pw"));
					member.setM_nick(rs.getString("m_nick"));
					member.setM_name(rs.getString("m_name"));
					member.setM_birth1(rs.getString("m_birth1"));
					member.setM_birth2(rs.getString("m_birth2"));
					member.setM_birth3(rs.getString("m_birth3"));
					member.setM_phone1(rs.getString("m_phone1"));
					member.setM_phone2(rs.getString("m_phone2"));
					member.setM_phone3(rs.getString("m_phone3"));
					member.setM_mail(rs.getString("m_mail"));
					member.setM_post(rs.getString("m_post"));
					member.setM_adds(rs.getString("m_adds"));
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				if(rs!=null) try { rs.close();}catch(Exception e) {}
				if(pstmt!=null) try { pstmt.close();}catch(Exception e) {}
				if(con!=null) try { con.close();}catch(Exception e) {}
			}
			
			
			return member;
		}
		
		// 회원 정보 수정
		public int update(JoinDTO member) {
			int result = 0;
			
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				con = getConnection();
				
				String sql = "update member set m_nick=?,m_name=?,m_birth1,m_birth2, ";
					   sql += " m_birth3,m_phone1,m_phone2,m_phone3,m_mail,m_post, " ;
					   sql += " m_adds=? where id=?";
					   
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, member.getM_nick());
				pstmt.setString(2, member.getM_name());
				pstmt.setString(3, member.getM_birth1());
				pstmt.setString(4, member.getM_birth2());
				pstmt.setString(5, member.getM_birth3());
				pstmt.setString(6, member.getM_phone1());
				pstmt.setString(7, member.getM_phone2());
				pstmt.setString(8, member.getM_phone3());
				pstmt.setString(9, member.getM_mail());
				pstmt.setString(10, member.getM_post());
				pstmt.setString(11, member.getM_adds());
				pstmt.setString(12, member.getId());
				
				result = pstmt.executeUpdate();
				
					   
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				if(pstmt!=null) try { pstmt.close();}catch(Exception e) {}
				if(con!=null) try { con.close();}catch(Exception e) {}
			}
				
			return result;
		}
		
		// 회원 탈퇴
		public int joindelete(String id) {
			int result = 0;
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				con = getConnection();
				
				String sql = "delete from member where id=?";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				result = pstmt.executeUpdate();
				
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				if(pstmt!=null) try { pstmt.close();}catch(Exception e) {}
				if(con!=null) try { con.close();}catch(Exception e) {}
			}
			
			return result;
		}
		
		// 아이디 중복 검사
		public int idcheck(String id) {
			int result = 0;
			
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				con = getConnection();
				
				String sql = "select * from member where id=?";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {		// 중복 아이디
					result = 1;
				} else {			// 사용 가능한 아이디
					result = -1;
				}
				
				
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				if(rs!=null) try { rs.close();}catch(Exception e) {}
				if(pstmt!=null) try { pstmt.close();}catch(Exception e) {}
				if(con!=null) try { con.close();}catch(Exception e) {}
			}
			
			
			
			return result;
		}
		
		
}
	
		

