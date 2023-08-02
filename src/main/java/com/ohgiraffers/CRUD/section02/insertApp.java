package com.ohgiraffers.CRUD.section02;

import com.ohgiraffers.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class insertApp {
    public static void main(String[] args) {
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        int result = 0;

        Scanner sc = new Scanner(System.in);

        ArrayList<MenuDTO> dtoList = new ArrayList<>();
        MenuDTO dto = new MenuDTO();

        System.out.println("메뉴 추가");
        System.out.println("메뉴이름이 어찌됨?");

        String foodName = sc.nextLine();
        dto.setMenuName(foodName);

        System.out.println("얼마?");

        int menuPrice = sc.nextInt();
        dto.setMenuPrice(menuPrice);
        sc.nextLine();

        System.out.println("주문가능함? Y/N");

        String orderableStatus = sc.nextLine();
        dto.setOrderableStatus(orderableStatus);

        System.out.println("음식 카테고리 번호");
        System.out.println("1.식사 2.음료 3.디저트 4.한식 5.중식");

        int categoryCode = sc.nextInt();
        dto.setCategoryCode(categoryCode);
        sc.nextLine();

        Properties prop = new Properties();
        dtoList.add(dto);

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));
            String query = prop.getProperty("insertMenu");

            /*MenuDTO menudto = new MenuDTO();*/

            pstmt = con.prepareStatement(query);

            for (MenuDTO menudto : dtoList) {
                pstmt.setString(1, menudto.getMenuName());
                pstmt.setInt(2, menudto.getMenuPrice());
                pstmt.setString(3, menudto.getOrderableStatus());
                pstmt.setInt(4, menudto.getCategoryCode());
            }

            result = pstmt.executeUpdate();

            System.out.println(result + "개의 메뉴 추가완료 :)");

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
