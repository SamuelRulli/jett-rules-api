CREATE TABLE jt_score_ranking (id INT AUTO_INCREMENT PRIMARY KEY
                              , userid VARCHAR(100)
                              , google_rate INT
                              , clock_in_on_time BOOLEAN
                              , clock_out_on_time BOOLEAN
                              , booking_date DATE);