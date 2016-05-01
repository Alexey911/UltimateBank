CREATE SEQUENCE BILL_ID_GENERATOR
START WITH 140
INCREMENT BY 1
NOMAXVALUE
/


CREATE TABLE BILL (
  id       INT,
  balance  INT,
  isActive INT,
  currency INT,
  client   INT,
  billCard INT
)
/

CREATE OR REPLACE PROCEDURE SAVE_BILL(c_id OUT    INT,
                                      c_balance   INT,
                                      c_is_active INT,
                                      c_currency  INT,
                                      c_client    INT,
                                      c_bill_card INT)
AS
  BEGIN
    IF (c_id IS NULL)
    THEN
      c_id := BILL_ID_GENERATOR.nextval;
    END IF;
    INSERT INTO BILL (id,
                      balance,
                      isActive,
                      currency,
                      client,
                      billCard) VALUES (c_id,
                                        c_balance,
                                        c_is_active,
                                        c_currency,
                                        c_client,
                                        c_bill_card);
  END;
/


CREATE OR REPLACE PROCEDURE LOAD_BILL(c_id            INT,
                                      c_balance   OUT INT,
                                      c_is_active OUT INT,
                                      c_currency  OUT INT,
                                      c_client    OUT INT,
                                      c_bill_card OUT INT)
AS
  BEGIN
    SELECT
      balance,
      isActive,
      currency,
      client,
      billCard
    INTO
      c_balance,
      c_is_active,
      c_currency,
      c_client,
      c_bill_card
    FROM BILL
    WHERE ID = c_id;
  END;
/

CREATE OR REPLACE PROCEDURE UPDATE_BILL(c_id        INT,
                                        c_balance   INT,
                                        c_is_active INT,
                                        c_currency  INT,
                                        c_client    INT,
                                        c_bill_card INT)
AS
  BEGIN
    UPDATE BILL
    SET
      balance  = c_balance,
      isActive = c_is_active,
      currency = c_currency,
      client   = c_client,
      billCard = c_bill_card
    WHERE ID = c_id;
  END;
/

CREATE OR REPLACE FUNCTION COUNT_BILL
  RETURN INT
IS
  c INT;
  BEGIN
    SELECT COUNT(*)
    INTO c
    FROM BILL;

    RETURN c;
  END;
/


CREATE OR REPLACE PROCEDURE REMOVE_BILL(c_id INT)
AS
  BEGIN
    DELETE BILL
    WHERE id = c_id;
  END;
/


CREATE OR REPLACE TYPE BILL_TYPE AS OBJECT (id          INT,
                                            c_balance   INT,
                                            c_is_active INT,
                                            c_currency  INT,
                                            c_client    INT,
                                            c_bill_card INT)
/
CREATE OR REPLACE TYPE BILL_ARRAY AS TABLE OF BILL_TYPE
/

CREATE OR REPLACE FUNCTION LOAD_ALL_BILL
  RETURN BILL_ARRAY
IS
  r     BILL%ROWTYPE;
  bills BILL_ARRAY := BILL_ARRAY();
  b     BILL_TYPE;
  BEGIN
    FOR r IN (SELECT *
              FROM BILL) LOOP
      bills.extend;
      b := BILL_TYPE(r.id, r.balance, r.isActive, r.currency, r.client, r.billCard);
      bills(bills.count) := b;
    END LOOP;
    RETURN bills;
  END;
/


CREATE OR REPLACE PROCEDURE CLEAR_BILL
AS
  BEGIN
    DELETE BILL;
  END;
/