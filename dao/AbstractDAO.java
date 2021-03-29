package dao;

import java.lang.reflect.Field;
import java.sql.*;
import connection.Connector;


/**
 *
 * @author Zahan Mara
 *
 * Abstract class for general operations
 *
 *
 * create, delete and update an "object" into the database
 */
public class AbstractDAO<T> {

    private Statement statement;

    // CREATE

    protected void create(Object object) {
        String table = object.getClass().getSimpleName();
        StringBuilder query = new StringBuilder();
        StringBuilder values = new StringBuilder();

        query.append("Insert into ").append(table).append(" (");

        Field[] all = object.getClass().getDeclaredFields();
        try {
            for (int i = 1; i < all.length - 1; i++) {
                all[i].setAccessible(true);
                query.append(all[i].getName());
                query.append(", ");
                Object value = all[i].get(object);
                String fieldType = all[i].getType().getSimpleName();

                if (fieldType.equals("String"))
                    values.append("\"").append(value).append("\"");
                else
                    values.append(value);
                values.append(", ");
            }

            int lastFieldIndex = all.length - 1;
            all[lastFieldIndex].setAccessible(true);
            query.append(all[lastFieldIndex].getName());
            Object value = all[lastFieldIndex].get(object);
            String fieldType = all[lastFieldIndex].getType().getSimpleName();
            if (fieldType.equals("String"))
                values.append("\"").append(value).append("\"");
            else
                values.append(value);

            query.append(") values (").append(values).append(")");
            System.out.println(query);
        } catch (Exception e) {
            System.out.println("Error at inserting");
        }
        try {
            Connection conn = Connector.getConnection();
            PreparedStatement prepInsertStatement = conn.prepareStatement(query.toString());
            prepInsertStatement.executeUpdate();
            conn.close();
            prepInsertStatement.close();
            // "Record created succesfully!
        } catch (Exception e) {
            System.out.println("Exception when executing insert query");
        }
    }


    // DELETE

    protected void delete(Object object) {
        String table = object.getClass().getSimpleName();
        StringBuilder query = new StringBuilder();
        query.append("Delete from ").append(table).append(" where ");
        Field[] all = object.getClass().getDeclaredFields();
        Field firstField = all[0];
        firstField.setAccessible(true);
        String fieldName = firstField.getName();
        query.append(fieldName).append(" = ");
        try {
            Object value = firstField.get(object);
            query.append(value);
        } catch (Exception e) {
            System.out.println("Error at getting id value");
        }
        System.out.println(query);
        try {
            Connection con = Connector.getConnection();
            PreparedStatement prepDeleteStatement = con.prepareStatement(query.toString());
            prepDeleteStatement.executeUpdate();
            con.close();
            prepDeleteStatement.close();
        } catch (Exception e) {
            System.out.println("Exception when executing delete query");
        }
    }

    // UPDATE

    protected void update(Object object) {
        String table = object.getClass().getSimpleName();
        StringBuilder query = new StringBuilder();
        query.append("update untold.").append(table).append(" set ");
        Field[] all = object.getClass().getDeclaredFields();
        try {
            for (int i = 1; i < all.length - 1; i++) {
                all[i].setAccessible(true);
                query.append(all[i].getName());
                query.append(" = ");
                Object value = all[i].get(object);
                String fieldType = all[i].getType().getSimpleName();
                if (fieldType.equals("String"))
                    query.append("\"").append(value).append("\"");
                else
                    query.append(value);
                query.append(", ");
            }
        } catch (Exception e) {
            System.out.println("Error at updating");
        }
        int lastFieldIndex = all.length - 1;
        all[lastFieldIndex].setAccessible(true);
        query.append(all[lastFieldIndex].getName());
        query.append(" = ");
        try {
            Object value = all[lastFieldIndex].get(object);
            String fieldType = all[lastFieldIndex].getType().getSimpleName();
            if (fieldType.equals("String"))
                query.append("\"").append(value).append("\"");
            else
                query.append(value);
        } catch (Exception e) {
            System.out.println("Error at updating");
        }

        query.append(" where ");
        Field firstField = all[0];
        firstField.setAccessible(true);
        String fieldName = firstField.getName();

        query.append(fieldName).append(" = ");

        try {
            Object value = firstField.get(object);
            query.append(value);
        } catch (Exception e) {
            System.out.println("Error at getting id value");
        }
        try {
            System.out.println(query);
            Connection con = Connector.getConnection();
            PreparedStatement prepUpdateStatement = con.prepareStatement(query.toString());
            prepUpdateStatement.executeUpdate();
            con.close();
            prepUpdateStatement.close();
        } catch (Exception e) {
            System.out.println("Exception when executing update query");
        }
    }

}