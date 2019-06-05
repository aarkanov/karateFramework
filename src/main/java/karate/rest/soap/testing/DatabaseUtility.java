
package karate.rest.soap.testing;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DatabaseUtility {
    
    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtility.class); 
    private DriverManagerDataSource dataSource = new DriverManagerDataSource();
    
    private final JdbcTemplate jdbc;

    public DatabaseUtility(Map<String, Object> config) {
        String url = (String) config.get("url");
        String username = (String) config.get("username");
        String password = (String) config.get("password");
        String driver = (String) config.get("driverClassName");
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        jdbc = new JdbcTemplate(dataSource);
        logger.info("init jdbc template: {}", url);
    }
    
    public Object readValue(String query) {
        return jdbc.queryForObject(query, Object.class);
    }    
    
    public Map<String, Object> readRow(String query) {
        return jdbc.queryForMap(query);
    }
    
    public List<Map<String, Object>> readRows(String query) {
        return jdbc.queryForList(query);
    }

    public void executeChangeStatement(String query) {
        jdbc.update(query);
    }
}