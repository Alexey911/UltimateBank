CREATE SEQUENCE BANKER_ID_GENERATOR
START WITH 120
INCREMENT BY 1
NOMAXVALUE
/


CREATE TABLE BANKER (
  id         INT,
  name       VARCHAR2(100),
  surname    VARCHAR2(100),
  address    VARCHAR2(100),
  password   VARCHAR2(100),
  privilege  INT,
  department INT
)
/

CREATE OR REPLACE PROCEDURE SAVE_BANKER(c_id OUT     INT,
                                        c_name       VARCHAR2,
                                        c_surname    VARCHAR2,
                                        c_address    VARCHAR2,
                                        c_password   VARCHAR2,
                                        c_privilege  INT,
                                        c_department INT)
AS
  BEGIN
    c_id := BANKER_ID_GENERATOR.nextval;
    INSERT INTO BANKER (id,
                        name,
                        surname,
                        address,
                        password,
                        privilege,
                        department) VALUES (c_id,
                                            c_name,
                                            c_surname,
                                            c_address,
                                            c_password,
                                            c_privilege,
                                            c_department);
  END;
/


CREATE OR REPLACE PROCEDURE LOAD_BANKER(c_id             INT,
                                        c_name       OUT VARCHAR2,
                                        c_surname    OUT VARCHAR2,
                                        c_address    OUT VARCHAR2,
                                        c_password   OUT VARCHAR2,
                                        c_privilege  OUT INT,
                                        c_department OUT INT)
AS
  BEGIN
    SELECT
      name,
      surname,
      address,
      password,
      privilege,
      department
    INTO
      c_name,
      c_surname,
      c_address,
      c_password,
      c_privilege,
      c_department
    FROM BANKER
    WHERE ID = c_id;
  END;
/

CREATE OR REPLACE PROCEDURE UPDATE_BANKER(c_id         INT,
                                          c_name       VARCHAR2,
                                          c_surname    VARCHAR2,
                                          c_address    VARCHAR2,
                                          c_password   VARCHAR2,
                                          c_privilege  INT,
                                          c_department INT)
AS
  BEGIN
    UPDATE BANKER
    SET
      name       = c_name,
      surname    = c_surname,
      address    = c_address,
      password   = c_password,
      privilege  = c_privilege,
      department = c_department
    WHERE ID = c_id;
  END;
/

CREATE OR REPLACE FUNCTION COUNT_BANKER
  RETURN INT
IS
  c INT;
  BEGIN
    SELECT COUNT(*)
    INTO c
    FROM BANKER;

    RETURN c;
  END;
/


CREATE OR REPLACE PROCEDURE REMOVE_BANKER(c_id INT)
AS
  BEGIN
    DELETE BANKER
    WHERE id = c_id;
  END;
/


CREATE OR REPLACE TYPE BANKER_TYPE AS OBJECT (id         INT,
                                              name       VARCHAR2(100),
                                              surname    VARCHAR2(100),
                                              address    VARCHAR2(100),
                                              password   VARCHAR2(100),
                                              privilege  INT,
                                              department INT)
/
CREATE OR REPLACE TYPE BANKER_ARRAY AS TABLE OF BANKER_TYPE
/

CREATE OR REPLACE FUNCTION LOAD_ALL_BANKER
  RETURN BANKER_ARRAY
IS
  r       CLIENT%ROWTYPE;
  bankers BANKER_ARRAY := BANKER_ARRAY();
  b       BANKER_TYPE;
  BEGIN
    FOR r IN (SELECT *
              FROM BANKER) LOOP
      bankers.extend;
      b := BANKER_TYPE(r.id, r.name, r.surname, r.address, r.password, r.privilege, r.department);
      bankers(bankers.count) := b;
    END LOOP;
    RETURN bankers;
  END;
/