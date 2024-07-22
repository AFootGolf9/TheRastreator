package repository

import (
	"AFootGolf9/TheRastreator/entity"
)

func InsertLocationRegister(client_id int, latitude float64, longitude float64) {
	_, err := db.Exec("INSERT INTO position (client_id, lat, long) VALUES ($1, $2, $3)",
		client_id, latitude, longitude)
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
		var c int
		var lat float64
		var long float64
		err := rows.Scan(&t, &c, &lat, &long)
		if err != nil {
			panic(err)
		}
		location := entity.NewLocationRegister(t, c, lat, long)
		locations = append(locations, location)
	}

	return locations
}

func GetLocationRegisterById(id int) []*entity.LocationRegister {
	rows, err := db.Query("SELECT * FROM position WHERE id = $1", id)
	if err != nil {
		panic(err)
	}
	defer rows.Close()

	locations := make([]*entity.LocationRegister, 0)

	for rows.Next() {
		var t string
		var c int
		var lat float64
		var long float64
		err := rows.Scan(&t, &c, &lat, &long)
		if err != nil {
			panic(err)
		}

		location := entity.NewLocationRegister(t, c, lat, long)
		locations = append(locations, location)
	}

	if len(locations) == 0 {
		return nil
	}

	return locations
}
