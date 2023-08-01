package com.ohgiraffers.CRUD.section01;

import com.ohgiraffers.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;
import static com.ohgiraffers.common.JDBCTemplate.close;

public class selectApp {
    public static void main(String[] args) {
    Connection con = getConnection();
    PreparedStatement pstmt = null;
    ResultSet rset = null;

    Scanner sc = new Scanner(System.in);
    MenuDTO mDto = new MenuDTO();

    System.out.println("음식 가격 조회");
    System.out.println("1.식사 2.음료 3.디저트 4.한식 5.중식");

    int categoryNum = sc.nextInt();
    mDto.setCategoryCode(categoryNum);

    Properties prop = new Properties();

    try {
        prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));
        String query = prop.getProperty("selectCategory");
        /* System.out.println("query : " + query); */

        pstmt = con.prepareStatement(query);
        pstmt.setInt(1, mDto.getCategoryCode());

        rset = pstmt.executeQuery();

        while(rset.next()) {
            String foodName = rset.getString("MENU_NAME");
            int price = rset.getInt("MENU_PRICE");
            int menuCode = rset.getInt("MENU_CODE");

            System.out.println("제품번호 " + menuCode + "번의 "+ foodName + " 가격은 " + price + "원 입니다.");
            }
        } catch (IOException e) {
        e.printStackTrace();
        } catch (SQLException e) {
        e.printStackTrace();
        } finally {
            close(con);
            close(pstmt);
            close(rset);
            /* 이전에 배울땐 close를 사용한 순서의 역순으로 닫으라했는데 그렇게 안함*/
    }
    }
}
