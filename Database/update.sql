-- Part 2.4 update.sql
--
-- Submitted by: Fareed Faisal, 1710393
-- 
-- One more thing… 

UPDATE COACH
SET dailySalary = dailySalary / 4;

ALTER TABLE COACH CHANGE COLUMN dailySalary hourlySalary DECIMAL(6,2);

UPDATE PARTICIPANT
SET dailySalary = dailySalary / 4;

ALTER TABLE PARTICIPANT
  CHANGE COLUMN dailySalary hourlySalary DECIMAL(6,2);

ALTER TABLE COACH_IN_SHOW
  ADD arrivalTime TIME,
  ADD departureTime TIME;

ALTER TABLE CONTENDER_IN_SHOW
  ADD arrivalTime TIME,
  ADD departureTime TIME;

UPDATE COACH_IN_SHOW s, TVSHOW t
SET s.arrivalTime = ADDTIME(t.startTime, '-1:00:00'),
    s.departureTime = ADDTIME(t.endTime, '1:00:00')
WHERE t.date <= CURDATE() AND s.idShow = t.idShow;

UPDATE CONTENDER_IN_SHOW c, TVSHOW t
SET c.arrivalTime = ADDTIME(t.startTime, '-1:00:00'),
    c.departureTime = ADDTIME(t.endTime, '1:00:00')
WHERE t.date <= CURDATE() AND c.idShow = t.idShow;