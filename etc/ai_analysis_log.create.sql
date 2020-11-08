CREATE TABLE 'ai_analysis_log' (
'id' INTEGER PRIMARY KEY AUTOINCREMENT,
'image_path' TEXT,
'success' TEXT,
'message' TEXT,
'class' INTEGER,
'confidence' REAL,
'request_timestamp' INTEGER,
'response_timestamp' INTEGER
);