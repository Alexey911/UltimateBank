CREATE SEQUENCE DEPOSIT_ID_GENERATOR
START WITH 22
INCREMENT BY 1
NOMAXVALUE
/

CREATE TABLE DEPOSIT (
  id          INT,
  sum         NUMBER,
  percent     NUMBER,
  expirtyDate TIMESTAMP,
  client      INT,
  currency    INT
)
/

CREATE OR REPLACE PROCEDURE SAVE_DEPOSIT(c_id OUT       INT,
                                         c_sum          NUMBER,
                                         c_percent      NUMBER,
                                         c_expirty_date TIMESTAMP,
                                         c_client       INT,
                                         c_currency     INT)
AS
  BEGIN
    IF (c_id IS NULL)
    THEN
      c_id := DEPOSIT_ID_GENERATOR.nextval;
    END IF;
    INSERT INTO DEPOSIT (id,
                         sum,
                         percent,
                         expirtyDate,
                         client,
                         currency) VALUES (c_id,
                                           c_sum,
                                           c_percent,
                                           c_expirty_date,
                                           c_client,
                                           c_currency);
  END;
/


CREATE OR REPLACE PROCEDURE LOAD_DEPOSIT(c_id               INT,
                                         c_sum          OUT NUMBER,
                                         c_percent      OUT NUMBER,
                                         c_expirty_date OUT TIMESTAMP,
                                         c_client       OUT INT,
                                         c_currency     OUT INT)
AS
  BEGIN
    SELECT
      sum,
      percent,
      expirtyDate,
      client,
      currency
    INTO
      c_sum,
      c_percent,
      c_expirty_date,
      c_client,
      c_currency
    FROM DEPOSIT
    WHERE id = c_id;
  END;
/

CREATE OR REPLACE PROCEDURE UPDATE_DEPOSIT(c_id           INT,
                                           c_sum          NUMBER,
                                           c_percent      NUMBER,
                                           c_expirty_date TIMESTAMP,
                                           c_client       INT,
                                           c_currency     INT)
AS
  BEGIN
    UPDATE DEPOSIT
    SET
      sum         = c_sum,
      percent     = c_percent,
      expirtyDate = c_expirty_date,
      client      = c_client,
      currency    = c_currency
    WHERE id = c_id;
  END;
/

CREATE OR REPLACE FUNCTION COUNT_DEPOSIT
  RETURN INT
IS
  c INT;
  BEGIN
    SELECT COUNT(*)
    INTO c
    FROM DEPOSIT;

    RETURN c;
  END;
/


CREATE OR REPLACE PROCEDURE REMOVE_DEPOSIT(c_id INT)
AS
  BEGIN
    DELETE DEPOSIT
    WHERE id = c_id;
  END;
/


CREATE OR REPLACE TYPE DEPOSIT_TYPE AS OBJECT (id          INT,
                                               sum         NUMBER,
                                               percent     NUMBER,
                                               expirtyDate TIMESTAMP,
                                               client      INT,
                                               currency    INT)
/
CREATE OR REPLACE TYPE DEPOSIT_ARRAY AS TABLE OF DEPOSIT_TYPE
/

CREATE OR REPLACE FUNCTION LOAD_ALL_DEPOSIT
  RETURN DEPOSIT_ARRAY
IS
  r        DEPOSIT%ROWTYPE;
  deposits DEPOSIT_ARRAY := DEPOSIT_ARRAY();
  d        DEPOSIT_TYPE;
  BEGIN
    FOR r IN (SELECT *
              FROM DEPOSIT) LOOP
      deposits.extend;
      d := DEPOSIT_TYPE(r.id, r.sum, r.percent, r.expirtyDate, r.client, r.currency);
      deposits(deposits.count) := d;
    END LOOP;
    RETURN deposits;
  END;
/


CREATE OR REPLACE PROCEDURE CLEAR_DEPOSIT
AS
  BEGIN
    DELETE DEPOSIT;
  END;
/