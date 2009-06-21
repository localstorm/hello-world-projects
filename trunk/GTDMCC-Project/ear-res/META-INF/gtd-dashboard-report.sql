select totals.name cname, totals.cid cid, total pending, awaited, flight, red, dead, done, effort1, effort2, effort3, effort4, effort5, hinted
from (
    (
            select c.name name, c.id cid, IF(total IS NULL, 0, total) total from (
                (select name, id from CONTEXTS where user_id=? and is_archived=false) c
                LEFT OUTER JOIN
                (
                    select context_id, SUM(lcnt) total from
                    (
                        LISTS l
                        join
                            (select list_id, COUNT(id) lcnt from TASKS where 
                                is_cancelled=false and
                                is_finished=false and
                                is_awaited=false and
                                is_delegated=false and
                                id not in (select task_id from HINTS)
                            GROUP BY list_id) tc
                        ON l.id=tc.list_id
                    ) GROUP BY context_id
                ) co
                ON c.id=co.context_id
            )
        ) totals
        JOIN
        (
            select c.id cid, IF(total IS NULL, 0, total) awaited  from (
                (select name, id from CONTEXTS where user_id=? and is_archived=false) c
                LEFT OUTER JOIN
                (
                    select context_id, SUM(lcnt) total from
                    (
                        LISTS l
                        join
                            (select list_id, COUNT(id) lcnt from TASKS t where
                                (t.is_cancelled = false) and (t.is_finished = false) and ((t.is_awaited = true) or (t.is_delegated = true))
                            GROUP BY list_id) tc
                        ON l.id=tc.list_id
                    ) GROUP BY context_id
                ) co
                
                ON c.id=co.context_id
            )
        ) awaitings
        ON totals.cid=awaitings.cid
        JOIN 
        (
            select id cid, IF(total IS NULL, 0, total) flight  from (
                
                    (select name, id from CONTEXTS where user_id=? and is_archived=false) c
                    LEFT OUTER JOIN
                    (
                        select context_id, SUM(lcnt) total from
                        (
                            select context_id, lcnt from  
                            (
                                LISTS lst 
                                JOIN (
                                    select list_id, SUM(1) lcnt from TASKS where 
                                        id IN (
                                            select task_id from TASKS_TO_FLIGHT_PLANS where plan_id = (select id from FLIGHT_PLANS where user_id=?)
                                        )
                                        and is_cancelled=false and is_finished=false
                                    GROUP BY list_id
                                ) lc ON lst.id=lc.list_id
                            ) 
                        ) ctx GROUP BY ctx.context_id
                    ) co
                    ON c.id=co.context_id
            )
        ) flight
        ON awaitings.cid=flight.cid
        JOIN
        (
            select id cid, IF(total IS NULL, 0, total) red  from (
                    (select name, id from CONTEXTS where user_id=? and is_archived=false) c
                    LEFT OUTER JOIN
                    (
                        select context_id, SUM(lcnt) total from
                        (
                            select context_id, lcnt from  
                            (
                                LISTS lst 
                                JOIN (
                                    select list_id, SUM(1) lcnt from TASKS where 
                                        ((redline is not null) and (redline<CURRENT_TIMESTAMP)) and
                                        ((deadline is null) or (deadline>CURRENT_TIMESTAMP))
                                        and is_cancelled=false and is_finished=false
                                    GROUP BY list_id
                                ) lc ON lst.id=lc.list_id
                            ) 
                        ) ctx GROUP BY ctx.context_id
                    ) co
                    ON c.id=co.context_id
            )
        ) redlined
        ON flight.cid = redlined.cid
        JOIN 
        (
            select id cid, IF(total IS NULL, 0, total) dead  from (
                    (select name, id from CONTEXTS where user_id=? and is_archived=false) c
                    LEFT OUTER JOIN
                    (
                        select context_id, SUM(lcnt) total from
                        (
                            select context_id, lcnt from  
                            (
                                LISTS lst 
                                JOIN (
                                    select list_id, SUM(1) lcnt from TASKS where 
                                        ((deadline is not null) and (deadline<CURRENT_TIMESTAMP)) and
                                        ((redline is null) or (deadline>=redline))
                                        and is_cancelled=false and is_finished=false
                                    GROUP BY list_id
                                ) lc ON lst.id=lc.list_id
                            ) 
                        ) ctx GROUP BY ctx.context_id
                    ) co
                    ON c.id=co.context_id
            )
        ) deadlined
        ON redlined.cid=deadlined.cid
        JOIN
        (
            select id cid, IF(total IS NULL, 0, total) done  from (
                    (select name, id from CONTEXTS where user_id=? and is_archived=false) c
                    LEFT OUTER JOIN
                    (
                        select context_id, SUM(lcnt) total from
                        (
                            select context_id, lcnt from
                            (
                                LISTS lst
                                JOIN (
                                    select list_id, SUM(1) lcnt from TASKS where
                                        is_cancelled=true or is_finished=true
                                    GROUP BY list_id
                                ) lc ON lst.id=lc.list_id
                            )
                        ) ctx GROUP BY ctx.context_id
                    ) co
                    ON c.id=co.context_id
            )
        ) finished
        ON deadlined.cid=finished.cid
        JOIN
        (
            select id cid, IF(total IS NULL, 0, total) effort1  from (
                    (select name, id from CONTEXTS where user_id=? and is_archived=false) c
                    LEFT OUTER JOIN
                    (
                        select context_id, SUM(lcnt) total from
                        (
                            select context_id, lcnt from
                            (
                                LISTS lst
                                JOIN (
                                    select list_id, SUM(1) lcnt from TASKS where
                                        is_cancelled=false and
                                        is_finished=false and
                                        is_awaited=false and
                                        is_delegated=false and
                                        effort=1 and
                                        id not in (select task_id from HINTS)
                                    GROUP BY list_id
                                ) lc ON lst.id=lc.list_id
                            )
                        ) ctx GROUP BY ctx.context_id
                    ) co
                    ON c.id=co.context_id
            )
        ) elementary
        ON finished.cid=elementary.cid
        JOIN
        (
            select id cid, IF(total IS NULL, 0, total) effort2  from (
                    (select name, id from CONTEXTS where user_id=? and is_archived=false) c
                    LEFT OUTER JOIN
                    (
                        select context_id, SUM(lcnt) total from
                        (
                            select context_id, lcnt from
                            (
                                LISTS lst
                                JOIN (
                                    select list_id, SUM(1) lcnt from TASKS where
                                        is_cancelled=false and
                                        is_finished=false and
                                        is_awaited=false and
                                        is_delegated=false and
                                        effort=2 and
                                        id not in (select task_id from HINTS)
                                    GROUP BY list_id
                                ) lc ON lst.id=lc.list_id
                            )
                        ) ctx GROUP BY ctx.context_id
                    ) co
                    ON c.id=co.context_id
            )
        ) easy
        ON elementary.cid=easy.cid
        JOIN
        (
            select id cid, IF(total IS NULL, 0, total) effort3  from (
                    (select name, id from CONTEXTS where user_id=? and is_archived=false) c
                    LEFT OUTER JOIN
                    (
                        select context_id, SUM(lcnt) total from
                        (
                            select context_id, lcnt from
                            (
                                LISTS lst
                                JOIN (
                                    select list_id, SUM(1) lcnt from TASKS where
                                        is_cancelled=false and
                                        is_finished=false and
                                        is_awaited=false and
                                        is_delegated=false and
                                        effort=3 and
                                        id not in (select task_id from HINTS)
                                    GROUP BY list_id
                                ) lc ON lst.id=lc.list_id
                            )
                        ) ctx GROUP BY ctx.context_id
                    ) co
                    ON c.id=co.context_id
            )
        ) medium
        ON easy.cid=medium.cid
        JOIN
        (
            select id cid, IF(total IS NULL, 0, total) effort4  from (
                    (select name, id from CONTEXTS where user_id=? and is_archived=false) c
                    LEFT OUTER JOIN
                    (
                        select context_id, SUM(lcnt) total from
                        (
                            select context_id, lcnt from
                            (
                                LISTS lst
                                JOIN (
                                    select list_id, SUM(1) lcnt from TASKS where
                                        is_cancelled=false and
                                        is_finished=false and
                                        is_awaited=false and
                                        is_delegated=false and
                                        effort=4 and
                                        id not in (select task_id from HINTS)
                                    GROUP BY list_id
                                ) lc ON lst.id=lc.list_id
                            )
                        ) ctx GROUP BY ctx.context_id
                    ) co
                    ON c.id=co.context_id
            )
        ) difficult
        ON medium.cid=difficult.cid
        JOIN
        (
            select id cid, IF(total IS NULL, 0, total) effort5  from (
                    (select name, id from CONTEXTS where user_id=? and is_archived=false) c
                    LEFT OUTER JOIN
                    (
                        select context_id, SUM(lcnt) total from
                        (
                            select context_id, lcnt from
                            (
                                LISTS lst
                                JOIN (
                                    select list_id, SUM(1) lcnt from TASKS where
                                        is_cancelled=false and
                                        is_finished=false and
                                        is_awaited=false and
                                        is_delegated=false and
                                        effort=5 and
                                        id not in (select task_id from HINTS)
                                    GROUP BY list_id
                                ) lc ON lst.id=lc.list_id
                            )
                        ) ctx GROUP BY ctx.context_id
                    ) co
                    ON c.id=co.context_id
            )
        ) vd
        ON difficult.cid=vd.cid
        JOIN
        (
            select id cid, IF(total IS NULL, 0, total) hinted  from (
                    (select name, id from CONTEXTS where user_id=? and is_archived=false) c
                    LEFT OUTER JOIN
                    (
                        select context_id, SUM(lcnt) total from
                        (
                            select context_id, lcnt from
                            (
                                LISTS lst
                                JOIN (
                                    select list_id, SUM(1) lcnt from TASKS where
                                        is_cancelled=false and
                                        is_finished=false and
                                        is_awaited=false and
                                        is_delegated=false and
                                        id in (select task_id from HINTS)
                                    GROUP BY list_id
                                ) lc ON lst.id=lc.list_id
                            )
                        ) ctx GROUP BY ctx.context_id
                    ) co
                    ON c.id=co.context_id
            )
        ) hnt
        ON vd.cid=hnt.cid
) ORDER BY cname