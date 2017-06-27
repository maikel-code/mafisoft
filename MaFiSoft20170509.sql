UPDATE mafisoftBD.course SET course_name = 'Fatburn', trainer_name = 'Shahl', start = '17:00:00', end = '20:00:00' WHERE course_id = 1;
UPDATE mafisoftBD.course SET course_name = 'Joga', trainer_name = 'Ali', start = '15:00:00', end = '19:00:00' WHERE course_id = 2;
UPDATE mafisoftBD.course SET course_name = 'Cycle', trainer_name = 'Schmidt', start = '18:00:00', end = '21:00:00' WHERE course_id = 3;
UPDATE mafisoftBD.course SET course_name = 'sdjkf', trainer_name = 'skdfjhsdk', start = '13:23:00', end = '14:22:00' WHERE course_id = 4;
UPDATE mafisoftBD.course SET course_name = 'Ploetz', trainer_name = 'Tester', start = '12:00:00', end = '21:00:00' WHERE course_id = 5;
UPDATE mafisoftBD.course SET course_name = 'Best training', trainer_name = 'John Cena', start = '14:00:00', end = '16:00:00' WHERE course_id = 29;
UPDATE mafisoftBD.customer SET customer_firstname = 'Peter', customer_lastname = 'Yermash', birthday = '1993-09-04 00:00:00', email = 'peter.yermash@fh-dortmund.de', mobilephone = '', zipCode = 44227, city = 'Dortmund', street = 'Am Gardenkamp 45', create_time = '2017-04-24', end_time = '2018-04-02' WHERE customer_id = 1;
UPDATE mafisoftBD.customer SET customer_firstname = 'Anil Can', customer_lastname = 'Afak', birthday = '1993-02-19 00:00:00', email = '', mobilephone = '', zipCode = 44135, city = 'Dortmund', street = 'Dortmund', create_time = '2017-04-24', end_time = '2018-04-02' WHERE customer_id = 11;
UPDATE mafisoftBD.customer SET customer_firstname = 'Michael', customer_lastname = 'Tjupalow', birthday = '1990-12-12 00:00:00', email = '', mobilephone = '', zipCode = 44135, city = 'Dortmund', street = 'Irgendwo 1', create_time = '2017-04-25', end_time = '2018-04-03' WHERE customer_id = 13;
UPDATE mafisoftBD.customer SET customer_firstname = 'Max', customer_lastname = 'Mustermann', birthday = '1991-01-01 00:00:00', email = '', mobilephone = '', zipCode = 12345, city = 'Narnia', street = 'Narnia', create_time = '2017-06-25', end_time = '2018-10-01' WHERE customer_id = 40;
UPDATE mafisoftBD.customer SET customer_firstname = 'Test', customer_lastname = 'Sasd', birthday = '2012-12-12 00:00:00', email = '', mobilephone = '', zipCode = 12345, city = 'Dortmund', street = 'Wallstreet 12', create_time = '2017-06-26', end_time = '2017-12-02' WHERE customer_id = 42;
UPDATE mafisoftBD.customer_course SET customer_id = 11, course_id = 1 WHERE id = 13;
UPDATE mafisoftBD.customer_course SET customer_id = 11, course_id = 2 WHERE id = 14;
UPDATE mafisoftBD.customer_course SET customer_id = 1, course_id = 2 WHERE id = 16;
UPDATE mafisoftBD.customer_course SET customer_id = 13, course_id = 2 WHERE id = 18;
UPDATE mafisoftBD.customer_course SET customer_id = 13, course_id = 3 WHERE id = 21;
UPDATE mafisoftBD.customer_course SET customer_id = 11, course_id = 3 WHERE id = 22;
UPDATE mafisoftBD.video_course SET courseName = 'Joga für anfänger', trainerName = 'Ali', link = 'jogahome.example/video=12345', remark = '' WHERE videoID = 1;
UPDATE mafisoftBD.video_course SET courseName = 'Joga für profi', trainerName = 'Ali', link = 'jogahome.example/video=54321', remark = 'Hallo

ich teste 

wie weit es

gehen kann' WHERE videoID = 2;
UPDATE mafisoftBD.video_course SET courseName = 'Best video training', trainerName = 'Undertaker', link = 'youtube.com', remark = 'The best video course ever
Undertaker as most popular WWE master' WHERE videoID = 28;