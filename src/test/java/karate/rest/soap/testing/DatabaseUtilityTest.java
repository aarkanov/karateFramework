package karate.rest.soap.testing;

import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;

import karate.rest.soap.testing.DatabaseUtilityTest;

public class DatabaseUtilityTest {
    Connection dbConnection;

    @Before
    public void setup() throws SQLException
    {
        dbConnection = DriverManager.getConnection("jdbc:hsqldb:mem:testcase;shutdown=true", "sa", "pass");
        Statement state = this.dbConnection.createStatement();
        state.execute("create table foo (id integer)");
        state.execute("insert into foo values 1");
        state.execute("insert into foo values 2");
        dbConnection.commit();
    }

    @Test
    public void testReadRows() {
        Map<String,Object> config = new HashMap<String,Object>();
        config.put("username", "sa");
        config.put("password", "pass");
        config.put("url", "jdbc:hsqldb:mem:testcase");
        config.put("driverClassName", "org.h2.Driver");
        DatabaseUtility dbUtil = new DatabaseUtility(config);

        Map<String,Object> firstRow = new HashMap<String,Object>();
        firstRow.put("ID", Integer.valueOf(1));
        Map<String,Object> secondRow = new HashMap<String,Object>();
        secondRow.put("ID", Integer.valueOf(2));
        List<Map<String, Object>> expectedDatabaseRows = new ArrayList<>();
        expectedDatabaseRows.add(firstRow);
        expectedDatabaseRows.add(secondRow);
        
        List<Map<String, Object>> actualDatabaseRows = dbUtil.readRows("SELECT * FROM foo");

        assertEquals(expectedDatabaseRows, actualDatabaseRows);
    }

    @Test
    public void testReadRow() {
        Map<String,Object> config = new HashMap<String,Object>();
        config.put("username", "sa");
        config.put("password", "pass");
        config.put("url", "jdbc:hsqldb:mem:testcase");
        config.put("driverClassName", "org.h2.Driver");
        DatabaseUtility dbUtil = new DatabaseUtility(config);

        Map<String,Object> expectedDatabaseRow = new HashMap<String,Object>();
        expectedDatabaseRow.put("ID", Integer.valueOf(2));
        
        Map<String, Object> actualDatabaseRow = dbUtil.readRow("SELECT * FROM foo WHERE ID = 2");

        assertEquals(expectedDatabaseRow, actualDatabaseRow);
    }

    @Test
    public void testReadValue() {
        Map<String,Object> config = new HashMap<String,Object>();
        config.put("username", "sa");
        config.put("password", "pass");
        config.put("url", "jdbc:hsqldb:mem:testcase");
        config.put("driverClassName", "org.h2.Driver");
        DatabaseUtility dbUtil = new DatabaseUtility(config);
        
        Object actualValue = dbUtil.readValue("SELECT ID FROM foo WHERE ID = 2");

        assertEquals(Integer.valueOf(2), actualValue);
    }

    @Test
    public void testInsertValue() throws SQLException {
        Map<String,Object> config = new HashMap<String,Object>();
        config.put("username", "sa");
        config.put("password", "pass");
        config.put("url", "jdbc:hsqldb:mem:testcase");
        config.put("driverClassName", "org.h2.Driver");
        DatabaseUtility dbUtil = new DatabaseUtility(config);

        dbUtil.executeChangeStatement("insert into foo values 3");
 
        Statement state = this.dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet actualValue = state.executeQuery("SELECT ID FROM foo WHERE ID = 3");
        
        actualValue.last();
        assertEquals(1, actualValue.getRow());

        actualValue.first();
        assertEquals(3, actualValue.getInt("ID"));
    }

    @Test
    public void testUpdateValue() throws SQLException {
        Map<String,Object> config = new HashMap<String,Object>();
        config.put("username", "sa");
        config.put("password", "pass");
        config.put("url", "jdbc:hsqldb:mem:testcase");
        config.put("driverClassName", "org.h2.Driver");
        DatabaseUtility dbUtil = new DatabaseUtility(config);

        dbUtil.executeChangeStatement("UPDATE foo SET ID = 4 WHERE ID = 2");
 
        Statement state = this.dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet actualValue = state.executeQuery("SELECT * FROM foo");
        
        actualValue.last();
        assertEquals(2, actualValue.getRow());

        actualValue.first();
        assertEquals(1, actualValue.getInt("ID"));

        actualValue.next();
        assertEquals(4, actualValue.getInt("ID"));
    }

    @Test
    public void testDeleteValue() throws SQLException {
        Map<String,Object> config = new HashMap<String,Object>();
        config.put("username", "sa");
        config.put("password", "pass");
        config.put("url", "jdbc:hsqldb:mem:testcase");
        config.put("driverClassName", "org.h2.Driver");
        DatabaseUtility dbUtil = new DatabaseUtility(config);

        dbUtil.executeChangeStatement("DELETE FROM foo WHERE ID = 1");
 
        Statement state = this.dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet actualValue = state.executeQuery("SELECT * FROM foo");
        
        actualValue.last();
        assertEquals(1, actualValue.getRow());

        actualValue.first();
        assertEquals(2, actualValue.getInt("ID"));
    }

    @After
    public void tearDown() 
    throws Exception
    {
        dbConnection.close();
    }
}