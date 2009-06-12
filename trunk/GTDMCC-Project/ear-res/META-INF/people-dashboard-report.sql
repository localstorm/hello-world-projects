select remains, id, name, lname, pname, birth_date from
    (SELECT DAYOFYEAR(birth_date + INTERVAL YEAR(NOW()) - YEAR(birth_date) YEAR) - DAYOFYEAR(NOW()) remains, id, name, lname, pname, birth_date FROM PERSONS
    where birth_date is not null ORDER BY remains ASC) a
where remains>=0 and id IN (select person_id from PERSONS_TO_GROUPS where group_id IN (select id from PERSON_GROUPS where owner=?))
