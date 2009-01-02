select totals.cid cid, total, awaited from (
        (
            select c.id cid, total from (
                (
                    select context_id, SUM(lcnt) total from
                    (
                        LISTS l
                        join
                            (select list_id, COUNT(id) lcnt from TASKS t where
                                (t.is_cancelled = false) and (t.is_finished = false) and (t.is_awaited = false) and (t.is_delegated = false)
                            GROUP BY list_id) tc
                        ON l.id=tc.list_id
                    ) GROUP BY context_id
                ) co
                JOIN CONTEXTS c ON co.context_id=c.id
            )
        ) totals
        JOIN
        (
            select c.id cid, IF(total IS NULL, 0, total) awaited  from (
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
                RIGHT OUTER JOIN
                CONTEXTS c ON co.context_id=c.id
            )
        ) awaitings
        ON totals.cid=awaitings.cid
)
