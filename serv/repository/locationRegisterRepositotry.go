package repository

import (
	"AFootGolf9/TheRastreator/entity"
	"database/sql"
)

var db *sql.DB

func SetDB(database *sql.DB) {
	db = database
}

func InsertLocationRegister(latitude float64, longitude float64) {
	_, err := db.Exec("INSERT INTO position (lat, long) VALUES ($1, $2)", latitude, longitude)
	if err != nil {
		panic(err)
	}
}

func GetLocationRegisters() []*entity.LocationRegister {
	rows, err := db.Query("SELECT * FROM position")
	if err != nil {
		panic(err)
	}
	defer rows.Close()

	locations := make([]*entity.LocationRegister, 0)
	for rows.Next() {
		var t string
		var lat float64
		var long float64
		err := rows.Scan(&t, &lat, &long)
		if err != nil {
			panic(err)
		}
		location := entity.NewLocationRegister(t, lat, long)
		locations = append(locations, location)
	}

	return locations
}
