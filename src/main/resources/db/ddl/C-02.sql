CREATE SEQUENCE DEPARTMENT_ID_GENERATOR
START WITH 77
INCREMENT BY 1
NOMAXVALUE
/

CREATE TABLE DEPARTMENT (
  id       INT,
  "number" INT,
  address  VARCHAR2(100)
)
/

CREATE OR REPLACE PROCEDURE SAVE_DEPARTMENT(c_id OUT  INT,
                                            c_number  INT,
                                            c_address VARCHAR2)
AS
  BEGIN
    c_id := DEPARTMENT_ID_GENERATOR.nextval;
    INSERT INTO DEPARTMENT (id,
                            "number",
                            address) VALUES (c_id,
                                             c_number,
                                             c_address);
  END;
/

CREATE OR REPLACE PROCEDURE LOAD_DEPARTMENT(c_id          INT,
                                            c_number  OUT INT,
                                            c_address OUT VARCHAR2)
AS
  BEGIN
    SELECT
      "number",
      address
    INTO
      c_number,
      c_address
    FROM DEPARTMENT
    WHERE id = c_id;
  END;
/

CREATE OR REPLACE PROCEDURE UPDATE_DEPARTMENT(c_id      INT,
                                              c_number  INT,
                                              c_address VARCHAR2)
AS
  BEGIN
    UPDATE DEPARTMENT
    SET
      "number" = c_number,
      address  = c_address
    WHERE id = c_id;
  END;
/

CREATE OR REPLACE FUNCTION COUNT_DEPARTMENT
  RETURN INT
IS
  c INT;
  BEGIN
    SELECT COUNT(*)
    INTO c
    FROM DEPARTMENT;

    RETURN c;
  END;
/


CREATE OR REPLACE PROCEDURE REMOVE_DEPARTMENT(c_id INT)
AS
  BEGIN
    DELETE DEPARTMENT
    WHERE id = c_id;
  END;
/


CREATE OR REPLACE TYPE DEPARTMENT_TYPE AS OBJECT (id       INT,
                                                  "number" INT,
                                                  address  VARCHAR2(100))
/
CREATE OR REPLACE TYPE DEPARTMENT_ARRAY AS TABLE OF DEPARTMENT_TYPE
/

CREATE OR REPLACE FUNCTION LOAD_ALL_DEPARTMENT
  RETURN DEPARTMENT_ARRAY
IS
  r           DEPARTMENT%ROWTYPE;
  departments DEPARTMENT_ARRAY := DEPARTMENT_ARRAY();
  c           DEPARTMENT_TYPE;
  BEGIN
    FOR r IN (SELECT *
              FROM DEPARTMENT) LOOP
      departments.extend;
      c := DEPARTMENT_TYPE(r.id, r."number", r.address);
      departments(departments.count) := c;
    END LOOP;
    RETURN departments;
  END;
/