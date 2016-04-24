CREATE SEQUENCE CLIENT_ID_GENERATOR
START WITH 1
INCREMENT BY 1
NOMAXVALUE
/

CREATE TABLE CLIENT (
  id       INT,
  name     VARCHAR2(100),
  surname  VARCHAR2(100),
  address  VARCHAR2(100),
  password VARCHAR2(100)
)
/

CREATE OR REPLACE PROCEDURE SAVE_CLIENT(c_id OUT   INT,
                                        c_name     VARCHAR2,
                                        c_surname  VARCHAR2,
                                        c_address  VARCHAR2,
                                        c_password VARCHAR2)
AS
  BEGIN
    c_id := CLIENT_ID_GENERATOR.nextval;
    INSERT INTO CLIENT (id,
                        name,
                        surname,
                        address,
                        password) VALUES (c_id,
                                          c_name,
                                          c_surname,
                                          c_address,
                                          c_password);
  END
/

CREATE OR REPLACE PROCEDURE LOAD_CLIENT(c_id           INT,
                                        c_name     OUT VARCHAR2,
                                        c_surname  OUT VARCHAR2,
                                        c_address  OUT VARCHAR2,
                                        c_password OUT VARCHAR2)
AS
  BEGIN
    SELECT
      name,
      surname,
      address,
      password
    INTO
      c_name,
      c_surname,
      c_address,
      c_password
    FROM CLIENT
    WHERE id = c_id;
  END
/

CREATE OR REPLACE PROCEDURE UPDATE_CLIENT(c_id       INT,
                                          c_name     VARCHAR2,
                                          c_surname  VARCHAR2,
                                          c_address  VARCHAR2,
                                          c_password VARCHAR2)
AS
  BEGIN
    UPDATE CLIENT
    SET
      name     = c_name,
      surname  = c_surname,
      address  = c_address,
      password = c_password
    WHERE id = c_id;
  END
/

CREATE OR REPLACE FUNCTION COUNT_CLIENT
  RETURN INT
IS
  c INT;
  BEGIN
    SELECT COUNT(*)
    INTO c
    FROM CLIENT;

    RETURN c;
  END
/


CREATE OR REPLACE PROCEDURE REMOVE_CLIENT(c_id INT)
AS
  BEGIN
    DELETE CLIENT
    WHERE id = c_id;
  END
/


CREATE OR REPLACE TYPE CLIENT_TYPE AS OBJECT (id       INT,
                                              name     VARCHAR2(100),
                                              surname  VARCHAR2(100),
                                              address  VARCHAR2(100),
                                              password VARCHAR2(100))
/
CREATE OR REPLACE TYPE CLIENT_ARRAY AS TABLE OF CLIENT_TYPE
/

CREATE OR REPLACE FUNCTION LOAD_ALL_CLIENT
  RETURN CLIENT_ARRAY
IS
  r       CLIENT%ROWTYPE;
  clients CLIENT_ARRAY := CLIENT_ARRAY();
  c       CLIENT_TYPE;
  BEGIN
    FOR r IN (SELECT *
              FROM CLIENT) LOOP
      clients.extend;
      c := CLIENT_TYPE(r.id, r.name, r.surname, r.address, r.password);
      clients(clients.count) := c;
    END LOOP;
    RETURN clients;
  END
/