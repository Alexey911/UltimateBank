CREATE SEQUENCE CURRENCY_ID_GENERATOR
START WITH 55
INCREMENT BY 1
NOMAXVALUE
/

CREATE TABLE CURRENCY (
  id    INT,
  name  VARCHAR2(100),
  value NUMBER
)
/


CREATE OR REPLACE PROCEDURE SAVE_CURRENCY(c_id OUT INT,
                                          c_name   VARCHAR2,
                                          c_value  NUMBER)
AS
  BEGIN
    IF (c_id IS NULL)
    THEN
      c_id := CURRENCY_ID_GENERATOR.nextval;
    END IF;

    INSERT INTO CURRENCY (id,
                          name,
                          value) VALUES (c_id,
                                         c_name,
                                         c_value);
  END;
/


CREATE OR REPLACE PROCEDURE LOAD_CURRENCY(c_id        INT,
                                          c_name  OUT VARCHAR2,
                                          c_value OUT NUMBER)
AS
  BEGIN
    SELECT
      name,
      value
    INTO
      c_name,
      c_value
    FROM CURRENCY
    WHERE id = c_id;
  END;
/

CREATE OR REPLACE PROCEDURE UPDATE_CURRENCY(c_id    INT,
                                            c_name  VARCHAR2,
                                            c_value NUMBER)
AS
  BEGIN
    UPDATE CURRENCY
    SET
      name  = c_name,
      value = c_value
    WHERE id = c_id;
  END;
/

CREATE OR REPLACE FUNCTION COUNT_CURRENCY
  RETURN INT
IS
  c INT;
  BEGIN
    SELECT COUNT(*)
    INTO c
    FROM CURRENCY;

    RETURN c;
  END;
/


CREATE OR REPLACE PROCEDURE REMOVE_CURRENCY(c_id INT)
AS
  BEGIN
    DELETE CURRENCY
    WHERE id = c_id;
  END;
/


CREATE OR REPLACE TYPE CURRENCY_TYPE AS OBJECT (id    INT,
                                                name  VARCHAR2(100),
                                                value NUMBER)
/
CREATE OR REPLACE TYPE CURRENCY_ARRAY AS TABLE OF CURRENCY_TYPE
/

CREATE OR REPLACE FUNCTION LOAD_ALL_CURRENCY
  RETURN CURRENCY_ARRAY
IS
  r          CURRENCY%ROWTYPE;
  currencies CURRENCY_ARRAY := CURRENCY_ARRAY();
  c          CURRENCY_TYPE;
  BEGIN
    FOR r IN (SELECT *
              FROM CURRENCY) LOOP
      currencies.extend;
      c := CURRENCY_TYPE(r.id, r.name, r.value);
      currencies(currencies.count) := c;
    END LOOP;
    RETURN currencies;
  END;
/


CREATE OR REPLACE PROCEDURE CLEAR_CURRENCY
AS
  BEGIN
    DELETE CURRENCY;
  END;
/

CREATE OR REPLACE FUNCTION CONVERT_CURRENCY(c_from INT, c_to INT, c_value INT)
  RETURN NUMBER
IS
  value_from NUMBER;
  value_to   NUMBER;
  BEGIN
    SELECT CURRENCY.VALUE
    INTO value_from
    FROM CURRENCY
    WHERE id = c_from;

    SELECT CURRENCY.VALUE
    INTO value_to
    FROM CURRENCY
    WHERE id = c_to;

    RETURN (value_from / value_to) * c_value;
  END;
/