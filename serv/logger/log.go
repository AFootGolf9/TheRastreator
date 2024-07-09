package logger

import (
	"database/sql"
	"fmt"
	"log"
)

var db *sql.DB

func SetDB(database *sql.DB) {
	db = database
}

func Log(log_msg string) {
	_, err := db.Exec("INSERT INTO log (log) VALUES ($1)", log_msg)
	if err != nil {
		log.Fatal(err)
	}
	fmt.Println("Log inserted successfully!")
}
