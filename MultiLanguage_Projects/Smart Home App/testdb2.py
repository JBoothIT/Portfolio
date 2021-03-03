<<<<<<< HEAD
#!/usr/bin/python
import psycopg2

conn = psycopg2.connect(database="Team2DB", user = "Team2", password = "2Team2", host = "164.111.161.243", port = "5432")

cursor = conn.cursor()

cursor.execute("""SELECT table_name FROM information_schema.tables
       WHERE table_schema = 'public'""")
for table in cursor.fetchall():
    print(table)



##if __name__ == '__main__':
=======
#!/usr/bin/python
import psycopg2

conn = psycopg2.connect(database="Team2DB", user = "Team2", password = "2Team2", host = "164.111.161.243", port = "5432")

cursor = conn.cursor()

cursor.execute("""SELECT table_name FROM information_schema.tables
       WHERE table_schema = 'public'""")
for table in cursor.fetchall():
    print(table)



##if __name__ == '__main__':
>>>>>>> 298a9f4b3d63edc2300a9f54db10a3318f776a41
   ## connect()