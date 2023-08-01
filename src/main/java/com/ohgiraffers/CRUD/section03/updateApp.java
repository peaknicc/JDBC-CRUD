package com.ohgiraffers.CRUD.section03;

import com.ohgiraffers.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class updateApp {
    public static void main(String[] args) {
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        int result = 0;

        Scanner sc = new Scanner(System.in);
        MenuDTO mDto = new MenuDTO();

        System.out.println("메뉴 수정");
        System.out.println("수정할 메뉴 몇번?");

        int menuCode = sc.nextInt();
        mDto.setMenuCode(menuCode);
        sc.nextLine();

        System.out.println("이름 뭐로?");

        String menuName = sc.nextLine();
        mDto.setMenuName(menuName);

        System.out.println("얼마로?");

        int menuPrice = sc.nextInt();
        mDto.setMenuPrice(menuPrice);
        sc.nextLine();

        System.out.println("카테고리 번호 바꾸실?");
        System.out.println("1.식사 2.음료 3.디저트 4.한식 5.중식");

        int categoryCode = sc.nextInt();
        mDto.setCategoryCode(categoryCode);
        sc.nextLine();

        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));
            String query = prop.getProperty("updateMenu");

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, mDto.getMenuName());
            pstmt.setInt(2, mDto.getMenuPrice());
            pstmt.setInt(3, mDto.getCategoryCode());
            pstmt.setInt(4, mDto.getMenuCode());

            result = pstmt.executeUpdate();
            System.out.println(result + "개의 메뉴 수정완료.");
    } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con);
            close(pstmt);
        }
    }
}

