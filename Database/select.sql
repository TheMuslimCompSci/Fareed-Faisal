-- Part 2.3 select.sql
--
-- Submitted by: Fareed Faisal, 1710393
-- 
-- Query the Data

-- 2.3.1 Average female salary

SELECT CAST(AVG(dailySalary) AS DECIMAL(6,2))
  AS avgFemaleSalary
  FROM PARTICIPANT
  WHERE gender = 'F';

-- 2.3.2 Coaching report

SELECT c.name, c.surname, COUNT(c1.idCoach) AS numOfCoachees
  FROM COACH c
LEFT JOIN CONTENDER c1
  ON c.idCoach = c1.idCoach
GROUP BY c.idCoach;

-- 2.3.3 Coach monthly attendance report

SELECT c.name, c.surname, 'March' AS month, COUNT(*) AS showAttendences
  FROM COACH c
  JOIN COACH_IN_SHOW s
    ON c.idCoach = s.idCoach
  JOIN TVSHOW t
    ON s.idShow = t.idShow
  WHERE MONTH(t.date) = 3
  GROUP BY s.idCoach
UNION
SELECT c.name, c.surname, 'April' AS month, COUNT(*) AS showAttendences
  FROM COACH c
  JOIN COACH_IN_SHOW s
    ON c.idCoach = s.idCoach
  JOIN TVSHOW t
    ON s.idShow = t.idShow
  WHERE MONTH(t.date) = 4
  GROUP BY s.idCoach;

-- 2.3.4 Most expensive contender

SELECT c.stageName, SUM(p.dailySalary) AS highestPaidContestant
  FROM PARTICIPANT p
  LEFT JOIN CONTENDER c
    ON p.idContender = c.idContender
  GROUP BY c.idContender
  ORDER BY highestPaidContestant DESC
  LIMIT 1;

-- 2.3.5 March payment report

SELECT * FROM (
  SELECT c.name AS name, c.surname AS surname, c.dailySalary as dailySalary,
    COUNT(*) AS showAttendences, c.dailySalary*COUNT(*) AS marchSalary
    FROM COACH c
    LEFT JOIN COACH_IN_SHOW s
      ON c.idCoach=s.idCoach
    JOIN TVSHOW t
      ON s.idShow = t.idShow
    WHERE MONTH(t.date) = 3
    GROUP BY s.idCoach
  UNION ALL
  SELECT p.name AS name, p.surname AS surname, p.dailySalary as dailySalary,
    COUNT(*) AS showAttendences, p.dailySalary*COUNT(*) AS marchSalary
    FROM PARTICIPANT p
    LEFT JOIN CONTENDER_IN_SHOW s
      ON p.idContender=s.idContender
    LEFT JOIN TVSHOW t
      ON s.idShow = t.idShow
    WHERE MONTH(t.date) = 3
    GROUP BY p.idParticipant
  ) temp
  UNION
  SELECT '', '', '', 'TOTAL', SUM(marchSalary)
    FROM (SELECT c.name AS name, c.surname AS surname, c.dailySalary as dailySalary,
      COUNT(*) AS showAttendences, c.dailySalary*COUNT(*) AS marchSalary
      FROM COACH c
      LEFT JOIN COACH_IN_SHOW s
        ON c.idCoach=s.idCoach
      LEFT JOIN TVSHOW t
        ON s.idShow = t.idShow
      WHERE MONTH(t.date) = 3
      GROUP BY s.idCoach
    UNION ALL
    SELECT p.name AS name, p.surname AS surname, p.dailySalary as dailySalary,
      COUNT(*) AS showAttendences, p.dailySalary*COUNT(*) AS marchSalary
      FROM PARTICIPANT p
      LEFT JOIN CONTENDER_IN_SHOW s
        ON p.idContender=s.idContender
      LEFT JOIN TVSHOW t
        ON s.idShow = t.idShow
      WHERE MONTH(t.date) = 3
      GROUP BY p.idParticipant) temp1;

-- 2.3.6 Well formed groups!

SELECT IF(SUM(isWellFormed)/COUNT(c.type = 'GROUP') = 1, 'TRUE', 'FALSE') AS isWellFormed FROM (
  SELECT IF(COUNT(*) > 1, TRUE, FALSE) AS isWellFormed
      FROM CONTENDER c
      LEFT JOIN PARTICIPANT p
        ON p.idContender = c.idContender
      WHERE c.type = 'GROUP'
      GROUP BY c.idContender
) temp, CONTENDER c;