package com.ohgiraffers.CRUD.section04;

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

public class deleteApp {
    public static void main(String[] args) {
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        int result = 0;

        Scanner sc = new Scanner(System.in);

        ArrayList<MenuDTO> dtoList = new ArrayList<>();
        MenuDTO dto = new MenuDTO();

        System.out.println("<메뉴 삭제>");
        System.out.println("삭제할 메뉴 몇번?");

        int menuCode = sc.nextInt();
        dto.setMenuCode(menuCode);
        sc.nextLine();
        
        Properties prop = new Properties();
        dtoList.add(dto);


        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));
            String query = prop.getProperty("deleteMenu");

            pstmt = con.prepareStatement(query);

            for(MenuDTO menuDTO : dtoList) {
                pstmt.setInt(1, menuDTO.getMenuCode());
            }

            result = pstmt.executeUpdate();

            System.out.println(result + "개의 메뉴 삭제완료.");

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

