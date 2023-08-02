package com.ohgiraffers.CRUD.section01;

import com.ohgiraffers.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    ArrayList<MenuDTO> dtoList = new ArrayList<>();
    MenuDTO dto = new MenuDTO();

    System.out.println("음식 가격 조회");
    System.out.println("1.식사 2.음료 3.디저트 4.한식 5.중식");

    int categoryNum = sc.nextInt();
    dto.setCategoryCode(categoryNum);

    Properties prop = new Properties();

    try {
        prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));
        String query = prop.getProperty("selectCategory");

        pstmt = con.prepareStatement(query);
        pstmt.setInt(1, dto.getCategoryCode());

        rset = pstmt.executeQuery();

        while (rset.next()) {
            String foodName = rset.getString("MENU_NAME");
            int price = rset.getInt("MENU_PRICE");
            int menuCode = rset.getInt("MENU_CODE");

            MenuDTO menuDTO = new MenuDTO();

            menuDTO.setMenuName(foodName);
            menuDTO.setMenuPrice(price);
            menuDTO.setMenuCode(menuCode);
            dtoList.add(menuDTO);
        }

        for (MenuDTO menudto : dtoList) {
            System.out.println("제품번호 " + menudto.getMenuCode()
                    + "번의 "+ menudto.getMenuName()
                    + " 가격은 " + menudto.getMenuPrice()
                    + "원 입니다.");
        }


        } catch (IOException e) {
        e.printStackTrace();
        } catch (SQLException e) {
        e.printStackTrace();
        } finally {
            close(con);
            close(pstmt);
            close(rset);
    }
    }
}
