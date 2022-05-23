CREATE DATABASE IF NOT EXISTS fwa
;
USE fwa
;
CREATE FUNCTION table_exists(tabName varchar(30)) RETURNS BOOLEAN
    DETERMINISTIC
    RETURN (SELECT IF (EXISTS (SELECT *
                               FROM INFORMATION_SCHEMA.TABLES
                               WHERE TABLE_SCHEMA = 'fwa'
                                 AND  TABLE_NAME = tabName),
                       TRUE, FALSE))
;
CREATE PROCEDURE execute_immediate(query VARCHAR(1000))
    MODIFIES SQL DATA
    SQL SECURITY DEFINER
BEGIN
    SET @q = query;
    PREPARE stmt FROM @q;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;
CREATE PROCEDURE alert_table(tabName varchar(30), params varchar(100))
    MODIFIES SQL DATA
    SQL SECURITY DEFINER
BEGIN
    SET @SQL := CONCAT('ALTER TABLE ', tabName, ' ADD  ', params);
    PREPARE stmt FROM @SQL;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;

CREATE PROCEDURE createTabs()
BEGIN
    IF NOT table_exists('USER') THEN CALL execute_immediate(
            'CREATE TABLE fwa.USER(
                            ID MEDIUMINT AUTO_INCREMENT,
                            NAME VARCHAR(30),
                            LAST_NAME VARCHAR(30),
                            MIDDLE_NAME VARCHAR(30),
                            PHONE_NUMBER VARCHAR(30),
                            PASSWORD VARCHAR(255),
                            PASSWORD VARCHAR(120),
                    PRIMARY KEY (ID));'
        );
    END IF;

    IF NOT table_exists('IMAGE') THEN CALL execute_immediate(
            'CREATE TABLE fwa.IMAGE(
                            ID MEDIUMINT AUTO_INCREMENT,
                            FILE_NAME VARCHAR(90),
                            SIZE MEDIUMINT,
                            MIME VARCHAR(30),
                            PATH VARCHAR(50),
                            USER_ID MEDIUMINT,
                    PRIMARY KEY (ID));'
        );
    END IF;
    
    IF NOT table_exists('VISIT') THEN CALL execute_immediate(
            'CREATE TABLE fwa.VISIT(
                            ID MEDIUMINT AUTO_INCREMENT,
                            TIME timestamp(6) DEFAULT CURRENT_TIMESTAMP(6),
                            USER_ID MEDIUMINT,
                    PRIMARY KEY (ID));'
        );
    END IF;

END
;

call createTabs
;

DROP FUNCTION table_exists
;

DROP PROCEDURE execute_immediate
;
DROP PROCEDURE alert_table
;
DROP PROCEDURE createTabs
;

