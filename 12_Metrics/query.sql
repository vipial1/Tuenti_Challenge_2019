select main.date_time, main.user_id, main.action, CASE
        WHEN lastClose >= lastOpen AND lastClose >= minUser THEN lastClose
        WHEN lastOpen >= lastClose AND lastOpen >= minUser THEN lastOpen
        WHEN minUser >= lastClose AND minUser >= lastOpen THEN minUser
        ELSE                                        lastClose
    END AS sessionOpened
from(
select outerAct.*,
 (select IFNULL(min(innerAct.date_time), 0) from activity innerAct
  where innerAct.user_id = outerAct.user_id
 and innerAct.date_time > (select max(sub.date_time) t from activity sub where sub.user_id = innerAct.user_id and sub.date_time < outerAct.date_time and sub.action = 'close')) lastClose,
  (select IFNULL(min(innerAct.date_time),0) from activity innerAct
  where innerAct.user_id = outerAct.user_id
 and innerAct.date_time >= (select max(sub.date_time) t from activity sub where sub.user_id = innerAct.user_id and sub.date_time <= outerAct.date_time and sub.action = 'open')) lastOpen,
 (select IFNULL(min(innerAct.date_time),0) from activity innerAct
  where innerAct.user_id = outerAct.user_id) minUser
 
 from activity outerAct
 order by outerAct.date_time
 ) main
 order by 1;